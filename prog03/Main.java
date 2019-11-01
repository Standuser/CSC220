package prog03;
import prog02.UserInterface;
import prog02.GUI;

/**
 *
 * @author vjm
 */
public class Main {
  /** Use this variable to store the result of each call to fib. */
  public static double fibn;

  /** Determine the average time in microseconds it takes to calculate
      the n'th Fibonacci number.
      @param fib an object that implements the Fib interface
      @param n the index of the Fibonacci number to calculate
      @param ncalls the number of calls to average over
      @return the average time per call
  */
  public static double averageTime (Fib fib, int n, int ncalls) {
    // Get the current time in nanoseconds.
    long start = System.nanoTime();

    // Call fib(n) ncalls times (needs a loop!).

      fibn = fib.fib(n);

    // Get the current time in nanoseconds.
    long end = System.nanoTime();

    // Return the average time converted to microseconds averaged over ncalls.
    return (end - start) / 1000.0 / ncalls;
  }

  /** Determine the time in microseconds it takes to to calculate the
      n'th Fibonacci number.  Average over enough calls for a total
      time of at least one second.
      @param fib an object that implements the Fib interface
      @param n the index of the Fibonacci number to calculate
      @return the time it it takes to compute the n'th Fibonacci number
  */
  public static double accurateTime (Fib fib, int n) {
    // Get the time in microseconds using the time method above.
    double t = averageTime(fib, n,1);

    // If the time is (equivalent to) more than a second, return it.
    if (t > 1e6)
    	return t;


    // Estimate the number of calls that would add up to one second.
    // Use   (int)(YOUR EXPESSION)   so you can save it into an int variable.
    int numcalls = (int) (1e6/t);


    // Get the average time using averageTime above and that many
    // calls and return it.
    return averageTime(fib, n, numcalls);
  }

  
  
  private static UserInterface ui = new TestUI(); //new GUI("FibonacciExperiments");

  public static boolean isInteger(String s, int radix) {
	    if(s.isEmpty()) return false;
	    for(int i = 0; i < s.length(); i++) {
	        if(i == 0 && s.charAt(i) == '-') {
	            if(s.length() == 1) return false;
	            else continue;
	        }
	        if(Character.digit(s.charAt(i),radix) < 0) return false;
	    }
	    return true;
	}
  
  public static void doExperiments (Fib fib) {
    System.out.println("doExperiments " + fib);
    // EXERCISES 8 and 9
     double c=0;
	 double time1est=0;
	  while(true)
	  { String number;
	  number=ui.getInfo("Enter n");
	  if(number==null)
			break;
	while(true) 
	{  
	  if(number.length()==0)
		{
			ui.sendMessage("is not an integer.");
			break;
		}
		  if(!isInteger(number,10))
		  {  ui.sendMessage(number+" is not an integer.");
			  break;
		  }
		 int n=Integer.parseInt(number);
		 if(n<0)
		 {
			 ui.sendMessage("no negative number");
			 break;
		 }
		 double time1 = accurateTime(fib, n);
		 if(c!=0)
		 	{				       
		     time1est = c * fib.O(n);	     //c * efib.O(n3)
		    ui.sendMessage("estimate time is "+time1);
		    }
		c = time1 / fib.O(n);	
		double error = 100*(time1est-time1)/time1;
		ui.sendMessage("Fib is "+fibn+" , running time is "+  time1est +","
				+ " percentage error is "+error+"%");
		break;	
	} 
	  }
  }
  // Calculate constant:  time = constant times O(n).
  //double c = time1 / efib.O(n1);
  //System.out.println("c " + c);
  
  // Estimate running time for n2=30.
  //int n2 = 30;
  //double time2est = c * efib.O(n2);
  //System.out.println("n2 " + n2 + " estimated time " + time2est);
  
  // Calculate actual running time for n2=30.
  //double time2 = averageTime(efib, n2, 100);
  //System.out.println("n2 " + n2 + " actual time " + time2);
  
  //add some code here
  //ncalls = (int)(1e6/time2);
  //time2 = averageTime(efib, n2, ncalls);   
  //System.out.println("avarge time:"+time2);
  //time2 = accurateTime(efib, n2);
 // System.out.println("accurate time:"+time2);
  
  //int n3=100;
  //double time3est = c * efib.O(n3);
  //System.out.println("n3 " + n3 + " estimated time " + time3est);
  
  

  public static void doExperiments () {
    // EXERCISE 10
	  String[] commands = {
				"ExponentialFib",
				"LinearFib",
				"LogFib",
				"ConstantFib",
				"MysteryFib",
		"Exit"};



		while (true) {
			int c = ui.getCommand(commands);
			switch (c) {
			case -1:
				ui.sendMessage("You clicked the red x, restarting.");
				break;
			case 0:
				doExperiments(new ExponentialFib());
				break;
			case 1:
				doExperiments(new LinearFib());
				break;
			case 2:
				doExperiments(new LogFib());
				break;
			case 3:
				doExperiments(new ConstantFib());
				break;
			case 4:
				doExperiments(new MysteryFib());
				break;
			case 5:
				
				return;
			}
		}
  }

  static void labExperiments () {
    // Create (Exponential time) Fib object and test it.
    Fib efib = new ExponentialFib();
    System.out.println(efib);
    for (int i = 0; i < 11; i++)
      System.out.println(i + " " + efib.fib(i));
    
    // Determine running time for n1 = 20 and print it out.
    int n1 = 20;
    double time1 = averageTime(efib, n1, 1000);
    System.out.println("n1 " + n1 + " time1 " + time1);
    
    //add some code here
    //int ncalls= 1e6/t(1seconnd)
    //time1=averageTime(efinb,n2,ncalls);
    int ncalls = (int) (1e6/time1);
    time1 = averageTime(efib,n1,ncalls);
    System.out.println("average time:"+time1);
    time1 = accurateTime(efib, n1);
    System.out.println("accurate time:"+time1);
    //
    
    
    // Calculate constant:  time = constant times O(n).
    double c = time1 / efib.O(n1);
    System.out.println("c: " + c);
    
    // Estimate running time for n2=30.
    int n2 = 30;
    double time2est = c * efib.O(n2);
    System.out.println("n2 " + n2 + " estimated time " + time2est);
    
    // Calculate actual running time for n2=30.
    double time2 = averageTime(efib, n2, 100);
    System.out.println("n2 " + n2 + " actual time " + time2);
    
    //add some code here
    ncalls = (int)(1e6/time2);
    time2 = averageTime(efib, n2, ncalls);   
    System.out.println("avarge time:"+time2);
    time2 = accurateTime(efib, n2);
    System.out.println("accurate time:"+time2);
    //
    
    int n3=100;
    double time3est = c * efib.O(n3);
    System.out.println("n3 " + n3 + " estimated time " + time3est);
    double years = time3est/ 1e6/3600/24/365.25;
    System.out.println("years: "+years);  
  }

  /**
   * @param args the command line arguments
   */
  public static void main (String[] args) {
   //labExperiments();
    // doExperiments(new ExponentialFib());
    doExperiments();
  }
}

