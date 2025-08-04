package instructions

class DrawInstruction: InstructionTemplate {
    override fun parseNibbles(instruction: Int): Map<String, Int> {
        val nibbles = mutableMapOf<String, Int>()
        val uInstruction = instruction.toUInt()
        
        nibbles["src"] = ((uInstruction shr 8) and 0x0fu).toInt()
        nibbles["row"] = ((uInstruction and 0xf0u) shr 4).toInt()
        nibbles["col"] = (uInstruction and 0x0fu).toInt()
        return nibbles
    }

    override fun performOperation(nibbles: Map<String, Int>, context: CPU.CPUContext) {
        val srcRegIndex = nibbles["src"]!!
        val value = context.registerMap[srcRegIndex]!!.value
        
        if (value > 0x7fu) {
            throw IllegalArgumentException("Can't write byte greater than 127 to screen")
        }
        
        // Use literal row/col values from instruction, not register values
        val row = nibbles["row"]!!.toUByte()
        val col = nibbles["col"]!!.toUByte()
        
        // Bounds check for 8x8 screen
        if (row >= 8u) {
            throw IllegalArgumentException("Row value $row out of bounds (must be 0-7)")
        }
        if (col >= 8u) {
            throw IllegalArgumentException("Col value $col out of bounds (must be 0-7)")
        }
        
        // Calculate address (8x8 screen buffer)
        val addr = (8u * row) + col
        
        context.screenBuffer.write(addr.toUShort(), value)
    }

    override fun programCounter(context: CPU.CPUContext) {
        context.registers.P = (context.registers.P + 2u).toUShort()
    }
}