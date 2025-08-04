package instructions

class AddInstruction: InstructionTemplate {
    override fun parseNibbles(instruction: Int): Map<String, Int> {
        val nibbles = mutableMapOf<String, Int>()
        val uInstruction = instruction.toUInt()

        nibbles["src1"] = ((uInstruction shr 8) and 0x0fu).toInt()
        nibbles["src2"] = ((uInstruction and 0xf0u) shr 4).toInt()
        nibbles["dest"] = (uInstruction and 0x0fu).toInt()
        return nibbles
    }

    override fun performOperation(nibbles: Map<String, Int>, context: CPU.CPUContext) {
        val src1: UByte = context.registerMap[nibbles["src1"]]?.value ?: 0u
        val src2: UByte = context.registerMap[nibbles["src2"]]?.value ?: 0u
        val result = src1 + src2
        context.registerMap[nibbles["dest"]]?.value = result.toUByte()
    }

    override fun programCounter(context: CPU.CPUContext) {
        context.registers.P = (context.registers.P + 2u).toUShort()
    }

}