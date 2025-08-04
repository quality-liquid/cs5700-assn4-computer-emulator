package instructions

class WriteInstruction: InstructionTemplate {
    override fun parseNibbles(instruction: Int): Map<String, Int> {
        val nibbles = mutableMapOf<String, Int>()
        val uInstruction = instruction.toUInt()
        
        nibbles["src"] = ((uInstruction shr 8) and 0x0fu).toInt()
        
        return nibbles
    }

    override fun performOperation(nibbles: Map<String, Int>, context: CPU.CPUContext) {
        val srcRegIndex = nibbles["src"]!!
        
        // Validate register index
        if (srcRegIndex !in 0..7) {
            throw IllegalArgumentException("Invalid register index: $srcRegIndex (must be 0-7)")
        }
        
        val register = context.registerMap[srcRegIndex]
        if (register == null) {
            throw IllegalArgumentException("Register r$srcRegIndex not found in register map")
        }
        
        val value = register.value
        
        if (context.registers.M) {
            context.rom.write(context.registers.A, value)
        } else {
            context.ram.write(context.registers.A, value)
        }
    }

    override fun programCounter(context: CPU.CPUContext) {
        context.registers.P = (context.registers.P + 2u).toUShort()
    }
}