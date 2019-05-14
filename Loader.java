/*
* Loader loads the information of each user job.
* The job contains the information in hex value for trace
* switch, Program size and starting address.
* */
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@SuppressWarnings("unchecked")
public class LOADER {
private static int checkLength;
public int LOADER(int x,int y) {
int flag=0;
int status = 0;
try {

SYSTEM.line = SYSTEM.reader.readLine();
int count = 1;
String regex = "^END*";
Pattern r =  Pattern.compile(regex);
Matcher m = r.matcher(SYSTEM.line);
while (!m.find()) {
String regex1 = "^JOB*";
Pattern r1 = Pattern.compile(regex1);
Matcher m1 = r1.matcher(SYSTEM.line);
if (m1.find())
{
flag = 4;
break;
}
if (count < SYSTEM.pc[SYSTEM.jobID].getProgramSize() * 2) {
for (int j = 0; j < SYSTEM.line.length(); j = j + 2) {
count++;
String str = null;
try {
str = SYSTEM.line.substring(j, j + 2);
} catch (Exception e) {
    e.printStackTrace();
SYSTEM.pc[SYSTEM.jobID].setNatureOfTermination("ABNORMAL");
SYSTEM.N = 8;
SYSTEM.dumpobj = SYSTEM.pc[SYSTEM.jobID];
ERRORHANDLER.getErrorMap(SYSTEM.N);
SYSTEM.pc[SYSTEM.jobID].setHexSystemClock(SYSTEM.intToHex(SYSTEM.clock));
SYSTEM.pc[SYSTEM.jobID].setEndHexSystemClock(SYSTEM.intToHex(SYSTEM.clock));
SYSTEM.pc[SYSTEM.jobID].setErrorMessage(ERRORHANDLER.getErrorMap(SYSTEM.N));
SYSTEM.printOutputFile(SYSTEM.pc[SYSTEM.jobID]);
MEMORY.freeMemory(SYSTEM.pc[SYSTEM.jobID]);
flag = 2;
}
String binary = null;
try {
binary = SYSTEM.hexToBinary(str);
} catch (Exception e) {
    e.printStackTrace();
SYSTEM.dumpobj = SYSTEM.pc[SYSTEM.jobID];
SYSTEM.pc[SYSTEM.jobID].setNatureOfTermination("ABNORMAL");
SYSTEM.N = 8;
SYSTEM.pc[SYSTEM.jobID].setHexSystemClock(SYSTEM.intToHex(SYSTEM.clock));
SYSTEM.pc[SYSTEM.jobID].setEndHexSystemClock(SYSTEM.intToHex(SYSTEM.clock));
SYSTEM.pc[SYSTEM.jobID].setErrorMessage(ERRORHANDLER.getErrorMap(SYSTEM.N));
SYSTEM.printOutputFile(SYSTEM.pc[SYSTEM.jobID]);
MEMORY.freeMemory(SYSTEM.pc[SYSTEM.jobID]);
flag = 2;

}
MEMORY.MEMORY("WRITE", x, binary);
x = x + 1;
}
} else {
status = 7;

try {
String[] st = SYSTEM.line.split(" ");
if (SYSTEM.hextoint(st[0]) != 0 && SYSTEM.hextoint(st[0]) != 1) {
SYSTEM.dumpobj = SYSTEM.pc[SYSTEM.jobID];
SYSTEM.pc[SYSTEM.jobID].setNatureOfTermination("ABNORMAL");
SYSTEM.N = 7;
SYSTEM.pc[SYSTEM.jobID].setHexSystemClock(SYSTEM.intToHex(SYSTEM.clock));
SYSTEM.pc[SYSTEM.jobID].setEndHexSystemClock(SYSTEM.intToHex(SYSTEM.clock));
SYSTEM.pc[SYSTEM.jobID].setErrorMessage(ERRORHANDLER.getErrorMap(SYSTEM.N));
SYSTEM.printOutputFile(SYSTEM.pc[SYSTEM.jobID]);
MEMORY.freeMemory(SYSTEM.pc[SYSTEM.jobID]);
status = 1;
flag = 2;
}
if (status != 1)
SYSTEM.pc[SYSTEM.jobID].setTraceSwitch(SYSTEM.hextoint(st[0]));
if (st.length>1)
if (SYSTEM.hextoint(st[1]) != 0 && SYSTEM.hextoint(st[1]) != 1) {
SYSTEM.dumpobj = SYSTEM.pc[SYSTEM.jobID];
SYSTEM.pc[SYSTEM.jobID].setNatureOfTermination("ABNORMAL");
SYSTEM.N = 10;
SYSTEM.pc[SYSTEM.jobID].setHexSystemClock(SYSTEM.intToHex(SYSTEM.clock));
SYSTEM.pc[SYSTEM.jobID].setEndHexSystemClock(SYSTEM.intToHex(SYSTEM.clock));
SYSTEM.pc[SYSTEM.jobID].setErrorMessage(ERRORHANDLER.getErrorMap(SYSTEM.N));
SYSTEM.printOutputFile(SYSTEM.pc[SYSTEM.jobID]);
MEMORY.freeMemory(SYSTEM.pc[SYSTEM.jobID]);
status = 10;
flag = 2;
}
if (status != 1 || status != 10)
SYSTEM.pc[SYSTEM.jobID].setPriority(SYSTEM.hextoint(st[1]));
} catch (Exception e) {
    e.printStackTrace();
SYSTEM.pc[SYSTEM.jobID].setNatureOfTermination("ABNORMAL");
SYSTEM.dumpobj = SYSTEM.pc[SYSTEM.jobID];
if (status == 1)
SYSTEM.N = 7;
else
SYSTEM.N = 10;
SYSTEM.pc[SYSTEM.jobID].setHexSystemClock(SYSTEM.intToHex(SYSTEM.clock));
SYSTEM.pc[SYSTEM.jobID].setEndHexSystemClock(SYSTEM.intToHex(SYSTEM.clock));
SYSTEM.pc[SYSTEM.jobID].setErrorMessage(ERRORHANDLER.getErrorMap(SYSTEM.N));
SYSTEM.printOutputFile(SYSTEM.pc[SYSTEM.jobID]);
MEMORY.freeMemory(SYSTEM.pc[SYSTEM.jobID]);
flag = 2;
}
}
SYSTEM.line = SYSTEM.reader.readLine();
if (SYSTEM.line.equals("END"))
break;
}

return flag;
} catch (Exception e) {
    e.printStackTrace();
SYSTEM.pc[SYSTEM.jobID].setNatureOfTermination("ABNORMAL");
SYSTEM.dumpobj = SYSTEM.pc[SYSTEM.jobID];
if (status == 1)
SYSTEM.N = 7;
else
SYSTEM.N = 10;
SYSTEM.pc[SYSTEM.jobID].setHexSystemClock(SYSTEM.intToHex(SYSTEM.clock));
SYSTEM.pc[SYSTEM.jobID].setEndHexSystemClock(SYSTEM.intToHex(SYSTEM.clock));
SYSTEM.pc[SYSTEM.jobID].setErrorMessage(ERRORHANDLER.getErrorMap(SYSTEM.N));
SYSTEM.printOutputFile(SYSTEM.pc[SYSTEM.jobID]);
MEMORY.freeMemory(SYSTEM.pc[SYSTEM.jobID]);
flag = 2;

}

return flag;
}
}















