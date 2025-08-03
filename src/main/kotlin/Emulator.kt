@file:OptIn(ExperimentalUnsignedTypes::class)
import memory.ROM
import java.io.File
import java.io.FileNotFoundException

class Emulator(inputProvider: () -> String? = ::readln) {
    // this is my facade for the system
    val ROM: ROM = loadProgram(inputProvider)
    val CPU: CPU = CPU(ROM)
    val SCREEN: Screen = Screen()

    fun start() {
        CPU.executeProgramInROM()
    }

    fun stop() {
        TODO()
        // interrupt the CPU
    }

    fun loadProgram(inputProvider: () -> String?): ROM {
        print("Enter the path to the ROM you'd like to load: ")
        val path = inputProvider()
        val file = File(path)

        if (file.exists()) {
            val bytes: UByteArray = file.readBytes().map { it.toUByte() }.toUByteArray()
            val rom = ROM(bytes)
            return rom
        } else {
            println("File not found: $path")
            throw FileNotFoundException()
        }
    }
}

fun main() {
    val emulator: Emulator = Emulator(::readln)
    emulator.start()
}
