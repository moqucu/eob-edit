package com.github.martinfrank.eobedit.data;

import java.nio.charset.StandardCharsets;

public class ByteArrayTool {

    public static String asString(byte[] data) {
        var sb = new StringBuilder();
        for (var b : data) {
            if (b == 0) {
                break;
            }
            sb.append((char) (b & 0xFF));
        }
        return sb.toString();
    }

    public static byte[] copy(byte[] content, int offset, int length) {
        var data = new byte[length];
        System.arraycopy(content, offset, data, 0, length);
        return data;
    }

    public static void set(byte[] dest, byte[] src, int offset, int length) {
        var srcTrimmed = new byte[length];
        for (var i = 0; i < length; i++) {
            srcTrimmed[i] = i < src.length ? src[i] : 0;
        }
        System.arraycopy(srcTrimmed, 0, dest, offset, length);
    }

    public static byte asByte(String str) {
        return (byte) Integer.parseInt(str, 16);
    }

    public static int asInt(byte[] data) {
        var value = 0;
        for (var i = 0; i < data.length; i++) {
            var shift = i * 8;
            value = value + ((0xff & data[i]) << shift);
        }
        return value;
    }

    public static byte[] fromInt(int value, int size) {
        var data = new byte[size];
        for (var i = 0; i < size; i++) {
            var shift = i * 8;
            data[i] = (byte)(0xFF & (value >> shift));
        }
        return data;
    }
}
