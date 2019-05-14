//Process control block maintains the state of the batch processing process
//PCB's are used to control the flow of the executions throughout the program.
import java.util.ArrayList;
@SuppressWarnings("unchecked")
public class PCB {
int jobID;
int[] GPR = new int[8];
int clockuponArrival;
int usageclock;
int arrivalTime;
int SystemClock;
int priority;
int executionStartAddress;
int executionEndAddress;
int programSize;
int partitionNumber;
int PCBpreviousaddress;
int loadStartAddress;
int executingAddress;
int traceSwitch;
int lastQuantum;
String ErrorMessage = null;
String NatureOfTermination;
String[] traceFile;

public int getNewloadstartaddress() {
    return newloadstartaddress;
}

public void setNewloadstartaddress(int newloadstartaddress) {
    this.newloadstartaddress = newloadstartaddress;
}

int newloadstartaddress;

public ArrayList getStartandendaddress() {
    return startandendaddress;
}

public void setStartandendaddress(ArrayList startandendaddress) {
    this.startandendaddress = startandendaddress;
}

ArrayList startandendaddress = new ArrayList();
StringBuilder tracebuild1 = new StringBuilder();
StringBuilder tracebuild2 = new StringBuilder();
StringBuilder tracebuild = new StringBuilder();

int PartitionStartAddress;
int PCBSystemClock;
int partitionNumberNew;


String SA;
String EA;

String hexSystemClock;
String endHexSystemClock;

int executionTime=0;
int ioTime=0;

//All getter and setter methods


public int getExecutionTime() {
    return executionTime;
}

public void setExecutionTime(int executionTime) {
    this.executionTime = executionTime;
}

public int getIoTime() {
    return ioTime;
}

public void setIoTime(int ioTime) {
    this.ioTime = ioTime;
}

public String getHexSystemClock() {
    return hexSystemClock;
}

public void setHexSystemClock(String hexSystemClock) {
    this.hexSystemClock = hexSystemClock;
}

public void setEndHexSystemClock(String endHexSystemClock) {
    this.endHexSystemClock = endHexSystemClock;
}


public String getEndHexSystemClock() {
    return endHexSystemClock;
}

public void setSA(String SA) {
    this.SA = SA;
}

public void setEA(String EA) {
    this.EA = EA;
}

public String getEA() {
    return EA;
}

public String getSA() {
    return SA;
}

public int getPartitionNumberNew() {
    return partitionNumberNew;
}

public void setPartitionNumberNew(int partitionNumberNew) {
    this.partitionNumberNew = partitionNumberNew;
}

public int getPartitionStartAddress() {
    return PartitionStartAddress;
}

public void setPartitionStartAddress(int partitionStartAddress) {
    PartitionStartAddress = partitionStartAddress;
}

public void setPCBSystemClock(int PCBSystemClock) {
    this.PCBSystemClock = PCBSystemClock;
}

public int getPCBSystemClock() {
    return PCBSystemClock;
}

public int getPartitionNumber() {
    return partitionNumber;
}

public void setPartitionNumber(int partitionNumber) {
    this.partitionNumber = partitionNumber;
}

public int getClockuponArrival() {
    return clockuponArrival;
}

public void setClockuponArrival(int clockuponArrival) {
    this.clockuponArrival = clockuponArrival;
}

public int getPCBpreviousaddress() {
    return PCBpreviousaddress;
}

public void setPCBpreviousaddress(int PCBpreviousaddress) {
    this.PCBpreviousaddress = PCBpreviousaddress;
}

public int getExecutionEndAddress() {
    return executionEndAddress;
}

public void setExecutionEndAddress(int executionEndAddress) {
    this.executionEndAddress = executionEndAddress;
}

public int getExecutingAddress() {
    return executingAddress;
}

public void setExecutingAddress(int executingAddress) {
    this.executingAddress = executingAddress;
}

public int getLoadStartAddress() {
    return loadStartAddress;
}

public void setLoadStartAddress(int loadStartAddress) {
    this.loadStartAddress = loadStartAddress;
}

public String getErrorMessage() {
    return ErrorMessage;
}

public void setErrorMessage(String errorMessage) {
    ErrorMessage = errorMessage;
}

public String getNatureOfTermination() {
    return NatureOfTermination;
}

public void setNatureOfTermination(String natureOfTermination) {
    NatureOfTermination = natureOfTermination;
}

public int getLastQuantum() {
    return lastQuantum;
}

public void setLastQuantum(int lastQuantum) {
    this.lastQuantum = lastQuantum;
}

public int getSystemClock() {
    return SystemClock;
}

public void setSystemClock(int systemClock) {
    SystemClock = systemClock;
}

public int getTraceSwitch() {
    return traceSwitch;
}

public void setTraceSwitch(int traceSwitch) {
    this.traceSwitch = traceSwitch;
}

public void setProgramSize(int programSize) {
    this.programSize = programSize;
}

public int getProgramSize() {
    return programSize;
}

public void setexecutionStartAddress(int startAddress) {
    this.executionStartAddress = startAddress;
}

public int getexecutionStartAddress() {
    return executionStartAddress;
}

public int getPriority() {
    return priority;
}

public void setPriority(int priority) {
    this.priority = priority;
}

public int getArrivalTime() {
    return arrivalTime;
}

public void setArrivalTime(int arrivalTime) {
    this.arrivalTime = arrivalTime;
}

public int getJobID() {
    return jobID;
}

public void setJobID(int jobID) {
    this.jobID = jobID;
}

}
