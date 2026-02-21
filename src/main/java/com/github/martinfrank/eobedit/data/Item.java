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
    public int nameId = -1;
    public int nameUnid = -1;
    public int rawType = -1;
    public int flags = 0;
    public int value = 0;
    public int armorClass = 0;
    public int dmgNumDiceS = 0;
    public int dmgNumPipsS = 0;
    public int dmgIncS = 0;
    public int dmgNumDiceL = 0;
    public int dmgNumPipsL = 0;
    public int dmgIncL = 0;

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
        this.classes = Items.ItemClass.values();
        this.description = description;
        this.details = description;
        this.iconIndex = iconIndex;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + Arrays.toString(id) +
                ", type=" + (type != null ? type.typeName : "null") +
                ", classes=" + Arrays.toString(classes) +
                ", description='" + description + '\'' +
                '}';
    }

    public String getId() {
        var prim = 0xFF & id[0];
        var sec = 0xFF & id[1];
        return (prim < 0x10 ? "0" : "") + Integer.toHexString(prim).toUpperCase(Locale.ROOT) +
                (sec < 0x10 ? "0" : "") + Integer.toHexString(sec).toUpperCase(Locale.ROOT);
    }

    public String getDetailString() {
        var sb = new StringBuilder();
        sb.append(description != null ? description : "Item " + getId());

        if ((flags & 0x20) != 0) sb.append(" (Cursed)");
        if ((flags & 0x40) == 0) sb.append(" (Unidentified)");

        if (value != 0 && isCombatItem()) {
            sb.append(value > 0 ? " +" : " ").append(value);
        }

        if (armorClass != 0) {
            sb.append(" [AC:").append(armorClass).append("]");
        } else if (dmgNumDiceS != 0) {
            sb.append(" [Dmg:").append(dmgNumDiceS).append("d").append(dmgNumPipsS);
            if (dmgIncS != 0) sb.append(dmgIncS > 0 ? "+" : "").append(dmgIncS);
            sb.append("]");
        }

        return sb.toString();
    }

    private boolean isCombatItem() {
        if (type == null) return false;
        var abbr = type.typeAbbreviation;
        return abbr.equals("PR") || abbr.equals("SE") || abbr.equals("TW") || abbr.equals("AR") || abbr.equals("SH") || abbr.equals("DA");
    }
}
