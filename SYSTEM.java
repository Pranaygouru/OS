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
	static String InstructionRegister;
	static String BaseRegister;
    static String[] mainmemoryarray=new String[256];
    static Scanner in = new Scanner(System.in);
    static String BufferRegister;
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
	String[] array=line.split(" ");
	/*for(String var:array)
		System.out.println(var);*/
	String X=array[1];
	String Y="1";
	String Z=null;
	Program_counter=Integer.parseInt(array[2],16);

	lr.Loader(X,Y);
	int x;
	x = Program_counter;
	int y=Integer.parseInt(Y);
	//X=array[2];
	cp.CPU(x,y);
	
	reader.close();
}
public static void exit() {
	// TODO Auto-generated method stub
	System.out.println("calculate time");
}
}
