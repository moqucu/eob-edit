package com.github.martinfrank.eobedit.data;

import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Items {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(Items.class);
    
    public static class ItemType{
        
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
            ITEM,
            DAGGER,
            PRIMARY,
            SECONDARY,
            SHIELD,
            TWOHANDED,
            RING,
            AMULETT,
            ARMOR,
            BRACER,
            HELMET,
            BOOTS,
            AMMO,
            SCROLL,
            POTION,
            RANGED,
            FOOD,
            WAND
        };
        
        public final String typeAbbreviation;
        public final String typeName;
        public final String typeDescription;
        
        public ItemType(String typeAbbreviation, String typeName, String typeDescription){
            this.typeAbbreviation = typeAbbreviation;
            this.typeName = typeName;
            this.typeDescription = typeDescription;
        }
        
        @Override
        public String toString(){
            return typeName;
        }
        
    }
    public static class ItemClass{
        
        public static final ItemClass FIGHTER = new ItemClass("F", "Fighter");
        public static final ItemClass RANGER = new ItemClass("R", "Ranger");
        public static final ItemClass PALADIN = new ItemClass("P", "Paladin");
        public static final ItemClass MAGE = new ItemClass("M", "Mage");
        public static final ItemClass CLERIC = new ItemClass("C", "Cleric");
        public static final ItemClass THIEF = new ItemClass("T", "Thief");
        public static final ItemClass[] CLASSES = new ItemClass[]{ 
            FIGHTER,
            RANGER,
            PALADIN,
            MAGE,
            CLERIC,
            THIEF
        };
        
        public final String classAbbreviation;
        public final String className;
        
        public ItemClass(String classAbbreviation, String className){
            this.classAbbreviation = classAbbreviation;
            this.className = className;
        }
        
        @Override
        public String toString(){
            return className;
        }
        
    }
    
    public static final Item NOTHING_A = new Item("00", "00", getType("IT"), getClasses("FRPMCT"), "nothing", "nothing clears inventory space");
    public static final Item LEATHER_ARMOR_A = new Item("01", "00", getType("AR"), getClasses("FRPMCT"), "Leather Armor", "Leather Armor");
    public static final Item ROBE_A = new Item("02", "00", getType("AR"), getClasses("FRPMCT"), "Robe", "Robe");
    public static final Item STAFF_A = new Item("03", "00", getType("PR"), getClasses("FRPMCT"), "Staff", "Staff");
    public static final Item DAGGER_A = new Item("04", "00", getType("DA"), getClasses("FRPMCT"), "Dagger", "Dagger");
    public static final Item SHORT_SWORD_A = new Item("05", "00", getType("SE"), getClasses("FRPMCT"), "Short Sword", "Short Sword");
    public static final Item LOCK_PICKS_A = new Item("06", "00", getType("IT"), getClasses("FRPMCT"), "Lock Picks", "Lock Picks");
    public static final Item SPELL_BOOK_A = new Item("07", "00", getType("IT"), getClasses("FRPMCT"), "Spell Book", "Spell Book");
    public static final Item HOLY_SYMBOL_CLERIC_A = new Item("08", "00", getType("IT"), getClasses("FRPMCT"), "Cleric Holy Symbol", "Cleric Holy Symbol");
    public static final Item BOOTS_A = new Item("09", "00", getType("BO"), getClasses("FRPMCT"), "Boots", "Boots");
    public static final Item IRON_RATION_A = new Item("0A", "00", getType("FO"), getClasses("FRPMCT"), "Iron Rations", "Iron Rations");
    public static final Item NULL_A = new Item("0B", "00", getType("IT"), getClasses("FRPMCT"), "nothing", "nothing clears inventory space");
    public static final Item NULL_B = new Item("0C", "00", getType("IT"), getClasses("FRPMCT"), "nothing", "nothing clears inventory space");
    public static final Item NULL_C = new Item("0D", "00", getType("IT"), getClasses("FRPMCT"), "nothing", "nothing clears inventory space");
    public static final Item NULL_D = new Item("0E", "00", getType("IT"), getClasses("FRPMCT"), "nothing", "nothing clears inventory space");
    public static final Item NULL_E = new Item("0F", "00", getType("IT"), getClasses("FRPMCT"), "nothing", "nothing clears inventory space");
    public static final Item NULL_F = new Item("10", "00", getType("IT"), getClasses("FRPMCT"), "nothing", "nothing clears inventory space");
    public static final Item KEY_JEWELED_A = new Item("11", "00", getType("IT"), getClasses("FRPMCT"), "Jeweled Key", "Jeweled Key");
    public static final Item POTION_GIANT_STRENGTH_A = new Item("12", "00", getType("PO"), getClasses("FRPMCT"), "Red Potion", "Red Potion of Giant Strength");
    public static final Item GEM_BLUE_A = new Item("13", "00", getType("IT"), getClasses("FRPMC"), "Blue Gem", "Blue Gem");
    public static final Item KEY_SKULL_A = new Item("14", "00", getType("IT"), getClasses("FRPMC"), "Skull Key", "Skull Key");
    public static final Item WAND_OF_FROST_A = new Item("15", "00", getType("WA"), getClasses("FRPMCT"), "Wand of Frost", "Wand of Frost");
    public static final Item SCROLL_WEAKNESS = new Item("16", "00", getType("SC"), getClasses("FRPMCT"), "Scroll", "Scroll about Weakness");
    public static final Item RING_SUSTENANCE_A = new Item("17", "00", getType("RI"), getClasses("FRPMCT"), "Ring of Sustenance", "Ring of Sustenance");
    public static final Item RING_FEATHER_FALL_A = new Item("18", "00", getType("RI"), getClasses("FRPMCT"), "Ring of Feather Fall", "Ring of Feather Fall");
    public static final Item RING_PROTECTION_A = new Item("19", "00", getType("RI"), getClasses("FRPMCT"), "Ring of Protection", "Ring of Protection +2");
    public static final Item ADAMATITE_DART_A = new Item("1A", "00", getType("DA"), getClasses("FRPMCT"), "Adamatite Dart", "Adamatite Dart +5");
    public static final Item SCROLL_STARS = new Item("1B", "00", getType("SC"), getClasses("FRPMCT"), "Scroll", "Scroll about Light of Stars");
    public static final Item SCROLL_RIDDLE_A = new Item("1C", "00", getType("SC"), getClasses("FRPMCT"), "Scroll", "Scroll with Riddle");
    public static final Item SCROLL_ORB_A = new Item("1D", "00", getType("SC"), getClasses("FRPMCT"), "Scroll", "Scroll warning about Orb");
    public static final Item IRON_RATION_B = new Item("1E", "00", getType("FO"), getClasses("FRPMCT"), "Iron Rations", "Iron Rations");
    public static final Item BONES_DWARF_A = new Item("1F", "00", getType("IT"), getClasses("FRPMCT"), "Dwarf Bones", "Dwarf Bones");
    public static final Item WAND_OF_SHIVAS = new Item("20", "00", getType("WA"), getClasses("FRPMCT"), "Wand of Shivas", "Wand of Shivas");
    public static final Item KEY_A = new Item("21", "00", getType("IT"), getClasses("FRPMCT"), "Key", "Key (golden)");
    public static final Item SCROLL_COMMISION_A = new Item("22", "00", getType("SC"), getClasses("FRPMCT"), "Scroll", "Commision and Letter of Marque");
    public static final Item NULL_G = new Item("23", "00", getType("IT"), getClasses("FRPMCT"), "nothing", "nothing clears inventory space");
    public static final Item AXE_A = new Item("24", "00", getType("PR"), getClasses("FRPMCT"), "Axe", "Axe");
    public static final Item DAGGER_B = new Item("25", "00", getType("DA"), getClasses("FRPMCT"), "Dagger", "Dagger");
    public static final Item DART_A = new Item("26", "00", getType("DA"), getClasses("FRPMCT"), "Dart", "Dart");
    public static final Item ADAMATITE_DART_B = new Item("27", "00", getType("DA"), getClasses("FRPMCT"), "Adamatite Dart", "Adamatite Dart +4");
    public static final Item HALBERD_A = new Item("28", "00", getType("TW"), getClasses("FRPMCT"), "Halberd", "Halberd");
    public static final Item CHAIN_MAIL_A = new Item("29", "00", getType("AR"), getClasses("FRPMCT"), "Chain Mail", "Chain Mail");
    public static final Item HELMET_A = new Item("2A", "00", getType("HE"), getClasses("FRPMCT"), "Helmet", "Helmet");
    public static final Item HELMET_DWARF_A = new Item("2B", "00", getType("HE"), getClasses("FRPMCT"), "Dwarf Helmet", "Dwarf Helmet");
    public static final Item KEY_SILVER_A = new Item("2C", "00", getType("IT"), getClasses("FRPMCT"), "Silver Key", "Silver Key");
    public static final Item ADAMTINE_LONG_SWORD_A = new Item("2D", "00", getType("PR"), getClasses("FRPMCT"), "Adamatite Long Sword", "Adamatite Long Sword +1");
    public static final Item MACE_A = new Item("2E", "00", getType("PR"), getClasses("FRPMCT"), "Mace", "Mace");
    public static final Item LONG_SWORD_ENCHANTED_A = new Item("2F", "00", getType("PR"), getClasses("FRPMCT"), "Long Sword", "Long Sword (enchanted)");
    public static final Item POTION_HEALING_A = new Item("30", "00", getType("PO"), getClasses("FRPMCT"), "Blue Potion", "Blue Potion of Healing");
    public static final Item GUINSOO = new Item("31", "00", getType("DA"), getClasses("FRPMCT"), "Dagger Guinsoo", "Dagger Guinsoo +4");
    public static final Item GEM_RED_A = new Item("32", "00", getType("IT"), getClasses("FRPMCT"), "Red Gem", "Red Gem");
    public static final Item ORB_POWER_A = new Item("33", "00", getType("IT"), getClasses("FRPMCT"), "Orb of Power", "Orb of Power");
    public static final Item POTION_DWARFEN_HEALING_A = new Item("34", "00", getType("PO"), getClasses("FRPMCT"), "Greeb Potion", "Green Potion of Dwarfen Healing");
    public static final Item IGNEOUS_ROCK_A = new Item("35", "00", getType("DA"), getClasses("FRPMCT"), "Igneous Rock", "Igneous Rock +1");
    public static final Item POTION_EXTRA_HEALING_A = new Item("36", "00", getType("PO"), getClasses("FRPMCT"), "Blue Potion", "Blue Potion of Extra Healing");
    public static final Item RATION_A = new Item("37", "00", getType("FO"), getClasses("FRPMCT"), "Ration", "Ration");
    public static final Item ROBE_FANCY_A = new Item("38", "00", getType("AR"), getClasses("FRPMCT"), "Fancy Robe", "Fancy Robe");
    public static final Item ROCK_A = new Item("39", "00", getType("DA"), getClasses("FRPMCT"), "Rock", "Rock");
    public static final Item IGNEOUS_ROCK_B = new Item("3A", "00", getType("DA"), getClasses("FRPMCT"), "Igneous Rock", "Igneous Rock +1");
    public static final Item SCROLL_MAGE_DETECT_MAGIC_A = new Item("3B", "00", getType("SC"), getClasses("FRPMCT"), "Mage Scroll", "Mage Scroll of Detect Magic");
    public static final Item SPEAR_A = new Item("3C", "00", getType("PR"), getClasses("FRPMCT"), "Spear", "Spear");
    public static final Item STAFF_B = new Item("3D", "00", getType("PR"), getClasses("FRPMCT"), "Staff", "Staff");
    public static final Item STONE_MEDALLION_A = new Item("3E", "00", getType("IT"), getClasses("FRPMCT"), "Stone Medallion", "Stone Medallion");
    public static final Item KEY_SILVER_B = new Item("3F", "00", getType("IT"), getClasses("FRPMCT"), "Silver Key", "Silver Key");
    public static final Item BONES_HALFLING_A = new Item("40", "00", getType("IT"), getClasses("FRPMCT"), "Halfling Bones", "Halfling Bones");
    public static final Item LOCK_PICKS_B = new Item("41", "00", getType("IT"), getClasses("FRPMCT"), "Lock Picks", "Lock Picks");
    public static final Item ROCK_B = new Item("42", "00", getType("DA"), getClasses("FRPMCT"), "Rock", "Rock");
    public static final Item DART_B = new Item("43", "00", getType("DA"), getClasses("FRPMCT"), "Dart", "Dart +2");
    public static final Item RATION_C = new Item("44", "00", getType("FO"), getClasses("FRPMCT"), "Ration", "Ration");
    public static final Item MACE_45 = new Item("45", "00", getType("PR"), getClasses("FRPMCT"), "Mace", "Mace");
    public static final Item SCROLL_CLERIC_BLESS_A = new Item("46", "00", getType("SC"), getClasses("FRPMCT"), "Cleric Scroll", "Cleric Scroll of Bless");
    public static final Item ROCK_C = new Item("47", "00", getType("DA"), getClasses("FRPMCT"), "Rock", "Rock");
    public static final Item RATION_B = new Item("48", "00", getType("FO"), getClasses("FRPMCT"), "Ration", "Ration");
    public static final Item ARROW_A = new Item("49", "00", getType("AM"), getClasses("FRPMCTC"), "Arrow", "Arrow");
    public static final Item SHIELD_A = new Item("4A", "00", getType("SH"), getClasses("FRPMCTC"), "Shield", "Shield");
    public static final Item ARROW_B = new Item("4B", "00", getType("AM"), getClasses("FRPMCTC"), "Arrow", "Arrow");
    public static final Item RATION_E = new Item("4C", "00", getType("FO"), getClasses("FRPMCT"), "Ration", "Ration");
    public static final Item RATION_F = new Item("4D", "00", getType("FO"), getClasses("FRPMCT"), "Ration", "Ration");
    public static final Item BOOTS_B = new Item("4E", "00", getType("BO"), getClasses("FRPMCT"), "Boots", "Boots");
    public static final Item POTION_HEALING_B = new Item("4F", "00", getType("PO"), getClasses("FRPMCT"), "Blue Potion", "Blue Potion of Healing");
    public static final Item HELMET_50 = new Item("50", "00", getType("HE"), getClasses("FRPMCT"), "Helmet", "Helmet");
    public static final Item HELMET_51 = new Item("51", "00", getType("HE"), getClasses("FRPMCT"), "Helmet", "Helmet");
    public static final Item MACE_B = new Item("52", "00", getType("PR"), getClasses("FRPMCT"), "Mace", "Mace");
    public static final Item ROCK_D = new Item("53", "00", getType("DA"), getClasses("FRPMCT"), "Rock", "Rock");
    public static final Item AXE_B = new Item("54", "00", getType("PR"), getClasses("FRPMCT"), "Axe", "Axe");
    public static final Item BOW_A = new Item("55", "00", getType("RA"), getClasses("FRPMCT"), "Bow", "Bow");
    public static final Item STONE_DAGGER_A = new Item("56", "00", getType("IT"), getClasses("FRPMCT"), "Stone Dagger", "Stone Dagger");
    public static final Item AXE_57 = new Item("57", "00", getType("PR"), getClasses("FRPMCT"), "Axe", "Axe");
    public static final Item MACE_58 = new Item("58", "00", getType("PR"), getClasses("FRPMCT"), "Mace", "Mace");
    public static final Item RATION_I = new Item("59", "00", getType("FO"), getClasses("FRPMCT"), "Ration", "Ration");
    public static final Item STAFF_5A = new Item("5A", "00", getType("PR"), getClasses("FRPMCT"), "Staff", "Staff");
    public static final Item SCROLL_MAGE_SHIELD_A = new Item("5B", "00", getType("SC"), getClasses("FRPMCTC"), "Mage Scroll", "Mage Scroll of Shield");
    public static final Item SLING_A = new Item("5C", "00", getType("RA"), getClasses("FRPMCTC"), "Sling", "Sling");
    public static final Item ARROW_C = new Item("5D", "00", getType("AM"), getClasses("FRPMCTC"), "Arrow", "Arrow");
    public static final Item AXE_5E = new Item("5E", "00", getType("PR"), getClasses("FRPMCT"), "Axe", "Axe");
    public static final Item RATION_D = new Item("5F", "00", getType("FO"), getClasses("FRPMCT"), "Ration", "Ration");
    public static final Item ROCK_E = new Item("60", "00", getType("DA"), getClasses("FRPMCT"), "Rock", "Rock");
    public static final Item CHAIN_MAIL_B = new Item("61", "00", getType("AR"), getClasses("FRPMCT"), "Chain Mail", "Chain Mail");
    public static final Item HELMET_DWARF_B = new Item("62", "00", getType("HE"), getClasses("FRPMCT"), "Dwarf Helmet", "Dwarf Helmet");
    public static final Item ARROW_D = new Item("63", "00", getType("AM"), getClasses("FRPMCTC"), "Arrow", "Arrow");
    public static final Item CHAIN_MAIL_C = new Item("64", "00", getType("AR"), getClasses("FRPMCT"), "Chain Mail", "Chain Mail");
    public static final Item SHIELD_B = new Item("65", "00", getType("SH"), getClasses("FRPMCTC"), "Shield", "Shield");
    public static final Item ARROW_E = new Item("66", "00", getType("AM"), getClasses("FRPMCTC"), "Arrow", "Arrow");
    public static final Item IRON_RATION_C = new Item("67", "00", getType("FO"), getClasses("FRPMCT"), "Iron Rations", "Iron Rations");
    public static final Item IRON_RATION_D = new Item("68", "00", getType("FO"), getClasses("FRPMCT"), "Iron Rations", "Iron Rations");
    public static final Item RATION_G = new Item("69", "00", getType("FO"), getClasses("FRPMCT"), "Ration", "Ration");
    public static final Item RATION_H = new Item("6A", "00", getType("FO"), getClasses("FRPMCT"), "Ration", "Ration");
    public static final Item RATION_J = new Item("6B", "00", getType("FO"), getClasses("FRPMCT"), "Ration", "Ration");
    public static final Item ARROW_F = new Item("6C", "00", getType("AM"), getClasses("FRPMCTC"), "Arrow", "Arrow");
    public static final Item POTION_HEALING_D = new Item("6D", "00", getType("PO"), getClasses("FRPMCT"), "Blue Potion", "Blue Potion of Healing");
    public static final Item POTION_EXTRA_HEALING_B = new Item("6E", "00", getType("PO"), getClasses("FRPMCT"), "Blue Potion", "Blue Potion of Healing");
    public static final Item RATION_K = new Item("6F", "00", getType("FO"), getClasses("FRPMCT"), "Ration", "Ration");
    public static final Item BACKSTABBER = new Item("70", "00", getType("DA"), getClasses("FRPMCT"), "Backstabber", "Dagger Backstabber +3");
    public static final Item RATION_L = new Item("71", "00", getType("FO"), getClasses("FRPMCT"), "Ration", "Ration");
    public static final Item SHIELD_C = new Item("72", "00", getType("SH"), getClasses("FRPMCTC"), "Shield", "Shield");
    public static final Item STONE_MEDALLION_73 = new Item("73", "00", getType("IT"), getClasses("FRPMCT"), "Stone Medallion", "Stone Medallion");
    public static final Item POTION_HEALING_E = new Item("74", "00", getType("PO"), getClasses("FRPMCT"), "Blue Potion", "Blue Potion of Healing");
    public static final Item ROCK_F = new Item("75", "00", getType("DA"), getClasses("FRPMCT"), "Rock", "Rock");
    public static final Item SCROLL_CLERIC_FLAME_BLADE_A = new Item("76", "00", getType("SC"), getClasses("FRPMCT"), "Cleric Scroll", "Cleric Scroll of Flame Blade");
    public static final Item ROCK_G = new Item("77", "00", getType("DA"), getClasses("FRPMCT"), "Rock", "Rock");
    public static final Item SCROLL_MAGE_FIREBALL_A = new Item("78", "00", getType("SC"), getClasses("FRPMCT"), "Mage Scroll", "Mage Scroll of Fireball");
    public static final Item SCROLL_CLERIC_LIGHT_WOUNDS_A = new Item("79", "00", getType("SC"), getClasses("FRPMCT"), "Cleric Scroll", "Cleric Scroll of Light Wounds");
    public static final Item GEM_RED_B = new Item("7A", "00", getType("IT"), getClasses("FRPMCT"), "Red Gem", "Red Gem");
    public static final Item ARROW_G = new Item("7B", "00", getType("AM"), getClasses("FRPMCTC"), "Arrow", "Arrow");
    public static final Item ROCK_H = new Item("7C", "00", getType("DA"), getClasses("FRPMCT"), "Rock", "Rock");
    public static final Item LONG_SWORD_A = new Item("7D", "00", getType("PR"), getClasses("FRPMCT"), "Long Sword", "Long Sword");
    public static final Item WAND_OF_MAGIC_MISSILE_A = new Item("7E", "00", getType("WA"), getClasses("FRPMCT"), "Wand of Magic Missile", "Wand of Magic Missile");
    public static final Item ARROW_H = new Item("7F", "00", getType("AM"), getClasses("FRPMCTC"), "Arrow", "Arrow");
    public static final Item MACE_C = new Item("80", "00", getType("PR"), getClasses("FRPMCT"), "Mace", "Mace");
    public static final Item RING_PROTECTION_B = new Item("81", "00", getType("RI"), getClasses("FRPMCT"), "Ring of Protection", "Ring of Protection +3");
    public static final Item NULL_H = new Item("82", "00", getType("IT"), getClasses("FRPMCT"), "nothing", "nothing clears inventory space");
    public static final Item NULL_I = new Item("83", "00", getType("IT"), getClasses("FRPMCT"), "nothing", "nothing clears inventory space");
    public static final Item RING_FEATHER_FALL_B = new Item("84", "00", getType("RI"), getClasses("FRPMCT"), "Blue Ring", "Blue Ring");
    public static final Item ROCK_I = new Item("85", "00", getType("DA"), getClasses("FRPMCT"), "Rock", "Rock");
    public static final Item POTION_HEALING_F = new Item("86", "00", getType("PO"), getClasses("FRPMCT"), "Blue Potion", "Blue Potion of Healing");
    public static final Item MACE_D = new Item("87", "00", getType("PR"), getClasses("FRPMCT"), "Mace", "Mace");
    public static final Item POTION_CURE_POISON_A = new Item("88", "00", getType("PO"), getClasses("FRPMCT"), "Blue Potion", "Blue Potion of Cure Poison");
    public static final Item POTION_CURE_POISON_B = new Item("89", "00", getType("PO"), getClasses("FRPMCT"), "Blue Potion", "Blue Potion of Cure Poison");
    public static final Item MEDALLION_OF_ADORNMENT_A = new Item("8A", "00", getType("AM"), getClasses("FRPMCT"), "Amulet of Adornment", "Amulet of Adornment");
    public static final Item ROBE_B = new Item("8B", "00", getType("AR"), getClasses("FRPMCT"), "Fancy Robe", "Fancy Robe");
    public static final Item DROW_CLEAVER = new Item("8C", "00", getType("TW"), getClasses("FRPMCT"), "Drow Cleaver", "Drow Cleaver +3");
    public static final Item STONE_SCEPTER_A = new Item("8D", "00", getType("IT"), getClasses("FRPMCT"), "Stone Scepter", "Stone Scepter");
    public static final Item WAND_OF_FROST_C = new Item("8E", "00", getType("WA"), getClasses("FRPMCT"), "Wand of Frost", "Wand of Frost");
    public static final Item POTION_HEALING_G = new Item("8F", "00", getType("PO"), getClasses("FRPMCT"), "Blue Potion", "Blue Potion of Healing");
    public static final Item SCROLL_MAGE_FLAME_ARROW_A = new Item("90", "00", getType("SC"), getClasses("FRPMCT"), "Mage Scroll", "Mage Scroll of Flame Arrow");
    public static final Item SCROLL_CLERIC_SLOW_POISON_A = new Item("91", "00", getType("SC"), getClasses("FRPMCT"), "Cleric Scroll", "Cleric Scroll of Slow Poison");
    public static final Item IRON_RATION_E = new Item("92", "00", getType("FO"), getClasses("FRPMCT"), "Iron Rations", "Iron Rations");
    public static final Item IRON_RATION_F = new Item("93", "00", getType("FO"), getClasses("FRPMCT"), "Iron Rations", "Iron Rations");
    public static final Item IRON_RATION_G = new Item("94", "00", getType("FO"), getClasses("FRPMCT"), "Iron Rations", "Iron Rations");
    public static final Item HELMET_DWARF_C = new Item("95", "00", getType("HE"), getClasses("FRPMCT"), "Dwarf Helmet", "Dwarf Helmet");
    public static final Item SHIELD_DWARFEN_A = new Item("96", "00", getType("SH"), getClasses("FRPMCT"), "Dwarfen Shield", "Dwarfen Shield");
    public static final Item ROCK_J = new Item("97", "00", getType("DA"), getClasses("FRPMCT"), "Rock", "Rock");
    public static final Item ARROW_J = new Item("98", "00", getType("AM"), getClasses("FRPMCTC"), "Arrow", "Arrow");
    public static final Item AXE_99 = new Item("99", "00", getType("PR"), getClasses("FRPMCT"), "Axe", "Axe");
    public static final Item ROCK_K = new Item("9A", "00", getType("DA"), getClasses("FRPMCT"), "Rock", "Rock");
    public static final Item SCROLL_CLERIC_HOLD_PERSON_A = new Item("9B", "00", getType("SC"), getClasses("FRPMCT"), "Cleric Scroll", "Cleric Scroll of Hold Person");
    public static final Item IRON_RATION_H = new Item("9C", "00", getType("FO"), getClasses("FRPMCT"), "Iron Rations", "Iron Rations");
    public static final Item SPEAR_B = new Item("9D", "00", getType("PR"), getClasses("FRPMCT"), "Spear", "Spear");
    public static final Item STONE_NECKLACE_A = new Item("9E", "00", getType("IT"), getClasses("FRPMCT"), "Stone Necklace", "Stone Necklace");
    public static final Item SCROLL_CLERIC_AID_A = new Item("9F", "00", getType("SC"), getClasses("FRPMCT"), "Cleric Scroll", "Cleric Scroll of Aid");
    public static final Item SCROLL_MAGE_HASTE_A = new Item("A0", "00", getType("SC"), getClasses("FRPMCT"), "Mage Scroll", "Mage Scroll of Haste");
    public static final Item POTION_HEALING_C = new Item("A1", "00", getType("PO"), getClasses("FRPMCT"), "Blue Potion", "Blue Potion of Healing");
    public static final Item SCROLL_CLERIC_DETECT_MAGIC_A = new Item("A2", "00", getType("SC"), getClasses("FRPMCT"), "Cleric Scroll", "Cleric Scroll of Detect Magic");
    public static final Item IRON_RATION_J = new Item("A3", "00", getType("FO"), getClasses("FRPMCT"), "Iron Rations", "Iron Rations");
    public static final Item LONG_SWORD_B = new Item("A4", "00", getType("PR"), getClasses("FRPMCT"), "Long Sword", "Long Sword");
    public static final Item IRON_RATION_K = new Item("A5", "00", getType("FO"), getClasses("FRPMCT"), "Iron Rations", "Iron Rations");
    public static final Item SCROLL_COMMISION_B = new Item("A6", "00", getType("SC"), getClasses("FRPMCT"), "Scroll", "Commision and Letter of Marque");
    public static final Item RATION_A7 = new Item("A7", "00", getType("FO"), getClasses("FRPMCT"), "Ration", "Ration");
    public static final Item POTION_POISON_A = new Item("A8", "00", getType("PO"), getClasses("FRPMCT"), "Green Potion", "Green Potion of Poison");
    public static final Item ROCK_L = new Item("A9", "00", getType("DA"), getClasses("FRPMCT"), "Rock", "Rock");
    public static final Item SCROLL_MAGE_DISPEL_MAGIC_A = new Item("AA", "00", getType("SC"), getClasses("FRPMCT"), "Mage Scroll", "Mage Scroll of Dispel Magic");
    public static final Item PLATE_MAIL_A = new Item("AB", "00", getType("AR"), getClasses("FRPMCT"), "Plate Mail", "Plate Mail");
    public static final Item CHAIN_MAIL_AC = new Item("AC", "00", getType("AR"), getClasses("FRPMCT"), "Chain Mail", "Chain Mail");
    public static final Item SCALE_MAIL_A = new Item("AD", "00", getType("AR"), getClasses("FRPMC"), "Scale Mail", "Scale Mail");
    public static final Item AXE_CURSED_A = new Item("AE", "00", getType("PR"), getClasses("FRPMC"), "Axe", "Cursed Axe -3");
    public static final Item SLING_ENCHANTED_A = new Item("AF", "00", getType("RA"), getClasses("FRPMCT"), "Sling", "Sling (enchanted)");
    public static final Item HELMET_DWARF_B0 = new Item("B0", "00", getType("HE"), getClasses("FRPMCT"), "Dwarf Helmet", "Dwarf Helmet");
    public static final Item RING_ADORNMENT_A = new Item("B1", "00", getType("RI"), getClasses("FRPMCT"), "Blue Ring", "Blue Ring");
    public static final Item RATION_M = new Item("B2", "00", getType("FO"), getClasses("FRPMCT"), "Ration", "Ration");
    public static final Item KEY_C = new Item("B3", "00", getType("IT"), getClasses("FRPMCT"), "Key", "Key (golden)");
    public static final Item SCROLL_CLERIC_PRAYER_A = new Item("B4", "00", getType("SC"), getClasses("FRPMCT"), "Cleric Scroll", "Cleric Scroll of Prayer");
    public static final Item BOOTS_C = new Item("B5", "00", getType("BO"), getClasses("FRPMCT"), "Boots", "Boots");
    public static final Item KENKU_EGG_A = new Item("B6", "00", getType("IT"), getClasses("FRPMCT"), "Kenku Egg", "Kenku Egg");
    public static final Item KENKU_EGG_B = new Item("B7", "00", getType("IT"), getClasses("FRPMCT"), "Kenku Egg", "Kenku Egg");
    public static final Item KENKU_EGG_C = new Item("B8", "00", getType("IT"), getClasses("FRPMCT"), "Kenku Egg", "Kenku Egg");
    public static final Item KENKU_EGG_D = new Item("B9", "00", getType("IT"), getClasses("FRPMCT"), "Kenku Egg", "Kenku Egg");
    public static final Item RING_ADORNMENT_B = new Item("BA", "00", getType("RI"), getClasses("FRPMCT"), "Blue Ring", "Blue Ring");
    public static final Item KENKU_EGG_F = new Item("BB", "00", getType("IT"), getClasses("FRPMCT"), "Kenku Egg", "Kenku Egg");
    public static final Item KENKU_EGG_G = new Item("BC", "00", getType("IT"), getClasses("FRPMCT"), "Kenku Egg", "Kenku Egg");
    public static final Item KENKU_EGG_H = new Item("BD", "00", getType("IT"), getClasses("FRPMCT"), "Kenku Egg", "Kenku Egg");
    public static final Item KENKU_EGG_I = new Item("BE", "00", getType("IT"), getClasses("FRPMCT"), "Kenku Egg", "Kenku Egg");
    public static final Item KENKU_EGG_J = new Item("BF", "00", getType("IT"), getClasses("FRPMCT"), "Kenku Egg", "Kenku Egg");
    public static final Item KEY_DWARFEN_D = new Item("C0", "00", getType("IT"), getClasses("FRPMCT"), "Dwarfen Key", "Dwarfen Key");
    public static final Item KEY_DWARFEN_E = new Item("C1", "00", getType("IT"), getClasses("FRPMCT"), "Dwarfen Key", "Dwarfen Key");
    public static final Item SCROLL_MAGE_HOLD_PERSON_A = new Item("C2", "00", getType("SC"), getClasses("FRPMCT"), "Mage Scroll", "Mage Scroll of Hold Person");
    public static final Item STONE_RING_A = new Item("C3", "00", getType("IT"), getClasses("FRPMCT"), "Stone Ring", "Stone Ring");
    public static final Item ROCK_M = new Item("C4", "00", getType("DA"), getClasses("FRPMCT"), "Rock", "Rock");
    public static final Item SCROLL_CLERIC_DISPEL_MAGIC_A = new Item("C5", "00", getType("SC"), getClasses("FRPMCT"), "Cleric Scroll", "Cleric Scroll of Dispel Magic");
    public static final Item SCROLL_CLERIC_CURE_SERIOUS_WOUNDS_A = new Item("C6", "00", getType("SC"), getClasses("FRPMCT"), "Cleric Scroll", "Cleric Scroll of Cure Serious Wound");
    public static final Item KEY_D = new Item("C7", "00", getType("IT"), getClasses("FRPMCT"), "Key", "Key (golden)");
    public static final Item NOTHING_B = new Item("C8", "00", getType("IT"), getClasses("FRPMCT"), "nothing", "nothing clears inventory space");
    public static final Item NOTHING_C = new Item("C9", "00", getType("IT"), getClasses("FRPMCT"), "nothing", "nothing clears inventory space");
    public static final Item NOTHING_D = new Item("CA", "00", getType("IT"), getClasses("FRPMCT"), "nothing", "nothing clears inventory space");
    public static final Item NOTHING_E = new Item("CB", "00", getType("IT"), getClasses("FRPMCT"), "nothing", "nothing clears inventory space");
    public static final Item NOTHING_F = new Item("CC", "00", getType("IT"), getClasses("FRPMCT"), "nothing", "nothing clears inventory space");
    public static final Item NOTHING_G = new Item("CD", "00", getType("IT"), getClasses("FRPMCT"), "nothing", "nothing clears inventory space");
    public static final Item SHIELD_DWARFEN_B = new Item("CE", "00", getType("SH"), getClasses("FRPMCT"), "Dwarfen Shield", "Dwarfen Shield");
    public static final Item ROCK_N = new Item("CF", "00", getType("PR"), getClasses("FRPMCT"), "Rock", "Rock");
    public static final Item MACE_E = new Item("D0", "00", getType("PR"), getClasses("FRPMCT"), "Mace", "Mace +3");
    public static final Item BRACERS_A = new Item("D1", "00", getType("BR"), getClasses("FRPMCT"), "Bracers", "Bracers");
    public static final Item WAND_OF_MAGIC_MISSILE_B = new Item("D2", "00", getType("WA"), getClasses("FRPMCT"), "Wand of Magic Missile", "Wand of Magic Missile");
    public static final Item KEY_DWARFEN_F = new Item("D3", "00", getType("IT"), getClasses("FRPMCT"), "Dwarfen Key", "Dwarfen Key");
    public static final Item RED_RING_A = new Item("D4", "00", getType("RI"), getClasses("FRPMCT"), "Red Ring", "Red Ring");
    public static final Item SCROLL_CLERIC_FLAME_BLADE_B = new Item("D5", "00", getType("SC"), getClasses("FRPMCT"), "Cleric Scroll", "Cleric Scroll of Flame Blade");
    public static final Item SCROLL_MAGE_FIREBALL_B = new Item("D6", "00", getType("SC"), getClasses("FRPMCT"), "Mage Scroll", "Mage Scroll of Fireball");
    public static final Item HALBERD_CHIFTAIN = new Item("D7", "00", getType("TW"), getClasses("FRPMCT"), "Halberd", "Chiftain Halberd +5");
    public static final Item IRON_RATION_M = new Item("D8", "00", getType("FO"), getClasses("FRPMCT"), "Iron Rations", "Iron Rations");
    public static final Item AMULETTE_ADORNMENT_A = new Item("D9", "00", getType("AM"), getClasses("FRPMCT"), "Necklace of Adornment", "Necklace of Adornment");
    public static final Item SCROLL_CLERIC_BLESS_B = new Item("DA", "00", getType("SC"), getClasses("FRPMCT"), "Cleric Scroll", "Cleric Scroll of Bless");
    public static final Item ARROW_K = new Item("DB", "00", getType("AM"), getClasses("FRPMCTC"), "Arrow", "Arrow");
    public static final Item SCROLL_CLERIC_PROTCT_EVIL_10_A = new Item("DC", "00", getType("SC"), getClasses("FRPMCT"), "Cleric Scroll", "Cleric Scroll of Protect Evil 10");
    public static final Item SCROLL_CLERIC_REMOVE_PARALYSIS_A = new Item("DD", "00", getType("SC"), getClasses("FRPMCT"), "Cleric Scroll", "Cleric Scroll of Remove Paralyse");
    public static final Item SCROLL_CLERIC_SLOW_POISON_B = new Item("DE", "00", getType("SC"), getClasses("FRPMCT"), "Cleric Scroll", "Cleric Scroll of Slow Poison");
    public static final Item SCROLL_CLERIC_CREATE_FOOD_A = new Item("DF", "00", getType("SC"), getClasses("FRPMCT"), "Cleric Scroll", "Cleric Scroll of Create Food");
    public static final Item KEY_E = new Item("E0", "00", getType("IT"), getClasses("FRPMCT"), "Key", "Key (golden)");
    public static final Item AMULETT_LUCK_A = new Item("E1", "00", getType("AM"), getClasses("FRPMCT"), "Luck Stone Medallion", "Luck Stone Medallion");
    public static final Item RING_PROTECTION_C = new Item("E2", "00", getType("RI"), getClasses("FRPMCT"), "Ring of Protection", "Ring of Protection +2");
    public static final Item ARROW_L = new Item("E3", "00", getType("AM"), getClasses("FRPMCTC"), "Arrow", "Arrow");
    public static final Item ARROW_M = new Item("E4", "00", getType("AM"), getClasses("FRPMCTC"), "Arrow", "Arrow");
    public static final Item ARROW_N = new Item("E5", "00", getType("AM"), getClasses("FRPMCTC"), "Arrow", "Arrow");
    public static final Item ARROW_O = new Item("E6", "00", getType("AM"), getClasses("FRPMCTC"), "Arrow", "Arrow");
    public static final Item SLICER = new Item("E7", "00", getType("SE"), getClasses("FRPMCTC"), "Slicer", "Slicer +3");
    public static final Item BRACERS_DROW_B = new Item("E8", "00", getType("BR"), getClasses("FRPMCT"), "Bracers", "Bracers +3");
    public static final Item RED_RING_B = new Item("E9", "00", getType("RI"), getClasses("FRPMCT"), "Red Ring", "Red Ring");
    public static final Item SCROLL_MAGE_FEAR_A = new Item("EA", "00", getType("SC"), getClasses("FRPMCT"), "Mage Scroll", "Mage Scroll of Fear");
    public static final Item KEY_JEWELED_B = new Item("EB", "00", getType("IT"), getClasses("FRPMCT"), "Jeweled Key", "Jeweled Key");
    public static final Item BANDED_MAIL_A = new Item("EC", "00", getType("AR"), getClasses("FRPMCT"), "Banded Mail", "Banded Mail");
    public static final Item ARROW_P = new Item("ED", "00", getType("AM"), getClasses("FRPMCTC"), "Arrow", "Arrow");
    public static final Item ARROW_Q = new Item("EE", "00", getType("AM"), getClasses("FRPMCTC"), "Arrow", "Arrow");
    public static final Item ARROW_R = new Item("EF", "00", getType("AM"), getClasses("FRPMCTC"), "Arrow", "Arrow");
    public static final Item KEY_DROW_A = new Item("F0", "00", getType("IT"), getClasses("FRPMCT"), "Drow Key", "Drow Key");
    public static final Item SCROLL_MAGE_LIGHTNING_BOLT_A = new Item("F1", "00", getType("SC"), getClasses("FRPMCT"), "Mage Scroll", "Mage Scroll of Lightning Bolt");
    public static final Item KEY_F = new Item("F2", "00", getType("IT"), getClasses("FRPMCT"), "Key", "Key (golden)");
    public static final Item POTION_HEALING_H = new Item("F3", "00", getType("PO"), getClasses("FRPMCT"), "Blue Potion", "Blue Potion of Healing");
    public static final Item KEY_DROW_B = new Item("F4", "00", getType("IT"), getClasses("FRPMCT"), "Drow Key", "Drow Key");
    public static final Item SCROLL_CLERIC_CURE_LIGHT_WOUNDS_A = new Item("F5", "00", getType("SC"), getClasses("FRPMCT"), "Cleric Scroll", "Cleric Scroll of Cure Light Wound");
    public static final Item KEY_JEWELED_C = new Item("F6", "00", getType("IT"), getClasses("FRPMCT"), "Jeweled Key", "Jeweled Key");
    public static final Item KEY_RUBY_A = new Item("F7", "00", getType("IT"), getClasses("FRPMCT"), "Ruby Key", "Ruby Key");
    public static final Item IGNEOUS_ROCK_C = new Item("F8", "00", getType("DA"), getClasses("FRPMCT"), "Rock", "Rock +1");
    public static final Item WAND_OF_STICK_A = new Item("F9", "00", getType("WA"), getClasses("FRPMCT"), "Wand of Stick", "Wand of Stick");
    public static final Item SHIELD_D = new Item("FA", "00", getType("SH"), getClasses("FRPMCTC"), "Shield", "Shield");
    public static final Item SCROLL_CLERIC_PRAYER_B = new Item("FB", "00", getType("SC"), getClasses("FRPMCT"), "Cleric Scroll", "Cleric Scroll of Prayer");
    public static final Item SCROLL_CLERIC_NEUTRALIZE_POISON_A = new Item("FC", "00", getType("SC"), getClasses("FRPMCT"), "Cleric Scroll", "Cleric Scroll of Neutralize Poison");
    public static final Item SCROLL_CLERIC_CURE_CRITICAL_WOUNDS_A = new Item("FD", "00", getType("SC"), getClasses("FRPMCT"), "Cleric Scroll", "Cleric Scroll of Cure Critical Wound");
    public static final Item AMULETTE_ADORNMENT_B = new Item("FE", "00", getType("AM"), getClasses("FRPMCT"), "Necklace of Adornment", "Necklace of Adornment");
    public static final Item RED_RING_C = new Item("FF", "00", getType("RI"), getClasses("FRPMCT"), "Red Ring", "Red Ring");
    public static final Item RING_SUSTENANCE_B = new Item("00", "01", getType("RI"), getClasses("FRPMCT"), "Ring of Sustenance", "Ring of Sustenance");
    public static final Item NIGHT_STALKER = new Item("01", "01", getType("PR"), getClasses("FRPMCTC"), "Night Stalker", "Night Stalker +3");
    public static final Item SCROLL_CLERIC_HOLD_PERSON_B = new Item("02", "01", getType("SC"), getClasses("FRPMCT"), "Cleric Scroll", "Cleric Scroll of Hold Person");
    public static final Item IGNEOUS_ROCK_D = new Item("03", "01", getType("DA"), getClasses("FRPMCT"), "Rock", "Rock +1");
    public static final Item KEY_RUBY_B = new Item("04", "01", getType("IT"), getClasses("FRPMCT"), "Ruby Key", "Ruby Key");
    public static final Item SCROLL_MAGE_INVISIBILITY_10_B = new Item("05", "01", getType("SC"), getClasses("FRPMCT"), "Mage Scroll", "Mage Scroll of Invisiblity 10");
    public static final Item DROW_BOW_A = new Item("06", "01", getType("RA"), getClasses("FRPMCT"), "Drow Bow", "Drow Bow");
    public static final Item KEY_DROW_C = new Item("07", "01", getType("IT"), getClasses("FRPMCT"), "Drow Key", "Drow Key");
    public static final Item SCROLL_CLERIC_PROTECT_EVIL_A = new Item("08", "01", getType("SC"), getClasses("FRPMCT"), "Cleric Scroll", "Cleric Scroll of Protect Evil");
    public static final Item BOOTS_DROW_A = new Item("09", "01", getType("BO"), getClasses("FRPMCT"), "Drow Boots", "Drow Boots");
    public static final Item POTION_EXTRA_HEALING_C = new Item("0A", "01", getType("PO"), getClasses("FRPMCT"), "Blue Potion", "Blue Potion of Extra Healing");
    public static final Item SCROLL_CLERIC_RAISE_DEAD_A = new Item("0B", "01", getType("SC"), getClasses("FRPMCT"), "Cleric Scroll", "Cleric Scroll of Raise Dead");
    public static final Item KEY_RUBY_C = new Item("0C", "01", getType("IT"), getClasses("FRPMCT"), "Ruby Key", "Ruby Key");
    public static final Item KEY_DROW_D = new Item("0D", "01", getType("IT"), getClasses("FRPMCT"), "Drow Key", "Drow Key");
    public static final Item KEY_JEWELED_D = new Item("0E", "01", getType("IT"), getClasses("FRPMCT"), "Jeweled Key", "Jeweled Key");
    public static final Item SCROLL_MAGE_SHIELD_B = new Item("0F", "01", getType("SC"), getClasses("FRPMCTC"), "Mage Scroll", "Mage Scroll of Shield");
    public static final Item WAND_OF_LIGHTNING_A = new Item("10", "01", getType("WA"), getClasses("FRPMCT"), "Wand of Lightning", "Wand of Lightning");
    public static final Item PLATE_MAIL_GREAT_BEAUTY_A = new Item("11", "01", getType("AR"), getClasses("FRPMCT"), "Plate Mail", "Plate Mail");
    public static final Item FLAIL_A = new Item("12", "01", getType("PR"), getClasses("FRPMCT"), "Flail", "Flail");
    public static final Item KEY_DROW_E = new Item("13", "01", getType("IT"), getClasses("FRPMCT"), "Drow Key", "Drow Key");
    public static final Item ROBE_C = new Item("14", "01", getType("AR"), getClasses("FRPMCT"), "Robe", "Robe");
    public static final Item SCEPTER_OF_KINGLY_MIGHT = new Item("15", "01", getType("IT"), getClasses("FRPMCT"), "Scepter of Kinlgy Might", "Scepter of Kinlgy Might");
    public static final Item SCROLL_MAGE_ICESTORM_A = new Item("16", "01", getType("SC"), getClasses("FRPMCT"), "Mage Scroll", "Mage Scroll of Ice Storm");
    public static final Item LOCK_PICKS_C = new Item("17", "01", getType("IT"), getClasses("FRPMCT"), "Lock Picks", "Lock Picks");
    public static final Item KEY_DROW_F = new Item("18", "01", getType("IT"), getClasses("FRPMCT"), "Drow Key", "Drow Key");
    public static final Item SCROLL_CLERIC_DETECT_MAGIC_B = new Item("19", "01", getType("SC"), getClasses("FRPMCT"), "Cleric Scroll", "Cleric Scroll of Detect Magic");
    public static final Item POTION_POISON_B = new Item("1A", "01", getType("PO"), getClasses("FRPMCT"), "Green Potion", "Green Potion of Poison");
    public static final Item SCROLL_MAGE_STONESKIN_A = new Item("1B", "01", getType("SC"), getClasses("FRPMCT"), "Mage Scroll", "Mage Scroll of Detect Magic");
    public static final Item ARROW_S = new Item("1C", "01", getType("AM"), getClasses("FRPMCTC"), "Arrow", "Arrow");
    public static final Item ARROW_T = new Item("1D", "01", getType("AM"), getClasses("FRPMCTC"), "Arrow", "Arrow");
    public static final Item ARROW_U = new Item("1E", "01", getType("AM"), getClasses("FRPMCTC"), "Arrow", "Arrow");
    public static final Item KEY_DROW_G = new Item("1F", "01", getType("IT"), getClasses("FRPMCT"), "Drow Key", "Drow Key");
    public static final Item SCROLL_CLERIC_DISPEL_MAGIC_B = new Item("20", "01", getType("SC"), getClasses("FRPMCT"), "Cleric Scroll", "Cleric Scroll of Dispel Magic");
    public static final Item SCROLL_CLERIC_CURE_SERIOUS_WOUNDS_B = new Item("21", "01", getType("SC"), getClasses("FRPMCT"), "Cleric Scroll", "Cleric Scroll of Cure Serious Wound");
    public static final Item SCROLL_MAGE_INVISIBILITY_B = new Item("22", "01", getType("SC"), getClasses("FRPMCTC"), "Mage Scroll", "Mage Scroll of Invisibility");
    public static final Item SCROLL_CLERIC_FLAME_BLADE_C = new Item("23", "01", getType("SC"), getClasses("FRPMCT"), "Cleric Scroll", "Cleric Scroll of Flame Blade");
    public static final Item SCROLL_CLERIC_PROTCT_EVIL_10_B = new Item("24", "01", getType("SC"), getClasses("FRPMCT"), "Cleric Scroll", "Cleric Scroll of Protect Evil 10");
    public static final Item SCROLL_MAGE_ARMOR_B = new Item("25", "01", getType("SC"), getClasses("FRPMCTC"), "Mage Scroll", "Mage Scroll of Armor");
    public static final Item SHIELD_DROW_A = new Item("26", "01", getType("SH"), getClasses("FRPMCT"), "Drow Shield", "Drow Shield +3");
    public static final Item SCROLL_CLERIC_RAISE_DEAD_B = new Item("27", "01", getType("SC"), getClasses("FRPMCT"), "Cleric Scroll", "Cleric Scroll of Raise Dead");
    public static final Item BOOTS_DROW_B = new Item("28", "01", getType("BO"), getClasses("FRPMCT"), "Drow Boots", "Drow Boots");
    public static final Item POTION_EXTRA_HEALING_D = new Item("29", "01", getType("PO"), getClasses("FRPMCT"), "Blue Potion", "Blue Potion of Extra Healing");
    public static final Item SPEAR_C = new Item("2A", "01", getType("PR"), getClasses("FRPMCT"), "Spear", "Spear");
    public static final Item WAND_OF_FIREBALL_A = new Item("2B", "01", getType("WA"), getClasses("FRPMCT"), "Wand of Fireball", "Wand of Fireball");
    public static final Item SCROLL_CLERIC_RAISE_DEAD_C = new Item("2C", "01", getType("SC"), getClasses("FRPMCT"), "Cleric Scroll", "Cleric Scroll of Raise Dead");
    public static final Item CHAIN_MAIL_D = new Item("2D", "01", getType("AR"), getClasses("FRPMCT"), "Chain Mail", "Chain Mail");
    public static final Item ROCK_O = new Item("2E", "01", getType("DA"), getClasses("FRPMCT"), "Rock", "Rock");
    public static final Item KEY_DWARFEN_G = new Item("2F", "01", getType("IT"), getClasses("FRPMCT"), "Dwarfen Key", "Dwarfen Key");
    public static final Item PLATE_MAIL_B = new Item("30", "01", getType("AR"), getClasses("FRPMCT"), "Plate Mail", "Plate Mail");
    public static final Item POTION_POISON_C = new Item("31", "01", getType("PO"), getClasses("FRPMCT"), "Green Potion", "Green Potion of Poison");
    public static final Item WAND_OF_FROST_D = new Item("32", "01", getType("WA"), getClasses("FRPMCT"), "Wand of Frost", "Wand of Frost");
    public static final Item SCROLL_CLERIC_FLAME_BLADE_D = new Item("33", "01", getType("SC"), getClasses("FRPMCT"), "Cleric Scroll", "Cleric Scroll of Flame Blade");
    public static final Item SCROLL_CLERIC_CURE_CRITICAL_WOUNDS_B = new Item("34", "01", getType("SC"), getClasses("FRPMCT"), "Cleric Scroll", "Cleric Scroll of Cure Critical Wound");
    public static final Item WAND_OF_STICK_B = new Item("35", "01", getType("WA"), getClasses("FRPMCT"), "Wand of Stick", "Wand of Stick");
    public static final Item STONE_HOLY_SYMBOL_A = new Item("36", "01", getType("IT"), getClasses("FRPMCT"), "Stone Holy Symbol", "Stone Holy Symbol");
    public static final Item ARROW_V = new Item("37", "01", getType("AM"), getClasses("FRPMCTC"), "Arrow", "Arrow");
    public static final Item ARROW_W = new Item("38", "01", getType("AM"), getClasses("FRPMCTC"), "Arrow", "Arrow");
    public static final Item KEY_SKULL_B = new Item("39", "01", getType("IT"), getClasses("FRPMC"), "Skull Key", "Skull Key");
    public static final Item RING_FEATHER_FALL_C = new Item("3A", "01", getType("RI"), getClasses("FRPMCT"), "Ring of Feather Fall", "Ring of Feather Fall");
    public static final Item POTION_GIANT_STRENGTH_C = new Item("3B", "01", getType("PO"), getClasses("FRPMCT"), "Red Potion", "Red Potion of Giant Strength");
    public static final Item SCROLL_CLERIC_FLAME_BLADE_E = new Item("3C", "01", getType("SC"), getClasses("FRPMCT"), "Cleric Scroll", "Cleric Scroll of Flame Blade");
    public static final Item SCROLL_CLERIC_REMOVE_PARALYSIS_B = new Item("3D", "01", getType("SC"), getClasses("FRPMCT"), "Cleric Scroll", "Cleric Scroll of Remove Paralyse");
    public static final Item SCROLL_CLERIC_NEUTRALIZE_POISON_B = new Item("3E", "01", getType("SC"), getClasses("FRPMCT"), "Cleric Scroll", "Cleric Scroll of Neutralize Poison");
    public static final Item SCROLL_MAGE_CONE_OF_COLD_A = new Item("3F", "01", getType("SC"), getClasses("FRPMCT"), "Mage Scroll", "Mage Scroll of Cone of Cold");
    public static final Item WAND_OF_LIGHTNING_B = new Item("40", "01", getType("WA"), getClasses("FRPMCT"), "Wand of Lightning", "Wand of Lightning");
    public static final Item AMULETT_LUCK_B = new Item("41", "01", getType("AM"), getClasses("FRPMCT"), "Luck Stone Medallion", "Luck Stone Medallion");
    public static final Item SCROLL_CLERIC_RAISE_DEAD_D = new Item("42", "01", getType("SC"), getClasses("FRPMCT"), "Cleric Scroll", "Cleric Scroll of Raise Dead");
    public static final Item STONE_ORB_A = new Item("43", "01", getType("IT"), getClasses("FRPMCT"), "Stone Orb", "Stone Orb");
    public static final Item KEY_DROW_H = new Item("44", "01", getType("IT"), getClasses("FRPMCT"), "Drow Key", "Drow Key");
    public static final Item ORB_POWER_B = new Item("45", "01", getType("IT"), getClasses("FRPMCT"), "Orb of Power", "Orb of Power");
    public static final Item SCROLL_CLERIC_RAISE_DEAD_E = new Item("46", "01", getType("SC"), getClasses("FRPMCT"), "Cleric Scroll", "Cleric Scroll of Raise Dead");
    public static final Item SLIMY_ROCK_A = new Item("47", "01", getType("DA"), getClasses("FRPMCT"), "Slimy Rock", "Slimy Rock +2");
    public static final Item SLIMY_ROCK_B = new Item("48", "01", getType("DA"), getClasses("FRPMCT"), "Slimy Rock", "Slimy Rock +2");
    public static final Item SLASHER_A = new Item("49", "01", getType("PR"), getClasses("FRPMCT"), "Slasher", "Slasher +4");
    public static final Item BANDED_MAIL_B = new Item("4A", "01", getType("AR"), getClasses("FRPMCT"), "Banded Mail", "Banded Mail +3");
    public static final Item RING_ADORNMENT_D = new Item("4B", "01", getType("RI"), getClasses("FRPMCT"), "Ring of Adornment", "Ring of Adornment");
    public static final Item SCROLL_MAGE_HOLD_MONSTER_A = new Item("4C", "01", getType("SC"), getClasses("FRPMCT"), "Mage Scroll", "Mage Scroll of Hold Monster");
    public static final Item SCROLL_CLERIC_CURE_SERIOUS_WOUNDS_C = new Item("4D", "01", getType("SC"), getClasses("FRPMCT"), "Cleric Scroll", "Cleric Scroll of Cure Serious Wound");
    public static final Item IRON_RATION_N = new Item("4E", "01", getType("FO"), getClasses("FRPMCT"), "Iron Rations", "Iron Rations");
    public static final Item ROBE_DEFENSE_A = new Item("4F", "01", getType("AR"), getClasses("FRPMCT"), "Robe of Defense", "Robe of Defense");
    public static final Item FLICKA = new Item("50", "01", getType("DA"), getClasses("FRPMCT"), "Flicka", "Flicka +5");
    public static final Item KEY_DROW_I = new Item("51", "01", getType("IT"), getClasses("FRPMCT"), "Drow Key", "Drow Key");
    public static final Item BONES_HUMAN_A = new Item("52", "01", getType("IT"), getClasses("FRPMCT"), "Human Bones", "Humand Bones");
    public static final Item BONES_HUMAN_B = new Item("53", "01", getType("IT"), getClasses("FRPMCT"), "Human Bones", "Humand Bones");
    public static final Item BONES_HUMAN_C = new Item("54", "01", getType("IT"), getClasses("FRPMCT"), "Human Bones", "Humand Bones");
    public static final Item BONES_HUMAN_D = new Item("55", "01", getType("IT"), getClasses("FRPMCT"), "Human Bones", "Humand Bones");
    public static final Item BONES_HUMAN_E = new Item("56", "01", getType("IT"), getClasses("FRPMCT"), "Human Bones", "Humand Bones");
    public static final Item RING_PROTECTION_D = new Item("57", "01", getType("RI"), getClasses("FRPMCT"), "Ring of Protection", "Ring of Protection +2");
    public static final Item BRACERS_B = new Item("58", "01", getType("BR"), getClasses("FRPMCT"), "Bracers", "Bracers +2");
    public static final Item LEATHER_ARMOR_B = new Item("59", "01", getType("AR"), getClasses("FRPMCT"), "Leather Armor", "Leather Armor");
    public static final Item SPEAR_D = new Item("5A", "01", getType("PR"), getClasses("FRPMCT"), "Spear", "Spear");
    public static final Item PLATE_MAIL_C = new Item("5B", "01", getType("AR"), getClasses("FRPMCT"), "Plate Mail", "Plate Mail");
    public static final Item SHIELD_E = new Item("5C", "01", getType("SH"), getClasses("FRPMCTC"), "Shield", "Shield");
    public static final Item SEVERIOUS = new Item("5D", "01", getType("PR"), getClasses("FRPMCTC"), "Severious", "Severious +5");
    public static final Item HELMET_B = new Item("5E", "01", getType("HE"), getClasses("FRPMCT"), "Helmet", "Helmet");
    public static final Item HOLY_SYMBOL_PALADIN_B = new Item("5F", "01", getType("IT"), getClasses("FRPMCT"), "Paladin Holy Symbol", "Paladin Holy Symbol");
    public static final Item SHORT_SWORD_B = new Item("60", "01", getType("SE"), getClasses("FRPMCT"), "Short Sword", "Short Sword");
    public static final Item HOLY_SYMBOL_PALADIN_C = new Item("61", "01", getType("IT"), getClasses("FRPMCT"), "Paladin Holy Symbol", "Paladin Holy Symbol");
    public static final Item LEATHER_ARMOR_C = new Item("62", "01", getType("AR"), getClasses("FRPMCT"), "Leather Armor", "Leather Armor");
    public static final Item BOOTS_D = new Item("63", "01", getType("BO"), getClasses("FRPMCT"), "Boots", "Boots");
    public static final Item IRON_RATION_O = new Item("64", "01", getType("FO"), getClasses("FRPMCT"), "Iron Rations", "Iron Rations");
    public static final Item SHORT_SWORD_C = new Item("65", "01", getType("SE"), getClasses("FRPMCT"), "Short Sword", "Short Sword");
    public static final Item LOCK_PICKS_D = new Item("66", "01", getType("IT"), getClasses("FRPMCT"), "Lock Picks", "Lock Picks");
    public static final Item LEATHER_ARMOR_D = new Item("67", "01", getType("AR"), getClasses("FRPMCT"), "Leather Armor", "Leather Armor");
    public static final Item BOOTS_E = new Item("68", "01", getType("BO"), getClasses("FRPMCT"), "Boots", "Boots");
    public static final Item IRON_RATION_P = new Item("69", "01", getType("FO"), getClasses("FRPMCT"), "Iron Rations", "Iron Rations");
    public static final Item MACE_F = new Item("6A", "01", getType("PR"), getClasses("FRPMCT"), "Mace", "Mace");
    public static final Item SPELL_BOOK_B = new Item("6B", "01", getType("IT"), getClasses("FRPMCT"), "Spell Book", "Spell Book");
    public static final Item HOLY_SYMBOL_CLERIC_B = new Item("6C", "01", getType("IT"), getClasses("FRPMCT"), "Cleric Holy Symbol", "Cleric Holy Symbol");
    public static final Item ROBE_D = new Item("6D", "01", getType("AR"), getClasses("FRPMCT"), "Robe", "Robe");
    public static final Item IRON_RATION_Q = new Item("6E", "01", getType("FO"), getClasses("FRPMCT"), "Iron Rations", "Iron Rations");
    public static final Item MACE_G = new Item("6F", "01", getType("PR"), getClasses("FRPMCT"), "Mace", "Mace");
    public static final Item SPELL_BOOK_C = new Item("70", "01", getType("IT"), getClasses("FRPMCT"), "Spell Book", "Spell Book");
    public static final Item HOLY_SYMBOL_CLERIC_C = new Item("71", "01", getType("IT"), getClasses("FRPMCT"), "Cleric Holy Symbol", "Cleric Holy Symbol");
    public static final Item ROBE_E = new Item("72", "01", getType("AR"), getClasses("FRPMCT"), "Robe", "Robe");
    public static final Item DAGGER_C = new Item("73", "01", getType("DA"), getClasses("FRPMCT"), "Dagger", "Dagger");
    public static final Item POTION_SPEED_A = new Item("74", "01", getType("PO"), getClasses("FRPMCT"), "Red Potion", "Red Potion of Speed");
    public static final Item ARROW_X = new Item("75", "01", getType("AM"), getClasses("FRPMCTC"), "Arrow", "Arrow");
    public static final Item ARROW_Y = new Item("76", "01", getType("AM"), getClasses("FRPMCTC"), "Arrow", "Arrow");
    public static final Item ARROW_Z = new Item("77", "01", getType("AM"), getClasses("FRPMCTC"), "Arrow", "Arrow");
    public static final Item KEY_DWARFEN_H = new Item("78", "01", getType("IT"), getClasses("FRPMCT"), "Dwarfen Key", "Dwarfen Key");
    public static final Item ROCK_P = new Item("79", "01", getType("DA"), getClasses("FRPMCT"), "Rock", "Rock");
    public static final Item ROCK_Q = new Item("7A", "01", getType("DA"), getClasses("FRPMCT"), "Rock", "Rock");
    public static final Item POTION_EXTRA_HEALING_E = new Item("7B", "01", getType("PO"), getClasses("FRPMCT"), "Blue Potion", "Blue Potion of Extra Healing");
    public static final Item ADAMATITE_DART_C = new Item("7C", "01", getType("DA"), getClasses("FRPMCT"), "Adamatite Dart", "Adamatite Dart +4");
    public static final Item DAGGER_D = new Item("7D", "01", getType("DA"), getClasses("FRPMCT"), "Dagger", "Dagger");
    public static final Item ORB_POWER_C = new Item("7E", "01", getType("IT"), getClasses("FRPMCT"), "Orb of Power", "Orb of Power");
    public static final Item ORB_POWER_D = new Item("7F", "01", getType("IT"), getClasses("FRPMCT"), "Orb of Power", "Orb of Power");
    public static final Item ORB_POWER_E = new Item("80", "01", getType("IT"), getClasses("FRPMCT"), "Orb of Power", "Orb of Power");
    public static final Item GEM_RED_C = new Item("81", "01", getType("IT"), getClasses("FRPMCT"), "Red Gem", "Red Gem");
    public static final Item POTION_EXTRA_HEALING_F = new Item("82", "01", getType("PO"), getClasses("FRPMCT"), "Blue Potion", "Blue Potion of Extra Healing");
    public static final Item POTION_EXTRA_HEALING_G = new Item("83", "01", getType("PO"), getClasses("FRPMCT"), "Blue Potion", "Blue Potion of Extra Healing");
    public static final Item RING_ADORNMENT_E = new Item("84", "01", getType("RI"), getClasses("FRPMCT"), "Ring of Adornment", "Ring of Adornment");
    public static final Item AMULETT_A = new Item("85", "01", getType("AM"), getClasses("FRPMCT"), "Necklace", "Necklace");
    public static final Item WAND_OF_FIREBALL_B = new Item("86", "01", getType("WA"), getClasses("FRPMCT"), "Wand of Fireball", "Wand of Fireball");
    public static final Item ORB_POWER_F = new Item("87", "01", getType("IT"), getClasses("FRPMCT"), "Orb of Power", "Orb of Power");
    public static final Item POTION_SPEED_B = new Item("88", "01", getType("PO"), getClasses("FRPMCT"), "Red Potion", "Red Potion of Speed");
    public static final Item ORB_POWER_G = new Item("89", "01", getType("IT"), getClasses("FRPMCT"), "Orb of Power", "Orb of Power");
    public static final Item ORB_POWER_H = new Item("8A", "01", getType("IT"), getClasses("FRPMCT"), "Orb of Power", "Orb of Power");
    public static final Item ORB_POWER_I = new Item("8B", "01", getType("IT"), getClasses("FRPMCT"), "Orb of Power", "Orb of Power");
    public static final Item IRON_RATION_R = new Item("8C", "01", getType("FO"), getClasses("FRPMCT"), "Iron Rations", "Iron Rations");
    public static final Item IRON_RATION_S = new Item("8D", "01", getType("FO"), getClasses("FRPMCT"), "Iron Rations", "Iron Rations");
    public static final Item IRON_RATION_T = new Item("8E", "01", getType("FO"), getClasses("FRPMCT"), "Iron Rations", "Iron Rations");
    public static final Item IRON_RATION_U = new Item("8F", "01", getType("FO"), getClasses("FRPMCT"), "Iron Rations", "Iron Rations");
    public static final Item KEY_SKULL_C = new Item("90", "01", getType("IT"), getClasses("FRPMC"), "Skull Key", "Skull Key");
    public static final Item POTION_INVISIBILITY_A = new Item("91", "01", getType("PO"), getClasses("FRPMCT"), "Green Potion", "Green Potion of Invisibility");
    public static final Item POTION_INVISIBILITY_B = new Item("92", "01", getType("PO"), getClasses("FRPMCT"), "Green Potion", "Green Potion of Invisibility");
    public static final Item POTION_VITALITY_A = new Item("93", "01", getType("PO"), getClasses("FRPMCT"), "Green Potion", "Green Potion of Vitality");
    public static final Item POTION_VITALITY_B = new Item("94", "01", getType("PO"), getClasses("FRPMCT"), "Green Potion", "Green Potion of Vitality");
    public static final Item POTION_INVISIBILITY_C = new Item("95", "01", getType("PO"), getClasses("FRPMCT"), "Green Potion", "Green Potion of Invisibility");
    public static final Item POTION_INVISIBILITY_D = new Item("96", "01", getType("PO"), getClasses("FRPMCT"), "Green Potion", "Green Potion of Invisibility");
    public static final Item WAND_OF_MAGIC_MISSILE_C = new Item("97", "01", getType("WA"), getClasses("FRPMCT"), "Wand of Magic Missile", "Wand of Magic Missile");
    public static final Item DAGGER_E = new Item("98", "01", getType("DA"), getClasses("FRPMCT"), "Dagger", "Dagger");
    public static final Item STONE_SCEPTER_B = new Item("99", "01", getType("IT"), getClasses("FRPMCT"), "Stone Scepter", "Stone Scepter");
    public static final Item STONE_DAGGER_B = new Item("9A", "01", getType("IT"), getClasses("FRPMCT"), "Stone Dagger", "Stone Dagger");
    public static final Item STONE_MEDALLION_B = new Item("9B", "01", getType("IT"), getClasses("FRPMCT"), "Stone Medallion", "Stone Medallion");
    public static final Item STONE_NECLACE_A = new Item("9C", "01", getType("IT"), getClasses("FRPMCT"), "Stone Neclace", "Stone Necklace");
    public static final Item STONE_RING_B = new Item("9D", "01", getType("IT"), getClasses("FRPMCT"), "Stone Ring", "Stone Ring");
    public static final Item STONE_HOLY_SYMBOL_B = new Item("9E", "01", getType("IT"), getClasses("FRPMCT"), "Stone Holy Symbol", "Stone Holy Symbol");
    public static final Item STONE_ORB_B = new Item("9F", "01", getType("IT"), getClasses("FRPMCT"), "Stone Orb", "Stone Orb");
    public static final Item IRON_RATION_V = new Item("A0", "01", getType("FO"), getClasses("FRPMCT"), "Iron Rations", "Iron Rations");
    public static final Item IRON_RATION_W = new Item("A1", "01", getType("FO"), getClasses("FRPMCT"), "Iron Rations", "Iron Rations");
    public static final Item IRON_RATION_X = new Item("A2", "01", getType("FO"), getClasses("FRPMCT"), "Iron Rations", "Iron Rations");
    public static final Item IRON_RATION_Y = new Item("A3", "01", getType("FO"), getClasses("FRPMCT"), "Iron Rations", "Iron Rations");
    public static final Item IRON_RATION_Z = new Item("A4", "01", getType("FO"), getClasses("FRPMCT"), "Iron Rations", "Iron Rations");
    public static final Item POTION_EXTRA_HEALING_H = new Item("A5", "01", getType("PO"), getClasses("FRPMCT"), "Blue Potion", "Blue Potion of Extra Healing");
    public static final Item NOTHING_H = new Item("A6", "01", getType("IT"), getClasses("FRPMCT"), "nothing", "nothing clears inventory space");
    public static final Item NOTHING_I = new Item("A7", "01", getType("IT"), getClasses("FRPMCT"), "nothing", "nothing clears inventory space");
    public static final Item NOTHING_J = new Item("A8", "01", getType("IT"), getClasses("FRPMCT"), "nothing", "nothing clears inventory space");
    public static final Item NOTHING_K = new Item("A9", "01", getType("IT"), getClasses("FRPMCT"), "nothing", "nothing clears inventory space");
    public static final Item NOTHING_L = new Item("AA", "01", getType("IT"), getClasses("FRPMCT"), "nothing", "nothing clears inventory space");
    public static final Item NOTHING_M = new Item("AB", "01", getType("IT"), getClasses("FRPMCT"), "nothing", "nothing clears inventory space");
    public static final Item NOTHING_N = new Item("AC", "01", getType("IT"), getClasses("FRPMCT"), "nothing", "nothing clears inventory space");
    public static final Item NOTHING_O = new Item("AD", "01", getType("IT"), getClasses("FRPMCT"), "nothing", "nothing clears inventory space");
    public static final Item POTION_CURE_POISON_C = new Item("AE", "01", getType("PO"), getClasses("FRPMCT"), "Blue Potion", "Blue Potion of Cure Poison");
    public static final Item POTION_CURE_POISON_D = new Item("AF", "01", getType("PO"), getClasses("FRPMCT"), "Blue Potion", "Blue Potion of Cure Poison");
    public static final Item POTION_CURE_POISON_E = new Item("B0", "01", getType("PO"), getClasses("FRPMCT"), "Blue Potion", "Blue Potion of Cure Poison");
    public static final Item POTION_CURE_POISON_F = new Item("B1", "01", getType("PO"), getClasses("FRPMCT"), "Blue Potion", "Blue Potion of Cure Poison");
    public static final Item HOLY_SYMBOL_CLERIC_D = new Item("B2", "01", getType("IT"), getClasses("FRPMCT"), "Cleric Holy Symbol", "Cleric Holy Symbol");
    public static final Item SPELL_BOOK_D = new Item("B3", "01", getType("IT"), getClasses("FRPMCT"), "Spell Book", "Spell Book");
    public static final Item ADAMATITE_DART_D = new Item("B4", "01", getType("DA"), getClasses("FRPMCT"), "Adamatite Dart", "Adamatite Dart +4");
    public static final Item ADAMATITE_DART_E = new Item("B5", "01", getType("DA"), getClasses("FRPMCT"), "Adamatite Dart", "Adamatite Dart +4");
    public static final Item ADAMATITE_DART_F = new Item("B6", "01", getType("DA"), getClasses("FRPMCT"), "Adamatite Dart", "Adamatite Dart +4");
    public static final Item ADAMATITE_DART_G = new Item("B7", "01", getType("DA"), getClasses("FRPMCT"), "Adamatite Dart", "Adamatite Dart +4");
    public static final Item ADAMATITE_DART_H = new Item("B8", "01", getType("DA"), getClasses("FRPMCT"), "Adamatite Dart", "Adamatite Dart +4");
    public static final Item ADAMATITE_DART_I = new Item("B9", "01", getType("DA"), getClasses("FRPMCT"), "Adamatite Dart", "Adamatite Dart +4");
    public static final Item ADAMATITE_DART_J = new Item("BA", "01", getType("DA"), getClasses("FRPMCT"), "Adamatite Dart", "Adamatite Dart +4");
    public static final Item ADAMATITE_DART_K = new Item("BB", "01", getType("DA"), getClasses("FRPMCT"), "Adamatite Dart", "Adamatite Dart +4");
    public static final Item ADAMATITE_DART_L = new Item("BC", "01", getType("DA"), getClasses("FRPMCT"), "Adamatite Dart", "Adamatite Dart +4");
    public static final Item ADAMATITE_DART_M = new Item("BD", "01", getType("DA"), getClasses("FRPMCT"), "Adamatite Dart", "Adamatite Dart +4");
    public static final Item POTION_VITALITY_C = new Item("BE", "01", getType("PO"), getClasses("FRPMCT"), "Green Potion", "Green Potion of Vitality");
    public static final Item SCROLL_MAGE_VAMPIRIC_TOUCH_A = new Item("BF", "01", getType("SC"), getClasses("FRPMCT"), "Mage Scroll", "Mage Scroll of Vampiric Touch");
    public static final Item SCROLL_MAGE_DETECT_MAGIC_C = new Item("C0", "01", getType("SC"), getClasses("FRPMCT"), "Mage Scroll", "Mage Scroll of Detect Magic");
    public static final Item DAGGER_F = new Item("C1", "01", getType("DA"), getClasses("FRPMCT"), "Dagger", "Dagger");
    public static final Item RATION_N = new Item("C2", "01", getType("FO"), getClasses("FRPMCT"), "Ration", "Ration");
    public static final Item HELMET_C = new Item("C3", "01", getType("HE"), getClasses("FRPMCT"), "Helmet", "Helmet");
    public static final Item HELMET_D = new Item("C4", "01", getType("HE"), getClasses("FRPMCT"), "Helmet", "Helmet");
    public static final Item AXE_C = new Item("C5", "01", getType("PR"), getClasses("FRPMCT"), "Axe", "Axe");
    public static final Item AXE_D = new Item("C6", "01", getType("PR"), getClasses("FRPMCT"), "Axe", "Axe");
    public static final Item HELMET_E = new Item("C7", "01", getType("HE"), getClasses("FRPMCT"), "Helmet", "Helmet");
    public static final Item HELMET_F = new Item("C8", "01", getType("HE"), getClasses("FRPMCT"), "Helmet", "Helmet");
    public static final Item AXE_E = new Item("C9", "01", getType("PR"), getClasses("FRPMCT"), "Axe", "Axe");
    public static final Item AXE_F = new Item("CA", "01", getType("PR"), getClasses("FRPMCT"), "Axe", "Axe");
    public static final Item HELMET_G = new Item("CB", "01", getType("HE"), getClasses("FRPMCT"), "Helmet", "Helmet");
    public static final Item HELMET_H = new Item("CC", "01", getType("HE"), getClasses("FRPMCT"), "Helmet", "Helmet");
    public static final Item KEY_SILVER_D = new Item("CD", "01", getType("IT"), getClasses("FRPMCT"), "Silver Key", "Silver Key");
    public static final Item MACE_H = new Item("CE", "01", getType("PR"), getClasses("FRPMCT"), "Mace", "Mace");
    public static final Item[] ITEMS = new Item[]{ 
        NOTHING_A,
        LEATHER_ARMOR_A,
        ROBE_A,
        STAFF_A,
        DAGGER_A,
        SHORT_SWORD_A,
        LOCK_PICKS_A,
        SPELL_BOOK_A,
        HOLY_SYMBOL_CLERIC_A,
        BOOTS_A,
        IRON_RATION_A,
        NULL_A,
        NULL_B,
        NULL_C,
        NULL_D,
        NULL_E,
        NULL_F,
        KEY_JEWELED_A,
        POTION_GIANT_STRENGTH_A,
        GEM_BLUE_A,
        KEY_SKULL_A,
        WAND_OF_FROST_A,
        SCROLL_WEAKNESS,
        RING_SUSTENANCE_A,
        RING_FEATHER_FALL_A,
        RING_PROTECTION_A,
        ADAMATITE_DART_A,
        SCROLL_STARS,
        SCROLL_RIDDLE_A,
        SCROLL_ORB_A,
        IRON_RATION_B,
        BONES_DWARF_A,
        WAND_OF_SHIVAS,
        KEY_A,
        SCROLL_COMMISION_A,
        NULL_G,
        AXE_A,
        DAGGER_B,
        DART_A,
        ADAMATITE_DART_B,
        HALBERD_A,
        CHAIN_MAIL_A,
        HELMET_A,
        HELMET_DWARF_A,
        KEY_SILVER_A,
        ADAMTINE_LONG_SWORD_A,
        MACE_A,
        LONG_SWORD_ENCHANTED_A,
        POTION_HEALING_A,
        GUINSOO,
        GEM_RED_A,
        ORB_POWER_A,
        POTION_DWARFEN_HEALING_A,
        IGNEOUS_ROCK_A,
        POTION_EXTRA_HEALING_A,
        RATION_A,
        ROBE_FANCY_A,
        ROCK_A,
        IGNEOUS_ROCK_B,
        SCROLL_MAGE_DETECT_MAGIC_A,
        SPEAR_A,
        STAFF_B,
        STONE_MEDALLION_A,
        KEY_SILVER_B,
        BONES_HALFLING_A,
        LOCK_PICKS_B,
        ROCK_B,
        DART_B,
        RATION_C,
        MACE_45,
        SCROLL_CLERIC_BLESS_A,
        ROCK_C,
        RATION_B,
        ARROW_A,
        SHIELD_A,
        ARROW_B,
        RATION_E,
        RATION_F,
        BOOTS_B,
        POTION_HEALING_B,
        HELMET_50,
        HELMET_51,
        MACE_B,
        ROCK_D,
        AXE_B,
        BOW_A,
        STONE_DAGGER_A,
        AXE_57,
        MACE_58,
        RATION_I,
        STAFF_5A,
        SCROLL_MAGE_SHIELD_A,
        SLING_A,
        ARROW_C,
        AXE_5E,
        RATION_D,
        ROCK_E,
        CHAIN_MAIL_B,
        HELMET_DWARF_B,
        ARROW_D,
        CHAIN_MAIL_C,
        SHIELD_B,
        ARROW_E,
        IRON_RATION_C,
        IRON_RATION_D,
        RATION_G,
        RATION_H,
        RATION_J,
        ARROW_F,
        POTION_HEALING_D,
        POTION_EXTRA_HEALING_B,
        RATION_K,
        BACKSTABBER,
        RATION_L,
        SHIELD_C,
        STONE_MEDALLION_73,
        POTION_HEALING_E,
        ROCK_F,
        SCROLL_CLERIC_FLAME_BLADE_A,
        ROCK_G,
        SCROLL_MAGE_FIREBALL_A,
        SCROLL_CLERIC_LIGHT_WOUNDS_A,
        GEM_RED_B,
        ARROW_G,
        ROCK_H,
        LONG_SWORD_A,
        WAND_OF_MAGIC_MISSILE_A,
        ARROW_H,
        MACE_C,
        RING_PROTECTION_B,
        NULL_H,
        NULL_I,
        RING_FEATHER_FALL_B,
        ROCK_I,
        POTION_HEALING_F,
        MACE_D,
        POTION_CURE_POISON_A,
        POTION_CURE_POISON_B,
        MEDALLION_OF_ADORNMENT_A,
        ROBE_B,
        DROW_CLEAVER,
        STONE_SCEPTER_A,
        WAND_OF_FROST_C,
        POTION_HEALING_G,
        SCROLL_MAGE_FLAME_ARROW_A,
        SCROLL_CLERIC_SLOW_POISON_A,
        IRON_RATION_E,
        IRON_RATION_F,
        IRON_RATION_G,
        HELMET_DWARF_C,
        SHIELD_DWARFEN_A,
        ROCK_J,
        ARROW_J,
        AXE_99,
        ROCK_K,
        SCROLL_CLERIC_HOLD_PERSON_A,
        IRON_RATION_H,
        SPEAR_B,
        STONE_NECKLACE_A,
        SCROLL_CLERIC_AID_A,
        SCROLL_MAGE_HASTE_A,
        POTION_HEALING_C,
        SCROLL_CLERIC_DETECT_MAGIC_A,
        IRON_RATION_J,
        LONG_SWORD_B,
        IRON_RATION_K,
        SCROLL_COMMISION_B,
        RATION_A7,
        POTION_POISON_A,
        ROCK_L,
        SCROLL_MAGE_DISPEL_MAGIC_A,
        PLATE_MAIL_A,
        CHAIN_MAIL_AC,
        SCALE_MAIL_A,
        AXE_CURSED_A,
        SLING_ENCHANTED_A,
        HELMET_DWARF_B0,
        RING_ADORNMENT_A,
        RATION_M,
        KEY_C,
        SCROLL_CLERIC_PRAYER_A,
        BOOTS_C,
        KENKU_EGG_A,
        KENKU_EGG_B,
        KENKU_EGG_C,
        KENKU_EGG_D,
        RING_ADORNMENT_B,
        KENKU_EGG_F,
        KENKU_EGG_G,
        KENKU_EGG_H,
        KENKU_EGG_I,
        KENKU_EGG_J,
        KEY_DWARFEN_D,
        KEY_DWARFEN_E,
        SCROLL_MAGE_HOLD_PERSON_A,
        STONE_RING_A,
        ROCK_M,
        SCROLL_CLERIC_DISPEL_MAGIC_A,
        SCROLL_CLERIC_CURE_SERIOUS_WOUNDS_A,
        KEY_D,
        NOTHING_B,
        NOTHING_C,
        NOTHING_D,
        NOTHING_E,
        NOTHING_F,
        NOTHING_G,
        SHIELD_DWARFEN_B,
        ROCK_N,
        MACE_E,
        BRACERS_A,
        WAND_OF_MAGIC_MISSILE_B,
        KEY_DWARFEN_F,
        RED_RING_A,
        SCROLL_CLERIC_FLAME_BLADE_B,
        SCROLL_MAGE_FIREBALL_B,
        HALBERD_CHIFTAIN,
        IRON_RATION_M,
        AMULETTE_ADORNMENT_A,
        SCROLL_CLERIC_BLESS_B,
        ARROW_K,
        SCROLL_CLERIC_PROTCT_EVIL_10_A,
        SCROLL_CLERIC_REMOVE_PARALYSIS_A,
        SCROLL_CLERIC_SLOW_POISON_B,
        SCROLL_CLERIC_CREATE_FOOD_A,
        KEY_E,
        AMULETT_LUCK_A,
        RING_PROTECTION_C,
        ARROW_L,
        ARROW_M,
        ARROW_N,
        ARROW_O,
        SLICER,
        BRACERS_DROW_B,
        RED_RING_B,
        SCROLL_MAGE_FEAR_A,
        KEY_JEWELED_B,
        BANDED_MAIL_A,
        ARROW_P,
        ARROW_Q,
        ARROW_R,
        KEY_DROW_A,
        SCROLL_MAGE_LIGHTNING_BOLT_A,
        KEY_F,
        POTION_HEALING_H,
        KEY_DROW_B,
        SCROLL_CLERIC_CURE_LIGHT_WOUNDS_A,
        KEY_JEWELED_C,
        KEY_RUBY_A,
        IGNEOUS_ROCK_C,
        WAND_OF_STICK_A,
        SHIELD_D,
        SCROLL_CLERIC_PRAYER_B,
        SCROLL_CLERIC_NEUTRALIZE_POISON_A,
        SCROLL_CLERIC_CURE_CRITICAL_WOUNDS_A,
        AMULETTE_ADORNMENT_B,
        RED_RING_C,
        RING_SUSTENANCE_B,
        NIGHT_STALKER,
        SCROLL_CLERIC_HOLD_PERSON_B,
        IGNEOUS_ROCK_D,
        KEY_RUBY_B,
        SCROLL_MAGE_INVISIBILITY_10_B,
        DROW_BOW_A,
        KEY_DROW_C,
        SCROLL_CLERIC_PROTECT_EVIL_A,
        BOOTS_DROW_A,
        POTION_EXTRA_HEALING_C,
        SCROLL_CLERIC_RAISE_DEAD_A,
        KEY_RUBY_C,
        KEY_DROW_D,
        KEY_JEWELED_D,
        SCROLL_MAGE_SHIELD_B,
        WAND_OF_LIGHTNING_A,
        PLATE_MAIL_GREAT_BEAUTY_A,
        FLAIL_A,
        KEY_DROW_E,
        ROBE_C,
        SCEPTER_OF_KINGLY_MIGHT,
        SCROLL_MAGE_ICESTORM_A,
        LOCK_PICKS_C,
        KEY_DROW_F,
        SCROLL_CLERIC_DETECT_MAGIC_B,
        POTION_POISON_B,
        SCROLL_MAGE_STONESKIN_A,
        ARROW_S,
        ARROW_T,
        ARROW_U,
        KEY_DROW_G,
        SCROLL_CLERIC_DISPEL_MAGIC_B,
        SCROLL_CLERIC_CURE_SERIOUS_WOUNDS_B,
        SCROLL_MAGE_INVISIBILITY_B,
        SCROLL_CLERIC_FLAME_BLADE_C,
        SCROLL_CLERIC_PROTCT_EVIL_10_B,
        SCROLL_MAGE_ARMOR_B,
        SHIELD_DROW_A,
        SCROLL_CLERIC_RAISE_DEAD_B,
        BOOTS_DROW_B,
        POTION_EXTRA_HEALING_D,
        SPEAR_C,
        WAND_OF_FIREBALL_A,
        SCROLL_CLERIC_RAISE_DEAD_C,
        CHAIN_MAIL_D,
        ROCK_O,
        KEY_DWARFEN_G,
        PLATE_MAIL_B,
        POTION_POISON_C,
        WAND_OF_FROST_D,
        SCROLL_CLERIC_FLAME_BLADE_D,
        SCROLL_CLERIC_CURE_CRITICAL_WOUNDS_B,
        WAND_OF_STICK_B,
        STONE_HOLY_SYMBOL_A,
        ARROW_V,
        ARROW_W,
        KEY_SKULL_B,
        RING_FEATHER_FALL_C,
        POTION_GIANT_STRENGTH_C,
        SCROLL_CLERIC_FLAME_BLADE_E,
        SCROLL_CLERIC_REMOVE_PARALYSIS_B,
        SCROLL_CLERIC_NEUTRALIZE_POISON_B,
        SCROLL_MAGE_CONE_OF_COLD_A,
        WAND_OF_LIGHTNING_B,
        AMULETT_LUCK_B,
        SCROLL_CLERIC_RAISE_DEAD_D,
        STONE_ORB_A,
        KEY_DROW_H,
        ORB_POWER_B,
        SCROLL_CLERIC_RAISE_DEAD_E,
        SLIMY_ROCK_A,
        SLIMY_ROCK_B,
        SLASHER_A,
        BANDED_MAIL_B,
        RING_ADORNMENT_D,
        SCROLL_MAGE_HOLD_MONSTER_A,
        SCROLL_CLERIC_CURE_SERIOUS_WOUNDS_C,
        IRON_RATION_N,
        ROBE_DEFENSE_A,
        FLICKA,
        KEY_DROW_I,
        BONES_HUMAN_A,
        BONES_HUMAN_B,
        BONES_HUMAN_C,
        BONES_HUMAN_D,
        BONES_HUMAN_E,
        RING_PROTECTION_D,
        BRACERS_B,
        LEATHER_ARMOR_B,
        SPEAR_D,
        PLATE_MAIL_C,
        SHIELD_E,
        SEVERIOUS,
        HELMET_B,
        HOLY_SYMBOL_PALADIN_B,
        SHORT_SWORD_B,
        HOLY_SYMBOL_PALADIN_C,
        LEATHER_ARMOR_C,
        BOOTS_D,
        IRON_RATION_O,
        SHORT_SWORD_C,
        LOCK_PICKS_D,
        LEATHER_ARMOR_D,
        BOOTS_E,
        IRON_RATION_P,
        MACE_F,
        SPELL_BOOK_B,
        HOLY_SYMBOL_CLERIC_B,
        ROBE_D,
        IRON_RATION_Q,
        MACE_G,
        SPELL_BOOK_C,
        HOLY_SYMBOL_CLERIC_C,
        ROBE_E,
        DAGGER_C,
        POTION_SPEED_A,
        ARROW_X,
        ARROW_Y,
        ARROW_Z,
        KEY_DWARFEN_H,
        ROCK_P,
        ROCK_Q,
        POTION_EXTRA_HEALING_E,
        ADAMATITE_DART_C,
        DAGGER_D,
        ORB_POWER_C,
        ORB_POWER_D,
        ORB_POWER_E,
        GEM_RED_C,
        POTION_EXTRA_HEALING_F,
        POTION_EXTRA_HEALING_G,
        RING_ADORNMENT_E,
        AMULETT_A,
        WAND_OF_FIREBALL_B,
        ORB_POWER_F,
        POTION_SPEED_B,
        ORB_POWER_G,
        ORB_POWER_H,
        ORB_POWER_I,
        IRON_RATION_R,
        IRON_RATION_S,
        IRON_RATION_T,
        IRON_RATION_U,
        KEY_SKULL_C,
        POTION_INVISIBILITY_A,
        POTION_INVISIBILITY_B,
        POTION_VITALITY_A,
        POTION_VITALITY_B,
        POTION_INVISIBILITY_C,
        POTION_INVISIBILITY_D,
        WAND_OF_MAGIC_MISSILE_C,
        DAGGER_E,
        STONE_SCEPTER_B,
        STONE_DAGGER_B,
        STONE_MEDALLION_B,
        STONE_NECLACE_A,
        STONE_RING_B,
        STONE_HOLY_SYMBOL_B,
        STONE_ORB_B,
        IRON_RATION_V,
        IRON_RATION_W,
        IRON_RATION_X,
        IRON_RATION_Y,
        IRON_RATION_Z,
        POTION_EXTRA_HEALING_H,
        NOTHING_H,
        NOTHING_I,
        NOTHING_J,
        NOTHING_K,
        NOTHING_L,
        NOTHING_M,
        NOTHING_N,
        NOTHING_O,
        POTION_CURE_POISON_C,
        POTION_CURE_POISON_D,
        POTION_CURE_POISON_E,
        POTION_CURE_POISON_F,
        HOLY_SYMBOL_CLERIC_D,
        SPELL_BOOK_D,
        ADAMATITE_DART_D,
        ADAMATITE_DART_E,
        ADAMATITE_DART_F,
        ADAMATITE_DART_G,
        ADAMATITE_DART_H,
        ADAMATITE_DART_I,
        ADAMATITE_DART_J,
        ADAMATITE_DART_K,
        ADAMATITE_DART_L,
        ADAMATITE_DART_M,
        POTION_VITALITY_C,
        SCROLL_MAGE_VAMPIRIC_TOUCH_A,
        SCROLL_MAGE_DETECT_MAGIC_C,
        DAGGER_F,
        RATION_N,
        HELMET_C,
        HELMET_D,
        AXE_C,
        AXE_D,
        HELMET_E,
        HELMET_F,
        AXE_E,
        AXE_F,
        HELMET_G,
        HELMET_H,
        KEY_SILVER_D,
        MACE_H
    };
    
    public static ItemType getType(String s){
        for(ItemType type: ItemType.TYPES){
            if(type.typeAbbreviation.equalsIgnoreCase(s)){
                return type;
            }
        }
        throw new IllegalArgumentException("unknown type "+s);
    }
    
    public static ItemClass getClass(String s){
        for(ItemClass clazz: ItemClass.CLASSES){
            if(clazz.classAbbreviation.equalsIgnoreCase(s)){
                return clazz;
            }
        }
        throw new IllegalArgumentException("unknown clazz "+s);
    }
    
    public static ItemClass[] getClasses(String str){
        ItemClass[] classes = new ItemClass[str.length()];
        for(int i = 0; i < str.length(); i++){
            classes[i] = getClass(""+str.charAt(i));
        }
        return classes;
    }
    public static Item getItem(byte[] data) {
        for (Item item: ITEMS){
            if (Arrays.equals(data, item.id)){
                return item;
            }
        }
        LOGGER.debug("no item found for id {}", Arrays.toString(data));
        return null;
    }
    

}
