/*
* Memory consists of 256 bytes and each byte is 8 bits long.
* Read,Write,Dump are the operations performed by the memory
* When an instruction is fetched from the memory.Dump operation
* is performed when there is an error occurred in a program
* then 64 bytes are loaded on to an output file.
* */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
@SuppressWarnings("unchecked")
public class MEMORY {
public static String[] mainMemory = new String[SYSTEM.memorysize];// Main Memory of size 256 bytes.
public static int[] lockbit = new int[SYSTEM.memorysize];
public static void freeMemory(Object obj)
{
ArrayList<Integer> al = new ArrayList<>();
PCB pc = (PCB)obj;
if (Partition.partitions.get(pc.getPartitionNumber())!=null)
    {
        al = Partition.partitions.get(pc.getPartitionNumber());
        Iterator ir = al.iterator();
        int start = 0, end = 0;
        while (ir.hasNext()) {
            start = (int) ir.next();
            end = (int) ir.next();
        }
        for (int i = start; i <= end; i++) {
            mainMemory[i] = null;
            Partition.referenceBit[i] = 0;
        }
        Partition.partitionstatus.put(pc.getPartitionNumber(), "EMPTY");
        obj = null;
    }
return;
}
public static String MEMORY(String X,int Y,String Z)
{
try {
// Write Operation.
if (X == "WRITE" && lockbit[Y] == 0)
{
mainMemory[Y] = Z;
Partition.referenceBit[Y] = 1;
}
// Read Operation.
if (X == "READ" && lockbit[Y] == 0)
{
Z = mainMemory[Y];
}
// Dump Operation.
if (X == "DUMP")
{
String[] dumparray = new String[64];
int val = SYSTEM.DumpFile(dumparray);

}
// Lock a particular memory location.
if(Z == "lock")
{
lockbit[Y] = 1;
}
// Unlock a particular memory location.
if (Z == "unlock")
{
lockbit[Y] = 0;
}
if(lockbit[Y] == 1)
{
return "lock";
}
} catch (Exception e) {
Z = "error";
    SYSTEM.pc[SYSTEM.jobID].setNatureOfTermination("ABNORMAL");
    SYSTEM.dumpobj = SYSTEM.pc[SYSTEM.jobID];
    SYSTEM.N = 4;
    SYSTEM.pc[SYSTEM.jobID].setEndHexSystemClock(SYSTEM.intToHex(SYSTEM.clock));
    SYSTEM.pc[SYSTEM.jobID].setErrorMessage(ERRORHANDLER.getErrorMap(SYSTEM.N));
    SYSTEM.printOutputFile(SYSTEM.pc[SYSTEM.jobID]);
    MEMORY.freeMemory(SYSTEM.pc[SYSTEM.jobID]);
// If there is an error in accessing illegal value in the memory
// It throws memory range fault exception.
ERRORHANDLER.getErrorMap(SYSTEM.N);
}
return Z;
}
}
