/**
Memory array of size 256 is created and receives the calls from loader
to perform RD or WR operations in and out of memory.**/

public class MEMORY {
 static String[] mainmemoryarray = new String[256];
 public static String MEMORY(int x, int y, String z) {

  if (x == 0 && y < 256) /**RD**/ {
   z = mainmemoryarray[y];
  }
  if (x == 1 && y < 256) /**WR**/ {
   mainmemoryarray[y] = z;
  }
  return z;
 }
}