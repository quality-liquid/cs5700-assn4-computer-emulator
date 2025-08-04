package instructions

class ConvertByteToASCIIInstruction: InstructionTemplate {
    override fun parseNibbles(instruction: Int): Map<String, Int> {
        val nibbles = mutableMapOf<String, Int>()
        nibbles["src"] = (instruction shr 8) and 0x0f
        nibbles["dest"] = (instruction and 0xf0) shr 4
        return nibbles
    }

    override fun performOperation(nibbles: Map<String, Int>, context: CPU.CPUContext) {
        val value = context.registerMap[nibbles["src"]]!!.value
        if (value > 0xfu) {
            throw IllegalArgumentException("Can't copy value greater than 0xF as ASCII")
        }
        val valueInHex: String = value.toString(radix = 16)
        context.registerMap[nibbles["dest"]]?.value = valueInHex.toCharArray().last().code.toUByte()
    }

    override fun programCounter(context: CPU.CPUContext) {
        context.registers.P = (context.registers.P + 2u).toUShort()
    }
}