import java.lang.reflect.Method;
import java.util.*;

/***
 * contains information about calculations possible
 * static as fuuuuu, don't need object to call
 * @author olivi
 *
 */
public class Comp  {
	public HashMap<String, Method> ops = new HashMap<String, Method>();

	public Comp() throws Exception {
		ops.put("+", Comp.class.getMethod("add", long.class, long.class));	
		ops.put("-", Comp.class.getMethod("sub", long.class, long.class));	
		ops.put("*", Comp.class.getMethod("mult", long.class, long.class));	
		ops.put("/", Comp.class.getMethod("div", long.class, long.class));

	}
	
	public long add(long x, long y) {
		return (x+y);
	}
	
	public long sub(long x, long y) {
		return (x-y);
	}
	
	public long mult(long x, long y) {
		return(x*y);
	}
	
	public long div(long x, long y) throws Exception {
		if(y==0) {
			throw new ArithmeticException("Division by zero");
		}
		else {
			return x/y;
		}
	}
	
}
