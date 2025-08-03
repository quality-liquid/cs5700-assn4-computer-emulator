@file:OptIn(ExperimentalUnsignedTypes::class)
import memory.ROM
import java.io.File

class Emulator {
    val ROM = loadProgram()
    val CPU: CPU = CPU()
    val SCREEN: Screen = Screen()

    fun start() {
        TODO()
    }

    fun stop() {
        TODO()
    }

    fun loadProgram(): ROM? {
        for (i in 1..5) {
            print("Enter the path to the ROM you'd like to load: ")
            val path = readln()
            val file = File(path)

            if (file.exists()) {
                val bytes: UByteArray = file.readLines().map { it.toUByte(radix = 16) }.toUByteArray()
                val rom = ROM(bytes)
                return rom
            } else {
                println("File not found: $path")
            }
        }
        println("you failed to provide a path that works.")
        return null
    }
}
