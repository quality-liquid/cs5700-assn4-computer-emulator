package instructions

class ReadInstruction: InstructionTemplate {
    override fun parseNibbles(instruction: Int): Map<String, Int> {
        val nibbles = mutableMapOf<String, Int>()
        val uInstruction = instruction.toUInt()
        
        nibbles["dest"] = ((uInstruction shr 8) and 0x0fu).toInt()
        return nibbles
    }

    override fun performOperation(nibbles: Map<String, Int>, context: CPU.CPUContext) {
        if (context.registers.M) {
            val result: UByte = context.rom.read(context.registers.A)
            context.registerMap[nibbles["dest"]]?.value = result
        } else {
            val result: UByte = context.ram.read(context.registers.A)
            context.registerMap[nibbles["dest"]]?.value = result
        }
    }

    override fun programCounter(context: CPU.CPUContext) {
        context.registers.P = (context.registers.P + 2u).toUShort()
    }
}