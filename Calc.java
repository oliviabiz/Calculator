import java.lang.reflect.Method;
import java.util.Stack;

/**
 * contains underlying functionality of calculator
 * @author olivi
 *
 */
public class Calc {
	public static String inLine;
	public long curr;
	public String forNow = "";

	public static Stack<Long> s = new Stack<Long>();
	public static Stack<Method> calcs = new Stack<Method>();
	public Comp c;

	public Calc() throws Exception {	
		//TODO: necessary?
		curr = 0;
		c = new Comp();
	}

	public String update(String next) {
		inLine = next;
		return inLine;
	}

	public void clear() {
		curr = 0;
		s.clear();
		calcs.clear();
	}

	public void run() {
		if(inLine.equals("=")) {

			if(!calcs.empty() && calcs.peek()!=null) {
				try{
					curr = (long) calcs.pop().invoke(c, curr , Long.parseLong(forNow));
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				forNow = "";
//				System.out.println("curr = " + curr);
			}
			return;
		}
		if(inLine.equals("CLR")) {
			curr = 0;
			forNow = "";
		}

		try {
			Integer.parseInt(inLine);
			forNow += inLine;
		}
		catch (NumberFormatException e) { //operator
			calcs.push(c.ops.get(inLine));
			if(!forNow.equals("")) {
				try{
					curr = Long.parseLong(forNow);
					forNow = "";
				}
				catch(NumberFormatException e2) {
					e2.printStackTrace();
				}
				
			}
		}
		catch(ArithmeticException er) {
			update("ERROR");
		}

	} //method: run
}//Class Calc
