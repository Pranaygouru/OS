/*Declaration of packages*/
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Stack;
/**
->The global variables declared in the CPU mainly are program counter,
Instruction register,Stack,TOS,Base register and few other variables which 
are useful to represent the TOS+1,TOS-1 and many variables for opcodes.

->CPU receives a system call then the corresponding instructions are
 executed by fetching with opcode.The instructions are executed as per
  the increment of program counter and moreover the effective address of each
Instruction(one address)is calculated and fetched from the memory to place it 
on the stack.According to the operation chosen the TOS value will be incremented
and decremented and if there is an underflow or overflow error is thrown from the 
error handler.The CPU loops indefinitely,fetching,decoding and executing halt or 
i/o instruction is performed by the CPU The system variable clock will be 
incremented by 4vtu by one address instructions and 1vtu by zero address instructions
If an I/O instruction is fetched from the memory the clock will be increased by
 15vtu and the result of i/o is printed on console and creates a new file as well.

->The following program implements thoroughly and accurately with
 integer starting from -2^13 to 2^13-1 and if trace flag is 0
contents are not executed and output files are not created.
The size of registers and length of tos are strictly mentioned and 
contained.
**/
public class CPU {
 /*Global variable declarations and values*/
 static int TOS = 0, termination;
 static int switchopcode = 0, switchopcodezero = 0, executiontime;
 static int Onlyhex = 1, test = 0, TraceSwitchone = 0;
 static int DecimalValue = 0, iotime;
 static int temppc = 0, stack = 0, stackplus = 0, stackminus = 0;
 static String[] S = new String[7];
 static int clockcheck = 0;
 String convr = null;
 String memor, EA;
 static String BR;
 static String IR;
 static char[] str = new char[16];
 static PrintWriter writeroutput;
 static ArrayList outputfile = new ArrayList();

 public Object CPU(int PC, int TraceSwitch) throws Exception {
   int ZeroAddress = 0;
   /*creation of files using printwriter class and streaming output into it*/
   PrintWriter writer = new PrintWriter(System.out);
   writeroutput = new PrintWriter(System.out);
   if (!(TraceSwitch == 0))
    writer = new PrintWriter(new FileOutputStream("Trace Flag Output", false));
   writeroutput = new PrintWriter(new FileOutputStream("Outputfile", false));
   writer.println("\t(PC)\t (BR)\t  (IR)\t     (TOS)\tS[TOS]\t EA\t(EA)" + " \t\tTOS\t S[TOS]   EA\t (EA)");
   writer.println("\t\t\t\t\tBeforeExecution\t BeforeExecution\t" + "AfterExecution\tBeforeExecution");
   outputfile.add("JOB ID:" + SYSTEM.array[0]);
   /*If TraceSwitch is more than one hex value errorhandler catches the exceptions*/
   if (TraceSwitch > 15) {
    ERRORHANDLER.unknownTraceSwitch();
   }
   outputfile.add("Warning Messages: ");
   /*Looping indefinitely till it exits*/
   while (true) {
    try {
     /*fetching IR instructions from the mainmemmory via memory call*/
     IR = MEMORY.MEMORY(0, PC, "");
     if (IR == null) {
      ERRORHANDLER.IRERROR();
     }
     if (PC > 127) {
      ERRORHANDLER.PCERROR();
     }
     /*creating a arraylist and adding contents to the trace file */
     ArrayList al = new ArrayList();

     if (TraceSwitch == 1 && TOS >= 0) {
      PC = PC + 1;
      al.add(inttohex(PC));
      BR = SYSTEM.array[1];
      al.add(BR);
      al.add(binarytohex(IR));
      al.add(inttohex(TOS));
      if (S[TOS] != null) {
       String sts = S[TOS];
       al.add(binarytohex(sts));
      } else {
       al.add("0");
      }
     }
     /*
      * If the contents of Instruction Register and character at 1st location is '0' 
      * and TraceSwitch is not executed if it is 0
      */
     if (IR.charAt(0) == '0' && TraceSwitch != 0) {
      String Opcode = IR.substring(3, 8);
      // PC=PC+1;
      TraceSwitchone = 0;
      /*Fetching two instructions of a zero address instruction and executing them one after the other*/
      while (ZeroAddress < 2) {
       switchopcode = Integer.parseInt(Opcode, 2);
       if (switchopcode < 0 || switchopcode > 25) {
        ERRORHANDLER.InvalidOpcode();
       }
       /*if there is a NOP after return statement clock value is not incremented*/
       if (clockcheck == 1 && switchopcode == 0 && ZeroAddress == 1) {
        SYSTEM.clock--;
        executiontime--;
       }
       clockcheck = 0;
       SYSTEM.clock = SYSTEM.clock + 1;
       executiontime++;
       /*conditions not to add values to prevent from out of bounds exception*/
       if (TOS >= 0 && TOS < 6) {
        if (S[TOS] != null) {
         stack = Integer.parseInt(S[TOS], 2);
         if (S[TOS + 1] != null) {
          stackplus = Integer.parseInt(S[TOS + 1], 2);
         }
         if (S[TOS - 1] != null) {
          stackminus = Integer.parseInt(S[TOS - 1], 2);
         }
        }
        /*Opcode which is fetched from the instruction register is executed via switch case*/
        if (switchopcode < 32000)
         switch (switchopcode) {
          case 0:
           break; /*NOP*/
          case 1:
           /* Or Operation*/
           stackminus = stack | stackminus;
           S[TOS - 1] = Integer.toBinaryString(stackminus);
           TOS = TOS - 1;
           break;
          case 2:
           /* And Operation*/
           stackminus = stack & stackminus;
           S[TOS - 1] = Integer.toBinaryString(stackminus);
           TOS = TOS - 1;
           break;
          case 3:
           /*not operation calculating 1's complement*/
           String splitstring1 = S[TOS];
           while (splitstring1.length() < 16) {
            splitstring1 = '0' + splitstring1;
           }
           for (int len = 0; len < splitstring1.length(); len++) {
            if (splitstring1.charAt(len) == '0') {
             str[len] = '1';
            } else {
             str[len] = '0';
            }
           }
           String conver = String.valueOf(str);

           S[TOS] = conver;
           break;
          case 4:
           /*XOR operation*/
           stackminus = stack ^ stackminus;
           S[TOS - 1] = Integer.toBinaryString(stackminus);
           TOS = TOS - 1;
           break;
          case 5:
           /*ADD Operation*/
           if (S[TOS].length() == 16) {
            if (S[TOS].charAt(0) == '1') {
             stack = reverse_complement(S[TOS]);
            }
           }
           stackminus = stack + stackminus;
           S[TOS - 1] = Integer.toBinaryString(stackminus);
           TOS = TOS - 1;
           break;
          case 6:
           /*SUBTRACT Operation*/
           if (S[TOS].length() == 16) {
            if (S[TOS].charAt(0) == '1') {
             stack = reverse_complement(S[TOS]);
            }
           }
           stackminus = stack - stackminus;
           S[TOS - 1] = Integer.toBinaryString(stackminus);
           TOS = TOS - 1;
           break;
          case 7:
           /*Multiplication Operation*/
           if (S[TOS].length() == 16) {
            if (S[TOS].charAt(0) == '1') {
             stack = reverse_complement(S[TOS]);
            }
           }
           stackminus = stack * stackminus;
           S[TOS - 1] = Integer.toBinaryString(stackminus);
           TOS = TOS - 1;
           break;
          case 8:
           /*Division Operation*/
           if (S[TOS].length() == 16) {
            if (S[TOS].charAt(0) == '1') {
             stack = reverse_complement(S[TOS]);
            }
           }
           stackminus = stack / stackminus;
           S[TOS - 1] = Integer.toBinaryString(stackminus);
           TOS = TOS - 1;
           break;
          case 9:
           /*Modular Division Operation*/
           if (S[TOS].length() == 16) {
            if (S[TOS].charAt(0) == '1') {
             stack = reverse_complement(S[TOS]);
            }
           }
           stackminus = stack % stackminus;
           S[TOS - 1] = Integer.toBinaryString(stackminus);
           TOS = TOS - 1;
           break;
          case 10:
           /*SHIFT LEFT Operation*/
           stack = stack << 1;
           S[TOS] = Integer.toBinaryString(stack);

           break;
          case 11:
           /*SHIFT RIGHT Operation*/
           stack = stack >> 1;
           S[TOS] = Integer.toBinaryString(stack);
           break;
          case 12:
           /*Compare if S[TOS-1]>S[TOS]*/
           if (S[TOS].length() == 16) {
            if (S[TOS].charAt(0) == '1') {
             stack = reverse_complement(S[TOS]);
            }
           }
           if (stackminus > stack) {
            stackplus = 1;
           } else {
            stackplus = 0;
           }
           S[TOS + 1] = Integer.toBinaryString(stackplus);
           TOS = TOS + 1;
           break;
          case 13:
           /*Compare if S[TOS-1]<S[TOS]*/
           if (S[TOS].length() == 16) {
            if (S[TOS].charAt(0) == '1') {
             stack = reverse_complement(S[TOS]);
            }
           }
           if (stackminus < stack) {
            stackplus = 1;
           } else {
            stackplus = 0;
           }
           S[TOS + 1] = Integer.toBinaryString(stackplus);
           TOS = TOS + 1;
           break;
          case 14:
           /*Compare if S[TOS-1]=S[TOS]*/
           if (S[TOS].length() == 16) {
            if (S[TOS].charAt(0) == '1') {
             stack = reverse_complement(S[TOS]);
            }
           }
           if (stackminus == stack) {
            stackplus = 1;
           } else {
            stackplus = 0;
           }
           S[TOS + 1] = Integer.toBinaryString(stackplus);
           TOS = TOS + 1;
           break;
          case 15:
           break; /*NOP*/
          case 16:
           break; /*NOP*/
          case 17:
           break; /*NOP*/
          case 18:
           break; /*NOP*/
          case 19:
           /*Reading input from the system*/
           TOS = TOS + 1;
           iotime = iotime + 15;
           SYSTEM.clock = SYSTEM.clock + 15;

           try {
            int rangeCheck = SYSTEM.in.nextInt();
            /*Checking the range of the number*/
            if (rangeCheck > (Math.pow(2, 13) - 1) || rangeCheck < (-Math.pow(2, 13))) {
             ERRORHANDLER.IntegerOutofrange();
            }
            String len32 = Integer.toBinaryString(rangeCheck);
            if (len32.charAt(0) == '1' && len32.length() == 32) {
             convr = len32.substring(16, 32);
             S[TOS] = convr;
            } else {
             S[TOS] = len32;
            }
           } catch (InputMismatchException e) {
            ERRORHANDLER.mismatch();
           }
           break;
          case 20:
           /*Printing output to console write operation*/
           String result = Integer.toBinaryString(stack);
           if (result.length() > 16) {
            int response = result.length();
            result = result.substring(response - 16, response);
           }
           if (result.length() < 16) {
            result = padzeros(result);
           }
           if (termination < 1) {
            outputfile.add("Nature Of termination: Normal");
           }
           termination++;
           outputfile.add("                              " + result);

           System.out.println(result);
           iotime = iotime + 15;
           SYSTEM.clock = SYSTEM.clock + 15;
           TOS = TOS - 1;
           break;
          case 21:
           /*Return Statement*/
           PC = stack;
           TOS = TOS - 1;
           clockcheck = 1;
           break;
          case 22:
           break; /*NOP*/
          case 23:
           break; /*NOP*/
          case 24:
           /*Incrementing the clock value*/
           writer.close();
           if (SYSTEM.clock > 10000) {
            ERRORHANDLER.clockValue();
           }
           String clockhex = (String) inttohex(SYSTEM.clock);
           /*ADDING CONTENTS TO OUTPUT FILE*/
           outputfile.add("clock value:" + clockhex);
           outputfile.add("Errors:");
           outputfile.add("RunTime :");
           outputfile.add("IO time  " + iotime);
           outputfile.add("Execution time" + executiontime);
           Iterator < String > itr = outputfile.iterator();
           while (itr.hasNext()) {
            String element = itr.next();
            writeroutput.print(String.format("%10s", element));
            writeroutput.println();
           }
           /*closing the writer object*/
           writeroutput.close();
           System.exit(0);
           break;
          default:
           break;
         }
       }
       /*Incrementing it to next address in the sequence*/
       ZeroAddress++;
       Opcode = IR.substring(11, 16);
      }
      ZeroAddress = 0;
      if (TraceSwitch == 1 && TOS >= 0) {
       /*Adding contents to the trace file*/
       al.add("--");
       al.add("--");
       al.add(inttohex(TOS));
       al.add(binarytohex(S[TOS]));
       al.add("--");
       al.add("--");
      }
     }
     /*If bit at character1 is 1 and traceswitch is not equal to zero performing one address instructions*/
     if (IR.charAt(0) == '1' && IR.length() == 16 && TraceSwitch != 0) {
      /*Decoding the opcode from one address instruction*/
      String Opcodeone = IR.substring(1, 6);
      /*Effective Address is calculated and fetched from the memory*/
      EA = IR.substring(9, 16);
      BR = SYSTEM.array[1];
      BR = hextobinary(BR);
      int baseadd = Integer.parseInt(BR);
      switchopcodezero = Integer.parseInt(Opcodeone, 2);
      if (switchopcodezero < 0 && switchopcodezero > 24) {
       ERRORHANDLER.InvalidOpcode();
      }
      int DecimalEA = Integer.parseInt(EA, 2);
      if (IR.charAt(6) == '1' && S[TOS] != null && S[TOS] == "10%") {
       int stackadd = Integer.parseInt(S[TOS]);
       DecimalEA = DecimalEA + baseadd + stackadd;
      } else {
       DecimalEA = DecimalEA + baseadd;
      }
      String Z = SYSTEM.BufferRegister;
      /*calculating and fetching from the memory buffer*/
      memor = MEMORY.MEMORY(0, DecimalEA, Z); /*0 -read and DecimalEA is EA and Z is the bufferregiser**/
      TraceSwitchone = 1;
      if (TraceSwitch == 1 && TraceSwitchone == 1) {
       al.add(binarytohex(EA));
       al.add(binarytohex(memor));
      }
      /*clock and execution times are incremented by 4vtu*/
      SYSTEM.clock = SYSTEM.clock + 4;
      executiontime = executiontime + 4;
      Z = memor;
      int ee = Integer.parseInt(Z, 2);
      if (TOS >= 0 && TOS < 6) {
       if (S[TOS] != null) {
        stack = Integer.parseInt(S[TOS], 2);
        if (S[TOS + 1] != null) {
         stackplus = Integer.parseInt(S[TOS + 1], 2);
        }

        if ((TOS - 1) >= 0 && S[TOS - 1] != null) {
         stackminus = Integer.parseInt(S[TOS - 1], 2);
        }
       }
       if (TOS == 7) {
        stack = Integer.parseInt(S[TOS], 2);
        if (S[TOS - 1] != null) {
         stackminus = Integer.parseInt(S[TOS - 1], 2);
        }
       }
       if (switchopcodezero < 32000)
        switch (switchopcodezero) {
         case 0:
          break; /*NOP*/
         case 1:
          stack = stack | ee;
          S[TOS] = Integer.toBinaryString(stack); /*OR operation*/
          break;
         case 2:
          stack = stack & ee;
          S[TOS] = Integer.toBinaryString(stack); /*AND operation*/
          break;
         case 3:
          break; /*NOT*/
         case 4:
          stack = stack ^ ee;
          S[TOS] = Integer.toBinaryString(stack); /*XOR operation*/
          break;
         case 5:
          /*ADD operation*/
          if (S[TOS].length() == 16) {
           if (S[TOS].charAt(0) == '1') {
            stack = reverse_complement(S[TOS]);
           }
          }
          stack = stack + ee;
          S[TOS] = Integer.toBinaryString(stack);
          break;
         case 6:
          /*SUBTRACTION operation*/
          if (S[TOS].length() == 16) {
           if (S[TOS].charAt(0) == '1') {
            stack = reverse_complement(S[TOS]);
           }
          }
          stack = stack - ee;
          S[TOS] = Integer.toBinaryString(stack);
          break;
         case 7:
          /*MULTIPLICATION operation*/
          if (S[TOS].length() == 16) {
           if (S[TOS].charAt(0) == '1') {
            stack = reverse_complement(S[TOS]);
           }
          }
          stack = stack * ee;
          S[TOS] = Integer.toBinaryString(stack);
          break;
         case 8:
          /*DIVISION operation*/
          if (S[TOS].length() == 16) {
           if (S[TOS].charAt(0) == '1') {
            stack = reverse_complement(S[TOS]);
           }
          }
          stack = stack / ee;
          S[TOS] = Integer.toBinaryString(stack);
          break;
         case 9:
          /*MODULAR DIVISION operation*/
          if (S[TOS].length() == 16) {
           if (S[TOS].charAt(0) == '1') {
            stack = reverse_complement(S[TOS]);
           }
          }
          stack = stack % ee;
          S[TOS] = Integer.toBinaryString(stack);
          break;
         case 10:

          break; /*SL operation*/
         case 11:
          break; /*SR operation*/
         case 12:
          /*Compare if greater*/
          if (S[TOS].length() == 16) {
           if (S[TOS].charAt(0) == '1' && S[TOS] != null) {
            stack = reverse_complement(S[TOS]);
           }
          }
          if (stack > ee) {
           stackplus = 1;
          } else {
           stackplus = 0;
          }
          S[TOS + 1] = Integer.toBinaryString(stackplus);
          TOS = TOS + 1;
          break;
         case 13:
          /*Compare if Lesser*/
          if (S[TOS].length() == 16) {
           if (S[TOS].charAt(0) == '1') {
            stack = reverse_complement(S[TOS]);
           }
          }
          if (stack < ee) {
           stackplus = 1;
          } else {
           stackplus = 0;
          }

          S[TOS + 1] = Integer.toBinaryString(stackplus);
          TOS = TOS + 1;
          break;
         case 14:
          /*Compare Both are equal*/
          if (S[TOS].length() == 16) {
           if (S[TOS].charAt(0) == '1') {
            stack = reverse_complement(S[TOS]);
           }
          }
          if (stack == ee) {
           stackplus = 1;
          } else {
           stackplus = 0;
          }
          S[TOS + 1] = Integer.toBinaryString(stackplus);
          TOS = TOS + 1;
          break;
         case 15:
          /*Break*/
          PC = Integer.parseInt(EA, 2);
          break;
         case 16:
          /*Break if True*/
          if (stack == 1) {
           PC = Integer.parseInt(EA, 2);
          }
          S[TOS] = "0000000000000000";
          TOS = TOS - 1;
          break;
         case 17:
          /*Break if False*/
          if (stack == 0) {
           PC = Integer.parseInt(EA, 2);
          }
          S[TOS] = "0000000000000000";
          TOS = TOS - 1;
          break;
         case 18:
          /*CALL Operation*/
          TOS = TOS + 1;
          String pcc = Integer.toBinaryString(PC);
          String form = "%16s";
          String splitstring = String.format(form, pcc).replace(" ", "0");
          S[TOS] = splitstring;
          PC = Integer.parseInt(EA, 2);
          break;
         case 19:
          break; /*RD*/
         case 20:
          break; /*WR*/
         case 21:
          break; /*RTN*/
         case 22:
          /*PUSH*/
          TOS = TOS + 1;
          stack = ee;
          S[TOS] = Integer.toBinaryString(stack);
          break;
         case 23:
          /*POP*/
          EA = Integer.toBinaryString(stack);
          TOS = TOS - 1;
          MEMORY.MEMORY(1, DecimalEA, EA);
          break;
         case 24:
          /*HALT*/
          System.exit(0);
          break;
         default:
          break;
        }
      }
      if (TraceSwitch == 1 && TraceSwitchone == 1 && TOS >= 0) {
       al.add(inttohex(TOS));
       al.add(binarytohex(S[TOS]));
       al.add(binarytohex(EA));
       al.add(binarytohex(memor));
       TraceSwitchone = 0;
      }
     }
     Iterator < String > itr = al.iterator();
     while (itr.hasNext()) {
      String element = itr.next();
      writer.print(String.format("%10s", element));
     }
     writer.println();
     if (TOS > 7 || TOS < 0) {
      ERRORHANDLER.overorunderflow(TOS);
     }

    } catch (NullPointerException n) {
     /*catching null pointer exception*/
     outputfile.add("NATURE OF TERMINATION: ABNORMAL");
     outputfile.add("Null pointer exception");
     outputfile.add("Clock Value" + inttohex(SYSTEM.clock));
     outputfile.add("RunTime" + iotime);
     outputfile.add("Execution time" + executiontime);
     listiterator();
     System.exit(0);
    } catch (ArrayIndexOutOfBoundsException e) {
     /*catching Array Index Out of Bounds exception*/
     outputfile.add("NATURE OF TERMINATION: ABNORMAL");
     outputfile.add("ArrayIndexOutofBounds exception");
     outputfile.add("Clock Value" + inttohex(SYSTEM.clock));
     outputfile.add("RunTime" + iotime);
     outputfile.add("Execution time" + executiontime);
     listiterator();
     System.exit(0);
    } catch (Exception e) {
     /*catching Other exception*/
     outputfile.add("NATURE OF TERMINATION: ABNORMAL");
     outputfile.add("invalid exception");
     outputfile.add("Clock Value" + inttohex(SYSTEM.clock));
     outputfile.add("RunTime" + iotime);
     outputfile.add("Execution time" + executiontime);
     listiterator();
     System.exit(0);
    }
   }

  }
  /**The below method will print the contents of list to a output file**/
@SuppressWarnings("unchecked")
 public static void listiterator() {

   Iterator < String > itr = outputfile.iterator();
   while (itr.hasNext()) {
    String element = itr.next();
    writeroutput.print(String.format("%10s", element));
    writeroutput.println();
   }
   writeroutput.close();

  }
  /**converting from binary to hex**/
 private Object binarytohex(String iR2) {
   String hex = "0";
   if (iR2 != null) {
    int num = iR2.length();
    if (iR2.length() > 16) {
     hex = iR2.substring(num - 16, num);
     hex = Integer.toHexString(Integer.parseInt(iR2, 2)).toUpperCase();
    } else {
     hex = Integer.toHexString(Integer.parseInt(iR2, 2)).toUpperCase();
    }
   }
   return hex;
  }
  /**converting from int to hex**/
 static Object inttohex(int pC) {
   String hex = Integer.toHexString(pC).toUpperCase();
   return hex;
  }
  /**converting from hex to binary**/
 private String hextobinary(String bR2) {
  String preBin = new BigInteger(bR2, 16).toString(2);
  String form = "%16s";
  String splitstring = String.format(form, preBin).replace(" ", "0");
  return splitstring;

 }
 private int reverse_complement(String string) {
  char[] str1 = new char[16];
  if (string.length() == 16) {
   for (int len = 0; len < string.length(); len++) {
    if (string.charAt(len) == '0') {
     str1[len] = '1';
    } else {
     str1[len] = '0';
    }
   }
  }
  String conver = String.valueOf(str1);
  int stack = 1 + Integer.parseInt(conver);
  stack = 0 - stack;
  return stack;
 }
 private String padzeros(String string) {
  String form = "%16s";
  String splitstring = String.format(form, string).replace(" ", "0");
  return splitstring;
 }

}
