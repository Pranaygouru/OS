public class MEMORY {
	public static String MEMORY(int x, int y, String z) {
		// TODO Auto-generated method stub
		//int tosvalue=Integer.parseInt(z);
		if(x==0 && y<256)
		{
			System.out.println(z);
			z=SYSTEM.mainmemoryarray[SYSTEM.BaseRg+y];
		}
		if(x==1 && y<256)
		{
			SYSTEM.mainmemoryarray[SYSTEM.BaseRg+y]=z;
		}
		return z;
	}

}
