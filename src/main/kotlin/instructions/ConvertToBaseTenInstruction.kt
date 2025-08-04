package instructions

class ConvertToBaseTenInstruction: InstructionTemplate {
    override fun parseNibbles(instruction: Int): Map<String, Int> {
        val nibbles = mutableMapOf<String, Int>()
        val uInstruction = instruction.toUInt()
        
        nibbles["src"] = ((uInstruction shr 8) and 0x0fu).toInt()
        return nibbles
    }

    override fun performOperation(nibbles: Map<String, Int>, context: CPU.CPUContext) {
        val value = context.registerMap[nibbles["src"]]?.value
        val baseTenValue = value!!.toString(radix = 10)
        
        // Pad with leading zeros to ensure 3 digits
        val paddedValue = baseTenValue.padStart(3, '0')
        val digits = paddedValue.map { it.digitToInt() }
        
        context.ram.write(context.registers.A, digits[0].toUByte())
        context.ram.write((context.registers.A + 1u).toUShort(), digits[1].toUByte())
        context.ram.write((context.registers.A + 2u).toUShort(), digits[2].toUByte())
    }

    override fun programCounter(context: CPU.CPUContext) {
        context.registers.P = (context.registers.P + 2u).toUShort()
    }
}