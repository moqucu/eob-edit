package com.github.martinfrank.eobedit.generate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ItemsGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemsGenerator.class);

    private static final String TYPES = "src/main/resources/types.txt";
    private static final String CLASSES = "src/main/resources/classes.txt";
    private static final String ITEMS = "src/main/resources/items.txt";
    private static final String DEST = "src/main/java/com/github/martinfrank/eobedit/data/Items.java";

    public static void main(String[] args) throws IOException {
        List<String> typeLines = Files.readAllLines(new File(TYPES).toPath()).stream().
                filter(l -> !l.startsWith("#")).
                filter(l -> !l.trim().isEmpty()).collect(Collectors.toList());
        List<String> classLines = Files.readAllLines(new File(CLASSES).toPath()).stream().
                filter(l -> !l.startsWith("#")).
                filter(l -> !l.trim().isEmpty()).collect(Collectors.toList());
        List<String> itemLines = Files.readAllLines(new File(ITEMS).toPath()).stream().
                filter(l -> !l.startsWith("#")).
                filter(l -> !l.trim().isEmpty()).collect(Collectors.toList());

        List<String> itemsLines = new ArrayList<>();
        createItemListHeader(itemsLines);

        //types
        createItemTypesHeader(itemsLines);
        createItemTypes(itemsLines, typeLines);
        createItemTypesFooter(itemsLines);

        //classes
        createItemClassesHeader(itemsLines);
        createItemClasses(itemsLines, classLines);
        createItemClassesFooter(itemsLines);

        //items
        createItems(itemsLines, itemLines);

        createItemListFooter(itemsLines);
        for (String line : itemsLines) {
            System.out.println(line);
        }

        Files.write(new File(DEST).toPath(),itemsLines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
    }

    private static void createItems(List<String> itemsLines, List<String> itemLines) {
        itemsLines.add("    ");
        for(String item: itemLines){
            LOGGER.debug("classes: "+item);
            String[] entries = item.split(",");
            for (String e: entries){
                LOGGER.debug("E: "+e);
            }
            itemsLines.add("    public static final Item "+entries[0]+" = new Item(\""+entries[1]+"\", \""+entries[2]+"\", getType(\""+entries[3]+"\"), getClasses(\""+entries[4]+"\"), \""+entries[5]+"\", \""+entries[6]+"\");");
        }

        itemsLines.add("    public static final Item[] ITEMS = new Item[]{ ");
        for(int i = 0; i < itemLines.size(); i ++){
            String[] entries = itemLines.get(i).split(",");
            itemsLines.add("        "+entries[0]+(i<itemLines.size()-1?",":""));
        }
        itemsLines.add("    };");
    }

    private static void createItemClassesHeader(List<String> itemsLines) {
        itemsLines.add("    public static class ItemClass{");
        itemsLines.add("        ");
    }

    private static void createItemClasses(List<String> itemsLines, List<String> clazzes) {
        for(String clazz: clazzes){
            String[] entries = clazz.split(",");
            itemsLines.add("        public static final ItemClass "+entries[0]+" = new ItemClass(\""+entries[1]+"\", \""+entries[2]+"\");");
        }

        itemsLines.add("        public static final ItemClass[] CLASSES = new ItemClass[]{ ");
        for(int i = 0; i < clazzes.size(); i ++){
            String[] entries = clazzes.get(i).split(",");
            itemsLines.add("            "+entries[0]+(i<clazzes.size()-1?",":""));
        }
        itemsLines.add("        };");

    }

    private static void createItemClassesFooter(List<String> itemsLines) {
        itemsLines.add("        ");
        itemsLines.add("        public final String classAbbreviation;");
        itemsLines.add("        public final String className;");
        itemsLines.add("        ");
        itemsLines.add("        public ItemClass(String classAbbreviation, String className){");
        itemsLines.add("            this.classAbbreviation = classAbbreviation;");
        itemsLines.add("            this.className = className;");
        itemsLines.add("        }");
        itemsLines.add("        ");
        itemsLines.add("        @Override");
        itemsLines.add("        public String toString(){");
        itemsLines.add("            return className;");
        itemsLines.add("        }");
        itemsLines.add("        ");
        itemsLines.add("    }");
    }


    private static void createItemTypesHeader(List<String> itemsLines) {
        itemsLines.add("    public static class ItemType{");
        itemsLines.add("        ");
    }

    private static void createItemTypes(List<String> itemsLines, List<String> types) {
        for(String type: types){
            String[] entries = type.split(",");
            itemsLines.add("        public static final ItemType "+entries[0]+" = new ItemType(\""+entries[1]+"\", \""+entries[2]+"\", \""+entries[3]+"\");");
        }

        itemsLines.add("        public static final ItemType[] TYPES = new ItemType[]{ ");
        for(int i = 0; i < types.size(); i ++){
            String[] entries = types.get(i).split(",");
            itemsLines.add("            "+entries[0]+(i<types.size()-1?",":""));
        }
        itemsLines.add("        };");

    }

    private static void createItemTypesFooter(List<String> itemsLines) {
        itemsLines.add("        ");
        itemsLines.add("        public final String typeAbbreviation;");
        itemsLines.add("        public final String typeName;");
        itemsLines.add("        public final String typeDescription;");
        itemsLines.add("        ");
        itemsLines.add("        public ItemType(String typeAbbreviation, String typeName, String typeDescription){");
        itemsLines.add("            this.typeAbbreviation = typeAbbreviation;");
        itemsLines.add("            this.typeName = typeName;");
        itemsLines.add("            this.typeDescription = typeDescription;");
        itemsLines.add("        }");
        itemsLines.add("        ");
        itemsLines.add("        @Override");
        itemsLines.add("        public String toString(){");
        itemsLines.add("            return typeName;");
        itemsLines.add("        }");
        itemsLines.add("        ");
        itemsLines.add("    }");
    }

    private static void createItemListFooter(List<String> itemsLines) {
        itemsLines.add("    ");
        itemsLines.add("    public static ItemType getType(String s){");
        itemsLines.add("        for(ItemType type: ItemType.TYPES){");
        itemsLines.add("            if(type.typeAbbreviation.equalsIgnoreCase(s)){");
        itemsLines.add("                return type;");
        itemsLines.add("            }");
        itemsLines.add("        }");
        itemsLines.add("        throw new IllegalArgumentException(\"unknown type \"+s);");
        itemsLines.add("    }");
        itemsLines.add("    ");
        itemsLines.add("    public static ItemClass getClass(String s){");
        itemsLines.add("        for(ItemClass clazz: ItemClass.CLASSES){");
        itemsLines.add("            if(clazz.classAbbreviation.equalsIgnoreCase(s)){");
        itemsLines.add("                return clazz;");
        itemsLines.add("            }");
        itemsLines.add("        }");
        itemsLines.add("        throw new IllegalArgumentException(\"unknown clazz \"+s);");
        itemsLines.add("    }");
        itemsLines.add("    ");
        itemsLines.add("    public static ItemClass[] getClasses(String str){");
        itemsLines.add("        ItemClass[] classes = new ItemClass[str.length()];");
        itemsLines.add("        for(int i = 0; i < str.length(); i++){");
        itemsLines.add("            classes[i] = getClass(\"\"+str.charAt(i));");
        itemsLines.add("        }");
        itemsLines.add("        return classes;");
        itemsLines.add("    }");
        itemsLines.add("    public static Item getItem(byte[] data) {");
        itemsLines.add("        for (Item item: ITEMS){");
        itemsLines.add("            if (Arrays.equals(data, item.id)){");
        itemsLines.add(("                return item;"));
        itemsLines.add("            }");
        itemsLines.add("        }");
        itemsLines.add("        LOGGER.debug(\"no item found for id {}\", Arrays.toString(data));");
        itemsLines.add("        return null;");
        itemsLines.add("    }");
        itemsLines.add("    ");
        itemsLines.add("");
        itemsLines.add("}");
    }

    private static void createItemListHeader(List<String> itemsLines) {
        itemsLines.add("package com.github.martinfrank.eobedit.data;");
        itemsLines.add("");
        itemsLines.add("import java.util.Arrays;");
        itemsLines.add("import org.slf4j.Logger;");
                itemsLines.add("import org.slf4j.LoggerFactory;");
        itemsLines.add("");
        itemsLines.add("public class Items {");
        itemsLines.add("    ");
        itemsLines.add("    private static final Logger LOGGER = LoggerFactory.getLogger(Items.class);");
        itemsLines.add("    ");

    }

}
