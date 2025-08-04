package instructions

class SkipEqualInstruction: InstructionTemplate {
    override fun parseNibbles(instruction: Int): Map<String, Int> {
        val nibbles = mutableMapOf<String, Int>()
        nibbles["src1"] = (instruction shr 8) and 0x0f
        nibbles["src2"] = (instruction and 0xf0) shr 4
        return nibbles
    }

    override fun performOperation(nibbles: Map<String, Int>, context: CPU.CPUContext) {
        val src1: UByte = context.registerMap[nibbles["src1"]]!!.value
        val src2: UByte = context.registerMap[nibbles["src2"]]!!.value

        if (src1 == src2) {
            context.registers.P = (context.registers.P + 2u).toUShort()
        }
    }

    override fun programCounter(context: CPU.CPUContext) {
        context.registers.P = (context.registers.P + 2u).toUShort()
    }
}