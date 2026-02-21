package com.github.martinfrank.eobedit.data;

import com.github.martinfrank.eobedit.event.ChangeEventType;
import com.github.martinfrank.eobedit.event.PlayerDataChangeEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SavegameFile {

    public static final int AMOUNT_CHARACTERS = 6;
    public static final int CHARACTER_DATA_LENGTH = 243;
    public static final int GLOBAL_ITEM_COUNT = 500;
    public static final int GLOBAL_ITEM_SIZE = 14;
    // 6*243 + 6*2 (party shorts) + 52 (inf-skip bytes)
    public static final int GLOBAL_ITEMS_OFFSET = 1522;

    private static final Logger LOGGER = LoggerFactory.getLogger(SavegameFile.class);

    private File file;
    private byte[] content;
    private final PlayerData[] playerData = new PlayerData[AMOUNT_CHARACTERS];
    private final GlobalItem[] globalItems = new GlobalItem[GLOBAL_ITEM_COUNT];
    private boolean hasUnsavedChanges = false;
    private PlayerDataChangeEventListener listener;

    public SavegameFile() {
    }

    public void load(String filename) throws IOException {
        if (filename == null) {
            throw new IllegalArgumentException("filename must be provided");
        }
        File file = new File(filename);
        load(file);
        if (!file.exists()) {
            throw new IllegalArgumentException("file " + filename + " does not exist");
        }
        reload();
    }

    public void load(File file) throws IOException {
        this.file = file;
        if (!file.exists()) {
            throw new IllegalArgumentException("file " + file.getName() + " does not exist");
        }
        reload();
    }

    public void reload() throws IOException {
        content = Files.readAllBytes(file.toPath());
        copyContentIntoPlayerData();
        parseGlobalItems();
        if (listener != null) {
            Arrays.stream(playerData).forEach(p -> p.setPlayerDataChangeListener(listener));
            listener.playerDataChanged(0, ChangeEventType.LOAD_DATA);
        }
        LOGGER.debug("successfully loaded file {}", file.getName());
    }

    private void copyContentIntoPlayerData() {
        for (int i = 0; i < AMOUNT_CHARACTERS; i++) {
            int position = i * CHARACTER_DATA_LENGTH;
            playerData[i] = new PlayerData(i, ByteArrayTool.copy(content, position, CHARACTER_DATA_LENGTH));
        }
    }

    private void parseGlobalItems() {
        if (content.length < GLOBAL_ITEMS_OFFSET + GLOBAL_ITEM_COUNT * GLOBAL_ITEM_SIZE) {
            LOGGER.warn("Save file too short to contain global items (size={})", content.length);
            for (int i = 0; i < GLOBAL_ITEM_COUNT; i++) {
                globalItems[i] = new GlobalItem();
            }
            return;
        }
        for (int i = 0; i < GLOBAL_ITEM_COUNT; i++) {
            int base = GLOBAL_ITEMS_OFFSET + i * GLOBAL_ITEM_SIZE;
            GlobalItem gi = new GlobalItem();
            gi.nameUnid = content[base]     & 0xFF;
            gi.nameId   = content[base + 1] & 0xFF;
            gi.flags    = content[base + 2] & 0xFF;
            gi.icon     = content[base + 3] & 0xFF;
            gi.type     = content[base + 4] & 0xFF;
            gi.pos      = content[base + 5] & 0xFF;
            gi.block    = (content[base + 6] & 0xFF) | ((content[base + 7] & 0xFF) << 8);
            gi.next     = (content[base + 8] & 0xFF) | ((content[base + 9] & 0xFF) << 8);
            gi.prev     = (content[base + 10] & 0xFF) | ((content[base + 11] & 0xFF) << 8);
            gi.level    = content[base + 12] & 0xFF;
            gi.value    = content[base + 13] & 0xFF;
            gi.displayName = Items.getItemNameString(gi.nameId);
            if (gi.displayName == null) {
                gi.displayName = gi.isEmpty() ? "(empty)" : "Item #" + gi.nameId;
            }
            globalItems[i] = gi;
        }
    }

    private void writeGlobalItems() {
        if (content.length < GLOBAL_ITEMS_OFFSET + GLOBAL_ITEM_COUNT * GLOBAL_ITEM_SIZE) {
            return;
        }
        for (int i = 0; i < GLOBAL_ITEM_COUNT; i++) {
            int base = GLOBAL_ITEMS_OFFSET + i * GLOBAL_ITEM_SIZE;
            GlobalItem gi = globalItems[i];
            content[base]      = (byte) gi.nameUnid;
            content[base + 1]  = (byte) gi.nameId;
            content[base + 2]  = (byte) gi.flags;
            content[base + 3]  = (byte) gi.icon;
            content[base + 4]  = (byte) gi.type;
            content[base + 5]  = (byte) gi.pos;
            content[base + 6]  = (byte) (gi.block & 0xFF);
            content[base + 7]  = (byte) ((gi.block >> 8) & 0xFF);
            content[base + 8]  = (byte) (gi.next & 0xFF);
            content[base + 9]  = (byte) ((gi.next >> 8) & 0xFF);
            content[base + 10] = (byte) (gi.prev & 0xFF);
            content[base + 11] = (byte) ((gi.prev >> 8) & 0xFF);
            content[base + 12] = (byte) gi.level;
            content[base + 13] = (byte) gi.value;
        }
    }

    public void save() throws IOException {
        copyPlayerDataIntoContent();
        writeGlobalItems();
        Files.write(file.toPath(), content, StandardOpenOption.WRITE);
        resetUnsavedChanges();
        LOGGER.debug("successfully written file {}", file.getName());
    }

    public void resetUnsavedChanges() {
        Arrays.stream(playerData).forEach(PlayerData::resetHasUnsavedChanged);
    }

    private void copyPlayerDataIntoContent() {
        for (int i = 0; i < AMOUNT_CHARACTERS; i++) {
            int position = i * CHARACTER_DATA_LENGTH;
            System.arraycopy(playerData[i].getContent(), 0, content, position, CHARACTER_DATA_LENGTH);
        }
    }

    public PlayerData getPlayer(int i) {
        return playerData[i];
    }

    public GlobalItem[] getGlobalItems() {
        return globalItems;
    }

    public boolean hasUnsavedChanges() {
        if (content == null) {
            return false;
        }
        return Arrays.stream(playerData).anyMatch(PlayerData::hasUnsavedChanges);
    }

    public void registerChangeListener(PlayerDataChangeEventListener listener) {
        this.listener = listener;
    }

    /** Re-resolves displayName on all global items after nameTable becomes available. */
    public void resolveGlobalItemNames() {
        for (GlobalItem gi : globalItems) {
            if (gi == null) continue;
            String name = Items.getItemNameString(gi.nameId);
            if (name != null) {
                gi.displayName = name;
            }
        }
    }

    /** Returns the set of global item indices currently referenced in any character's inventory. */
    public Set<Integer> getReferencedGlobalIndices() {
        Set<Integer> refs = new HashSet<>();
        for (int c = 0; c < AMOUNT_CHARACTERS; c++) {
            if (playerData[c] == null) continue;
            for (int s = 0; s < PlayerData.INVENTORY_SLOT_AMOUNT; s++) {
                int idx = playerData[c].getInventoryIndex(s);
                if (idx > 0) refs.add(idx);
            }
        }
        return refs;
    }

    /** Finds the first global slot index that is empty and not referenced. Index 0 is reserved (null item). */
    public int findFreeGlobalSlot() {
        Set<Integer> refs = getReferencedGlobalIndices();
        for (int i = 1; i < GLOBAL_ITEM_COUNT; i++) {
            if (globalItems[i].isEmpty() && !refs.contains(i)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Assigns a prototype Item to a character's inventory slot.
     * Reuses the existing global slot if already carried by this char/slot; otherwise finds a free slot.
     */
    public void assignItem(int charIdx, int slot, Item proto) {
        int currentIdx = playerData[charIdx].getInventoryIndex(slot);
        int targetIdx;
        if (currentIdx > 0 && globalItems[currentIdx].isCarried()) {
            targetIdx = currentIdx;
        } else {
            targetIdx = findFreeGlobalSlot();
            if (targetIdx < 0) {
                LOGGER.warn("No free global item slot available");
                return;
            }
        }
        GlobalItem gi = globalItems[targetIdx];
        gi.nameUnid = proto.nameId >= 0 ? proto.nameId : 0;
        gi.nameId   = proto.nameId >= 0 ? proto.nameId : 0;
        gi.type     = proto.rawType >= 0 ? proto.rawType : 0;
        gi.icon     = proto.iconIndex >= 0 ? proto.iconIndex : 0;
        gi.flags    = 0;
        gi.value    = 0;
        gi.level    = 0xFF;
        gi.block    = charIdx;
        gi.pos      = slot;
        gi.next     = 0;
        gi.prev     = 0;
        gi.displayName = proto.description;
        playerData[charIdx].setInventoryIndex(slot, targetIdx);
    }

    /** Clears an inventory slot: zeros out the global item entry and sets the slot index to 0. */
    public void clearInventorySlot(int charIdx, int slot) {
        int currentIdx = playerData[charIdx].getInventoryIndex(slot);
        if (currentIdx > 0) {
            globalItems[currentIdx] = new GlobalItem();
        }
        playerData[charIdx].setInventoryIndex(slot, 0);
    }
}
