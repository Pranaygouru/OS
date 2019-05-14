/* Error Handler with the error numbers and corresponding messages */
// Import Statements.
import java.util.HashMap;
@SuppressWarnings("unchecked")
public class ERRORHANDLER {
static int N;
private static HashMap<Integer,String> errorMap = new HashMap<>();
// GET THE ERROR MESSAGE//
public static String getErrorMap(int N) {
SYSTEM.NatureOfTermination = "ABNORMAL";
MEMORY.MEMORY("DUMP",0,"");
return errorMap.get(N);
}
//CONSTRUCTOR FOR INITIALISING N//
public ERRORHANDLER(int N) {
this.N = N;
}
//CUSTOMIZING USER EXCEPTIONS//
static void init(){
setErrorInMap(1,"ILLEGAL INSTRUCTION");
setErrorInMap(2,"MEMORY RANGE FAULT");
setErrorInMap(3,"PROGRAM SIZE TOO LARGE");
setErrorInMap(4,"ACCESSING LOCKED MEMORY");
setErrorInMap(5,"INFINITE LOOP IN THE PROGRAM");
setErrorInMap(6,"INCORRECT LOADER FORMAT");
setErrorInMap(7,"MISSING/UNRECOGNISABLE TRACE BIT");
setErrorInMap(8,"UNRECOGNIZABLE CHARACTER ENCOUNTERED WHILE LOADING");
setErrorInMap(9,"ALLOC LARGE DATA");
setErrorInMap(10,"MISSING/UNRECOGNIZABLE PRIORITY");
setErrorInMap(11,"MISSING END");
setErrorInMap(12,"MISSING JOB");
}
//SET ERROR MESSAGE IN THE HASH MAP //
private static void setErrorInMap(int ErrorNumber,String message) {
errorMap.put(ErrorNumber,message);
}
}
