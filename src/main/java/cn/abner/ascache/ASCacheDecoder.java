package cn.abner.ascache;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Description for this class
 *
 * <p>
 *
 * @author: Abner Song
 * <p>
 * @date: 2024/6/12 20:46
 */
public class ASCacheDecoder extends ByteToMessageDecoder {
    AtomicLong counter = new AtomicLong(0);

    @Override
    protected void decode(ChannelHandlerContext chc,
                          ByteBuf in, List<Object> out) throws Exception {
        System.out.println("ASCacheDecoder decode count:" + counter.incrementAndGet());
        if (in.readableBytes() <= 0) {
            return;
        }
        int count = in.readableBytes();
        int index = in.readerIndex();
        System.out.println("count: " + count + " ,index: " + index);

        byte[] bytes = new byte[count];
        in.readBytes(bytes);
        String ret = new String(bytes);
        System.out.println("ret: " + ret);

        out.add(ret);
    }
}
