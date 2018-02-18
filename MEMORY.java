public class MEMORY {

	public static String MEMORY(int x, int y, String z) {
		// TODO Auto-generated method stub
		if(x==0 && y<256)
		{
			y=y+1;
			z=SYSTEM.mainmemoryarray[y];
		}
		if(x==1 && y<256)
		{
			SYSTEM.mainmemoryarray[y]=z;
		}
		return z;
	}

}
