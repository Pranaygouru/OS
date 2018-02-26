import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;
import javax.print.DocFlavor.URL;
public class SYSTEM {
	static String line;
	static BufferedReader reader;
	static int Program_counter;
	static int clock=0;
	static int indexing=0;
	static String InstructionRegister;
	static String BaseRegister;
	static int BaseRg=0;
    static String[] mainmemoryarray=new String[256];
    static Scanner in = new Scanner(System.in);
    static String BufferRegister;
    static String[] array=new String[5];
public static void main(String[] args) throws IOException {
	//java.net.URL path = SYSTEM.class.getResource("Job1.txt");
	//File f = new File(path.getFile());
	Loader lr=new Loader();
	CPU cp=new CPU();
	MEMORY mr=new MEMORY();
	FileReader fr=new FileReader("Job1.txt");
	reader=new BufferedReader(fr);
	if((line = reader.readLine()) != null)
	{
	    System.out.println(line);
	}
   array=line.split(" ");
	/*for(String var:array)
		System.out.println(var);*/
	String X=array[1];
	String Y=array[4];
	SYSTEM.BaseRegister=array[1];
	String Z=null;
	//Program_counter=Integer.parseInt(X+array[2],16);
	int baseload=Integer.parseInt(array[1],16);
	int plusbr=Integer.parseInt(array[2],16);
	
	lr.Loader(X,Y);
	int x;
	x = baseload+plusbr;
	int y=Integer.parseInt(Y);
	//X=array[2];
	cp.CPU(x,y);
	//System.out.println(clock);
	reader.close();
}
public static void exit() {
	// TODO Auto-generated method stub
	System.out.println("calculate time");
}
}