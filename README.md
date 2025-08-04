# Computer Emulator - CS5700 Assignment 4

A custom 8-bit computer emulator implemented in Kotlin with support for a 16-instruction CPU architecture, memory management, and a simple text-based screen display.

## Features

- **Custom CPU Architecture**: 16 different instruction types including arithmetic, memory operations, control flow, and I/O
- **Memory Management**: Separate RAM, ROM, and screen buffer with proper memory mapping
- **Timer System**: 60Hz timer that decrements independently of CPU execution
- **Screen Display**: 8x8 character display with ASCII output
- **Observer Pattern**: Real-time screen updates using observer pattern
- **Coroutines**: Multi-threaded execution with separate timer and CPU threads

## Architecture

The emulator follows clean architecture principles with separate packages for:
- `memory/` - RAM, ROM, and ScreenBuffer implementations
- `instructions/` - All 16 instruction implementations using strategy pattern
- Core classes: `CPU`, `Emulator`, `CPURegisters`, `Register`

See `doc/uml/emulator.plantuml` for the complete class diagram.

## Instruction Set

| Opcode | Instruction | Format | Description |
|--------|-------------|---------|-------------|
| 0 | STORE | `0rXX` | Store byte XX in register rX |
| 1 | ADD | `1XYZ` | Add rX + rY → rZ |
| 2 | SUB | `2XYZ` | Subtract rY from rX → rZ |
| 3 | READ | `3X00` | Read memory[A] → rX |
| 4 | WRITE | `4X00` | Write rX → memory[A] |
| 5 | JUMP | `5AAA` | Set PC to AAA |
| 6 | READ_KEYBOARD | `6X00` | Read hex input → rX |
| 7 | SWITCH_MEMORY | `7000` | Toggle M register |
| 8 | SKIP_EQUAL | `8XY0` | Skip if rX == rY |
| 9 | SKIP_NOT_EQUAL | `9XY0` | Skip if rX != rY |
| A | SET_A | `AAAA` | Set A register to AAA |
| B | SET_T | `BAA0` | Set timer to AA |
| C | READ_T | `CX00` | Read timer → rX |
| D | CONVERT_TO_BASE_10 | `DX00` | Convert rX to BCD at A, A+1, A+2 |
| E | CONVERT_BYTE_TO_ASCII | `EXY0` | Convert rX (0-F) to ASCII → rY |
| F | DRAW | `FXYZ` | Draw rX at screen position (Y,Z) |

## Running the Emulator

### Using Gradle (Recommended)
```bash
./gradlew run
```

### Using IntelliJ IDEA
1. Open the project in IntelliJ IDEA
2. Run the `main()` function in `Emulator.kt`

### Using JAR
```bash
./gradlew build
java -jar build/libs/cs5700-assn4-computer-emulator-1.0-SNAPSHOT.jar
```

## Sample Programs

The `roms/` directory contains several sample programs:

- `hello.out` - Displays "HELLO" on screen
- `hello_from_rom.out` - Reads "HELLO" from ROM data section
- `addition.out` - Interactive addition program with keyboard input
- `subtraction.out` - Interactive subtraction program
- `keyboard.out` - Keyboard input demonstration
- `timer.out` - Countdown timer demonstration

## Testing

Run the comprehensive test suite:
```bash
./gradlew test
```

Tests cover:
- All instruction implementations
- Memory management (RAM, ROM, ScreenBuffer)
- CPU register operations
- Observer pattern functionality
- Error conditions and bounds checking

## ROM File Format

ROM files contain 16-bit instructions in big-endian format:
```
0048  # Store 'H' (0x48) in register 0
0145  # Store 'E' (0x45) in register 1
F000  # Draw register 0 at position (0,0)
F101  # Draw register 1 at position (0,1)
```

## Development

### Project Structure
```
src/
├── main/kotlin/
│   ├── memory/           # Memory implementations
│   ├── instructions/     # Instruction implementations
│   ├── CPU.kt           # Main CPU class
│   ├── Emulator.kt      # Emulator facade
│   └── ...
├── test/kotlin/         # Unit tests
└── ...
doc/uml/                 # UML diagrams
roms/                    # Sample ROM files
```

### Key Design Patterns
- **Strategy Pattern**: Instruction execution
- **Observer Pattern**: Screen updates
- **Facade Pattern**: Emulator class
- **Template Method**: Instruction parsing and execution

## Requirements

- JDK 19+
- Kotlin 2.1.21+
- Gradle 8.0+

## License

Educational project for CS5700 - Computer Architecture

