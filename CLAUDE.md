# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Eye of the Beholder Savegame Editor — a Java Swing GUI for reading and modifying Eye of the Beholder (DOS RPG) save files. Operates at the byte level on binary savegame files (6 characters × 243 bytes each, fixed offsets for all attributes).

## Build & Test Commands

```bash
# Build
mvn clean compile

# Run all tests
mvn test

# Run a single test class
mvn test -Dtest=ImageTest

# Run a single test method
mvn test -Dtest=ImageTest#testPortraits

# Package fat JAR
mvn package

# Run GUI (no args = file chooser on open)
java -jar target/eob-edit-0.1.0.jar [savegame-file] [--game-data /path/to/game-dir]
```

## Architecture

### Data flow
`SavegameFile` reads the binary `.SAV` into a `byte[]`, slices it into 6 `PlayerData` objects (one per character slot, each holding a 243-byte subarray). All edits mutate those subarrays in-place. `SavegameFile.save()` writes the merged bytes back to disk. Changes propagate to the GUI via `PlayerDataChangeEventListener` / `ChangeEventType` observer pattern — `EditorFrame` implements the listener and routes events to `StatsPanel` or `InventoryPanel`.

### Item catalog — two modes
Items can come from two sources, selected at runtime:

1. **Built-in** (`Items.ITEMS[]`): ~460 items hardcoded in `Items.java` (generated from `items.txt`/`types.txt` via `ItemsGenerator`). Lookup is a linear scan matching the 2-byte LE ID.
2. **Game data** (`Items.dynamicItems[]`): Loaded at runtime from the game's PAK files via `pak/EobItemLoader`. When active, `Items.getItem(byte[])` uses direct index lookup (2-byte LE → array index) instead of scanning. `Items.isUsingGameData()` / `Items.getAllItems()` control which source is active. The game data path is persisted via `java.util.prefs.Preferences`.

### PAK file pipeline (`pak/` package)
- `PakReader` — extracts named files from `.PAK` archives (null-terminated filename directory + 4-byte LE offsets)
- `CpsDecoder` — decompresses CPS sprite sheets (Frame4 LZSS variant, type byte in header)
- `EobItemLoader` — orchestrates: reads `ITEM.DAT` (uint16 count + 14-byte records + 35-byte name strings), loads palette from `EOBPAL.COL`, decodes `ITEMICN.CPS` (320×200 indexed), extracts 16×16 icons scaled 5× as `BufferedImage[]`

### GUI
`EditorFrame` owns the menu bar (File + Settings), player selector combo, `StatsPanel`, `InventoryPanel`, and a status bar showing the active item source. `InventoryPanel.reloadItems()` must be called after switching item sources to repopulate the 14 `JComboBox` models.

### Key conventions
- Java 8 source/target compatibility
- JUnit 4 (not JUnit 5)
- `Items.java` is **generated** — edit `items.txt`/`types.txt` and run `ItemsGenerator`, never edit directly
- Item 2-byte IDs are little-endian: `id[0]` = low byte, `id[1]` = high byte; `getId()` formats them as a 4-char uppercase hex string used as the PNG filename key (e.g., `0400.PNG`)
- `ImageProvider.getItem()` falls through to PAK icons when game data is loaded, then falls back to classpath PNGs
