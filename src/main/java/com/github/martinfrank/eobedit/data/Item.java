package com.github.martinfrank.eobedit.data;

import java.util.Arrays;
import java.util.Locale;

public class Item {

    public final byte[] id = new byte[2];
    public final Items.ItemType type;
    public final Items.ItemClass[] classes;
    public final String description;
    public final String details;
    public int iconIndex = -1;

    public Item(String firstHex, String secondHex, Items.ItemType type, Items.ItemClass[] classes, String description, String details) {
        id[0] = ByteArrayTool.asByte(firstHex);
        id[1] = ByteArrayTool.asByte(secondHex);
        this.type = type;
        this.classes = classes;
        this.description = description;
        this.details = details;
    }

    public Item(int high, int low) {
        id[0] = (byte)high;
        id[1] = (byte)low;
        this.type = null;
        this.classes = null;
        this.description = null;
        this.details = null;
    }

    public Item(int index, String description, Items.ItemType type, int iconIndex) {
        id[0] = (byte)(index & 0xFF);
        id[1] = (byte)((index >> 8) & 0xFF);
        this.type = type;
        this.classes = Items.ItemClass.CLASSES;
        this.description = description;
        this.details = description;
        this.iconIndex = iconIndex;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + Arrays.toString(id) +
                ", type=" + (type != null ? type.typeDescription : "null") +
                ", classes=" + Arrays.toString(classes) +
                ", description='" + description + '\'' +
                '}';
    }

    public String getId() {
        int prim = 0xFF & id[0];
        int sec = 0xFF & id[1];
        return (prim < 0x10 ? "0" : "") + Integer.toHexString(prim).toUpperCase(Locale.ROOT) +
                (sec < 0x10 ? "0" : "") + Integer.toHexString(sec).toUpperCase(Locale.ROOT);
    }
}
