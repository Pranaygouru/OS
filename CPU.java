import java.math.BigInteger;
import java.util.Stack;

public class CPU {
	static int TOS=0;
	static int switchopcode=0,switchopcodezero=0;
	static int Onlyhex=1,test=0;
	static int DecimalValue=0,srno=1;
	static int temppc=0,stack=0,stackplus=0,stackminus=0;
	static String[] S=new String[8];
	public Object CPU(int ProgramCounter, int TraceSwitch) {
		 int ZeroAddress=0;
		 //System.out.println(SYSTEM.mainmemoryarray[41]);
		 SYSTEM.InstructionRegister = SYSTEM.mainmemoryarray[SYSTEM.Program_counter];
		 System.out.println(srno+")."+SYSTEM.InstructionRegister);
		 srno++;
		 System.out.println(TOS);
		for(int var=0;var<S.length;var++) {
			System.out.println(S[var]);
		}
		
			//System.out.println(SYSTEM.Program_counter);
		 if(SYSTEM.InstructionRegister.charAt(0) =='0')
		 {
			 String Opcode=SYSTEM.InstructionRegister.substring(1,6);
			 while(ZeroAddress<2)
			 {	 
			 System.out.println(Opcode);
			 switchopcode=Integer.parseInt(Opcode,2); 
			// System.out.println(switchopcode);
			if (TOS>=0 && TOS<7)
			{
				if(S[TOS]!=null)
				{
					stack=Integer.parseInt(S[TOS],2);
					System.out.println(stack);
				}
				if(S[TOS+1]!=null) {
					stackplus=Integer.parseInt(S[TOS+1],2);
				}
				if(TOS>0)
				if(S[TOS-1]!=null)
				{
				stackminus=Integer.parseInt(S[TOS-1],2);
				}
				
				//System.out.println(switchopcode);
				if(switchopcode<32000)
			 switch(switchopcode)
			 {
			 case 0:
				 break;
			 case 1:
				stackminus=stack|stackminus;	
				 TOS=TOS-1;
				 break;
			 case 2:
				 stackminus=stack&stackminus;
				 TOS=TOS-1;
				 break;
			 case 3:
				 stack=~stack;
				 break;
			 case 4:
				 stackminus=stack^stackminus;
				 TOS=TOS-1;
				 break;
			 case 5:
				 stackminus=stack+stackminus;
				 TOS=TOS-1;
				 break;
			 case 6:
				 stackminus=stack-stackminus;
				 TOS=TOS-1;
				 break;
			 case 7:
				 stackminus=stack*stackminus;
				 TOS=TOS-1;
				 break;
			 case 8: 
				 stackminus=stack/stackminus;
				 TOS=TOS-1;
				 break;
			 case 9:
				 stackminus=stack%stackminus;
				 TOS=TOS-1;
				 break;
			 case 10:
				 System.out.println(stack);
				 stack=stack<<1;
				 System.out.println(stack);
				 break;
			 case 11:
				 stack=stack>>1;
				 break;
			 case 12:
				 if(stackminus > stack)
				 {
					 stackplus=1;
				 }
				 TOS=TOS+1;
				 break;
			 case 13:
				 if(stackminus < stack)
				 {
					 stackplus=1;
				 }
				 TOS=TOS+1;
				 
				 break;
			 case 14:
				 if(stackminus == stack)
				 {
					 stackplus=1;
				 }
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
				 S[TOS]=Integer.toBinaryString(SYSTEM.in.nextInt());
				 //System.out.println(stack);
				 break;
			 case 20:
				 TOS=TOS-1;
				 System.out.println(stack+"hello world");
				 break;
			 case 21:
				 SYSTEM.Program_counter=stack;
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
			}
			 ZeroAddress++;
			 if(switchopcode==24)
				 break;
			 Opcode=SYSTEM.InstructionRegister.substring(11,16);
			 }
			 if((switchopcode >= 0) && (switchopcode <= 24)) {
				
				 temppc=SYSTEM.Program_counter;
				 temppc=temppc+1;
				// System.out.println(temppc);
				 SYSTEM.Program_counter=temppc;
				// System.out.println(SYSTEM.Program_counter);
			 }	
			 ZeroAddress=0;
		 }	 
		
		 if(SYSTEM.InstructionRegister.charAt(0) =='1')
		 {
			 String Opcodeone=SYSTEM.InstructionRegister.substring(1,6);
			// System.out.println(Opcodeone);
			 String EA=SYSTEM.InstructionRegister.substring(9,16);
			 //System.out.println(EA);
			 switchopcodezero=Integer.parseInt(Opcodeone,2);
			 //System.out.println(switchopcodezero);
			 int DecimalEA=Integer.parseInt(EA,2);
			 System.out.println(DecimalEA);
			 String Z=SYSTEM.BufferRegister;
			 String memor=MEMORY.MEMORY(0,DecimalEA,Z);
			 System.out.println(memor);
			 Z=memor;
			 int ee=Integer.parseInt(Z,2);
			 //String ValueAtmemory=SYSTEM.MEMORY(X,Y,Z);
			// int ee=Integer.parseInt(SYSTEM.mainmemoryarray[switchopcodezeroEA],2);
			 //System.out.println(switchopcodezero);
			 if(TOS>=0 && TOS<7)
			 {
				 if(S[TOS]!=null)
				 {
				 stack=Integer.parseInt(S[TOS],2);
				 if(S[TOS+1]!=null)
				 {
				  stackplus=Integer.parseInt(S[TOS+1],2);
				 }
				 if(S[TOS-1]!=null)
				 {
				 stackminus=Integer.parseInt(S[TOS-1],2);
				 }
				 }
				 if(switchopcodezero<32000)
			 switch(switchopcodezero)
			 {
			 case 0:
				 break;
			 case 1:
				 stack=stack|ee;
				 break;
			 case 2:
				 stack=stack&ee;
				 break;
			 case 3:
				 break;
			 case 4:
				 stack=stack^ee;
				 break;
			 case 5:
				 stack=stack+ee;
				 break;
			 case 6:
				 stack=stack-ee;
				 break;
			 case 7:
				 stack=stack*ee;
				 break;
			 case 8: 
				 stack=stack/ee;
				 break;
			 case 9:
				 stack=stack%ee;
				 break;
			 case 10:
				 break;
			 case 11:
				 break;
			 case 12:
				// for(String print:S)
				// System.out.println(print);
				
				 if(stack > ee)
				 {
					 stackplus=1;
					 
				 }
				 S[TOS+1]=Integer.toBinaryString(stackplus);
				 System.out.println(stack); 
				 System.out.println(ee);
				 if(TOS<7)
				 TOS=TOS+1;
				 //stack=stackplus;
				 //System.out.println(stack);
				 break;
			 case 13:
				 if(stack < ee)
				 {
					 stackplus=1;
				 }
				 else
				 {
				 TOS=TOS+1;
				 }
				 break;
			 case 14:
				 if(stack == ee)
				 {
					 stackplus=1;
				 }
				 else
				 {
				 TOS=TOS+1;
				 }
				 break;
			 case 15:
				 SYSTEM.Program_counter=Integer.parseInt(EA,2);
				 System.out.println(SYSTEM.Program_counter);
				 test=1;
				 break;
			 case 16:
				 System.out.println(stack);
				 if(stack==1)
				 {
					 SYSTEM.Program_counter=Integer.parseInt(EA,2); 
					 test=1;
				 }
				 else
				 {
				 TOS=TOS-1;
				 }
				 break;
			 case 17:
				 //System.out.println(stack);
				 if(stack==0)
				 {
					 SYSTEM.Program_counter=Integer.parseInt(EA,2); 
					 test=1;
				 }
				 else
				 {
				 TOS=TOS-1;
				 }
				 break;
			 case 18:
				TOS=TOS+1;
				//int program_decimal=Integer.parseInt(SYSTEM.Program_counter,2);
				String pcc=Integer.toBinaryString(SYSTEM.Program_counter);
				String form="%16s";
				String splitstring = String.format(form, pcc).replace(" ", "0");
				S[TOS]=splitstring;
				SYSTEM.Program_counter=Integer.parseInt(EA,2);
				//System.out.println(SYSTEM.Program_counter);
				SYSTEM.Program_counter--;
				//CPU(SYSTEM.Program_counter,1);
			 case 19:
				 break;
			 case 20:
				 break;
			 case 21:
				 break;
			 case 22:
				 TOS=TOS+1;
				 stack=ee;
				 System.out.println(ee);
				 break;
			 case 23:
				 EA=Integer.toBinaryString(stack);
				 TOS=TOS-1;
				 MEMORY.MEMORY(1,DecimalEA,EA);
				 
			 case 24:
				 break;
			 default:
				 break;
			 }
			 }
			 if((switchopcodezero < 0) && (switchopcodezero >= 24) || (test==1)) {
				 test=0;
				SYSTEM.exit();
			 } 
				else {
					SYSTEM.Program_counter=SYSTEM.Program_counter+1;
				}
			 
		 }
		 
		 return CPU(SYSTEM.Program_counter,1);
	}
	
}
