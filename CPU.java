import java.math.BigInteger;
import java.util.Stack;

public class CPU {
	static int TOS=0;
	static int switchopcode=0,switchopcodezero=0;
	public void CPU(String ProgramCounter, String TraceSwitch) {
		// TODO Auto-generated method stub
		 String[] S=new String[8];
		 int ZeroAddress=0,Temp=0,Temptos=1,Tempplus=0,temppc=0;
		 String sc = new BigInteger(ProgramCounter, 16).toString(2);
		 //System.out.println(sc);
		 int DecimalValue=Integer.parseInt(sc,2);
		 //System.out.println(DecimalValue);
		 SYSTEM.InstructionRegister = SYSTEM.mainmemoryarray[DecimalValue];
		 System.out.println(SYSTEM.InstructionRegister);
		 if(SYSTEM.InstructionRegister.charAt(0) =='0')
		 {
			 String Opcode=SYSTEM.InstructionRegister.substring(2,7);
			 while(ZeroAddress<2)
			 {
			 System.out.println(Opcode);
			 switchopcode=Integer.parseInt(Opcode,2);
			 System.out.println(switchopcode);
			 if(S[TOS]!=null && TOS>0)
			 {
			  Temp=Integer.parseInt(S[TOS],2);
			  Temptos=Integer.parseInt(S[TOS-1],2);
			 Tempplus=Integer.parseInt(S[TOS+1],2);
			 }
			 switch(switchopcode)
			 {
			 case 0:
				 break;
			 case 1:
				 Temptos=Temp|Temptos;
				 S[TOS-1]=Integer.toBinaryString(Temptos);
				 TOS=TOS-1;
				 break;
			 case 2:
				 Temptos=Temp&Temptos;
				 S[TOS-1]=Integer.toBinaryString(Temptos);
				 TOS=TOS-1;
				 break;
			 case 3:
				 Temp=~Temp;
				 S[TOS]=Integer.toBinaryString(Temp);
				 break;
			 case 4:
				 
				 Temptos=Temp^Temptos;
				 S[TOS-1]=Integer.toBinaryString(Temptos);
				 TOS=TOS-1;
				 break;
			 case 5:
				 Temptos=Temp+Temptos;
				 S[TOS-1]=Integer.toBinaryString(Temptos);
				 TOS=TOS-1;
				 break;
			 case 6:
				 Temptos=Temp-Temptos;
				 S[TOS-1]=Integer.toBinaryString(Temptos);
				 TOS=TOS-1;
				 break;
			 case 7:
				 Temptos=Temp*Temptos;
				 S[TOS-1]=Integer.toBinaryString(Temptos);
				 TOS=TOS-1;
				 break;
			 case 8: 
				 Temptos=Temp/Temptos;
				 S[TOS-1]=Integer.toBinaryString(Temptos);
				 TOS=TOS-1;
				 break;
			 case 9:
				 Temptos=Temp%Temptos;
				 S[TOS-1]=Integer.toBinaryString(Temptos);
				 TOS=TOS-1;
				 break;
			 case 10:
				 Temp=Temp<<1;
				 S[TOS]=Integer.toBinaryString(Temp);
				 break;
			 case 11:
				 Temp=Temp>>1;
				 S[TOS]=Integer.toBinaryString(Temp);
				 break;
			 case 12:
				 if(Temptos > Temp)
				 {
					 Tempplus=1;
				 }
				 else
					 Tempplus=0;
				 S[TOS+1]=Integer.toBinaryString(Tempplus);
				 TOS=TOS+1;
				 break;
			 case 13:
				 if(Temptos < Temp)
				 {
					 Tempplus=1;
				 }
				 else
					 Tempplus=0;
				 S[TOS+1]=Integer.toBinaryString(Tempplus);
				 TOS=TOS+1;
				 break;
			 case 14:
				 if(Temptos == Temp)
				 {
					 Tempplus=1;
				 }
				 else
					 Tempplus=0;
				 S[TOS+1]=Integer.toBinaryString(Tempplus);
				 TOS=TOS+1;
				 break;
			 case 15:
				 break;
			 case 16:
				 break;
			 case 17:
				 break;
			 case 18:
				 break;
			 case 19:
				 TOS=TOS+1;
				 Temp=SYSTEM.in.nextInt();
				 S[TOS]=Integer.toBinaryString(Temp);
				 System.out.println(S[TOS]);
				 break;
			 case 20:
				 TOS=TOS-1;
				 System.out.println(S[TOS]);
				 break;
			 case 21:
				 SYSTEM.Program_counter=S[TOS];
				 TOS=TOS-1;
				 break;
			 case 22:
				 break;
			 case 23:
				 break;
			 case 24:
				 break;
			 default:
				 break; 
			 }
			 ZeroAddress++;
			 if(switchopcode==24)
				 break;
			 Opcode=SYSTEM.InstructionRegister.substring(11,16);
			 }
			 if((switchopcode >= 0) && (switchopcode <= 24)) {
				 System.out.println(SYSTEM.Program_counter);
				 temppc=Integer.parseInt(SYSTEM.Program_counter,16);
				 temppc=temppc+1;
				 System.out.println(temppc);
				 SYSTEM.Program_counter=Integer.toBinaryString(temppc);
			 }	 
		 }	 
		 Temp=0;
		 Temptos=1;
		 Tempplus=0;
		 if(SYSTEM.InstructionRegister.charAt(0) =='1')
		 {
			 String Opcodeone=SYSTEM.InstructionRegister.substring(2,7);
			 String EA=SYSTEM.InstructionRegister.substring(9,16);
			 switchopcodezero=Integer.parseInt(Opcodeone,2);
			 int switchopcodezeroEA=Integer.parseInt(EA,2);
			 int MofEA=Integer.parseInt(SYSTEM.mainmemoryarray[switchopcodezeroEA],2);
			 System.out.println(switchopcodezero);
			 if(S[TOS]!=null && TOS>0)
			 {
			  Temp=Integer.parseInt(S[TOS],2);
			  Temptos=Integer.parseInt(S[TOS-1],2);
			 Tempplus=Integer.parseInt(S[TOS+1],2);
			 }
			 switch(switchopcodezero)
			 {
			 case 0:
				 break;
			 case 1:
				 Temp=Temp|MofEA;
				 S[TOS]=Integer.toBinaryString(Temp);
				 break;
			 case 2:
				 Temp=Temp&MofEA;
				 S[TOS]=Integer.toBinaryString(Temp);
				 break;
			 case 3:
				 break;
			 case 4:
				 Temp=Temp^MofEA;
				 S[TOS]=Integer.toBinaryString(Temp);
				 break;
			 case 5:
				 Temp=Temp+MofEA;
				 S[TOS]=Integer.toBinaryString(Temp);
				 break;
			 case 6:
				 Temp=Temp-MofEA;
				 S[TOS]=Integer.toBinaryString(Temp);
				 break;
			 case 7:
				 Temp=Temp*MofEA;
				 S[TOS]=Integer.toBinaryString(Temp);
				 break;
			 case 8: 
				 Temp=Temp/MofEA;
				 S[TOS]=Integer.toBinaryString(Temp);
				 break;
			 case 9:
				 Temp=Temp%MofEA;
				 S[TOS]=Integer.toBinaryString(Temp);
				 break;
			 case 10:
				 break;
			 case 11:
				 break;
			 case 12:
				 if(Temp > MofEA)
				 {
					 Tempplus=1;
				 }
				 else
					 Tempplus=0;
				 S[TOS+1]=Integer.toBinaryString(Tempplus);
				 TOS=TOS+1;
				 break;
			 case 13:
				 if(Temptos < Temp)
				 {
					 Tempplus=1;
				 }
				 else
					 Tempplus=0;
				 S[TOS+1]=Integer.toBinaryString(Tempplus);
				 TOS=TOS+1;
				 break;
			 case 14:
				 if(Temptos == Temp)
				 {
					 Tempplus=1;
				 }
				 else
					 Tempplus=0;
				 S[TOS+1]=Integer.toBinaryString(Tempplus);
				 TOS=TOS+1;
				 break;
			 case 15:
				 SYSTEM.Program_counter=EA;
				 break;
			 case 16:
				 if(Temp==1)
				 {
					 SYSTEM.Program_counter=EA; 
				 }
				 TOS=TOS-1;
				 break;
			 case 17:
				 if(Temp==0)
				 {
					 SYSTEM.Program_counter=EA; 
				 }
				 TOS=TOS-1;
				 break;
			 case 18:
				TOS=TOS+1;
				S[TOS]=SYSTEM.Program_counter;
				SYSTEM.Program_counter=EA; 
			 case 19:
				 break;
			 case 20:
				 break;
			 case 21:
				 break;
			 case 22:
				 TOS=TOS+1;
				 Temp=MofEA;
				 S[TOS]=Integer.toBinaryString(Temp);
				 break;
			 case 23:
				 EA=S[TOS];
				 TOS=TOS-1;
			 case 24:
				 break;
			 default:
				 break;
			 }
			 if((switchopcode >= 0) && (switchopcode <= 24)) {
				 System.out.println(SYSTEM.Program_counter);
				 temppc=Integer.parseInt(SYSTEM.Program_counter,16);
				 temppc=temppc+1;
				 System.out.println(temppc);
				 SYSTEM.Program_counter=Integer.toBinaryString(temppc);
		 }
	}

}
