import java.math.BigInteger;
import java.util.Stack;

public class CPU {
	static int TOS=0;
	static int switchopcode=0,switchopcodezero=0;
	static int Onlyhex=1,test=0;
	static int DecimalValue=0,srno=1;
	static int temppc=0,stack=0,stackplus=0,stackminus=0;
	static  String[] S=new String[8];
	static char[] str=new char[16];
	public Object CPU(int ProgramCounter, int TraceSwitch) {
		 int ZeroAddress=0;
		 //System.out.println(SYSTEM.mainmemoryarray[63]);
		 while(true)
			{
				System.out.println(SYSTEM.Program_counter);
		 SYSTEM.InstructionRegister = SYSTEM.mainmemoryarray[SYSTEM.Program_counter];
		 SYSTEM.Program_counter=SYSTEM.Program_counter+1;
		System.out.println(srno+")."+SYSTEM.InstructionRegister);
		 srno++;
	
		/*for(int var=0;var<S.length;var++) {
			System.out.println(S[var]);
		}*/
			//System.out.println(SYSTEM.Program_counter);
		 if(SYSTEM.InstructionRegister.charAt(0) =='0')
		 {
			 String Opcode=SYSTEM.InstructionRegister.substring(3,8);
			// SYSTEM.Program_counter=SYSTEM.Program_counter+1;
			 while(ZeroAddress<2)
			 {	 
			 //System.out.println(Opcode);
			 switchopcode=Integer.parseInt(Opcode,2); 
			 
			//System.out.println(switchopcode);
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
				
				//System.out.println(switchopcode);
				if(switchopcode<32000)
			 switch(switchopcode)
			 {
			 case 0:
				 break;
			 case 1:
				stackminus=stack|stackminus;	
				S[TOS-1]=Integer.toBinaryString(stackminus);
				 TOS=TOS-1;
				 
				 break;
			 case 2:
				 stackminus=stack&stackminus;
				 S[TOS-1]=Integer.toBinaryString(stackminus);
				 TOS=TOS-1;
				 break;
			 case 3:
				
				 String splitstring1 = S[TOS];
				 while(splitstring1.length()<16)
				 {
					 splitstring1='0'+splitstring1;
				 }
				 for(int len=0;len<splitstring1.length();len++)
				 {
					 if(splitstring1.charAt(len)=='0')
					 {
						str[len]='1';
					 }
					 else
					 {
						 str[len]='0';
					 }		 
				 }
				 String conver=String.valueOf(str);
				 System.out.println(conver);
				 S[TOS]=conver;
				 break;
			 case 4:
				 stackminus=stack^stackminus;
				 S[TOS-1]=Integer.toBinaryString(stackminus);
				 TOS=TOS-1;
				 break;
			 case 5:
				 stackminus=stack+stackminus;
				 S[TOS-1]=Integer.toBinaryString(stackminus);
				 TOS=TOS-1;
				 break;
			 case 6:
				 stackminus=stack-stackminus;
				 S[TOS-1]=Integer.toBinaryString(stackminus);
				 TOS=TOS-1;
				 break;
			 case 7:
				 stackminus=stack*stackminus;
				 S[TOS-1]=Integer.toBinaryString(stackminus);
				 TOS=TOS-1;
				 break;
			 case 8: 
				 stackminus=stack/stackminus;
				 S[TOS-1]=Integer.toBinaryString(stackminus);
				 TOS=TOS-1;
				 break;
			 case 9:
				 stackminus=stack%stackminus;
				 S[TOS-1]=Integer.toBinaryString(stackminus);
				 TOS=TOS-1;
				 break;
			 case 10:
				 System.out.println(stack);
				 stack=stack<<1;
				 S[TOS]=Integer.toBinaryString(stack);
				 System.out.println(stack);
				 break;
			 case 11:
				 stack=stack>>1;
				 S[TOS]=Integer.toBinaryString(stack);
				 break;
			 case 12:
				 if(stackminus > stack)
				 {
					 stackplus=1;
				 }
				 else
				 {
					 stackplus=0;
				 }
				 S[TOS+1]=Integer.toBinaryString(stackplus);
				 TOS=TOS+1;
				 break;
			 case 13:
				 if(stackminus < stack)
				 {
					 stackplus=1;
				 }
				 else
				 {
					 stackplus=0;
				 }
				 S[TOS+1]=Integer.toBinaryString(stackplus);
				 TOS=TOS+1;
				 
				 break;
			 case 14:
				 if(stackminus == stack)
				 {
					 stackplus=1;
				 }
				 else
				 {
					 stackplus=0;
				 }
				 S[TOS+1]=Integer.toBinaryString(stackplus);
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
				 String result=Integer.toBinaryString(stack);
				 System.out.println(result+"hello world"+TOS);
				 
				 TOS=TOS-1;
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
				 System.out.println("Halt");
				 System.exit(0);
				 break;
			 default:
				 break; 
			 }
			}
			 ZeroAddress++;
			 System.out.println(Opcode);
			 Opcode=SYSTEM.InstructionRegister.substring(11,16);
			 //System.out.println(Opcode);
			 }
			/* if((switchopcode >= 0) && (switchopcode <= 24)) {
				
				 temppc=SYSTEM.Program_counter;
				 temppc=temppc+1;
				// System.out.println(temppc);
				 SYSTEM.Program_counter=temppc;
				// System.out.println(SYSTEM.Program_counter);
			 }	*/
			 ZeroAddress=0;
			 System.out.println(TOS);
			 //SYSTEM.Program_counter=SYSTEM.Program_counter+1;
		 }	 
		 if(SYSTEM.InstructionRegister.charAt(0) =='1' && SYSTEM.InstructionRegister.length()==16)
		 {
			 //SYSTEM.Program_counter=SYSTEM.Program_counter+1;
			 
			 String Opcodeone=SYSTEM.InstructionRegister.substring(1,6);
			 //SYSTEM.Program_counter=SYSTEM.Program_counter+1;
			// System.out.println(Opcodeone);
			 String EA=SYSTEM.InstructionRegister.substring(9,16);
			 //System.out.println(EA);
			 switchopcodezero=Integer.parseInt(Opcodeone,2);
			 System.out.println(switchopcodezero);
			 int DecimalEA=Integer.parseInt(EA,2);
			 //System.out.println(DecimalEA);
			 String Z=SYSTEM.BufferRegister;
			 String memor=MEMORY.MEMORY(0,DecimalEA,Z);
			 //System.out.println(memor);
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
				 if(TOS==7)
				 {
					 stack=Integer.parseInt(S[TOS],2);
					 if(S[TOS-1]!=null)
					 {
					 stackminus=Integer.parseInt(S[TOS-1],2);
					 }
				 }
				 //System.out.println(stack);
				
				// System.out.println(stackplus);
				 if(switchopcodezero<32000)
			 switch(switchopcodezero)
			 {
			 case 0:
				 break;
			 case 1:
				 stack=stack|ee;
				 S[TOS]=Integer.toBinaryString(stack);
				 break;
			 case 2:
				 stack=stack&ee;
				 S[TOS]=Integer.toBinaryString(stack);
				 System.out.println(S[TOS]);
				 break;
			 case 3:
				 break;
			 case 4:
				 stack=stack^ee;
				 S[TOS]=Integer.toBinaryString(stack);
				 break;
			 case 5:
				 stack=stack+ee;
				 S[TOS]=Integer.toBinaryString(stack);
				 break;
			 case 6:
				 stack=stack-ee;
				 S[TOS]=Integer.toBinaryString(stack);
				 break;
			 case 7:
				 stack=stack*ee;
				 S[TOS]=Integer.toBinaryString(stack);
				 break;
			 case 8: 
				 stack=stack/ee;
				 S[TOS]=Integer.toBinaryString(stack);
				 break;
			 case 9:
				 stack=stack%ee;
				 S[TOS]=Integer.toBinaryString(stack);
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
				 else
				 {
					 stackplus=0;
				 }
				 S[TOS+1]=Integer.toBinaryString(stackplus);
				 //System.out.println(stack); 
				 //System.out.println(ee);
				 
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
					 stackplus=0;
				 }
				 
				 S[TOS+1]=Integer.toBinaryString(stackplus);
				 TOS=TOS+1;
				 System.out.println(S[TOS]);
				 break;
			 case 14:
				 if(stack == ee)
				 {
					 stackplus=1;
				 }
				 else
				 {
					 stackplus=0;
				 }
				 S[TOS+1]=Integer.toBinaryString(stackplus);
				 TOS=TOS+1;
				 break;
			 case 15:
				 SYSTEM.Program_counter=Integer.parseInt(EA,2);
				 //SYSTEM.Program_counter=SYSTEM.Program_counter-1;
				 //System.out.println(SYSTEM.Program_counter);
				 break;
			 case 16:
				 //System.out.println(stack);
				 if(stack==1)
				 {
					// System.out.println(EA);
					 SYSTEM.Program_counter=Integer.parseInt(EA,2); 
					 //SYSTEM.Program_counter=SYSTEM.Program_counter-1;
				 }
				 S[TOS]=null;
				 //System.out.println(stack);
				 TOS=TOS-1;
				 break;
			 case 17:
				 //System.out.println(stack);
				 if(stack==0)
				 {
					 SYSTEM.Program_counter=Integer.parseInt(EA,2); 
				 }
				 S[TOS]=null;
				 TOS=TOS-1;
				
				 break;
			 case 18:
				TOS=TOS+1;
				//int program_decimal=Integer.parseInt(SYSTEM.Program_counter,2);
				String pcc=Integer.toBinaryString(SYSTEM.Program_counter);
				String form="%16s";
				String splitstring = String.format(form, pcc).replace(" ", "0");
				S[TOS]=splitstring;
				SYSTEM.Program_counter=Integer.parseInt(EA,2);
				break;
				//System.out.println(SYSTEM.Program_counter);
				//SYSTEM.Program_counter--;
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
				 S[TOS]=Integer.toBinaryString(stack);
				 
				 System.out.println(stack);
				 break;
			 case 23:
				 EA=Integer.toBinaryString(stack);
				 TOS=TOS-1;
				 MEMORY.MEMORY(1,DecimalEA,EA); 
				 break;
			 case 24:
				 System.out.println("Halt");
				System.exit(0);
				 break;
			 default:
				 break;
			 }
			 }
			 System.out.println(TOS);
			 /*if((switchopcodezero < 0) && (switchopcodezero >= 24) ) {
				
				SYSTEM.exit();
			 } 
				else {
					SYSTEM.Program_counter=SYSTEM.Program_counter+1;
				}*/
			 
		 }
		}
		
	}
	
}
