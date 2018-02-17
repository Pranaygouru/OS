import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
public class Loader {
	static String line;
	static BufferedReader reader;
	public void Loader(String x, String y) throws IOException {
		// TODO Auto-generated method stub
		 FileReader fr=new FileReader("Job1.txt");
			 reader=new BufferedReader(fr);
			 line=reader.readLine();	
			 int mainindex=0;
			
			 while((line = reader.readLine()) != null)
			 {
		    //System.out.println(line);  
				int i=0;		
				String preBin = new BigInteger(line, 16).toString(2);
				int num = line.length();
				num=num*4;
				String form="%"+num+"s";
				String splitstring = String.format(form, preBin).replace(" ", "0");
				// System.out.println(splitstring);
				 char[] tempArr = splitstring.toCharArray();
				 for(int splitline=0;splitline<splitstring.length();) {
				 String splitword=splitstring.substring(splitline,splitline+16);
				 splitline=splitline+16;
				 SYSTEM.mainmemoryarray[mainindex]=splitword;
				 //System.out.println( SYSTEM.mainmemoryarray[mainindex]);
				 mainindex++;
				 }
				 //System.out.print(tempArr.length);	 
			 }
			 //System.out.println(mainindex);
		}

	}
