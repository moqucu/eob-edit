package com.github.martinfrank.eobedit.pak;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class CpsDecoder {

    public static byte[] decodeCPS(byte[] src) {
        ByteBuffer bb = ByteBuffer.wrap(src).order(ByteOrder.LITTLE_ENDIAN);
        bb.getShort(); // skip type
        int compType = bb.get() & 0xFF;
        bb.get(); // skip
        int uncompressedSize = bb.getInt();
        int palSize = bb.getShort() & 0xFFFF;

        byte[] dst = new byte[uncompressedSize];
        int srcPos = 10 + palSize;

        if (compType == 4) {
            decodeFrame4(src, srcPos, dst);
        } else if (compType == 0) {
            System.arraycopy(src, srcPos, dst, 0, uncompressedSize);
        }
        return dst;
    }

    private static void decodeFrame4(byte[] src, int srcPos, byte[] dst) {
        int dstPos = 0;
        int dstSize = dst.length;

        while (dstPos < dstSize && srcPos < src.length) {
            int code = src[srcPos++] & 0xFF;
            if ((code & 0x80) == 0) {
                int len = ((code >> 4) & 0x07) + 3;
                int offs = ((code & 0x0F) << 8) | (src[srcPos++] & 0xFF);
                for (int i = 0; i < len && dstPos < dstSize; i++) {
                    dst[dstPos] = dst[dstPos - offs];
                    dstPos++;
                }
            } else if ((code & 0x40) != 0) {
                if (code == 0xFE) {
                    int len = (src[srcPos++] & 0xFF) | ((src[srcPos++] & 0xFF) << 8);
                    byte val = src[srcPos++];
                    for (int i = 0; i < len && dstPos < dstSize; i++) dst[dstPos++] = val;
                } else {
                    int len = (code & 0x3F) + 3;
                    if (code == 0xFF) {
                        len = (src[srcPos++] & 0xFF) | ((src[srcPos++] & 0xFF) << 8);
                    }
                    int offs = (src[srcPos++] & 0xFF) | ((src[srcPos++] & 0xFF) << 8);
                    for (int i = 0; i < len && dstPos < dstSize; i++) {
                        dst[dstPos] = dst[offs + i];
                        dstPos++;
                    }
                }
            } else if (code != 0x80) {
                int len = code & 0x3F;
                for (int i = 0; i < len && dstPos < dstSize; i++) dst[dstPos++] = src[srcPos++];
            } else {
                break;
            }
        }
    }
}
