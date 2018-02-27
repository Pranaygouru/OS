import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;
import javax.print.DocFlavor.URL;
/**
NAME :- PRANAY GOURU
COURSE NUMBER:-CS5323
ASSIGNMENT TITLE:-A SIMPLE BATCH SYSTEM
DATE:-02/27/2018

->Buffer Register,clock,line,BufferedReader variables are created in the SYSTEM 
class and the memory words are loaded one by one on to the memory array

->System contains the modules of CPU,Loader,ErrorHandler and memory 
and the procedures CPU,MEMORY and Loader are accessible and the
values are passed from the procedure calls to respective classes.
Input file is fetched as command line argument and processed to keep
on the memory.

->The following program implements thoroughly with correct procedure calls
and perfect execution of instructions is done.
**/
public class SYSTEM {
 static String line;
 static BufferedReader reader;
 static int Program_counter;
 static int clock = 0;
 static int indexing = 0;
 static int BaseRg = 0;
 static Scanner in = new Scanner(System.in);
 static String BufferRegister;
 static String[] array = new String[5];
 static String path;
 public static void main(String[] args) throws Exception {
  /*The input file is read from the command line and processing is started*/
  if (args.length == 1) {
   path = args[0];
   String firstLine;
   try {
    BufferedReader reader = new BufferedReader(new FileReader(
     java.lang.System.getProperty("user.dir") + "/" + path));
    if ((line = reader.readLine()) != null) {}
    Loader lr = new Loader();
    CPU cp = new CPU();
    MEMORY mr = new MEMORY();
    array = line.split(" ");
    String X = array[1];
    String Y = array[4];
    String Z = null;
    int baseload = Integer.parseInt(array[1], 16);
    int plusbr = Integer.parseInt(array[2], 16);
    lr.Loader(X, Y);
    int x;
    x = baseload + plusbr;
    int y = Integer.parseInt(Y, 16);
    cp.CPU(x, y);
    reader.close();
   } catch (FileNotFoundException f) {
    ERRORHANDLER.TerminationType = "ABNORMAL";
    f.printStackTrace();
   }
  }
 }
}