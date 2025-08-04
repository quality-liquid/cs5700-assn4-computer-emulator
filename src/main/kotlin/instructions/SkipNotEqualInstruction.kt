package instructions

class SkipNotEqualInstruction: InstructionTemplate {
    override fun parseNibbles(instruction: Int): Map<String, Int> {
        val nibbles = mutableMapOf<String, Int>()
        val uInstruction = instruction.toUInt()
        
        nibbles["src1"] = ((uInstruction shr 8) and 0x0fu).toInt()
        nibbles["src2"] = ((uInstruction and 0xf0u) shr 4).toInt()
        return nibbles
    }

    override fun performOperation(nibbles: Map<String, Int>, context: CPU.CPUContext) {
        val src1: UByte = context.registerMap[nibbles["src1"]]!!.value
        val src2: UByte = context.registerMap[nibbles["src2"]]!!.value

        if (src1 != src2) {
            context.registers.P = (context.registers.P + 2u).toUShort()
        }
    }

    override fun programCounter(context: CPU.CPUContext) {
        context.registers.P = (context.registers.P + 2u).toUShort()
    }
}