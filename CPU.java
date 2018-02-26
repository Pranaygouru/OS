import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;
public class CPU {
	static int TOS = 0;
	static int switchopcode = 0, switchopcodezero = 0;
	static int Onlyhex = 1, test = 0,TraceSwitchone=0;
	static int DecimalValue = 0, srno = 1;
	static int temppc = 0, stack = 0, stackplus = 0, stackminus = 0;
	static String[] S = new String[7];
	static int clockcheck = 0;
	String convr = null;
	String memor, EA;
	static String BR;
	static String IR;
	static char[] str = new char[16];

	public Object CPU(int PC, int TraceSwitch) throws FileNotFoundException {
		int ZeroAddress = 0;

		PrintWriter writer = new PrintWriter(System.out);
		writer = new PrintWriter(new FileOutputStream("Trace Flag Output",false));
		writer.println("\t(PC)\t (BR)\t  (IR)\t     (TOS)\tS[TOS]\t EA\t(EA) \t\tTOS\t S[TOS]   EA\t (EA)");
		writer.println("\t\t\t\t\tBeforeExecution\t BeforeExecution\tAfterExecution\tBeforeExecution");

		while (true) {

			IR = MEMORY.MEMORY(0,PC,"");
			ArrayList al = new ArrayList();
			if (TraceSwitch == 1 && TOS>=0) {
				//writer.print(PC + "                 " + SYSTEM.BaseRegister + "                 "
				//	+ IR + "                 " + TOS + ", " + S[TOS] + "            	                   ");
			
			
			PC = PC + 1;
			al.add(inttohex(PC));
			BR=SYSTEM.array[1];
			al.add(BR);
			al.add(binarytohex(IR));
			al.add(inttohex(TOS));
			if(S[TOS]!=null)
			{
				String sts=S[TOS];
				al.add(binarytohex(sts));
			}
			else
			{
				al.add("0");
			}
			}
			srno++;
			if (IR.charAt(0) == '0') {
				String Opcode = IR.substring(3, 8);
				// PC=PC+1;
				TraceSwitchone=0;
				while (ZeroAddress < 2) {

					switchopcode = Integer.parseInt(Opcode, 2);
					if (clockcheck == 1 && switchopcode == 0 && ZeroAddress == 1) {
						SYSTEM.clock--;
					}
					clockcheck = 0;
					SYSTEM.clock = SYSTEM.clock + 1;

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


						if (switchopcode < 32000)
							switch (switchopcode) {
							case 0:
								break;
							case 1:
								stackminus = stack | stackminus;
								S[TOS - 1] = Integer.toBinaryString(stackminus);
								TOS = TOS - 1;
								break;
							case 2:
								stackminus = stack & stackminus;
								S[TOS - 1] = Integer.toBinaryString(stackminus);
								TOS = TOS - 1;
								break;
							case 3:

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
								stackminus = stack ^ stackminus;
								S[TOS - 1] = Integer.toBinaryString(stackminus);
								TOS = TOS - 1;
								break;
							case 5:
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

								stack = stack << 1;
								S[TOS] = Integer.toBinaryString(stack);

								break;
							case 11:
								stack = stack >> 1;
								S[TOS] = Integer.toBinaryString(stack);
								break;
							case 12:
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
								break;
							case 16:
								break;
							case 17:
								break;
							case 18:
								break;
							case 19:
								TOS = TOS + 1;
								SYSTEM.clock = SYSTEM.clock + 15;
								String len32 = Integer.toBinaryString(SYSTEM.in.nextInt());
								if (len32.charAt(0) == '1' && len32.length() == 32) {
									convr = len32.substring(16, 32);
									S[TOS] = convr;
								} else {
									S[TOS] = len32;
								}

								break;
							case 20:
								String result = Integer.toBinaryString(stack);
								if (result.length() > 16) {
									int response = result.length();
									result = result.substring(response - 16, response);
								}
								if (result.length() < 16) {
									result = padzeros(result);
								}
								SYSTEM.clock = SYSTEM.clock + 15;
								TOS = TOS - 1;
								break;
							case 21:
								PC = stack;
								TOS = TOS - 1;
								clockcheck = 1;
								break;
							case 22:
								break;
							case 23:
								break;
							case 24:
								writer.close();
								System.exit(0);
								break;
							default:
								break;
							}
					}
					ZeroAddress++;
					Opcode = IR.substring(11, 16);	
				}
				ZeroAddress = 0;
				if(TraceSwitch==1 && TOS>=0)
				{
					
					al.add("--");
					al.add("--");
					al.add(inttohex(TOS));
					al.add(binarytohex(S[TOS]));
					al.add("--");
					al.add("--");
				

				}
			}
			if (IR.charAt(0) == '1' && IR.length() == 16) {
				String Opcodeone = IR.substring(1, 6);
				EA = IR.substring(9, 16);
				BR = SYSTEM.array[1];

				BR = hextobinary(BR);
				int baseadd=Integer.parseInt(BR);
				switchopcodezero = Integer.parseInt(Opcodeone, 2);

				int DecimalEA = Integer.parseInt(EA, 2);


				if(IR.charAt(6)=='1' && S[TOS]!=null && S[TOS]=="10%")
				{
					int stackadd=Integer.parseInt(S[TOS]);
					DecimalEA=DecimalEA+baseadd+stackadd;
				}
				else
				{
					DecimalEA=DecimalEA+baseadd;
				}

				String Z = SYSTEM.BufferRegister;
				memor = MEMORY.MEMORY(0, DecimalEA, Z);
				TraceSwitchone=1;
				if (TraceSwitch == 1 && TraceSwitchone==1) {
					//writer.print(EA + ", " + memor + "                                           ");
					al.add(binarytohex(EA));
					al.add(binarytohex(memor));
				}
				SYSTEM.clock = SYSTEM.clock + 4;

				Z = memor;
				int ee = Integer.parseInt(Z, 2);
				if (TOS >= 0 && TOS < 6) {
					if (S[TOS] != null) {
						stack = Integer.parseInt(S[TOS], 2);
						if (S[TOS + 1] != null) {
							stackplus = Integer.parseInt(S[TOS + 1], 2);
						}

						if ((TOS-1)>=0 && S[TOS - 1] != null ) {
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
							break;
						case 1:
							stack = stack | ee;
							S[TOS] = Integer.toBinaryString(stack);
							break;
						case 2:
							stack = stack & ee;
							S[TOS] = Integer.toBinaryString(stack);
							break;
						case 3:
							break;
						case 4:
							stack = stack ^ ee;
							S[TOS] = Integer.toBinaryString(stack);
							break;
						case 5:
							if (S[TOS].length() == 16) {
								if (S[TOS].charAt(0) == '1') {
									stack = reverse_complement(S[TOS]);
								}
							}
							stack = stack + ee;
							S[TOS] = Integer.toBinaryString(stack);
							break;
						case 6:
							if (S[TOS].length() == 16) {
								if (S[TOS].charAt(0) == '1') {
									stack = reverse_complement(S[TOS]);
								}
							}
							stack = stack - ee;
							S[TOS] = Integer.toBinaryString(stack);
							break;
						case 7:
							if (S[TOS].length() == 16) {
								if (S[TOS].charAt(0) == '1') {
									stack = reverse_complement(S[TOS]);
								}
							}
							stack = stack * ee;
							S[TOS] = Integer.toBinaryString(stack);
							break;
						case 8:
							if (S[TOS].length() == 16) {
								if (S[TOS].charAt(0) == '1') {
									stack = reverse_complement(S[TOS]);
								}
							}
							stack = stack / ee;
							S[TOS] = Integer.toBinaryString(stack);
							break;
						case 9:
							if (S[TOS].length() == 16) {
								if (S[TOS].charAt(0) == '1') {
									stack = reverse_complement(S[TOS]);
								}
							}
							stack = stack % ee;
							S[TOS] = Integer.toBinaryString(stack);
							break;
						case 10:
							break;
						case 11:
							break;
						case 12:
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
							PC = Integer.parseInt(EA, 2);
							PC = PC+baseadd;
							break;
						case 16:
							if (stack == 1) {
								PC = Integer.parseInt(EA, 2);	
								PC = PC+baseadd;
							}
							S[TOS] = "0000000000000000";
							TOS = TOS - 1;
							break;
						case 17:
							if (stack == 0) {
								PC = Integer.parseInt(EA, 2);
								PC = PC+baseadd;
							}
							S[TOS] = "0000000000000000";
							TOS = TOS - 1;
							break;
						case 18:
							TOS = TOS + 1;
							String pcc = Integer.toBinaryString(PC);
							String form = "%16s";
							String splitstring = String.format(form, pcc).replace(" ", "0");
							S[TOS] = splitstring;
							PC = Integer.parseInt(EA, 2);
							PC = PC+baseadd;
							break;
						case 19:
							break;
						case 20:
							break;
						case 21:
							break;
						case 22:
							TOS = TOS + 1;
							stack = ee;
							S[TOS] = Integer.toBinaryString(stack);
							break;
						case 23:
							EA = Integer.toBinaryString(stack);
							TOS = TOS - 1;
							MEMORY.MEMORY(1, DecimalEA, EA);
							break;
						case 24:
							System.exit(0);
							break;
						default:
							break;
						}
				}
				if (TraceSwitch == 1 && TraceSwitchone==1 && TOS>=0) {
					//writer.print(TOS + "," + S[TOS] + "                                          " + EA + "," + memor);
					al.add(inttohex(TOS));
					al.add(binarytohex(S[TOS]));
					al.add(binarytohex(EA));
					al.add(binarytohex(memor));
					//writer.println();
					TraceSwitchone=0;
				}
			}

			Iterator<String> itr = al.iterator();
			while (itr.hasNext()) {
				String element = itr.next();
				writer.print(String.format("%10s",element));
			}
			writer.println();
		}
	}

	private Object binarytohex(String iR2) {
		String hex="0";
		if(iR2!=null)
		{
			int num=iR2.length();
			if(iR2.length()>16)
			{
				hex=iR2.substring(num-16, num);
				hex=Integer.toHexString(Integer.parseInt(iR2, 2)).toUpperCase();
			}
			else
			{
				hex=Integer.toHexString(Integer.parseInt(iR2, 2)).toUpperCase();
			}
		}
		return hex;
	}

	private Object inttohex(int pC) {
		String hex=Integer.toHexString(pC);
		return hex;
	}

	private String hextobinary(String bR2) {
		String preBin = new BigInteger(bR2, 16).toString(2);
		String form="%16s";
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
		// TODO Auto-generated method stub
		String form = "%16s";
		String splitstring = String.format(form, string).replace(" ", "0");
		return splitstring;
	}

}