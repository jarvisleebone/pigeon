package org.pigeon.rpc.mina;

import org.apache.log4j.Logger;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.ReadFuture;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.executor.UnorderedThreadPoolExecutor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.pigeon.common.NamedThreadFactory;
import org.pigeon.common.ServerRejectedExecutorHandler;
import org.pigeon.model.PigeonRequest;
import org.pigeon.rpc.RpcHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MinaRpcHandler extends RpcHandler {

    private static final Logger LOGGER = Logger.getLogger(MinaRpcHandler.class);

    /**
     * 客户端持有的与服务端的连接
     */
    private static final Map<String, Map<String, ConnectFuture>> CONNECTIONS = new ConcurrentHashMap<>();

    @Override
    public void bindService(int port) {
        try {
            // 创建服务端监控线程
            IoAcceptor acceptor = new NioSocketAcceptor(5);
            acceptor.getSessionConfig().setReadBufferSize(2048);
            acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
            // 设置编码过滤器
            acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
            // 配置ThreadPoolExecutor
            BlockingQueue<Runnable> requestQueue = new LinkedBlockingDeque<>();
            RejectedExecutionHandler rejectedExecutionHandler = new ServerRejectedExecutorHandler();
            ThreadPoolExecutor processPoolExecutor = new ThreadPoolExecutor(
                    512,
                    512,
                    0L,
                    TimeUnit.MILLISECONDS,
                    requestQueue,
                    new NamedThreadFactory("processThread", true),
                    rejectedExecutionHandler);
            // 指定业务逻辑处理器
            acceptor.setHandler(new MinaServerHandler(processPoolExecutor));
            // 设置端口号
            acceptor.bind(new InetSocketAddress(port));
            // 启动监听线程
            acceptor.bind();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object sendMessageSync(PigeonRequest request, String serverAddress) throws Exception {
        // 初始化连接
        initConn(request.isSync(), serverAddress);
        // 获取连接
        ConnectFuture cf = CONNECTIONS.get(serverAddress).get("sync");
        // 请求发送到服务端
        if ("void".equals(request.getReturnType())) {
            // 接口无返回值无需等待发送完成，直接返回null
            cf.getSession().write(request);
            return null;
        } else {
            // 等待发送完成
            cf.getSession().write(request).awaitUninterruptibly();
            // 读取接口返回值
            ReadFuture readFuture = cf.getSession().read();
            // 是否超时
            if (readFuture.awaitUninterruptibly(10, TimeUnit.SECONDS)) {
                return readFuture.getMessage();
            } else {
                throw new Exception("time out");
            }
        }
    }

    @Override
    public void sendMessageAsync(PigeonRequest request, String serverAddress) {
        // 初始化连接
        initConn(request.isSync(), serverAddress);
        // 获取连接
        ConnectFuture cf = CONNECTIONS.get(serverAddress).get("async");
        // 请求发送到服务端
        cf.getSession().write(request);
    }

    /**
     * 初始化连接，如果当前客户端未持有对该服务端的连接，则创建一个长连接
     *
     * @param isSync
     * @param serverAddress
     */
    private void initConn(boolean isSync, String serverAddress) {
        if (null == CONNECTIONS.get(serverAddress)) {
            synchronized (CONNECTIONS) {
                CONNECTIONS.computeIfAbsent(serverAddress, k -> new HashMap<>());
            }
        }

        String connType = isSync ? "sync" : "async";
        ConnectFuture cf = CONNECTIONS.get(serverAddress).get(connType);
        if (null == cf) {
            synchronized (CONNECTIONS) {
                String[] ipPort = serverAddress.split(":");
                // 创建客户端连接器
                NioSocketConnector connector = new NioSocketConnector(5);
                connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
                connector.getFilterChain().addLast("exec", new ExecutorFilter(new UnorderedThreadPoolExecutor()));
                // 设置连接超时检查时间
                connector.setConnectTimeoutCheckInterval(30);
                if (isSync) {
                    // 同步连接
                    connector.getSessionConfig().setUseReadOperation(true);
                } else {
                    // 异步连接
                    connector.setHandler(new MinaClientHandler());
                }
                // 创建连接
                cf = connector.connect(new InetSocketAddress(ipPort[0], Integer.parseInt(ipPort[1])));
                // 等待连接创建完成
                cf.awaitUninterruptibly();
                CONNECTIONS.get(serverAddress).put(connType, cf);
            }
        }
    }
}
