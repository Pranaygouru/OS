/*
* CPU performs arithmetic operations looping infinitely by fetching instructions of job
* from the memory.Cpu contains branch instructions arithmetic operations like add,sub
* and conditional operators.It contains eight 16 bit general purpose registers where
* all the arithmetic is done in 2's complement.A system wide named CLOCK is maintained
* through out the program where clock is incremented by one virtual time unit.If an i/o
* instruction is encountered CLOCK is incremented by 10 virtual time units.If a trap
* instruction is occurred the control is transferred to the system and the next job from
* the queue is fetched and loaded to perform its corresponding operations.
*/
//Import statements
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
@SuppressWarnings("unchecked")
public class CPU {
int[] GPR = new int[8];
String InsFirstByte = "";
String InsSecondByte = "";
private int destinationRegister;
int cpureturnvalue = 0;
private int sourceRegister;
private int secondSourceRegister;
private int Immeditate6;
private int Immeditate12;
private int halt1;
public static String errorZ;
private static HashMap<String,String> InstructionMap = new HashMap<>();

// Adding the instructions hashmap used for tracefile
static void init(){
InstructionMap.put("0000","add");
InstructionMap.put("0001","addi");
InstructionMap.put("0010","sub");
InstructionMap.put("0011","subi");
InstructionMap.put("1000","load");
InstructionMap.put("1001","store");
InstructionMap.put("1010","move");
InstructionMap.put("1011","movei");
InstructionMap.put("1101","seq");
InstructionMap.put("1110","sgt");
InstructionMap.put("1111","sne");
InstructionMap.put("0111","beqz");
InstructionMap.put("1100","bnez");
InstructionMap.put("0100","trap");
InstructionMap.put("0101","lock");
InstructionMap.put("0110","unlock");
}
// Method of CPU
public int CPU(int PC,int y,Object ob) {
PCB object = (PCB)ob;
Scanner sc = new Scanner(System.in);
object.clockuponArrival = SYSTEM.clock;
cpureturnvalue = 0;
while (true) {
object.traceFile = new String[]{"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ",};
// Fetching first byte from the memory
    if (PC<256) {
        InsFirstByte = MEMORY.MEMORY("READ", PC, "");
// Fetching Second byte from the memory.
        InsSecondByte = MEMORY.MEMORY("READ", PC + 1, "");
    }
if ((InsFirstByte == "error") || (InsSecondByte == "error")) {
break;
}
//  Incrementing the program counter value.
PC = PC + 2;
// Word wrap for trace file
if (PC < 10)
object.traceFile[0] = "0" + PC + " ";
else
object.traceFile[0] = "" + PC + " ";
String Instruction = InsFirstByte + InsSecondByte;
String opcode = Instruction.substring(0, 4);
// Increasing System clock with one virtual time unit.
SYSTEM.clock = SYSTEM.clock + 1;
SYSTEM.ExecutionTime = SYSTEM.ExecutionTime + 1;
int exec = object.getExecutionTime();
object.setExecutionTime(exec + 1);
int infiniteloopCheck = object.getExecutionTime();
if (infiniteloopCheck > 1000)
{
object.setNatureOfTermination("ABNORMAL");
halt1 = 3;
SYSTEM.dumpobj = object;
SYSTEM.N = 5;
object.setEndHexSystemClock(SYSTEM.intToHex(SYSTEM.clock));
object.setErrorMessage(ERRORHANDLER.getErrorMap(SYSTEM.N));
SYSTEM.printOutputFile(object);
MEMORY.freeMemory(object);
break;
}
// Switch case to perform the operations and fetch instructions.
switch (opcode) {
case "0000":  // Add
destinationRegister = Integer.parseInt(Instruction.substring(4, 7), 2);
sourceRegister = Integer.parseInt(Instruction.substring(7, 10), 2);
secondSourceRegister = Integer.parseInt(Instruction.substring(10, 13), 2);
object.traceFile[4] = object.GPR[destinationRegister] + "@" + object.GPR[sourceRegister] + "@" + object.GPR[secondSourceRegister] + " ";
object.GPR[destinationRegister] = object.GPR[sourceRegister] + object.GPR[secondSourceRegister];
object.traceFile[1] = InstructionMap.get(opcode) + " r" + destinationRegister + ",r" + sourceRegister + ",r" + secondSourceRegister + " ";
object.traceFile[2] = "r" + destinationRegister + "@" + "r" + sourceRegister + "@" + "r" + secondSourceRegister + " ";
object.traceFile[6] = object.GPR[destinationRegister] + "@" + object.GPR[sourceRegister] + "@" + object.GPR[secondSourceRegister] + " ";
break;
case "0001":  // Addi
destinationRegister = Integer.parseInt(Instruction.substring(4, 7), 2);
Immeditate6 = SYSTEM.Immediate6(Instruction.substring(10, 16));
sourceRegister = Integer.parseInt(Instruction.substring(7, 10), 2);
object.traceFile[4] = object.GPR[destinationRegister] + "@" + object.GPR[sourceRegister] + " ";
object.GPR[destinationRegister] = object.GPR[sourceRegister] + Immeditate6;
object.traceFile[1] = InstructionMap.get(opcode) + " r" + destinationRegister + ",r" + sourceRegister + "," + Immeditate6 + " ";
object.traceFile[2] = "r" + destinationRegister + "@" + "r" + sourceRegister + " ";
object.traceFile[6] = object.GPR[destinationRegister] + "@" + object.GPR[sourceRegister] + " ";
break;
case "0010":  // Sub
sourceRegister = Integer.parseInt(Instruction.substring(7, 10), 2);
destinationRegister = Integer.parseInt(Instruction.substring(4, 7), 2);
secondSourceRegister = Integer.parseInt(Instruction.substring(10, 13), 2);
object.traceFile[4] = object.GPR[destinationRegister] + "@" + object.GPR[sourceRegister] + "@" + object.GPR[secondSourceRegister] + " ";
object.GPR[destinationRegister] = object.GPR[sourceRegister] - object.GPR[secondSourceRegister];
object.traceFile[1] = InstructionMap.get(opcode) + " r" + destinationRegister + ",r" + sourceRegister + ",r" + secondSourceRegister + " ";
object.traceFile[2] = "r" + destinationRegister + "@" + "r" + sourceRegister + "@" + "r" + secondSourceRegister + " ";
object.traceFile[6] = object.GPR[destinationRegister] + "@" + object.GPR[sourceRegister] + "@" + object.GPR[secondSourceRegister] + " ";
break;
case "0011":  // Subi
destinationRegister = Integer.parseInt(Instruction.substring(4, 7), 2);
sourceRegister = Integer.parseInt(Instruction.substring(7, 10), 2);
Immeditate6 = SYSTEM.Immediate6(Instruction.substring(10, 16));
object.traceFile[4] = object.GPR[destinationRegister] + "@" + object.GPR[sourceRegister] + " ";
object.GPR[destinationRegister] = object.GPR[sourceRegister] - Immeditate6;
object.traceFile[1] = InstructionMap.get(opcode) + " r" + destinationRegister + ",r" + sourceRegister + "," + Immeditate6 + " ";
object.traceFile[2] = "r" + destinationRegister + "@" + "r" + sourceRegister + " ";
object.traceFile[6] = object.GPR[destinationRegister] + "@" + object.GPR[sourceRegister] + " ";
break;
case "1000":  // Load
Immeditate6 = SYSTEM.Immediate6(Instruction.substring(10, 16));
sourceRegister = Integer.parseInt(Instruction.substring(7, 10), 2);
destinationRegister = Integer.parseInt(Instruction.substring(4, 7), 2);
object.traceFile[4] = object.GPR[destinationRegister] + "@" + object.GPR[sourceRegister] + " ";
int val = object.GPR[sourceRegister] + Immeditate6 + object.getPartitionStartAddress();
String Bytes1, comp = null;
Bytes1 = "00";
if (val > object.getNewloadstartaddress() || val % 2 != 0) {
    object.setNatureOfTermination("ABNORMAL");
    SYSTEM.dumpobj = object;
    SYSTEM.N = 2;
    object.setEndHexSystemClock(SYSTEM.intToHex(SYSTEM.clock));
    object.setErrorMessage(ERRORHANDLER.getErrorMap(SYSTEM.N));
    SYSTEM.printOutputFile(object);
    MEMORY.freeMemory(object);
    halt1 = 3;
    break;
}
try {
    comp = MEMORY.MEMORY("READ", val, "");
    if (comp != "lock")
        if (val>0) {
            String sp = MEMORY.mainMemory[val];
            if (sp!=null)
            object.traceFile[5] = SYSTEM.binaryToHex(MEMORY.MEMORY("READ", val, "")) + " ";
        }
    if (comp != "lock") {
        if (val>0) {
            Bytes1 = MEMORY.MEMORY("READ", val, "");
            Bytes1 = Bytes1 + MEMORY.MEMORY("READ", val + 1, "");
        }
    }
} catch (Exception e) {

    SYSTEM.N = 2;
    ERRORHANDLER.getErrorMap(SYSTEM.N);
    errorZ = "error";
    break;
}
if (comp != "lock") {
    if (comp.indexOf(0) == '1') {
        object.GPR[destinationRegister] = SYSTEM.Immediate6(Bytes1);
    }
}
if (comp != "lock")
    object.GPR[destinationRegister] = SYSTEM.binaryToDecimal(Bytes1);
object.traceFile[1] = InstructionMap.get(opcode) + " r" + destinationRegister + "," + Immeditate6 + "(r" + sourceRegister + ")" + " ";
object.traceFile[2] = "r" + destinationRegister + "@" + "r" + sourceRegister + " ";
object.traceFile[6] = object.GPR[destinationRegister] + "@" + object.GPR[sourceRegister] + " ";
object.traceFile[3] = "" + val + " ";
if (comp != "lock")
    object.traceFile[7] = SYSTEM.binaryToHex(MEMORY.MEMORY("READ", val, "")) + " ";
break;
case "1001": // Store
destinationRegister = Integer.parseInt(Instruction.substring(4, 7), 2);
sourceRegister = Integer.parseInt(Instruction.substring(7, 10), 2);
Immeditate6 = SYSTEM.Immediate6(Instruction.substring(10, 16));
object.traceFile[4] = object.GPR[destinationRegister] + "@" + object.GPR[sourceRegister] + " ";
int val2 = object.GPR[destinationRegister] + Immeditate6 + object.getPartitionStartAddress();
if (MEMORY.MEMORY("READ", val2, "") != "lock") {
    object.traceFile[5] = SYSTEM.binaryToHex(MEMORY.MEMORY("READ", val2, "")) + " ";
    String twoBytes = SYSTEM.intToBinary(object.GPR[sourceRegister]);
    if ( val2 > object.getNewloadstartaddress() || val2 % 2 != 0) {
        object.setNatureOfTermination("ABNORMAL");
        SYSTEM.dumpobj = object;
        SYSTEM.N = 2;
        object.setEndHexSystemClock(SYSTEM.intToHex(SYSTEM.clock));
        object.setErrorMessage(ERRORHANDLER.getErrorMap(SYSTEM.N));
        SYSTEM.printOutputFile(object);
        MEMORY.freeMemory(object);
        halt1 = 3;
        break;
    }
    try {
        MEMORY.MEMORY("WRITE", val2, twoBytes.substring(0, 8));
        MEMORY.MEMORY("WRITE", val2 + 1, twoBytes.substring(8, 16));
    } catch (Exception e) {

        object.setNatureOfTermination("ABNORMAL");
        SYSTEM.N = 2;
        SYSTEM.dumpobj = object;
        object.setEndHexSystemClock(SYSTEM.intToHex(SYSTEM.clock));
        object.setErrorMessage(ERRORHANDLER.getErrorMap(SYSTEM.N));
        SYSTEM.printOutputFile(object);
        MEMORY.freeMemory(object);
        halt1 = 3;
        break;
    }
    object.traceFile[7] = SYSTEM.binaryToHex(MEMORY.MEMORY("READ", val2, "")) + " ";
}
object.traceFile[2] = "r" + destinationRegister + "@" + "r" + sourceRegister + " ";
object.traceFile[6] = object.GPR[destinationRegister] + "@" + object.GPR[sourceRegister] + " ";
object.traceFile[3] = "" + val2 + " ";
object.traceFile[1] = InstructionMap.get(opcode) + Immeditate6 + "(r" + sourceRegister + ")" + ", r" + destinationRegister + " ";
break;
case "1010":  // Move
destinationRegister = Integer.parseInt(Instruction.substring(4, 7), 2);
sourceRegister = Integer.parseInt(Instruction.substring(7, 10), 2);
secondSourceRegister = Integer.parseInt(Instruction.substring(10, 13), 2);
object.traceFile[4] = object.GPR[destinationRegister] + "@" + object.GPR[sourceRegister] + " ";
object.GPR[destinationRegister] = object.GPR[sourceRegister];
if (secondSourceRegister != 0) {
    object.setNatureOfTermination("ABNORMAL");
    SYSTEM.N = 1;
    SYSTEM.dumpobj = object;
    object.setEndHexSystemClock(SYSTEM.intToHex(SYSTEM.clock));
    object.setErrorMessage(ERRORHANDLER.getErrorMap(SYSTEM.N));
    SYSTEM.printOutputFile(object);
    MEMORY.freeMemory(object);
    halt1 = 3;
    break;
}
object.traceFile[2] = "r" + destinationRegister + "@" + "r" + sourceRegister + " ";
object.traceFile[6] = object.GPR[destinationRegister] + "@" + object.GPR[sourceRegister] + " ";
object.traceFile[1] = InstructionMap.get(opcode) + " r" + destinationRegister + ",r" + sourceRegister + " ";
break;
case "1011":  // Movei
destinationRegister = Integer.parseInt(Instruction.substring(4, 7), 2);
Immeditate6 = SYSTEM.Immediate6(Instruction.substring(10, 16));
sourceRegister = Integer.parseInt(Instruction.substring(7, 10), 2);
object.traceFile[4] = object.GPR[destinationRegister] + "@" + object.GPR[sourceRegister] + " ";
object.GPR[destinationRegister] = Immeditate6;
if (sourceRegister != 0) {
    object.setNatureOfTermination("ABNORMAL");
    SYSTEM.N = 1;
    SYSTEM.dumpobj = object;
    object.setEndHexSystemClock(SYSTEM.intToHex(SYSTEM.clock));
    object.setErrorMessage(ERRORHANDLER.getErrorMap(SYSTEM.N));
    SYSTEM.printOutputFile(object);
    MEMORY.freeMemory(object);
    halt1 = 3;
    break;
}
object.traceFile[1] = InstructionMap.get(opcode) + " r" + destinationRegister + "," + Immeditate6 + ",r" + sourceRegister + " ";
object.traceFile[2] = "r" + destinationRegister + "@" + "r" + sourceRegister + " ";
object.traceFile[6] = object.GPR[destinationRegister] + "@" + object.GPR[sourceRegister] + " ";
break;
case "1101":  //Seq
sourceRegister = Integer.parseInt(Instruction.substring(7, 10), 2);
secondSourceRegister = Integer.parseInt(Instruction.substring(10, 13), 2);
destinationRegister = Integer.parseInt(Instruction.substring(4, 7), 2);
object.traceFile[4] = object.GPR[destinationRegister] + "@" + object.GPR[sourceRegister] + "@" + object.GPR[secondSourceRegister] + " ";
if (object.GPR[sourceRegister] == object.GPR[secondSourceRegister]) {
    object.GPR[destinationRegister] = 1;
} else {
    object.GPR[destinationRegister] = 0;
}
object.traceFile[1] = InstructionMap.get(opcode) + " r" + destinationRegister + ",r" + sourceRegister + ",r" + secondSourceRegister + " ";
object.traceFile[2] = "r" + destinationRegister + "@" + "r" + sourceRegister + "@" + "r" + secondSourceRegister + " ";
object.traceFile[6] = object.GPR[destinationRegister] + "@" + object.GPR[sourceRegister] + "@" + object.GPR[secondSourceRegister] + " ";
break;
case "1110":  //Sgt
sourceRegister = Integer.parseInt(Instruction.substring(7, 10), 2);
secondSourceRegister = Integer.parseInt(Instruction.substring(10, 13), 2);
destinationRegister = Integer.parseInt(Instruction.substring(4, 7), 2);
object.traceFile[4] = object.GPR[destinationRegister] + "@" + object.GPR[sourceRegister] + "@" + object.GPR[secondSourceRegister] + " ";
if (object.GPR[sourceRegister] > object.GPR[secondSourceRegister]) {
    object.GPR[destinationRegister] = 1;
} else {
    object.GPR[destinationRegister] = 0;
}
object.traceFile[1] = InstructionMap.get(opcode) + " r" + destinationRegister + ",r" + sourceRegister + ",r" + secondSourceRegister + " ";
object.traceFile[2] = "r" + destinationRegister + "@" + "r" + sourceRegister + "@" + "r" + secondSourceRegister + " ";
object.traceFile[6] = object.GPR[destinationRegister] + "@" + object.GPR[sourceRegister] + "@" + object.GPR[secondSourceRegister] + " ";
break;
case "1111":  //Sne
sourceRegister = Integer.parseInt(Instruction.substring(7, 10), 2);
secondSourceRegister = Integer.parseInt(Instruction.substring(10, 13), 2);
destinationRegister = Integer.parseInt(Instruction.substring(4, 7), 2);
object.traceFile[4] = object.GPR[destinationRegister] + "@" + object.GPR[sourceRegister] + "@" + object.GPR[secondSourceRegister] + " ";
if (object.GPR[sourceRegister] != object.GPR[secondSourceRegister]) {
    object.GPR[destinationRegister] = 1;
} else {
    object.GPR[destinationRegister] = 0;
}
object.traceFile[1] = InstructionMap.get(opcode) + " r" + destinationRegister + ",r" + sourceRegister + ",r" + secondSourceRegister + " ";
object.traceFile[2] = "r" + destinationRegister + "@" + "r" + sourceRegister + "@" + "r" + secondSourceRegister + " ";
object.traceFile[6] = object.GPR[destinationRegister] + "@" + object.GPR[sourceRegister] + "@" + object.GPR[secondSourceRegister] + " ";
break;
case "0111":  //beqz
destinationRegister = Integer.parseInt(Instruction.substring(4, 7), 2);
sourceRegister = Integer.parseInt(Instruction.substring(7, 10), 2);
Immeditate6 = SYSTEM.Immediate6(Instruction.substring(10, 16));
object.traceFile[4] = "" + object.GPR[sourceRegister] + " ";
if (PC < 10)
    object.traceFile[0] = "0" + PC + "  ";
else
    object.traceFile[0] = "" + PC + "  ";
if (object.GPR[sourceRegister] == 0) {
    PC = PC + Immeditate6;
    if (PC < object.getNewloadstartaddress()) {
        object.setNatureOfTermination("ABNORMAL");
        SYSTEM.N = 2;
        SYSTEM.dumpobj = object;
        object.setEndHexSystemClock(SYSTEM.intToHex(SYSTEM.clock));
        object.setErrorMessage(ERRORHANDLER.getErrorMap(SYSTEM.N));
        SYSTEM.printOutputFile(object);
        MEMORY.freeMemory(object);
        halt1 = 3;
        break;
    }
}
if (destinationRegister != 0) {
    object.setNatureOfTermination("ABNORMAL");
    SYSTEM.N = 2;
    SYSTEM.dumpobj = object;
    object.setEndHexSystemClock(SYSTEM.intToHex(SYSTEM.clock));
    object.setErrorMessage(ERRORHANDLER.getErrorMap(SYSTEM.N));
    SYSTEM.printOutputFile(object);
    MEMORY.freeMemory(object);
    halt1 = 3;
    break;
}
object.traceFile[1] = InstructionMap.get(opcode) + " r" + sourceRegister + "," + Immeditate6 + "  ";
object.traceFile[2] = "r" + sourceRegister + " ";
object.traceFile[6] = "" + object.GPR[sourceRegister] + " ";
break;
case "1100":  //bnez
destinationRegister = Integer.parseInt(Instruction.substring(4, 7), 2);
Immeditate6 = SYSTEM.Immediate6(Instruction.substring(10, 16));
sourceRegister = Integer.parseInt(Instruction.substring(7, 10), 2);
object.traceFile[4] = "" + object.GPR[sourceRegister] + " ";
if (PC < 10)
    object.traceFile[0] = "0" + PC + "  ";
else
    object.traceFile[0] = "" + PC + "  ";
if (object.GPR[sourceRegister] != 0) {
    PC = PC + Immeditate6;
    if (PC < object.getNewloadstartaddress()) {
        object.setNatureOfTermination("ABNORMAL");
        SYSTEM.N = 2;
        SYSTEM.dumpobj = object;
        object.setEndHexSystemClock(SYSTEM.intToHex(SYSTEM.clock));
        object.setErrorMessage(ERRORHANDLER.getErrorMap(SYSTEM.N));
        SYSTEM.printOutputFile(object);
        MEMORY.freeMemory(object);
        halt1 = 3;
        break;
    }
}
if (destinationRegister != 0) {
    SYSTEM.N = 1;
    halt1 = 3;
    ERRORHANDLER.getErrorMap(1);
    object.setNatureOfTermination("ABNORMAL");
}
object.traceFile[1] = InstructionMap.get(opcode) + " r" + sourceRegister + "," + Immeditate6 + " ";
object.traceFile[2] = "r" + sourceRegister + " ";
object.traceFile[6] = "" + object.GPR[sourceRegister] + " ";
break;
case "0100":  //Trap
//Fetching Immediate 12 bits from the instruction
if (Instruction.substring(4,16).length()==12)
Immeditate12 = SYSTEM.Immediate12(Instruction.substring(4, 16));
if (PC < 10)
    object.traceFile[0] = "0" + PC + "  ";
else
    object.traceFile[0] = "" + PC + "  ";
//For an invalid immediate 12 value.
if (Immeditate12 < 0 || Immeditate12 > 2) {
    object.setNatureOfTermination("ABNORMAL");
    SYSTEM.N = 1;
    SYSTEM.dumpobj = object;
    object.setEndHexSystemClock(SYSTEM.intToHex(SYSTEM.clock));
    object.setErrorMessage(ERRORHANDLER.getErrorMap(SYSTEM.N));
    SYSTEM.printOutputFile(object);
    MEMORY.freeMemory(object);
    halt1 = 3;
    break;
}
// If Immediate 12 bits value is 0 halt.
if (Immeditate12 == 0) {
    halt1 = 1;
    SYSTEM.haltcount++;
    object.traceFile[0] = "    ";
    cpureturnvalue = 2;
    object.setNatureOfTermination("Normal");

}
// If Immediate 12 bits value is 1 write to console
if (Immeditate12 == 1) {
    SYSTEM.IOTime = SYSTEM.IOTime + 10;
    int iotime = object.getIoTime();
     object.setIoTime(iotime+10);
    object.traceFile[4] = "" + object.GPR[1] + " ";
    object.traceFile[6] = "" + object.GPR[1] + " ";
    String output = SYSTEM.intToBinary(object.GPR[1]);
    object.setLastQuantum(30-(SYSTEM.clock - object.clockuponArrival));
    if (output!=null)
    System.out.println("Output for JobId "+object.getJobID()+":  "+SYSTEM.binaryToHex(output));
    object.setNatureOfTermination("normal");
    object.setExecutingAddress(PC);
    object.setPCBSystemClock(SYSTEM.clock);
    object.traceFile[2] = "r" + "1" + " ";
    int flag_1 = 0;
    if(!Scheduler.BlockedQueue.isEmpty())
        for (Object obs: Scheduler.BlockedQueue)
        {
            PCB pc = (PCB) obs;
            if(pc == object) {
                flag_1 = 1;
                break;
            }
        }
    if(!Scheduler.LowPriority.isEmpty())
        for (Object obs: Scheduler.LowPriority)
        {
            PCB pc = (PCB) obs;
            if(pc == object) {
                flag_1 = 1;
                break;
            }
        }
    if(!Scheduler.HighPriority.isEmpty())
        for (Object obs: Scheduler.HighPriority)
        {
            PCB pc = (PCB) obs;
            if(pc == object) {
                flag_1 = 1;
                break;
            }
        }if (flag_1 != 1) {
        Scheduler.BlockedQueue.add(object);

    }
    else
        flag_1 = 0;
    cpureturnvalue = 1;

}
// If Immediate 12 bits value is 2 read the input.
if (Immeditate12 == 2) {
    SYSTEM.IOTime = SYSTEM.IOTime + 10;
    int iotime = object.getIoTime();
    object.setIoTime(iotime+10);
    object.traceFile[4] = "" + object.GPR[1] + " ";
    object.setLastQuantum(30-(SYSTEM.clock - object.clockuponArrival));
    object.setExecutingAddress(PC);
    System.out.println("Enter Input for JobId "+object.getJobID()+": ");
    object.GPR[1] = sc.nextInt();
    if (object.GPR[1] <= -32768 || object.GPR[1] >= 32767) {
        object.setNatureOfTermination("ABNORMAL");
        SYSTEM.N = 9;
        SYSTEM.dumpobj = object;
        object.setEndHexSystemClock(SYSTEM.intToHex(SYSTEM.clock));
        object.setErrorMessage(ERRORHANDLER.getErrorMap(SYSTEM.N));
        SYSTEM.printOutputFile(object);
        MEMORY.freeMemory(object);
        halt1 = 3;
        break;
    }
    object.traceFile[6] = "" + object.GPR[1] + " ";
    object.traceFile[2] = "r" + "1" + " ";
    object.setPCBSystemClock(SYSTEM.clock);
    int flag_1 = 0;
    if(!Scheduler.BlockedQueue.isEmpty())
        for (Object obs: Scheduler.BlockedQueue)
        {
            PCB pc = (PCB) obs;
            if(pc == object) {
                flag_1 = 1;
                break;
            }
        }
    if(!Scheduler.LowPriority.isEmpty())
        for (Object obs: Scheduler.LowPriority)
        {
            PCB pc = (PCB) obs;
            if(pc == object) {
                flag_1 = 1;
                break;
            }
        }
    if(!Scheduler.HighPriority.isEmpty())
        for (Object obs: Scheduler.HighPriority)
        {
            PCB pc = (PCB) obs;
            if(pc == object) {
                flag_1 = 1;
                break;
            }
        }if (flag_1 != 1)
        Scheduler.BlockedQueue.add(object);
    else
        flag_1 = 0;
    cpureturnvalue = 1;
}
object.traceFile[1] = InstructionMap.get(opcode) + " " + Immeditate12 + "  ";
break;
case "0101": //Lock
// Lock a particular memory location from read,write and delete.
Immeditate12 = SYSTEM.Immediate12(Instruction.substring(4, 16));
Immeditate12 = Immeditate12 +  object.getPartitionStartAddress();
errorZ = MEMORY.MEMORY("", Immeditate12, "lock");
object.traceFile[1] = InstructionMap.get(opcode) + " " + Immeditate12 + " ";
break;
case "0110":  //Unlock
// Unlock a particular memory location from read,write and delete.
Immeditate12 = SYSTEM.Immediate12(Instruction.substring(4, 16));
Immeditate12 = Immeditate12 +  object.getPartitionStartAddress();
errorZ = MEMORY.MEMORY("", Immeditate12, "unlock");
object.traceFile[1] = InstructionMap.get(opcode) + " " + Immeditate12 + " ";
break;
default:
break;
//Error Handler for invalid opcode.
//                    ERRORHANDLER.getErrorMap(1);
//                   object.setNatureOfTermination("Abnormal");
}
//Building trace file and formatting.
StringBuilder tracebuild1 = new StringBuilder();
StringBuilder tracebuild2 = new StringBuilder();
object.tracebuild.append(object.traceFile[0]);
int index = object.traceFile[2].indexOf("@");
String[] t2 = new String[3];
String[] t4 = new String[3];
String[] t6 = new String[3];
if (index != -1) {
t2 = object.traceFile[2].split("@");
t4 = object.traceFile[4].split("@");
t6 = object.traceFile[6].split("@");
object.traceFile[2] = t2[0];
object.traceFile[4] = t4[0];
object.traceFile[6] = t6[0];
if (t2.length == 2) {
tracebuild1.append(String.format("%35s", t2[1]) + " " + String.format("%15s", t4[1]) + " " + String.format("%17s", t6[1]));
}
if (t2.length > 2) {
tracebuild1.append(String.format("%34s", t2[1]) + " " + String.format("%15s", t4[1]) + " " + String.format("%17s", t6[1]));
tracebuild2.append(String.format("%35s", t2[2]) + " " + String.format("%15s", t4[2]) + " " + String.format("%17s", t6[2]));
}
}
object.tracebuild.append(String.format("%18s", object.traceFile[1]));
if (opcode == "1100")
object.tracebuild.append(String.format("%14s", object.traceFile[2]));
else
object.tracebuild.append(String.format("%13s", object.traceFile[2]));
object.tracebuild.append(String.format("%8s", object.traceFile[3]));
object.tracebuild.append(String.format("%8s", object.traceFile[4]));
object.tracebuild.append(String.format("%7s", object.traceFile[5]));
object.tracebuild.append(String.format("%11s", object.traceFile[6]));
object.tracebuild.append(String.format("%6s", object.traceFile[7]));
object.tracebuild.append(":");
if (tracebuild1 != null) {
object.tracebuild.append(tracebuild1);
object.tracebuild.append(":");
}
if (tracebuild2 != null) {
object.tracebuild.append(tracebuild2);
object.tracebuild.append(":");
}
//Adding the Gap in the trace if the system clock has incremented by 10 vtu.
if (SYSTEM.clock - SYSTEM.oldclock >= 10) {
SYSTEM.oldclock = SYSTEM.clock;
object.tracebuild.append("AddGap");
object.tracebuild.append(":");
}
//Program Counter is incremented by 2 for every instruction fetched unless it
//is a branch instruction.
//            if(SYSTEM.clock - object.clockuponArrival >= 30 && cpureturnvalue!=2 && cpureturnvalue!=1 && halt1!=1)
if (SYSTEM.clock - object.clockuponArrival >= 30)
{
object.setExecutingAddress(PC);
Scheduler.schedule(object);
cpureturnvalue = 1;
SYSTEM.contextswitch = 1;
object.setLastQuantum(0);
}

if (halt1 == 1 ) {
halt1 = 0;
break;
}

if (halt1 == 3) {
halt1 =0;
object = null;
break;
}


if (errorZ == "error") {
break;
}
if (cpureturnvalue == 1 || cpureturnvalue == 2)
break;
//            if (cpureturnvalue!=2 && cpureturnvalue!=1)
//                cpureturnvalue =0;
}
//        if (y == 1) {
//            SYSTEM.printingTraceFile(object.tracebuild);
//        }
return cpureturnvalue;
}
}
