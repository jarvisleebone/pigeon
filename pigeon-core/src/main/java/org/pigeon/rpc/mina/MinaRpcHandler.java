package org.pigeon.rpc.mina;

import org.apache.log4j.Logger;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.ReadFuture;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.pigeon.config.handler.ConfigHandler;
import org.pigeon.model.PigeonRequest;
import org.pigeon.registry.RegisterHandler;
import org.pigeon.router.Router;
import org.pigeon.rpc.RpcHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class MinaRpcHandler extends RpcHandler {

    private static final Logger LOGGER = Logger.getLogger(MinaRpcHandler.class);

    // 客户端持有的与服务端的连接
    private static Map<String, Map<String, ConnectFuture>> minaConnections = new ConcurrentHashMap<>();

    @Override
    public void bindService(int port) {
        try {
            // 创建服务端监控线程
            IoAcceptor acceptor = new NioSocketAcceptor();
            acceptor.getSessionConfig().setReadBufferSize(2048);
            acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
            // 设置日志记录器
//             acceptor.getFilterChain().addLast("logger", new LoggingFilter());
            // 设置编码过滤器
            acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
            // 指定业务逻辑处理器
            acceptor.setHandler(new MinaServerHandler());
            // 设置端口号
            acceptor.bind(new InetSocketAddress(port));
            // 启动监听线程
            acceptor.bind();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object sendMessageSync(PigeonRequest request) throws Exception {
        Router router = ConfigHandler.router;
        List<String> servers = RegisterHandler.services.get(request.getInterfaceName());
        String serverAddress = router.elect(servers);
        String[] ipPort = serverAddress.split(":");
        String ip = ipPort[0];
        int port = Integer.parseInt(ipPort[1]);

        if (null == minaConnections.get(serverAddress)) {
            synchronized (minaConnections) {
                if (null == minaConnections.get(serverAddress)) {
                    minaConnections.put(serverAddress, new ConcurrentHashMap<>());
                }
            }
        }

        ConnectFuture cf = minaConnections.get(serverAddress).get("sync");
        if (null == cf) {
            synchronized (MinaRpcHandler.class) {
                if (null == cf) {
                    // 创建客户端连接器
                    NioSocketConnector connector = new NioSocketConnector();
                    connector.getFilterChain().addLast("logger", new LoggingFilter());
                    // TODO 需要根据配置决定序列化实现
                    connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
                    // 设置连接超时检查时间
                    connector.setConnectTimeoutCheckInterval(30);
                    // 同步连接
                    if (request.isSync()) connector.getSessionConfig().setUseReadOperation(true);
                        // 异步连接
                    else connector.setHandler(new MinaClientHandler());
                    // 创建连接
                    cf = connector.connect(new InetSocketAddress(ip, port));
                    // 等待连接创建完成
                    cf.awaitUninterruptibly();
                    minaConnections.get(serverAddress).put("sync", cf);
                }
            }
        }

        cf.getSession().write(request).awaitUninterruptibly();
        ReadFuture readFuture = cf.getSession().read();
        Object result;
        //判断传输是否超时
        if (readFuture.awaitUninterruptibly(10, TimeUnit.SECONDS)) result = readFuture.getMessage();
        else throw new Exception("time out");

        return result;
    }

    @Override
    public void sendMessageAsync(PigeonRequest request) throws Exception {

    }
}
