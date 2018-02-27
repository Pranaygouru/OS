import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
/** line and Buffered reader are declared and used to 
 * read file line by line and process the data into memory
 * No pre processing or parsing is done before keeping into memory
 * 
 * ->loader calls procedure memory with WR operation so that contents
 * are added into the array.  
 *  **/
public class Loader {
 static String line;
 static BufferedReader reader;
 public void Loader(String x, String y) throws IOException {
   FileReader fr = new FileReader(SYSTEM.path);
   reader = new BufferedReader(fr);
   line = reader.readLine();
   x = hextobinary(x);
   int load = Integer.parseInt(x);
   int mainindex = 0;
   /*Try and catch blocks are used to handle exceptions*/
   try {
    while ((line = reader.readLine()) != null) {
     int i = 0;
     String preBin = new BigInteger(line, 16).toString(2);
     int num = line.length();
     num = num * 4;
     String form = "%" + num + "s";
     String splitstring = String.format(form, preBin).replace(" ", "0");
     char[] tempArr = splitstring.toCharArray();
     for (int splitline = 0; splitline < splitstring.length();) {
      String splitword = splitstring.substring(splitline, splitline + 16);
      splitline = splitline + 16;
      MEMORY.MEMORY(1, load, splitword); /*call for memory procedure*/
      load++;
     }
    }
   } catch (Exception e) {
    System.exit(0);
   }
  }
  /*converting the strings from hex to binary*/
 private String hextobinary(String x) {
  String preBin = new BigInteger(x, 16).toString(2);
  int num = x.length();
  String form = "%16s";
  String splitstring = String.format(form, preBin).replace(" ", "0");
  return splitstring;
 }
}