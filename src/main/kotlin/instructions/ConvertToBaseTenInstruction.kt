package instructions

class ConvertToBaseTenInstruction: InstructionTemplate {
    override fun parseNibbles(instruction: Int): Map<String, Int> {
        val nibbles = mutableMapOf<String, Int>()
        nibbles["src"] = (instruction shr 8) and 0x0f
        return nibbles
    }

    override fun performOperation(nibbles: Map<String, Int>, context: CPU.CPUContext) {
        val value = context.registerMap[nibbles["src"]]?.value
        val baseTenValue = value!!.toString(radix = 10)
        val digits = baseTenValue.toString().map { it.digitToInt() }
        context.ram.write(context.registers.A, digits[0].toUByte())
        context.ram.write((context.registers.A + 1u).toUShort(), digits[1].toUByte())
        context.ram.write((context.registers.A + 2u).toUShort(), digits[2].toUByte())
    }

    override fun programCounter(context: CPU.CPUContext) {
        context.registers.P = (context.registers.P + 2u).toUShort()
    }
}