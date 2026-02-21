package com.github.martinfrank.eobedit.pak;

import com.github.martinfrank.eobedit.data.Item;
import com.github.martinfrank.eobedit.data.Items;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class EobItemLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(EobItemLoader.class);

    private static final int ICON_SIZE = 16;
    private static final int ICONS_PER_ROW = 20;
    private static final int SCALE = 5;
    private static final int SHEET_WIDTH = 320;
    private static final int SHEET_HEIGHT = 200;

    private static final Items.ItemType[] TYPE_MAP = {
            Items.ItemType.ITEM,       // 0
            Items.ItemType.DAGGER,     // 1
            Items.ItemType.PRIMARY,    // 2
            Items.ItemType.SECONDARY,  // 3
            Items.ItemType.SHIELD,     // 4
            Items.ItemType.TWOHANDED,  // 5
            Items.ItemType.RING,       // 6
            Items.ItemType.AMULETT,    // 7
            Items.ItemType.ARMOR,      // 8
            Items.ItemType.BRACER,     // 9
            Items.ItemType.HELMET,     // 10
            Items.ItemType.BOOTS,      // 11
            Items.ItemType.AMMO,       // 12
            Items.ItemType.SCROLL,     // 13
            Items.ItemType.POTION,     // 14
            Items.ItemType.RANGED,     // 15
            Items.ItemType.FOOD,       // 16
            Items.ItemType.WAND        // 17
    };

    public static class LoadResult {
        public final Item[] items;
        public final BufferedImage[] icons;
        public final String[] nameTable;

        public LoadResult(Item[] items, BufferedImage[] icons, String[] nameTable) {
            this.items = items;
            this.icons = icons;
            this.nameTable = nameTable;
        }
    }

    private static class ItemTypeRecord {
        int armorClass;
        int dmgNumDiceS, dmgNumPipsS, dmgIncS;
        int dmgNumDiceL, dmgNumPipsL, dmgIncL;
    }

    public static LoadResult loadItems(File gameDir) throws IOException {
        byte[] itemDat = PakReader.findInDirectory(gameDir, "ITEM.DAT");
        if (itemDat == null) {
            throw new IOException("ITEM.DAT not found in game directory: " + gameDir);
        }

        ByteBuffer bb = ByteBuffer.wrap(itemDat).order(ByteOrder.LITTLE_ENDIAN);
        int numItems = bb.getShort() & 0xFFFF;

        int[] nameUnid = new int[numItems];
        int[] nameId = new int[numItems];
        int[] flags = new int[numItems];
        int[] icons = new int[numItems];
        int[] types = new int[numItems];
        int[] values = new int[numItems];

        for (int i = 0; i < numItems; i++) {
            nameUnid[i] = bb.get() & 0xFF;
            nameId[i] = bb.get() & 0xFF;
            flags[i] = bb.get() & 0xFF;
            icons[i] = bb.get() & 0xFF;
            types[i] = bb.get() & 0xFF;
            bb.get(); // pos
            bb.getShort(); // block
            bb.getShort(); // next
            bb.getShort(); // prev
            bb.get(); // level
            values[i] = bb.get() & 0xFF;
        }

        int numNames = bb.getShort() & 0xFFFF;
        String[] names = new String[numNames];
        for (int i = 0; i < numNames; i++) {
            byte[] nameBytes = new byte[35];
            bb.get(nameBytes);
            names[i] = new String(nameBytes, StandardCharsets.US_ASCII).trim();
        }

        ItemTypeRecord[] typeTable = loadItemTypes(gameDir);

        Item[] items = new Item[numItems];
        for (int i = 0; i < numItems; i++) {
            String nameIdentified = (nameId[i] > 0 && nameId[i] < numNames) ? names[nameId[i]] : null;
            String nameGeneric = (nameUnid[i] < numNames) ? names[nameUnid[i]] : "Unknown";
            String description = (nameIdentified != null && !nameIdentified.isEmpty()) ? nameIdentified : nameGeneric;
            
            Items.ItemType type = (types[i] < TYPE_MAP.length) ? TYPE_MAP[types[i]] : Items.ItemType.ITEM;
            items[i] = new Item(i, description, type, icons[i]);
            items[i].nameId = nameId[i];
            items[i].nameUnid = nameUnid[i];
            items[i].rawType = types[i];
            items[i].flags = flags[i];
            items[i].value = values[i];

            if (typeTable != null && types[i] >= 0 && types[i] < typeTable.length) {
                ItemTypeRecord tr = typeTable[types[i]];
                items[i].armorClass = tr.armorClass;
                items[i].dmgNumDiceS = tr.dmgNumDiceS;
                items[i].dmgNumPipsS = tr.dmgNumPipsS;
                items[i].dmgIncS = tr.dmgIncS;
                items[i].dmgNumDiceL = tr.dmgNumDiceL;
                items[i].dmgNumPipsL = tr.dmgNumPipsL;
                items[i].dmgIncL = tr.dmgIncL;
            }
        }

        LOGGER.info("Loaded {} items from ITEM.DAT", numItems);

        BufferedImage[] iconImages = loadIcons(gameDir);

        return new LoadResult(items, iconImages, names);
    }

    private static ItemTypeRecord[] loadItemTypes(File gameDir) throws IOException {
        byte[] typeDat = PakReader.findInDirectory(gameDir, "ITEMTYPE.DAT");
        if (typeDat == null) {
            LOGGER.warn("ITEMTYPE.DAT not found, detailed item properties will be missing");
            return null;
        }

        ByteBuffer bb = ByteBuffer.wrap(typeDat).order(ByteOrder.LITTLE_ENDIAN);
        int numTypes = bb.getShort() & 0xFFFF;
        ItemTypeRecord[] records = new ItemTypeRecord[numTypes];

        for (int i = 0; i < numTypes; i++) {
            ItemTypeRecord r = new ItemTypeRecord();
            bb.getShort(); // invFlags
            bb.getShort(); // handFlags
            r.armorClass = bb.get();
            bb.get(); // allowedClasses
            bb.get(); // requiredHands
            r.dmgNumDiceS = bb.get();
            r.dmgNumPipsS = bb.get();
            r.dmgIncS = bb.get();
            r.dmgNumDiceL = bb.get();
            r.dmgNumPipsL = bb.get();
            r.dmgIncL = bb.get();
            bb.get(); // unk1
            bb.getShort(); // extraProperties
            records[i] = r;
        }
        return records;
    }

    private static BufferedImage[] loadIcons(File gameDir) throws IOException {
        byte[] palData = PakReader.findInDirectory(gameDir, "EOBPAL.COL");
        if (palData == null) {
            LOGGER.warn("EOBPAL.COL not found, icons will not be available");
            return null;
        }

        int[] palette = new int[256];
        for (int i = 0; i < 256; i++) {
            int r = (palData[i * 3] & 0xFF) << 2;
            int g = (palData[i * 3 + 1] & 0xFF) << 2;
            int b = (palData[i * 3 + 2] & 0xFF) << 2;
            palette[i] = (0xFF << 24) | (r << 16) | (g << 8) | b;
        }

        byte[] cpsData = PakReader.findInDirectory(gameDir, "ITEMICN.CPS");
        if (cpsData == null) {
            LOGGER.warn("ITEMICN.CPS not found, icons will not be available");
            return null;
        }

        byte[] decoded = CpsDecoder.decodeCPS(cpsData);

        int maxIcons = (SHEET_WIDTH / ICON_SIZE) * (SHEET_HEIGHT / ICON_SIZE);
        BufferedImage[] iconImages = new BufferedImage[maxIcons];

        for (int idx = 0; idx < maxIcons; idx++) {
            int x = (idx % ICONS_PER_ROW) * ICON_SIZE;
            int y = (idx / ICONS_PER_ROW) * ICON_SIZE;

            if (x + ICON_SIZE > SHEET_WIDTH || y + ICON_SIZE > SHEET_HEIGHT) continue;

            BufferedImage img = new BufferedImage(ICON_SIZE * SCALE, ICON_SIZE * SCALE, BufferedImage.TYPE_INT_ARGB);
            for (int iy = 0; iy < ICON_SIZE; iy++) {
                for (int ix = 0; ix < ICON_SIZE; ix++) {
                    int pixel = decoded[(y + iy) * SHEET_WIDTH + (x + ix)] & 0xFF;
                    int color = (pixel == 0) ? 0x00000000 : palette[pixel];
                    for (int sy = 0; sy < SCALE; sy++) {
                        for (int sx = 0; sx < SCALE; sx++) {
                            img.setRGB(ix * SCALE + sx, iy * SCALE + sy, color);
                        }
                    }
                }
            }
            iconImages[idx] = img;
        }

        LOGGER.info("Loaded {} icons from ITEMICN.CPS", maxIcons);
        return iconImages;
    }
}
