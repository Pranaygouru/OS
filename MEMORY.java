public class MEMORY {
	static String[] mainmemoryarray = new String[256];
	
	public static String MEMORY(int x, int y, String z) {
		// TODO Auto-generated method stub
		//int tosvalue=Integer.parseInt(z);
		if(x==0 && y<256)
		{
			//System.out.println(z);
			z=mainmemoryarray[y];
		}
		if(x==1 && y<256)
		{
			mainmemoryarray[y]=z;
		}
		return z;
	}

}