import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.print.DocFlavor.URL;
public class SYSTEM {
	static String line;
	static BufferedReader reader;
	static String Program_counter;
	static String InstructionRegister;
	static String BaseRegister;
    static String[] mainmemoryarray=new String[256];
    static Scanner in = new Scanner(System.in);
public static void main(String[] args) throws IOException {
	//java.net.URL path = SYSTEM.class.getResource("Job1.txt");
	//File f = new File(path.getFile());
	Loader lr=new Loader();
	CPU cp=new CPU();
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
	String Y="0";
	Program_counter=array[2];
	lr.Loader(X,Y);
	X=array[2];
	cp.CPU(X,Y);
	reader.close();
}
}
