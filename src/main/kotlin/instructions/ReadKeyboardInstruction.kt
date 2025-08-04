package instructions

class ReadKeyboardInstruction: InstructionTemplate {
    override fun parseNibbles(instruction: Int): Map<String, Int> {
        val nibbles = mutableMapOf<String, Int>()
        nibbles["dest"] = (instruction shr 8) and 0x0f
        return nibbles
    }

    override fun performOperation(nibbles: Map<String, Int>, context: CPU.CPUContext) {
        println("Enter up to one byte as one or two hex digits (0-f): ")
        val value: String? = readln()
        val valueAsByte: UByte = value?.toUByte() ?: 0u
        context.registerMap[nibbles["dest"]]?.value = valueAsByte
    }

    override fun programCounter(context: CPU.CPUContext) {
        context.registers.P = (context.registers.P + 2u).toUShort()
    }
}