package cn.abner.ascache;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Description for this class
 *
 * <p>
 *
 * @author: Abner Song
 * <p>
 * @date: 2024/6/12 20:37
 */
public class ASCacheHandler extends SimpleChannelInboundHandler<String> {
    private static final String CRLF = "\r\n";
    private static final String STR_PREFIX = "+";
    private static final String OK = STR_PREFIX + "OK" + CRLF;
    private static final String INFO = "ASCache server[v1.0.0], created by abner song." + CRLF +
                                       "Mock Redis Service, at 2024-06-12 in Hangzhou." + CRLF;

    @Override
    protected void channelRead0(ChannelHandlerContext chc,
                                String message) throws Exception {
        String[] args = message.split(CRLF);
        System.out.println("ASCacheHandler: " + String.join(",", args));
        String cmd = args[2].toUpperCase();
        if ("COMMAND".equals(cmd)) {
            writeByteBuf(chc, "*2" +
                    CRLF + "$7" +
                    CRLF + "COMMAND" +
                    CRLF + "$4" +
                    CRLF + "PING" +
                    CRLF);
        } else if ("PING".equals(cmd)) {
            String ret = "PONG";
            if (args.length > 5) {
                ret = args[4];
            }
            writeByteBuf(chc, "+" + ret + CRLF);
        } else if ("INFO".equals(cmd)) {
            writeByteBuf(chc, "$" + INFO.getBytes().length + CRLF + INFO + CRLF);
        } else {
            writeByteBuf(chc, OK);
        }
    }

    private void bulkString(ChannelHandlerContext chc, String content) {
        writeByteBuf(chc, "$" + content.getBytes().length + CRLF + content + CRLF);
    }

    private void simpleString(ChannelHandlerContext chc, String content) {
        writeByteBuf(chc, STR_PREFIX + content + CRLF);
    }

    private void writeByteBuf(ChannelHandlerContext chc, String content) {
        System.out.println("wrap byte buffer and reply: " + content);
        ByteBuf buf = Unpooled.buffer(128);
        buf.writeBytes(content.getBytes());
        chc.writeAndFlush(buf);
    }

}
