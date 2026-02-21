package com.github.martinfrank.eobedit.data;

public class GlobalItem {
    public int nameUnid, nameId, flags, icon, type;
    public int pos, block, next, prev, level, value;
    public String displayName;

    public boolean isEmpty() { return nameUnid == 0 && nameId == 0; }
    public boolean isCarried() { return (level & 0xFF) == 0xFF; }

    @Override
    public String toString() { return isEmpty() ? "(empty)" : displayName; }
}
