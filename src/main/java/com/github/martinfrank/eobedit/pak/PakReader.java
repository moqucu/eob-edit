package com.github.martinfrank.eobedit.pak;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

public class PakReader {

    public static byte[] extractFile(File pakFile, String targetName) throws IOException {
        try (var raf = new RandomAccessFile(pakFile, "r")) {
            var filesize = raf.length();
            var startOffset = readIntLE(raf) & 0xFFFFFFFFL;
            while (raf.getFilePointer() < startOffset) {
                var fileName = readNullTerminatedString(raf);
                if (fileName.isEmpty()) break;
                var nextEntryOffset = readIntLE(raf) & 0xFFFFFFFFL;
                var endOffset = nextEntryOffset;
                if (endOffset == 0 || endOffset > filesize || (endOffset < startOffset && endOffset != 0)) {
                    endOffset = filesize;
                }
                if (fileName.equalsIgnoreCase(targetName)) {
                    var data = new byte[(int) (endOffset - startOffset)];
                    raf.seek(startOffset);
                    raf.readFully(data);
                    return data;
                }
                if (endOffset == filesize || nextEntryOffset == 0) break;
                startOffset = endOffset;
            }
        }
        return null;
    }

    public static byte[] findInDirectory(File gameDir, String targetName) throws IOException {
        var pakFiles = gameDir.listFiles((d, name) -> name.toUpperCase().endsWith(".PAK"));
        if (pakFiles == null) return null;
        for (var f : pakFiles) {
            var data = extractFile(f, targetName);
            if (data != null) return data;
        }
        return null;
    }

    private static int readIntLE(RandomAccessFile raf) throws IOException {
        int b1 = raf.read();
        int b2 = raf.read();
        int b3 = raf.read();
        int b4 = raf.read();
        return (b4 << 24) | (b3 << 16) | (b2 << 8) | b1;
    }

    private static String readNullTerminatedString(RandomAccessFile raf) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int b;
        while ((b = raf.read()) > 0) baos.write(b);
        return baos.toString(StandardCharsets.US_ASCII.name());
    }
}
