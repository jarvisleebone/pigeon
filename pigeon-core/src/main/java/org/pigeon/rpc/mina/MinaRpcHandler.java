package org.pigeon.rpc.mina;

import org.apache.log4j.Logger;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.ReadFuture;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
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
import java.util.concurrent.TimeUnit;

public class MinaRpcHandler extends RpcHandler {

    private static final Logger LOGGER = Logger.getLogger(MinaRpcHandler.class);

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
    public Object sendMessage(PigeonRequest request) {
        Router router = ConfigHandler.router;
        List<String> servers = RegisterHandler.services.get(request.getInterfaceName());
        String[] serverAddress = router.elect(servers).split(":");
        String ip = serverAddress[0];
        int port = Integer.parseInt(serverAddress[1]);

        Object result;
        // 创建客户端连接器
        NioSocketConnector connector = new NioSocketConnector();
//        connector.getFilterChain().addLast("logger", new LoggingFilter());
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
        // 设置连接超时检查时间
        connector.setConnectTimeoutCheckInterval(30);
        // 异步处理响应
//        connector.setHandler(new MinaClientHandler());
        // 使用同步连接
        connector.getSessionConfig().setUseReadOperation(true);
        // 建立连接
        ConnectFuture cf = connector.connect(new InetSocketAddress(ip, port));
        // 等待连接创建完成
        cf.awaitUninterruptibly();
        cf.getSession().write(request).awaitUninterruptibly();
        ReadFuture readFuture = cf.getSession().read();
        //判断传输超时否
        if(readFuture.awaitUninterruptibly(10, TimeUnit.SECONDS)){
            result = readFuture.getMessage();
        } else {
            result = "time out";
        }
        // 关闭连接
        cf.getSession().closeOnFlush();
        // 等待连接断开
        cf.getSession().getCloseFuture().awaitUninterruptibly();
        // 释放连接
        connector.dispose();

        return result;
    }
}
