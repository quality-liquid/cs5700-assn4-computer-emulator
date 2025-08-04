package instructions

class DrawInstruction: InstructionTemplate {
    override fun parseNibbles(instruction: Int): Map<String, Int> {
        val nibbles = mutableMapOf<String, Int>()
        nibbles["src"] = (instruction shr 8) and 0x0f
        nibbles["row"] = (instruction and 0xf0) shr 4
        nibbles["col"] = instruction and 0x0f
        return nibbles
    }

    override fun performOperation(nibbles: Map<String, Int>, context: CPU.CPUContext) {
        val value = context.registerMap[nibbles["src"]]!!.value
        if (value > 0x7fu) {
            throw IllegalArgumentException("Can't write byte greater than 127 to screen")
        }
        val row = context.registerMap[nibbles["row"]]!!.value
        val col = context.registerMap[nibbles["col"]]!!.value
        val addr = (8u * row) + col
        context.screenBuffer.write(addr.toUShort(), value)
    }

    override fun programCounter(context: CPU.CPUContext) {
        context.registers.P = (context.registers.P + 2u).toUShort()
    }
}