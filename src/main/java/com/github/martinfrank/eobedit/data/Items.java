package com.github.martinfrank.eobedit.data;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.martinfrank.eobedit.pak.EobItemLoader;

public class Items {

    private static final Logger LOGGER = LoggerFactory.getLogger(Items.class);

    private static Item[] dynamicItems = null;
    private static BufferedImage[] dynamicIcons = null;
    private static String[] nameTable = null;

    public enum ItemType {
        ITEM("IT", "Item", "any other item"),
        DAGGER("DA", "Dagger", "Dagger Stone or Dart"),
        PRIMARY("PR", "Primary", "Primary Weapon"),
        SECONDARY("SE", "Secondary", "Secondary Weapon"),
        SHIELD("SH", "Shield", "Shield"),
        TWOHANDED("TW", "TwoHanded", "Two handed Weapon"),
        RING("RI", "Ring", "Ring"),
        AMULETT("AT", "Amulett", "Amulett"),
        ARMOR("AR", "Armor", "Body Armor"),
        BRACER("BR", "Bracers", "Bracers or Arm Protections"),
        HELMET("HE", "Helmet", "Helmet"),
        BOOTS("BO", "Boots", "Boots or Foot wear"),
        AMMO("AM", "Ammuniton", "Arrow or Bolt"),
        SCROLL("SC", "Scroll", "Scroll"),
        POTION("PO", "Potion", "Potion"),
        RANGED("RA", "Ranged", "Ranged Weapon"),
        FOOD("FO", "Food", "Food and Rations"),
        WAND("WA", "Wand", "Wand or Staff");

        public final String typeAbbreviation;
        public final String typeName;
        public final String typeDescription;

        ItemType(String typeAbbreviation, String typeName, String typeDescription) {
            this.typeAbbreviation = typeAbbreviation;
            this.typeName = typeName;
            this.typeDescription = typeDescription;
        }

        @Override
        public String toString() {
            return typeName;
        }

        public static ItemType fromAbbreviation(String abbr) {
            return Arrays.stream(values())
                    .filter(t -> t.typeAbbreviation.equalsIgnoreCase(abbr))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Unknown type: " + abbr));
        }
    }

    public enum ItemClass {
        FIGHTER("F", "Fighter"),
        RANGER("R", "Ranger"),
        PALADIN("P", "Paladin"),
        MAGE("M", "Mage"),
        CLERIC("C", "Cleric"),
        THIEF("T", "Thief");

        public final String classAbbreviation;
        public final String className;

        ItemClass(String classAbbreviation, String className) {
            this.classAbbreviation = classAbbreviation;
            this.className = className;
        }

        @Override
        public String toString() {
            return className;
        }

        public static ItemClass fromAbbreviation(String abbr) {
            return Arrays.stream(values())
                    .filter(c -> c.classAbbreviation.equalsIgnoreCase(abbr))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Unknown class: " + abbr));
        }
    }

    public static ItemType getType(String s) {
        return ItemType.fromAbbreviation(s);
    }

    public static ItemClass getClass(String s) {
        return ItemClass.fromAbbreviation(s);
    }

    public static ItemClass[] getClasses(String str) {
        return str.chars()
                .mapToObj(c -> getClass(String.valueOf((char) c)))
                .toArray(ItemClass[]::new);
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
        var result = EobItemLoader.loadItems(gameDir);
        dynamicItems = result.items();
        dynamicIcons = result.icons();
        nameTable = result.nameTable();
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
