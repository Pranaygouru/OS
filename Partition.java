//Partitioning contains the methods for allocating partitions to memory
//Choosing the best fit algorithm and also the compaction process is done
//here with the best allocated memory locations.

import java.util.*;
@SuppressWarnings("unchecked")
public class Partition {
public static int previousStartAddress;
public static int partitionnumber = 1;
public static int EndAddress;
public static HashMap<Integer, ArrayList> partitions = new HashMap<Integer,ArrayList>();
public static HashMap<Integer, Integer> partitionsizes = new HashMap<>();
public static HashMap<Integer, String> partitionstatus = new HashMap<>();
public static int[] referenceBit = new int[SYSTEM.memorysize];
public static int[] lockBit = new int[SYSTEM.memorysize];
public static int[] lockBitnew = new int[SYSTEM.memorysize];
public static int AllocatePartitions(int Program_Size)
{
if (partitions.isEmpty())
{
ArrayList<Integer> al = new ArrayList<>();
al.add(previousStartAddress);
EndAddress = previousStartAddress + Program_Size - 1;
al.add(EndAddress);
partitionsizes.put(partitionnumber,EndAddress + 1 - previousStartAddress);
previousStartAddress = EndAddress + 1;
partitions.put(partitionnumber,al);
}
else
{
int countFull = previousStartAddress + Program_Size-1;
if (countFull >= SYSTEM.memorysize)
{
    return -1;
}
else
{
    partitionnumber++;
    ArrayList<Integer> al = new ArrayList<>();
    EndAddress = previousStartAddress + Program_Size-1;
    al.add(previousStartAddress);
    al.add(EndAddress);
    partitionsizes.put(partitionnumber,EndAddress + 1 - previousStartAddress);
    previousStartAddress = EndAddress + 1;
    partitions.put(partitionnumber,al);
}
}

return partitionnumber;
}
//Best Fit Partition.
public static int BestFitPartition(int programsize) {
int minsize = Integer.MAX_VALUE;
int partitionNumber = 0;
if (!partitionstatus.isEmpty()) {
for (int val : partitionstatus.keySet()) {
    // search  for value
    String status = partitionstatus.get(val);
    if (status.equals("EMPTY")) {
        int size = partitionsizes.get(val);
        if (size >= programsize && minsize > size) {
            minsize = size;
            partitionNumber = val;
        }
    }
}
if (minsize != Integer.MAX_VALUE)
    return partitionNumber;
else
    partitionNumber = Partition.AllocatePartitions(programsize);
return partitionNumber;
}
else
{
partitionNumber = Partition.AllocatePartitions(programsize);
return partitionNumber;
}
}
//Check for Compaction
public static String checkForCompaction(int programsize) {
int count = 0;

for (int i:referenceBit) {
if (i == 0)
    count++;

}
if (count >= programsize)
return "FOUND";
else
return "NOT FOUND";
}

//Reallocate partitions for the entire program.

public static int reallocatePartitions(int programsize) {
HashMap<Integer,ArrayList> newPartitionMap = new HashMap<>();
HashMap<Integer,Integer> OldandNewPartition = new HashMap<>();
HashMap<Integer,Integer> Programsizenew = new HashMap<>();
HashMap<Integer,String> Programstatusnew = new HashMap<>();
int newStartAddress = 0;
int newendAddress = 0;
int newPartitionNo = 0;
int[] referenceBitNew = new int[SYSTEM.memorysize];
Iterator it = partitionstatus.entrySet().iterator();
ArrayList alist1 = new ArrayList();
while (it.hasNext()) {
Map.Entry pair = (Map.Entry) it.next();
int val = (int) pair.getKey();
String status = partitionstatus.get(val);
if (status.equals("EMPTY")) {
   alist1.add(val);
}
}
Iterator ip = alist1.iterator();
if (!alist1.isEmpty())
   while (ip.hasNext()) {
       int val = (int) ip.next();
       partitions.remove(val);
       partitionstatus.remove(val);
       partitionsizes.remove(val);
   }
Iterator it1 = partitionstatus.entrySet().iterator();
while (it1.hasNext())
{
Map.Entry pair = (Map.Entry) it1.next();
int val = (int) pair.getKey();
String status = partitionstatus.get(val);
if (status.equals("FULL"))
{
    newPartitionNo++;
    OldandNewPartition.put(val,newPartitionNo);
    ArrayList al = partitions.get(val);
    int start = (int)al.get(0);
    int end = (int)al.get(1);
    int count = 0;
    for (int k = end; k >= start; k--)
    {
        if (referenceBit[k] == 1)
            break;
        else
            count++;
    }
    end = end - count;
    ArrayList alist = new ArrayList();
    int size = end - start;
    alist.add(newStartAddress);
    for (int p = 0 ; p <= size ; p++)
    {
        referenceBitNew[newStartAddress] = 1;
        newStartAddress++;
    }
    newendAddress = newStartAddress-1;
    alist.add(newendAddress);
    newPartitionMap.put(newPartitionNo,alist);
}
}
referenceBit = referenceBitNew;
previousStartAddress = newStartAddress;
int count = 0;
if (!partitions.isEmpty()) {
Queue<PCB> objectsQueue = new LinkedList<>();
if (!Scheduler.HighPriority.isEmpty()) {
    for (Object ob : Scheduler.HighPriority) {
        count++;
        ((LinkedList<PCB>) objectsQueue).add((PCB) ob);
    }
}
if (!Scheduler.LowPriority.isEmpty())
    for (Object ob : Scheduler.LowPriority) {
        count++;
        ((LinkedList<PCB>) objectsQueue).add((PCB) ob);
    }

if (!Scheduler.BlockedQueue.isEmpty())
    for (Object ob : Scheduler.BlockedQueue) {
        count++;
        ((LinkedList<PCB>) objectsQueue).add((PCB) ob);
    }
for (Object ob : objectsQueue)
{
    PCB pc = (PCB)ob;
    int num = pc.getPartitionNumber();
    int loadstart = pc.getLoadStartAddress();
    int executingAddress = pc.getExecutingAddress();
    ArrayList addlist = pc.getStartandendaddress();
    ArrayList pl = new ArrayList();
    pl = partitions.get(num);
    int newExecutingAddress = executingAddress-(int)pl.get(0);
    ArrayList ql = new ArrayList();
    int newPart = OldandNewPartition.get(num);
    pc.setPartitionNumber(newPart);
    ql = newPartitionMap.get(newPart);
    addlist.add(ql);
    pc.setStartandendaddress(addlist);
    pc.setPartitionStartAddress((int)ql.get(0));
    pc.setNewloadstartaddress((int)ql.get(0)+loadstart);
    pc.setExecutingAddress((int)ql.get(0) + newExecutingAddress);
}
}
changememorylines(partitions,newPartitionMap,OldandNewPartition);
Iterator it8 = OldandNewPartition.entrySet().iterator();
//while (it8.hasNext()) {
//Map.Entry pair = (Map.Entry) it8.next();
//ArrayList Oldal = new ArrayList();
//ArrayList newal = new ArrayList();
//Oldal = partitions.get(pair.getKey());
//newal = newPartitionMap.get(pair.getValue());
//int newStart = (int)newal.get(0);
//int oldstart = (int)Oldal.get(0);
//int newEnd = (int)newal.get(1);
//int oldEnd = (int)Oldal.get(1);
//for (int i = oldstart;i<=oldEnd;i++)
//{
//    lockBitnew[newEnd]=lockBit[i];
//    newEnd++;
//}
//}
partitions = newPartitionMap;
lockBitnew = lockBit;
for (int t: OldandNewPartition.keySet())
{
int val = OldandNewPartition.get(t);
int size = partitionsizes.get(t);
Programsizenew.put(val,size);
Programstatusnew.put(val,"FULL");
}
partitionsizes = Programsizenew;
partitionstatus = Programstatusnew;
partitionnumber = newPartitionNo;
int PartitionNumber = BestFitPartition(programsize);
System.out.println(PartitionNumber);
return PartitionNumber;

}
//Checking the size empty from the program
public  static int checkTheSize(int size)
{
int count = 0;

for (int i :referenceBit)
{
if(i == 0)
    count++;
}
return count;
}
//Change the memory lines after compaction.
public static void changememorylines(HashMap<Integer, ArrayList> partitions, HashMap<Integer, ArrayList> newPartitionMap,
                             HashMap<Integer, Integer> oldandNewPartition) {
String[] memory=new String[SYSTEM.memorysize];
for (int i : oldandNewPartition.keySet())
{
ArrayList oldal = new ArrayList();
ArrayList newal = new ArrayList();
int val = oldandNewPartition.get(i);
newal = newPartitionMap.get(val);
oldal = partitions.get(i);
int startnew = (int)newal.get(0);
int startold = (int)oldal.get(0);
int newend = (int)newal.get(1);
for (int mem = startnew; mem<=newend; mem++)
{
    memory[mem] = MEMORY.mainMemory[startold];
    startold++;
}
}
MEMORY.mainMemory = Arrays.copyOf(memory,SYSTEM.memorysize);
return;
}
}
