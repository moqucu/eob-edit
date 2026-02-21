# GEMINI.md

## Project Overview
`eob-edit` is a Java-based savegame editor for the classic DOS RPG **Eye of the Beholder** (1991). It provides a Swing GUI to modify character stats (STR, INT, WIS, DEX, CON, CHA, HP, AC) and manage inventory. The application can also parse original game data files (PAK) to provide accurate item names and icons.

### Main Technologies
- **Java 8+**: Core application logic.
- **Swing**: Graphical User Interface.
- **Maven**: Project management and build automation.
- **JUnit 4**: Testing framework.
- **SLF4J/Log4j**: Logging infrastructure.

### Architecture
- `com.github.martinfrank.eobedit.App`: Application entry point.
- `com.github.martinfrank.eobedit.data`: Contains data models like `SavegameFile` (binary parsing), `PlayerData`, and `Items`.
- `com.github.martinfrank.eobedit.gui`: Swing components for the main frame, stats, and inventory panels.
- `com.github.martinfrank.eobedit.pak`: Specialized logic for reading and decoding Eye of the Beholder's original PAK and CPS files.
- `com.github.martinfrank.eobedit.event`: Event-driven architecture for tracking changes in savegame data.

## Building and Running

### Build
To compile the project and generate a runnable "fat" JAR:
```bash
mvn clean package
```

### Run
To start the application:
```bash
java -jar target/eob-edit-0.1.0.jar
```
You can also specify a savegame and game data directory as arguments:
```bash
java -jar target/eob-edit-0.1.0.jar /path/to/EOBDATA.SAV --game-data /path/to/eob-game-directory
```

### Tests
To run the automated test suite:
```bash
mvn test
```

### Regenerate Item Catalog
If you modify `src/main/resources/items.txt`, you need to regenerate the `Items.java` source file:
```bash
mvn compile exec:java -Dexec.mainClass="com.github.martinfrank.eobedit.generate.ItemsGenerator"
```

## Development Conventions
- **Code Style**: Follow standard Java coding conventions.
- **Binary Data**: Use `ByteArrayTool` for common byte-level operations.
- **Change Tracking**: Use the classes in `com.github.martinfrank.eobedit.event` to ensure the UI stays in sync with data changes and "unsaved changes" warnings work correctly.
- **Resources**: Item icons and portraits are stored in `src/main/resources/item` and `src/main/resources/portrait`.
- **Testing**: Add new tests in `src/test/java` using JUnit 4. Ensure binary manipulation logic is thoroughly tested.
