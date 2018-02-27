/*->System and User errors are handled through calls from the 
 * CPU and SYSTEM programs with correct description and the 
 * error is printed back to the Output File  
 -> PrintError is the global variable declared and used to
 print the error to the output file.
 *
 */
public class ERRORHANDLER {
 static String printerror;
 public static String TerminationType;

 public static void IntegerOutofrange() {
  /*Integer Out of Range*/
  CPU.outputfile.add("NATURE OF TERMINATION: ABNORMAL");
  printerror = "IntegerOutofrange";
  CPU.outputfile.add("Error" + printerror);
  CPU.outputfile.add("Clock Value" + CPU.inttohex(SYSTEM.clock));
  CPU.outputfile.add("RunTime" + CPU.iotime);
  CPU.outputfile.add("Execution time" + CPU.executiontime);
  CPU.listiterator();
  System.exit(0);
 }

 public static void overorunderflow(int tOS) {
  /*Overflow or underflow*/
  if (tOS < -1) {
   CPU.outputfile.add("NATURE OF TERMINATION: ABNORMAL");

   printerror = "Underflow";
   CPU.outputfile.add("Error" + printerror);
   CPU.outputfile.add("Clock Value" + CPU.inttohex(SYSTEM.clock));
   CPU.outputfile.add("RunTime" + CPU.iotime);
   CPU.outputfile.add("Execution time" + CPU.executiontime);
   CPU.listiterator();
  } else if (tOS > 7) {
   CPU.outputfile.add("NATURE OF TERMINATION: ABNORMAL");
   printerror = "Overflow";
   CPU.outputfile.add("Error:-" + printerror);
   CPU.outputfile.add("Clock Value:-" + CPU.inttohex(SYSTEM.clock));
   CPU.outputfile.add("RunTime:-" + CPU.iotime);
   CPU.outputfile.add("Execution time:-" + CPU.executiontime);
   CPU.listiterator();
  }
  System.exit(0);
 }

 public static void unknownTraceSwitch() {
  /*unknown Trace Switch*/
  CPU.outputfile.add("NATURE OF TERMINATION:- ABNORMAL");
  printerror = "unknownTraceSwitch";
  CPU.outputfile.add("Error:-" + printerror);
  CPU.outputfile.add("Clock Value:-" + CPU.inttohex(SYSTEM.clock));
  CPU.outputfile.add("RunTime:-" + CPU.iotime);
  CPU.outputfile.add("Execution time:-" + CPU.executiontime);
  CPU.listiterator();
  System.exit(0);
 }

 public static void InvalidOpcode() {
  /*Invalid opcode*/
  CPU.outputfile.add("NATURE OF TERMINATION: ABNORMAL");
  printerror = "Opcode is Invalid";
  CPU.outputfile.add("Error:-" + printerror);
  CPU.outputfile.add("Clock Value:-" + CPU.inttohex(SYSTEM.clock));
  CPU.outputfile.add("RunTime:-" + CPU.iotime);
  CPU.outputfile.add("Execution time:-" + CPU.executiontime);
  CPU.listiterator();
  System.exit(0);
 }

 public static void IRERROR() {
  CPU.outputfile.add("NATURE OF TERMINATION: ABNORMAL");
  printerror = "Cannot Fetch Invalid location in memory";
  CPU.outputfile.add("Error:-" + printerror);
  System.exit(0);
 }

 public static void fileNotFoundException() {
  // TODO Auto-generated method stub
  CPU.outputfile.add("NATURE OF TERMINATION: ABNORMAL");
  CPU.outputfile.add("Error:-" + printerror);
  CPU.outputfile.add("Clock Value:-" + CPU.inttohex(SYSTEM.clock));
  CPU.outputfile.add("RunTime:-" + CPU.iotime);
  CPU.outputfile.add("Execution time:-" + CPU.executiontime);
  CPU.listiterator();
  System.exit(0);
 }

 public static void clockValue() {
  // TODO Auto-generated method stub
  CPU.outputfile.add("NATURE OF TERMINATION: ABNORMAL");
  printerror = "Infinite job";
  CPU.outputfile.add("Error:-" + printerror);
  CPU.outputfile.add("Clock Value:-" + CPU.inttohex(SYSTEM.clock));
  CPU.outputfile.add("RunTime:-" + CPU.iotime);
  CPU.outputfile.add("Execution time:-" + CPU.executiontime);
  CPU.listiterator();
  System.exit(0);
 }

 public static void PCERROR() {
  // TODO Auto-generated method stub
  CPU.outputfile.add("NATURE OF TERMINATION:- ABNORMAL");
  printerror = "program counter is invalid";
  CPU.outputfile.add("Error:-" + printerror);
  CPU.outputfile.add("Clock Value" + CPU.inttohex(SYSTEM.clock));
  CPU.outputfile.add("RunTime" + CPU.iotime);
  CPU.outputfile.add("Execution time" + CPU.executiontime);
  CPU.listiterator();
  System.exit(0);
 }

 public static void mismatch() {
  // TODO Auto-generated method stub
  CPU.outputfile.add("NATURE OF TERMINATION: ABNORMAL");
  printerror = "Invalid input";
  CPU.outputfile.add("Error" + printerror);
  CPU.outputfile.add("Clock Value:-" + CPU.inttohex(SYSTEM.clock));
  CPU.outputfile.add("RunTime:-" + CPU.iotime);
  CPU.outputfile.add("Execution time:-" + CPU.executiontime);
  CPU.listiterator();
  System.exit(0);
 }

}
