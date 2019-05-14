//Import statements
import java.util.*;
//Declaration of queues and HighPriorityTime and LowPriorityTime.
@SuppressWarnings("unchecked")
public class Scheduler {
public static Queue<PCB> HighPriority = new LinkedList<>();
public static Queue<PCB> LowPriority = new LinkedList<>();
public static Queue<PCB> BlockedQueue = new LinkedList<>();
public static int HighPriorityTime;
public static int LowPriorityTime;
public static PriorityQueue<Integer> pQueue = new PriorityQueue<Integer>();
// queues are arranged according to the time quantum of the program produced from the cpu.
public static void init(){
if (!HighPriority.isEmpty()) {
ArrayList<Integer> alforquant = new ArrayList();
ArrayList<Object> alforobject = new ArrayList();
for (Object obj : HighPriority) {
PCB pc = (PCB) obj;
alforobject.add(alforobject);
alforquant.add(pc.getLastQuantum());
}
ArrayList<Integer> Sortedalforquant = new ArrayList<>(alforquant);
Collections.sort(Sortedalforquant,Collections.reverseOrder());
Iterator<Integer> iter
= Sortedalforquant.iterator();
ArrayList<Integer> indices = new ArrayList();
//Find the indices of the sorted Arraylist
while (iter.hasNext()) {
int val = iter.next();
for (int val1=0 ; val1<alforquant.size(); val1 ++){
if(val == alforquant.get(val1))
{
if (!indices.contains(val1))
{
indices.add(val1);
}
}
}
}
Queue<PCB> HighPriorityNew = new LinkedList<>();
for (int i : indices)
{
for (Object ob:HighPriority)
{
if(i == 0)
{
((LinkedList<PCB>) HighPriorityNew).add((PCB)ob);
}
i--;
}
}
HighPriority = null;
HighPriority = HighPriorityNew;

//Find the values and place them in the queue.

//If there is a low priority process add it to low priority queue.

}
if (!LowPriority.isEmpty()) {
ArrayList<Integer> alforquant = new ArrayList();
ArrayList<Object> alforobject = new ArrayList();
for (Object obj : LowPriority) {
PCB pc = (PCB) obj;
alforobject.add(alforobject);
alforquant.add(pc.getLastQuantum());
}
ArrayList<Integer> Sortedalforquant = new ArrayList<>(alforquant);
Collections.sort(Sortedalforquant,Collections.reverseOrder());
Iterator<Integer> iter
= Sortedalforquant.iterator();
ArrayList<Integer> indices = new ArrayList();
//Find the indices of the sorted Arraylist
while (iter.hasNext()) {
int val = iter.next();

for (int val1=0 ; val1<alforquant.size(); val1 ++){

if(val == alforquant.get(val1))
{

if (!indices.contains(val1))
{
indices.add(val1);
}
}
}
}
Queue<PCB> HighPriorityNew = new LinkedList<>();

for (int i : indices)
{
for (Object ob:LowPriority)
{
if(i == 0)
{
((LinkedList<PCB>) HighPriorityNew).add((PCB)ob);
}
i--;
}
}
LowPriority = null;
LowPriority = HighPriorityNew;
return;
//Find the values and place them in the queue.


}
}
//Scheduler method is called from the program and is added to the queue.

public  static void schedule(PCB pcb) {
if(pcb.getPriority() == 1)
{
int flag = 0;
if (!Scheduler.HighPriority.isEmpty())
for (Object ob: Scheduler.HighPriority)
{
if (pcb==ob)
flag = 1;
}
if (flag!=1)
HighPriority.add(pcb);


}
if(pcb.getPriority() == 0)
{
LowPriority.add(pcb);

}
init();
return;
}
//Check the blocked queue and the object is reinserted into the queue.
public static String checkBlockedQueue(int clock) {
PCB ob = null;
if (!BlockedQueue.isEmpty()) {
ob = BlockedQueue.peek();
if (clock - ob.getPCBSystemClock()>=10)
{
ob = BlockedQueue.poll();
Scheduler.schedule(ob);
return "Added";
}
}
return "Not Added";
}
}
