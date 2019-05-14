/*
 * Name: Pranay Gouru
 * Course Number: CS5323 - DESIGN AND IMPLEMENTATION OF OPERATING SYSTEMS 2
 * Assignment Title: Operating Systems Project PHASE 2
 * Date : 24 April 2019
 */
/*
 * System is the key component among the components and it is used to
 * call other classes using corresponding objects like cpu,loader.
 * The job's first line consists of job length and start address which
 * is followed by job and trace switch turn on/off.Now System includes all the files
 * like progress file, dump files and output file which is concatenated into one file.
 *
 * Now system fetches the code from the batch file and loads on to memory via loader and
 * then each job from the batch queue will be executed sequentially and then the statisitics
 * and progress file are printed and used for further purpose.
 *
 * */
import java.io.*;// Import statements
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.*;
@SuppressWarnings("unchecked")
public class SYSTEM {
    //Reader variables
    public static BufferedReader reader = null;
    private static int x;//Start Address.
    public static int baseAddress;
    private static int y;//Trace File
    public static int N;
    public static int contextswitch;
    public static int totalTimeLost;
    public static int haltcount;
    public static String NatureOfTermination;
    public static int CumulativeJobID;
    //Clock Variables
    public static int clock;
    public static int oldclock;
    public static int flagforend;
    public static int IOTime;
    public static int ExecutionTime;
    public static int PC;
    public static int jobCount;
    public static BufferedReader reader1 = null;
    public static BufferedReader reader2 = null;
    public static int linescount = 2;
    public static int decimal;
    public static int jobID;
    public static int normalTerminationCount;
    public static int abnormalTerminationCount;
    public static PCB[] pc = new PCB[1000];
    public static String line = null;
    public static int loopproblem = 0;
    public static int memorysize = 256;
    public static int newarrivalTime;
    public static int meanArrivalTime;
    public static int ArrivalTime = 0;
    public static int nextsizeCheck;
    public static int nextsizes;
    public static int cpuIdleTime;
    //Mean calculation variables
    public static double meanSystemTime;
    public static double meanIOtime;
    public static double meanExecutionTime;
    //File writers
    public static  FileOutputStream fos;
    public static  FileOutputStream fos1;
    public static BufferedWriter bw1;
    //Mean Degree values.
    public static int HighestDegree = Integer.MIN_VALUE;
    public static int LowDegree = Integer.MAX_VALUE;
    public static int meanDegree;
    public static int meanDegreeCount;
    public static BufferedWriter bw;
    public static int loadreturnval;
    public static int counthigh;
    // Progress clock for progress file
    public static int progressClock;
    public static PCB dumpobj;
    //Static initializers for error handler and cpu functions.
    static {
        ERRORHANDLER.init();
    }
    static {
        CPU.init();
    }
    public static void main(String[] args) {
        CumulativeJobID = 01;
        LOADER lr = new LOADER();//Object Creation
        CPU cp = new CPU();//Object Creation
        Scheduler sched = new Scheduler();
        Partition pr = new Partition();
        int flag = 0;
//File Reading from the system path.
        try {
//reader = new BufferedReader(new FileReader("./"+args[0]));
            reader = new BufferedReader(new FileReader("mix1"));
        }
        catch (Exception e)
        {

            System.out.println(e);
        }
        try {
//Creation of output file and progress file
            File fr = new File("OutputFile.txt");// Creating output file
            File fr2 = new File("ProgressFile.txt");
            fos1 = new FileOutputStream(fr2);
            fos = new FileOutputStream(fr);
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw1 =new BufferedWriter(new OutputStreamWriter(fos1));
            progressClock = SYSTEM.clock;
            line = reader.readLine();
            while (line != null) {
//reader2 = new BufferedReader(new FileReader("./"+args[0]));

                reader2 = new BufferedReader(new FileReader("mix1"));
                int ceilvalue = 0;
                try {
//Pattern matching for job
                    String regex = "^JOB*";
                    Pattern r =  Pattern.compile(regex);
                    Matcher m = r.matcher(line);
//while(!line.contains("J"))
//{
//    line = reader.readLine();
//}
                    int count = linescount;
                    String lines = null;
                    while (count > -1) {
                        lines = reader2.readLine();
                        count--;
                    }
                    String regex1 = "^JOB*";
                    Pattern r1 = Pattern.compile(regex);
                    Matcher m1 = r.matcher(lines);
                    while (true) {
                        try {
                            if ((lines = reader2.readLine()) == null) {
                                flagforend = 2;
                                flag = 1;
                                break;
                            }
                        } catch (Exception e) {

                            System.out.println(e);
                        }
                        linescount++;
                        m1 = r.matcher(lines);
                        if (m1.find() && linescount > 1)
                            break;
                    }
                    if (m.find()) {
                        String[] firstlinesplit = new String[4];
                        line = reader.readLine();
                        firstlinesplit = line.split(" ");
                        jobID = SYSTEM.hextoint(firstlinesplit[0]);
                        jobCount++;
                        newarrivalTime = SYSTEM.hextoint(firstlinesplit[1]);
                        int programsize = SYSTEM.hextoint(firstlinesplit[2]);
                        if (programsize*2 > 256)
                        {
//Programsize *2 >256 then program size too large.
                            pc[jobID] = new PCB();
                            SYSTEM.dumpobj = pc[jobID];
                            pc[jobID].setArrivalTime(newarrivalTime);
                            ArrayList startandend = new ArrayList();
                            startandend.add(0);
                            startandend.add(0);
                            pc[jobID].setJobID(jobID);
                            pc[jobID].setStartandendaddress(startandend);
                            pc[jobID].setHexSystemClock(SYSTEM.intToHex(SYSTEM.clock));
                            pc[jobID].setEndHexSystemClock(SYSTEM.intToHex(SYSTEM.clock));
                            pc[jobID].setNatureOfTermination("ABNORMAL");
                            pc[jobID].setExecutionTime(0);
                            pc[jobID].setIoTime(0);
                            pc[jobID].setSystemClock(SYSTEM.clock);
                            SYSTEM.N = 3;
                            SYSTEM.dumpobj = pc[jobID];
                            String progze = ERRORHANDLER.getErrorMap(SYSTEM.N);
                            pc[jobID].setErrorMessage(progze);
                            printOutputFile(pc[jobID]);
                            while(!line.equals("END"))
                            {
                                linescount++;
                                line = reader.readLine();
                            }
                            line = reader.readLine();
                            linescount += 2;
                        }
                        else {
//StartAddress starting the file
                            int startAddress = SYSTEM.hextoint(firstlinesplit[3]);
                            if (newarrivalTime > SYSTEM.clock)
                                meanArrivalTime+=newarrivalTime-SYSTEM.clock;
                            else
                                meanArrivalTime+=SYSTEM.clock - newarrivalTime;
                            if (newarrivalTime > SYSTEM.clock)
                                while (SYSTEM.clock < newarrivalTime) {
                                    SYSTEM.clock = SYSTEM.clock + 1;
                                    cpuIdleTime = cpuIdleTime +1;
                                }
                            if (ArrivalTime >= SYSTEM.clock)
                                flag = 1;
                            String status = null;
                            if (newarrivalTime <= SYSTEM.clock) {
//Best Fit Partition
                                int partitionNumber = Partition.BestFitPartition(programsize * 2);

                                if (partitionNumber == -1) {
//Check for compaction.
                                    status = Partition.checkForCompaction(programsize * 2);

                                    if (status.equals("FOUND")) {
                                        partitionNumber = Partition.reallocatePartitions(programsize * 2);


//    Partition.partitionstatus.put(partitionNumber, "FULL");

                                    }
                                } else {
                                    Partition.partitionstatus.put(partitionNumber, "FULL");
                                }

                                if (status != "NOT FOUND") {
//New PCB creation and new Partitions.
                                    pc[jobID] = new PCB();
                                    pc[jobID].setJobID(jobID);
                                    pc[jobID].setArrivalTime(newarrivalTime);
                                    pc[jobID].setProgramSize(programsize);
                                    pc[jobID].setLoadStartAddress(startAddress);
                                    pc[jobID].setPartitionNumber(partitionNumber);
                                    ArrayList<Integer> al = new ArrayList<>();
                                    al = Partition.partitions.get(pc[jobID].getPartitionNumber());
                                    ArrayList endstart = new ArrayList();
                                    endstart = pc[jobID].getStartandendaddress();
                                    endstart.add(al);
                                    pc[jobID].setStartandendaddress(endstart);
                                    int PartitionAddress = al.get(0);
                                    pc[jobID].setPartitionStartAddress(PartitionAddress);
                                    pc[jobID].setExecutingAddress(PartitionAddress + pc[jobID].getLoadStartAddress());
                                    pc[jobID].setNewloadstartaddress(PartitionAddress + pc[jobID].getLoadStartAddress());
                                    loadreturnval = lr.LOADER(PartitionAddress, 0);
//Finding missing END.
                                    if (loadreturnval == 4) {
                                        SYSTEM.pc[SYSTEM.jobID].setNatureOfTermination("ABNORMAL");
                                        SYSTEM.dumpobj = SYSTEM.pc[SYSTEM.jobID];
                                        SYSTEM.N = 11;
                                        SYSTEM.pc[SYSTEM.jobID].setEndHexSystemClock(SYSTEM.intToHex(SYSTEM.clock));
                                        SYSTEM.pc[SYSTEM.jobID].setErrorMessage(ERRORHANDLER.getErrorMap(SYSTEM.N));
                                        SYSTEM.printOutputFile(SYSTEM.pc[SYSTEM.jobID]);
                                        MEMORY.freeMemory(SYSTEM.pc[SYSTEM.jobID]);
                                    }
//call to progress file
                                    ProgressFile(pc[jobID],Scheduler.LowPriority,Scheduler.HighPriority,Scheduler.BlockedQueue,
                                            "Job Initiation",counthigh,SYSTEM.clock,jobID);

                                    if (loadreturnval == 2 || loadreturnval == 4)
                                    {
                                        pc[jobID] = null;
                                    }
                                    if (loadreturnval != 2 && loadreturnval!=4) {

                                        pc[jobID].setLastQuantum(0);
                                        pc[jobID].setHexSystemClock(SYSTEM.intToHex(SYSTEM.clock));
                                        Scheduler.schedule(pc[jobID]);
                                    }
                                } else if (line.isEmpty()) {
                                    flag = 2;
                                } else {
                                    flag = 1;
                                }
                                if (loadreturnval != 4) {
                                    line = reader.readLine();
                                }
                            }
//                            int count = linescount;
//                            String lines = null;
//                            while (count > -1) {
//                                lines = reader2.readLine();
//                                count--;
//                            }
//                            String regex1 = "^JOB*";
//                            Pattern r1 = Pattern.compile(regex);
//                            Matcher m1 = r.matcher(lines);
//                            while (true) {
//                                try {
//                                    if ((lines = reader2.readLine()) == null) {
//                                        flagforend = 2;
//                                        flag = 1;
//                                        break;
//                                    }
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                    System.out.println(e);
//                                }
//                                linescount++;
//                                m1 = r.matcher(lines);
//                                if (m1.find() && linescount > 1)
//                                    break;
//                            }
                            if (lines != null) {

                                lines = reader2.readLine();
                                String[] str = lines.split(" ");
                                ArrivalTime = hextoint(str[1]);
                                nextsizeCheck = hextoint(str[2]);
                                nextsizes = Partition.checkTheSize(nextsizeCheck * 2);
                                if (nextsizes < nextsizeCheck * 2) {
                                    flag = 1;
                                }
                            }
                            reader2.close();
                            if (flag == 1 || flag == 2) {
                                int setflag = 0;
//Blocked queue,HighPriority and Low Priority queue checking
                                while (!(Scheduler.HighPriority.isEmpty() && Scheduler.LowPriority.isEmpty() && Scheduler.BlockedQueue.isEmpty())) {
                                    counthigh = 0;
                                    if (SYSTEM.clock - progressClock >= 100)
                                    {
                                        setflag = 1;
                                        progressClock = SYSTEM.clock;
                                    }

                                    meanDegreeCount++;
                                    if (!Scheduler.HighPriority.isEmpty()) {
                                        Iterator ir = Scheduler.HighPriority.iterator();
                                        while (ir.hasNext()) {
                                            ir.next();
                                            counthigh++;
                                        }
                                    }
                                    if (!Scheduler.LowPriority.isEmpty())
                                    {
                                        Iterator ir1 = Scheduler.LowPriority.iterator();
                                        while (ir1.hasNext())
                                        {
                                            ir1.next();
                                            counthigh++;
                                        }
                                    }

                                    if (counthigh > HighestDegree)
                                        HighestDegree = counthigh;
                                    if (counthigh < LowDegree)
                                        LowDegree = counthigh;
                                    meanDegree += counthigh;
//Checking for the 300 and 100
                                    if (Scheduler.HighPriorityTime > 300 && Scheduler.LowPriorityTime > 100) {
                                        Scheduler.HighPriorityTime = 0;
                                        Scheduler.LowPriorityTime = 0;
                                    }

                                    PCB obj = null;
//Scheduler HighPriority and Scheduler LowPriority check
                                    if (Scheduler.HighPriority.isEmpty() && Scheduler.LowPriority.isEmpty()) {
                                        obj = Scheduler.BlockedQueue.poll();
                                        Scheduler.schedule(obj);
                                    }

                                    if (Scheduler.HighPriorityTime <= 300) {
                                        if (!Scheduler.HighPriority.isEmpty()) {
                                            Scheduler.HighPriorityTime += 30;
                                            obj = Scheduler.HighPriority.poll();

                                        } else {
                                            if (!Scheduler.LowPriority.isEmpty()) {
                                                Scheduler.LowPriorityTime += 30;
                                                obj = Scheduler.LowPriority.poll();
                                            }
                                        }
                                    } else {
                                        //Scheduler Lowpriority time is less than 100.
                                        if (Scheduler.LowPriorityTime <= 100) {
                                            if (!Scheduler.LowPriority.isEmpty()) {
                                                Scheduler.LowPriorityTime += 30;
                                                obj = Scheduler.LowPriority.poll();
                                            } else {
                                                if (!Scheduler.HighPriority.isEmpty()) {
                                                    Scheduler.HighPriorityTime += 30;
                                                    obj = Scheduler.HighPriority.poll();
                                                }
                                            }
                                        }
                                    }
//Base Address execution
                                    baseAddress = obj.getExecutingAddress();
                                    y = obj.traceSwitch;
                                    flag = 0;
//Call to CPU.
                                    int cpuReturnValue = cp.CPU(baseAddress, y, obj);
                                    if (setflag == 1)
                                        if (cpuReturnValue == 1 && SYSTEM.contextswitch==1)
                                        {
                                            //A call to progressfile
                                            ProgressFile(obj,Scheduler.LowPriority,Scheduler.HighPriority,Scheduler.BlockedQueue,
                                                    "Context Switch",counthigh,SYSTEM.clock, jobID);
                                            setflag =0;
                                            SYSTEM.contextswitch =0;
                                        }
                                    if (setflag == 1)
                                        if (cpuReturnValue == 1 && SYSTEM.contextswitch == 0)
                                        {
                                            ProgressFile(obj,Scheduler.LowPriority,Scheduler.HighPriority,Scheduler.BlockedQueue,
                                                    "I/O request",counthigh,SYSTEM.clock, jobID);
                                            setflag =0;
                                        }
//If cpureturn value is 2 then exit from the block to initialize
                                    if (cpuReturnValue == 2) {
                                        Partition.partitionstatus.put(obj.getPartitionNumber(), "EMPTY");
                                        obj.setEndHexSystemClock(SYSTEM.intToHex(SYSTEM.clock));
//    printOutputFile(obj);
                                        obj.setNatureOfTermination("NORMAL");
                                        printOutputFile(obj);
                                        printingTraceFile(obj);
                                        ProgressFile(obj, Scheduler.LowPriority, Scheduler.HighPriority, Scheduler.BlockedQueue,
                                                    "Job Termination", counthigh, SYSTEM.clock, jobID);
                                            setflag =0;
                                        MEMORY.freeMemory(obj);
                                        obj = null;
                                        nextsizes = Partition.checkTheSize(nextsizeCheck * 2);

                                        if (nextsizes < (nextsizeCheck * 2) ) {
                                            flag = 2;
                                        } else {
                                            cpuReturnValue = 2;
                                            flag = 1;
                                        }
                                    }
                                    String statusOfBlockQueue = Scheduler.checkBlockedQueue(SYSTEM.clock);
                                    if (statusOfBlockQueue.equals("Not Added"))
                                        if (cpuReturnValue == 2 && flag != 2 && flagforend != 2) {
                                            if (setflag == 1) {
                                                ProgressFile(obj, Scheduler.LowPriority, Scheduler.HighPriority, Scheduler.BlockedQueue,
                                                        "Job Initiation", counthigh, SYSTEM.clock, jobID);
                                                setflag =0;
                                            }
                                            break;
                                        }
                                }
                                if (Scheduler.HighPriority.isEmpty() && Scheduler.LowPriority.isEmpty() && Scheduler.BlockedQueue.isEmpty())
                                {
                                    for (int i = 0;i<256;i++)
                                    {
                                        Partition.referenceBit[i]=0;
                                    }
                                }
                            }
                        }
//                        if (flag == 2 )
//                            break;
                    }
                    if (flagforend == 2) {
                        bw1.close();
                        break;
                    }
                } catch (Exception e) {
//Invalid Loader Format
                    SYSTEM.N = 6;
                    SYSTEM.dumpobj = SYSTEM.pc[SYSTEM.jobID];
                    SYSTEM.NatureOfTermination = "ABNORMAL";
                    ERRORHANDLER.getErrorMap(SYSTEM.N);
                }
                Matcher m1 =null;
                if (line!=null) {
                    String regex1 = "^JOB*";
                    Pattern r1 = Pattern.compile(regex1);
                    m1 = r1.matcher(line);
                    if (!m1.find()) {
                        if (line != null) {
                            String[] str = line.split(" ");
                            int jobID = hextoint(str[0]);
                            pc[jobID] = new PCB();
                            pc[jobID].setNatureOfTermination("ABNORMAL");
                            SYSTEM.N = 12;
                            pc[jobID].setEndHexSystemClock(SYSTEM.intToHex(SYSTEM.clock));
                            pc[jobID].setErrorMessage(ERRORHANDLER.getErrorMap(SYSTEM.N));
                            pc[jobID].setJobID(jobID);
                            printOutputFile(pc[jobID]);
                            int size=0;
                            if (str.length>2)
                             size = hextoint(str[2]);
                            int sizes = (int) Math.ceil((double) size / 4);
                            sizes += 3;
                            for (int k = 0; k < sizes; k++) {
                                line = reader.readLine();
                            }
                            while (!line.contains("J"))
                            {
                                line=reader.readLine();
                            }
                        }
                    }
                }
            }
            while (!(Scheduler.HighPriority.isEmpty() && Scheduler.LowPriority.isEmpty() && Scheduler.BlockedQueue.isEmpty())){
                counthigh = 0;
                meanDegreeCount++;
                if (!Scheduler.HighPriority.isEmpty()) {
                    Iterator ir = Scheduler.HighPriority.iterator();
                    while (ir.hasNext()) {
                        ir.next();
                        counthigh++;
                    }
                }
                if (!Scheduler.LowPriority.isEmpty())
                {
                    Iterator ir1 = Scheduler.LowPriority.iterator();
                    while (ir1.hasNext())
                    {
                        ir1.next();
                        counthigh++;
                    }
                }

                if (counthigh > HighestDegree)
                    HighestDegree = counthigh;
                if (counthigh < LowDegree)
                    LowDegree = counthigh;
                meanDegree += counthigh;

                if (Scheduler.HighPriorityTime > 300 && Scheduler.LowPriorityTime > 100) {
                    Scheduler.HighPriorityTime = 0;
                    Scheduler.LowPriorityTime = 0;
                }

                PCB obj = null;
                if (Scheduler.HighPriority.isEmpty() && Scheduler.LowPriority.isEmpty()) {
                    obj = Scheduler.BlockedQueue.poll();
                    Scheduler.schedule(obj);
                }

                if (Scheduler.HighPriorityTime <= 300) {
                    if (!Scheduler.HighPriority.isEmpty()) {
                        Scheduler.HighPriorityTime += 30;
                        obj = Scheduler.HighPriority.poll();

                    } else {
                        if (!Scheduler.LowPriority.isEmpty()) {
                            Scheduler.LowPriorityTime += 30;
                            obj = Scheduler.LowPriority.poll();

                        }
                    }
                } else {
                    if (Scheduler.LowPriorityTime <= 100) {
                        if (!Scheduler.LowPriority.isEmpty()) {
                            Scheduler.LowPriorityTime += 30;
                            obj = Scheduler.LowPriority.poll();
                        } else {
                            if (!Scheduler.HighPriority.isEmpty()) {
                                Scheduler.HighPriorityTime += 30;
                                obj = Scheduler.HighPriority.poll();
                            }
                        }
                    }
                }
                baseAddress = obj.getExecutingAddress();
                y = obj.traceSwitch;
                flag = 0;
                int cpuReturnValue = cp.CPU(baseAddress, y, obj);
                if (cpuReturnValue == 1 && SYSTEM.contextswitch==1)
                {
                    ProgressFile(obj,Scheduler.LowPriority,Scheduler.HighPriority,Scheduler.BlockedQueue,
                            "Context Switch",counthigh,SYSTEM.clock, jobID);
                    SYSTEM.contextswitch =0;
                }
                if (cpuReturnValue == 1 && SYSTEM.contextswitch == 0)
                {
                    ProgressFile(obj,Scheduler.LowPriority,Scheduler.HighPriority,Scheduler.BlockedQueue,
                            "I/O request",counthigh,SYSTEM.clock, jobID);
                }
                if (cpuReturnValue == 2) {
                    Partition.partitionstatus.put(obj.getPartitionNumber(), "EMPTY");
                    obj.setEndHexSystemClock(SYSTEM.intToHex(SYSTEM.clock));
                    printOutputFile(obj);
                    normalTerminationCount++;
                    obj.setNatureOfTermination("NORMAL");
                    printingTraceFile(obj);
                    ProgressFile(obj, Scheduler.LowPriority, Scheduler.HighPriority, Scheduler.BlockedQueue,
                            "Job Termination", counthigh, SYSTEM.clock, jobID);
                    if (obj!=null)
                        MEMORY.freeMemory(obj);
                    obj = null;
                }
                String statusOfBlockQueue = Scheduler.checkBlockedQueue(SYSTEM.clock);
            }
            try {
                if (jobCount!=0 && meanDegreeCount!=0) {
                    meanSystemTime = (double) SYSTEM.clock / jobCount;
                    meanIOtime = (double) SYSTEM.IOTime / jobCount;
                    meanExecutionTime = (double) (SYSTEM.clock - SYSTEM.IOTime) / jobCount;
                    meanArrivalTime = meanArrivalTime / jobCount;
                    meanDegree = meanDegree / meanDegreeCount;
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            StatisticsFile(SYSTEM.clock,meanSystemTime,meanIOtime,meanExecutionTime,cpuIdleTime,totalTimeLost,normalTerminationCount,
                    abnormalTerminationCount,HighestDegree,LowDegree,meanDegree,meanArrivalTime);


            reader.close();
            bw.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

//Progress File

    public static void ProgressFile(PCB obj, Queue<PCB> lowPriority, Queue<PCB> highPriority, Queue<PCB> priority, String job_termination, int counthigh, int clock, int jobID) {
        try {
            bw1.newLine();
            bw1.write("===========================================================================");
            bw1.newLine();
            bw1.write("==========================="+job_termination.toUpperCase()+"=================================");
            bw1.newLine();
            if(!job_termination.equals("Job Initiation"))
                bw1.write("1) Warnings and error messages indexed by user job ids: " + obj.getNatureOfTermination());
            else
                bw1.write("1) Warnings and error messages indexed by user job ids: " + jobID);
            bw1.newLine();
            bw1.write("2) Current Degree of multi programming in DECIMAL: " + counthigh);
            bw1.newLine();
            if(!job_termination.equals("Job Initiation"))
                bw1.write("3) id or # of user job currently executing in DECIMAL " +  obj.getJobID());
            else
                bw1.write("3) id or # of user job currently executing in DECIMAL " +  jobID);
            bw1.newLine();
            bw1.write("4) Number of jobs terminated abnormally: " + abnormalTerminationCount );
            bw1.newLine();
            bw1.write("5) statistics and partition dumps for normal terminations: a) System Clock: "+clock);
            bw1.newLine();
            if(!job_termination.equals("Job Initiation"))
                bw1.write("                                                           b) Execution time"+obj.getExecutionTime());
            bw1.newLine();
//if(job_termination.equals("Job Termination")) {
//ArrayList alp = Partition.partitions.get(obj.getPartitionNumber());
//int val = (int)alp.get(0);
//int val2 = (int)alp.get(1);
//ArrayList aa= new ArrayList();
//for (int l = val;l<=val2;l++)
//{
//    if (MEMORY.mainMemory[l] != null)
//     aa.add(MEMORY.mainMemory[l]);
//    else
//        aa.add("null");
//}
//bw1.write("                                "+aa);
//}
            bw1.newLine();
            int size = 0;
            if(!job_termination.equals("Job Initiation"))
                if (Partition.partitionsizes.get(obj.getPartitionNumber()) != null) {
                    if (job_termination.equals("Job Termination"))
                        size = Partition.partitionsizes.get(obj.getPartitionNumber());
                    size += Partition.checkTheSize(size);
                    bw1.write("6) External Fragmentation in FRACTION : " + size + "/" + SYSTEM.memorysize);
                    bw1.newLine();
                    ArrayList al = getList(highPriority);
                    bw1.write("7) Queue List for various queues: a) High Priority: " + al);
                    bw1.newLine();
                    ArrayList al2 = getList(lowPriority);
                    bw1.write("                                  b) Low Priority: " + al2);
                    bw1.newLine();
                    ArrayList al3 = getList(priority);
                    bw1.write("                                  c) Blocked Queue: " + al3);
                    bw1.newLine();
                    bw1.write("8) Degree of multiprogramming: " + counthigh);
                    bw1.newLine();
                    if (!job_termination.equals("Job Initiation"))
                        bw1.write("9) Memory Allocation " + obj.getStartandendaddress());
                }
            bw1.newLine();
//            ArrayList alh = getMemoryDumps(highPriority);
//            ArrayList all = getMemoryDumps(lowPriority);
//            ArrayList alb = getMemoryDumps(priority);
//            bw1.write("10) Partition Dumps: " + );
            bw1.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }
    //To get the list of elements addresses from the queues.
    private static ArrayList getList(Queue<PCB> highPriority) {
        Iterator ir = highPriority.iterator() ;
        ArrayList al = new ArrayList();
        while (ir.hasNext())
        {
            PCB pc = (PCB) ir.next();
            al.add(pc.getJobID());
        }
        return al;
    }
    //Statistics file
    public static void StatisticsFile(int clock, double meanSystemTime, double meanIOtime, double meanExecutionTime,
                                      int cpuIdleTime, int totalTimeLost,int normalTerminationCount, int abnormalTerminationCount, int highestDegree,
                                      int lowDegree, int meanDegree, int meanArrivalTime) {
        try {
            File fr2 = new File("Statistics.txt");
            FileOutputStream fos5 = new FileOutputStream(fr2);
            BufferedWriter bw5 = new BufferedWriter(new OutputStreamWriter(fos5));


            bw5.write("1) current value of the clock: "+ clock);
            bw5.newLine();
            bw5.write("2) mean user job run time: " + (int)meanSystemTime);
            bw5.newLine();
            bw5.write("3) mean user job I/O time: " + (int)meanIOtime);
            bw5.newLine();
            bw5.write("4) mean user job in the SYSTEM: " + (int)meanExecutionTime);
            bw5.newLine();
            bw5.write("5) Total CPU idle time: " + cpuIdleTime);
            bw5.newLine();
            bw5.write("6) Total time lost due to abnormally terminated jobs : "+totalTimeLost);
            bw5.newLine();
            bw5.write("7) Number of jobs that terminated normally: " + normalTerminationCount);
            bw5.newLine();
            bw5.write("8) Number of jobs that terminated abnormally: "+abnormalTerminationCount);
            bw5.newLine();
            bw5.write("9) Highest degree of multiprogramming: " + highestDegree);
            bw5.newLine();
            bw5.write("10) Lowest degree of multiprogramming: " + lowDegree);
            bw5.newLine();
            bw5.write("11) Mean degree of multiprogramming: " + meanDegree);
            bw5.newLine();
            bw5.write("12) Mean Difference between arrival time and start time : " + meanArrivalTime);
            bw5.newLine();

            bw5.close();

        }catch (Exception e)
        {
            System.out.println(e);
        }

    }


    //Converting Hex value to Int value
    public static int hextoint(String line)
    {
        int decimal = 0;
        if (line.length()<5)
            decimal = Integer.parseInt(line, 16);
        return decimal;
    }
    //Converting Hex to Binary value.
    public static String hexToBinary(String line)
    {
        int decimal = Integer.parseInt(line,16);
        String binary = Integer.toBinaryString(decimal);
        return padZeros(binary);
    }
    // Padding Zeros for correct byte length
    public static String padZeros(String line)
    {
        String str = line;
        for(int i = 0; i < 8-line.length(); i++)
        {
            str = "0"+str;
        }
        return str;
    }
    // Converting Binary to Hex value
    public static String binaryToHex(String line)
    {
        line = padZeros16(line);
        Long decimal = Long.parseLong(line,2);
        String hexStr = Long.toString(decimal,16);
        if (hexStr.length()>4)
            hexStr = hexStr.substring(hexStr.length()-4,hexStr.length());
        return hexStr;
    }
    // Evaluating immediate6 bits using 2'complement form.
    public static int Immediate6(String line)
    {
        String line1 = "";
        int flag = 0,value = 0;
        if (line.charAt(0) == '1') {
            for (int j = line.length() - 1; j >= 0; j--) {
                if (flag == 0) {
                    if (line.charAt(j) == '0') {
                        line1 = '0' + line1;
                    } else {
                        line1 = '1' + line1;
                        flag = 1;
                    }
                    continue;
                }
                if (flag == 1) {
                    if (line.charAt(j) == '0') {
                        line1 = '1' + line1;
                    } else
                        line1 = '0' + line1;
                }
            }
            value = Integer.parseInt(line1,2);
            value = -value;
        }
        else {
            value = Integer.parseInt(line,2);
        }
        return value;
    }
    // Converting Binary to Decimal value
    public static int binaryToDecimal(String line)
    {
        int value = Integer.parseInt(line,2);
        return value;
    }
    // Converting Int to Binary value
    public static String intToBinary(int value)
    {
        String str = Integer.toBinaryString(value);
        return padZeros16(str);
    }
    // Padding zeros for if instruction doesn't match 16 bits
    public static String padZeros16(String line)
    {
        String str = line;
        for(int i = 0; i < 16 - line.length(); i++)
        {
            str = "0" + str;
        }
        return str;
    }
    // Converting Integer to Hex value.
    public static String intToHex(int value)
    {
        String Hex = Integer.toHexString(value);
        return Hex;
    }
    // Creation of dump file if error occurs in the file.
    public static int DumpFile(String[] memoryDump)
    {
        try {
//Creating a dump file to write 64 bytes

            if (SYSTEM.dumpobj != null) {
                File fr = new File("memoryDump" + SYSTEM.dumpobj.getJobID() + ".txt");
                FileOutputStream fos = new FileOutputStream(fr);
                BufferedWriter bw4 = new BufferedWriter(new OutputStreamWriter(fos));

                ArrayList al = new ArrayList();
                if (Partition.partitions.get(SYSTEM.dumpobj.getPartitionNumber()) != null) {
                    al = Partition.partitions.get(SYSTEM.dumpobj.getPartitionNumber());
                    int startAddress = (int) al.get(0);
                    int endAddress = (int) al.get(1);
                    int k = startAddress;
// Printing the format by 8*8
                    for (int i = 0; i < 8; i++) {
                        if (k < 10)
                            bw4.write("000" + k + "\t");
                        else
                            bw4.write("00" + k + "\t");
                        for (int j = 0; j < 8; j++) {
                            if (k + j < 256)
                                if (MEMORY.mainMemory[k + j] != null) {
                                    String x = SYSTEM.binaryToHex(MEMORY.mainMemory[k + j]);
                                    if (x.length() < 4) {
                                        while (x.length() != 4)
                                            x = "0" + x;
                                    }
                                    bw4.write(x + "\t");

                                } else
                                    bw4.write("----" + "\t");
                        }
                        if (k > endAddress)
                            break;
                        k = k + 8;
                        bw4.newLine();
                    }
                }
                bw4.close();
            }
        }
        catch (Exception e)
        {

            System.out.println(e);

        }
        return 0;
    }
    // Printing the output file with job id,execution time,Nature of termination.
    public static void printOutputFile(Object obj)
    {
        try {
            PCB pc = (PCB)obj;
            bw.write("1) Cumulative job identification Number in DECIMAL: "+ pc.getJobID());
            bw.newLine();
            bw.write("2) All the start and end addresses occupied by this job: "+pc.getStartandendaddress());
            bw.newLine();
            bw.write("3) Arrival Time: "+pc.getArrivalTime());
            bw.newLine();
            if (pc.getNatureOfTermination().equals("ABNORMAL")) {
                totalTimeLost  = totalTimeLost + pc.getExecutionTime();
                abnormalTerminationCount++;
            }
            else
                normalTerminationCount++;
            bw.write("4) Time the job entered the system: "+pc.getHexSystemClock());
            bw.newLine();
            bw.write("5) Time the job is leaving the system: "+pc.getEndHexSystemClock());
            bw.newLine();
            bw.write("6) Execution Time:"+pc.getExecutionTime());
            bw.newLine();
            bw.write("7) Time spent during I/O :"+pc.getIoTime());
            bw.newLine();
            bw.write("8) Priority "+pc.getPriority());
            bw.newLine();
            bw.write("9) Nature of Termination: "+pc.getNatureOfTermination());
            bw.newLine();
            if (pc.getNatureOfTermination().equals("ABNORMAL"))
                bw.write("                        : "+pc.getErrorMessage());
            bw.newLine();
            bw.newLine();
            bw.write("---------------------------------------------------------------------------------");
            bw.newLine();
        }
        catch (Exception e)
        {

            System.out.println(e);
        }
        return;
    }
    // Printing trace file if trace switch is on.
    private static void printingTraceFile(Object ob) {
        PCB pc = (PCB) ob;
        if (((PCB) ob).getNatureOfTermination()!="ABNORMAL" && ((PCB) ob).getTraceSwitch()==1) {
            String ss = pc.tracebuild.toString();
            String[] st = ss.split(":");
// Converting strings to array if not null.
            st = Arrays.stream(st)
                    .filter(s -> (s != null && s.length() > 0))
                    .toArray(String[]::new);
// Creation of tracefile.
            File fr = new File("TraceFile"+((PCB) ob).getJobID()+".txt");
            FileOutputStream fos9 = null;
            try {
// FileOutput write up
                fos = new FileOutputStream(fr);
                BufferedWriter bw9 = new BufferedWriter(new OutputStreamWriter(fos));
                bw9.write("                                             Before Execution   After Execution");
                bw9.newLine();
                bw9.write("PC     Instruction              R       EA      (R)   (EA)        (R)  (EA)  ");
                bw9.newLine();
                bw9.write("                                        Int     Int    Hex        Int   Hex  ");
                bw9.newLine();
                bw9.write("-------------------------------------------------------------------------------");
                bw9.newLine();
// Adding gap to word wrap in a trace file.
                for (String s : st
                ) {
                    if (s.equals("AddGap")) {
                        bw9.newLine();
                    } else {
                        bw9.write(s);
                        bw9.newLine();
                    }

                }
                bw9.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        return;
    }
    //Evaluating immediate12 bits using 2'complement form.
    public static int Immediate12(String line) {
        String line1 = "";
        int flag = 0,value = 0;
        if (line.charAt(0) == '1') {
            for (int j = line.length() - 1; j >= 0; j--) {
                if (flag == 0)
                {
                    if (line.charAt(j) == '0') {
                        line1 = '0' + line1;

                    } else {
                        line1 = '1' + line1;
                        flag = 1;
                    }
                    continue;
                }
                if (flag == 1) {
                    if (line.charAt(j) == '0') {
                        line1 = '1' + line1;
                    } else
                        line1 = '0' + line1;
                }
            }
            value = Integer.parseInt(line1,2);
            value = -value;
        }
        else {
            value = Integer.parseInt(line,2);
        }
        return value;
    }
}
