package com.github.martinfrank.eobedit.data;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.martinfrank.eobedit.pak.EobItemLoader;

public class Items {

    private static final Logger LOGGER = LoggerFactory.getLogger(Items.class);

    private static Item[] dynamicItems = null;
    private static BufferedImage[] dynamicIcons = null;
    private static String[] nameTable = null;

    public static class ItemType {

        public static final ItemType ITEM = new ItemType("IT", "Item", "any other item");
        public static final ItemType DAGGER = new ItemType("DA", "Dagger", "Dagger Stone or Dart");
        public static final ItemType PRIMARY = new ItemType("PR", "Primary", "Primary Weapon");
        public static final ItemType SECONDARY = new ItemType("SE", "Secondary", "Secondary Weapon");
        public static final ItemType SHIELD = new ItemType("SH", "Shield", "Shield");
        public static final ItemType TWOHANDED = new ItemType("TW", "TwoHanded", "Two handed Weapon");
        public static final ItemType RING = new ItemType("RI", "Ring", "Ring");
        public static final ItemType AMULETT = new ItemType("AT", "Amulett", "Amulett");
        public static final ItemType ARMOR = new ItemType("AR", "Armor", "Body Armor");
        public static final ItemType BRACER = new ItemType("BR", "Bracers", "Bracers or Arm Protections");
        public static final ItemType HELMET = new ItemType("HE", "Helmet", "Helmet");
        public static final ItemType BOOTS = new ItemType("BO", "Boots", "Boots or Foot wear");
        public static final ItemType AMMO = new ItemType("AM", "Ammuniton", "Arrow or Bolt");
        public static final ItemType SCROLL = new ItemType("SC", "Scroll", "Scroll");
        public static final ItemType POTION = new ItemType("PO", "Potion", "Potion");
        public static final ItemType RANGED = new ItemType("RA", "Ranged", "Ranged Weapon");
        public static final ItemType FOOD = new ItemType("FO", "Food", "Food and Rations");
        public static final ItemType WAND = new ItemType("WA", "Wand", "Wand or Staff");
        public static final ItemType[] TYPES = new ItemType[]{
            ITEM, DAGGER, PRIMARY, SECONDARY, SHIELD, TWOHANDED, RING, AMULETT, ARMOR, BRACER, HELMET, BOOTS, AMMO, SCROLL, POTION, RANGED, FOOD, WAND
        };

        public final String typeAbbreviation;
        public final String typeName;
        public final String typeDescription;

        public ItemType(String typeAbbreviation, String typeName, String typeDescription) {
            this.typeAbbreviation = typeAbbreviation;
            this.typeName = typeName;
            this.typeDescription = typeDescription;
        }

        @Override
        public String toString() {
            return typeName;
        }
    }

    public static class ItemClass {

        public static final ItemClass FIGHTER = new ItemClass("F", "Fighter");
        public static final ItemClass RANGER = new ItemClass("R", "Ranger");
        public static final ItemClass PALADIN = new ItemClass("P", "Paladin");
        public static final ItemClass MAGE = new ItemClass("M", "Mage");
        public static final ItemClass CLERIC = new ItemClass("C", "Cleric");
        public static final ItemClass THIEF = new ItemClass("T", "Thief");
        public static final ItemClass[] CLASSES = new ItemClass[]{
            FIGHTER, RANGER, PALADIN, MAGE, CLERIC, THIEF
        };

        public final String classAbbreviation;
        public final String className;

        public ItemClass(String classAbbreviation, String className) {
            this.classAbbreviation = classAbbreviation;
            this.className = className;
        }

        @Override
        public String toString() {
            return className;
        }
    }

    public static ItemType getType(String s) {
        for (ItemType type : ItemType.TYPES) {
            if (type.typeAbbreviation.equalsIgnoreCase(s)) {
                return type;
            }
        }
        throw new IllegalArgumentException("unknown type " + s);
    }

    public static ItemClass getClass(String s) {
        for (ItemClass clazz : ItemClass.CLASSES) {
            if (clazz.classAbbreviation.equalsIgnoreCase(s)) {
                return clazz;
            }
        }
        throw new IllegalArgumentException("unknown clazz " + s);
    }

    public static ItemClass[] getClasses(String str) {
        ItemClass[] classes = new ItemClass[str.length()];
        for (int i = 0; i < str.length(); i++) {
            classes[i] = getClass("" + str.charAt(i));
        }
        return classes;
    }

    public static String getItemNameString(int nameIndex) {
        if (nameTable != null && nameIndex >= 0 && nameIndex < nameTable.length) {
            return nameTable[nameIndex];
        }
        return null;
    }

    public static Item getItem(byte[] data) {
        if (dynamicItems == null) {
            return null;
        }
        int index = (data[0] & 0xFF) | ((data[1] & 0xFF) << 8);
        if (index >= 0 && index < dynamicItems.length) {
            return dynamicItems[index];
        }
        return null;
    }

    public static Item[] getAllItems() {
        return dynamicItems != null ? dynamicItems : new Item[0];
    }

    public static void loadFromGameData(File gameDir) throws IOException {
        EobItemLoader.LoadResult result = EobItemLoader.loadItems(gameDir);
        dynamicItems = result.items;
        dynamicIcons = result.icons;
        nameTable = result.nameTable;
        LOGGER.info("Loaded {} items from game data", dynamicItems.length);
    }

    public static BufferedImage getIcon(int iconIndex) {
        if (dynamicIcons != null && iconIndex >= 0 && iconIndex < dynamicIcons.length) {
            return dynamicIcons[iconIndex];
        }
        return null;
    }

    public static boolean isUsingGameData() {
        return dynamicItems != null;
    }

    public static int getItemCount() {
        return dynamicItems != null ? dynamicItems.length : 0;
    }
}
