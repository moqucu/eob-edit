package com.github.martinfrank.eobedit.pak;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class CpsDecoder {

    public static byte[] decodeCPS(byte[] src) {
        var bb = ByteBuffer.wrap(src).order(ByteOrder.LITTLE_ENDIAN);
        bb.getShort(); // skip type
        var compType = bb.get() & 0xFF;
        bb.get(); // skip
        var uncompressedSize = bb.getInt();
        var palSize = bb.getShort() & 0xFFFF;

        var dst = new byte[uncompressedSize];
        var srcPos = 10 + palSize;

        switch (compType) {
            case 4 -> decodeFrame4(src, srcPos, dst);
            case 0 -> System.arraycopy(src, srcPos, dst, 0, uncompressedSize);
            default -> {}
        }
        return dst;
    }

    private static void decodeFrame4(byte[] src, int srcPos, byte[] dst) {
        var dstPos = 0;
        var dstSize = dst.length;

        while (dstPos < dstSize && srcPos < src.length) {
            var code = src[srcPos++] & 0xFF;
            if ((code & 0x80) == 0) {
                var len = ((code >> 4) & 0x07) + 3;
                var offs = ((code & 0x0F) << 8) | (src[srcPos++] & 0xFF);
                for (var i = 0; i < len && dstPos < dstSize; i++) {
                    dst[dstPos] = dst[dstPos - offs];
                    dstPos++;
                }
            } else if ((code & 0x40) != 0) {
                if (code == 0xFE) {
                    var len = (src[srcPos++] & 0xFF) | ((src[srcPos++] & 0xFF) << 8);
                    var val = src[srcPos++];
                    for (var i = 0; i < len && dstPos < dstSize; i++) dst[dstPos++] = val;
                } else {
                    var len = (code & 0x3F) + 3;
                    if (code == 0xFF) {
                        len = (src[srcPos++] & 0xFF) | ((src[srcPos++] & 0xFF) << 8);
                    }
                    var offs = (src[srcPos++] & 0xFF) | ((src[srcPos++] & 0xFF) << 8);
                    for (var i = 0; i < len && dstPos < dstSize; i++) {
                        dst[dstPos] = dst[offs + i];
                        dstPos++;
                    }
                }
            } else if (code != 0x80) {
                var len = code & 0x3F;
                for (var i = 0; i < len && dstPos < dstSize; i++) dst[dstPos++] = src[srcPos++];
            } else {
                break;
            }
        }
    }
}
