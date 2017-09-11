package org.pigeon.rpc.mina;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.pigeon.codec.SerializerFactory;
import org.pigeon.config.PigeonConfig;
import org.pigeon.model.PigeonRequest;
import org.pigeon.rpc.RpcHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class MinaRpcHandler extends RpcHandler {

    private static final Logger LOGGER = Logger.getLogger(MinaRpcHandler.class);

    @Override
    public void bindService(PigeonConfig pigeonConfig) {
        try {
            // 创建服务端监控线程
            IoAcceptor acceptor = new NioSocketAcceptor();
            acceptor.getSessionConfig().setReadBufferSize(2048);
            acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
            // 设置日志记录器
            // acceptor.getFilterChain().addLast("logger", new LoggingFilter());
            // 设置编码过滤器
            // acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
            // 指定业务逻辑处理器
            acceptor.setHandler(new MinaServerHandler(SerializerFactory.getSerializer(pigeonConfig.getSerializer())));
            // 设置端口号
            acceptor.bind(new InetSocketAddress(pigeonConfig.getPort()));
            // 启动监听线程
            acceptor.bind();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object sendMessage(PigeonRequest request) {





        return null;
    }
}
