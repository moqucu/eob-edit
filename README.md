# eob-edit

A Java savegame editor for **Eye of the Beholder** (DOS, 1991) by Westwood/TSR. It reads and modifies the binary `EOBDATA.SAV` file at the byte level, letting you tweak character stats, inventory, and more through a modern JavaFX GUI.

## Features

- **Stats editing** — Modify STR, INT, WIS, DEX, CON, CHA, HP (current/max), and AC for each character
- **Inventory management** — View and swap items across 27 inventory slots (including equipment and pack) with item images and searchable dropdowns
- **Searchable selection** — Click on any item icon or use the "..." button to open a searchable dialog, making it easy to find specific items among the 400+ available prototypes
- **6-character support** — Switch between all six party slots via a player selector
- **Unsaved changes tracking** — Title bar indicator and confirmation dialogs prevent accidental data loss
- **Game data loading** — Load items directly from the game's PAK files (ITEM.DAT, EOBPAL.COL, ITEMICN.CPS) for authoritative item names, types, and icons
- **Persistent settings** — Game data path is remembered across sessions

## Prerequisites

- Java 25 or later
- Maven 3.x

## Build

### Build standard JAR
```bash
mvn clean package
```
This produces a runnable fat JAR at `target/eob-edit-0.1.0-fat.jar`.

### Build native macOS App
```bash
mvn clean package -Pbundle-macos
```
This produces a standalone `EOBEdit.app` in `target/dist/` with its own Java runtime and an authentic Beholder icon.

## Run

### Via Maven
```bash
mvn javafx:run
```

### Via JAR
```bash
java -jar target/eob-edit-0.1.0-fat.jar
```

### Via macOS App
Double-click `target/dist/EOBEdit.app`.

---

An example savegame is included at `src/main/resources/EOBDATA.SAV` for testing.

## Tests

```bash
mvn test
```

## Project Structure

```
src/main/java/com/github/martinfrank/eobedit/
  App.java              — Entry point, launches JavaFX UI
  data/                 — SavegameFile, PlayerData, GlobalItem, Items, Stat, enums
  event/                — Observer pattern for change tracking
  gui/fx/               — Modern JavaFX UI components
  image/                — ImageProvider (item/portrait/icon handling)
  pak/                  — PakReader, CpsDecoder, EobItemLoader (game file parsing)
```

## License

GPL v3
