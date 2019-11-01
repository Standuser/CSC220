package prog05;

import java.util.Stack;
import prog02.UserInterface;
import prog02.GUI;

public class Tower {
  static UserInterface ui = new GUI("Towers of Hanoi");

  static public void main (String[] args) {
    int n = getInt("How many disks?");
    if (n <= 0)
      return;
    Tower tower = new Tower(n);

    String[] commands = { "Human plays.", "Computer plays." };
    int c = ui.getCommand(commands);
    if (c == -1)
      return;
    if (c == 0)
      tower.play();
    else
      tower.solve();
  }

  /** Get an integer from the user using prompt as the request.
   *  Return 0 if user cancels.  */
  static int getInt (String prompt) {
    while (true) {
      String number = ui.getInfo(prompt);
      if (number == null)
        return 0;
      try {
        return Integer.parseInt(number);
      } catch (Exception e) {
        ui.sendMessage(number + " is not a number.  Try again.");
      }
    }
  }

  int nDisks;
  StackInt<Integer>[] pegs = (StackInt<Integer>[]) new ArrayStack[3];

  Tower (int nDisks) {
    this.nDisks = nDisks;
    for (int i = 0; i < pegs.length; i++)
      pegs[i] = new ArrayStack<Integer>();

    // EXERCISE: Initialize game with pile of nDisks disks on peg 'a' (pegs[0]).
    for(Integer i = nDisks; i > 0; i--)
    	pegs[0].push(i);
  }

  void play () {
    String[] moves = { "ab", "ac", "ba", "bc", "ca", "cb" };

    while (! (pegs[0].empty() && pegs[1].empty())){
    		/* EXERCISE:  player has not moved all the disks to 'c'. */ 
           /* Not right. */
      displayPegs();
      String move = getMove();
      int from = move.charAt(0) - 'a';
      int to = move.charAt(1) - 'a';
      move(from, to);
    }

    ui.sendMessage("You win!");
  }

  private String getMove() {
	// TODO Auto-generated method stub
	  String[] moves = { "ab", "ac", "ba", "bc", "ca", "cb" };
	  return moves[ui.getCommand(moves)];	  
  }

String stackToString (StackInt<Integer> peg) {
    StackInt<Integer> helper = new ArrayStack<Integer>();

    // String to append items to.
    String s = "";

    // EXERCISE:  append the items in peg to s from bottom to top.
    while(! peg.empty())
    	helper.push(peg.pop());
    while(! helper.empty()){
    	s += helper.peek();
    	peg.push(helper.pop());
    }
    
    return s;
  }

  void displayPegs () {
    String s = "";
    for (int i = 0; i < pegs.length; i++) {
      char abc = (char) ('a' + i);
      s = s + abc + stackToString(pegs[i]);
      if (i < pegs.length-1)
        s = s + "\n";
    }
    ui.sendMessage(s);
  }

  void move (int from, int to) {
    // EXERCISE:  move one disk form pegs[from] to pegs[to].
    // Don't allow illegal moves:  send a warning message instead.
    // For example "Cannot place disk 2 on top of disk 1."
    // Use ui.sendMessage() to send messages.
	  if(pegs[from].empty())
		  ui.sendMessage("Empty");
	  else if(! pegs[to].empty() && pegs[from].peek() > pegs[to].peek())
		  ui.sendMessage("Invalid move");
	  else
		  pegs[to].push(pegs[from].pop());
  }
  StackInt<Goal> goals = new ListStack<Goal>();	 
  // EXERCISE:  create Goal class.
  class Goal {
    // Data.
	  int howMany;
	  int fromPeg;
	  int toPeg;
	  Goal(int howMany, int fromPeg, int toPeg) {
		  this.howMany = howMany;
		  this.fromPeg = fromPeg;
		  this.toPeg = toPeg;
	  }
  }
	  
    // Constructor.
    public String toString (int[] a, String t1, int[] b,
            String t2, int[] c, String t3) {
      String[] pegNames = { "a", "b", "c" };
      String s = a +":";
      for(int index =0 ;index < a.length && a[index] != 0; index++) {
    	  s += a[index] + ", ";
      } 
      s =b +":";
      for(int index = 0;index < b.length && b[index] != 0; index++) {
    	  s += b[index] + ", ";    	  
      }  
      s += c +":";
      for(int index = 0;index < c.length && c[index] != 0; index++) {
    	  s += c[index] + ", ";    	  
      }
      return s;
    }


  // EXERCISE:  display contents of a stack of goals
    void displayGoals() {
   	 StackInt<Goal> helper = new ListStack<Goal>();
   	 String s = "";
   	 while (!goals.empty()) {
   		 s += "Move " + goals.peek().howMany + " disk(s) from " +  (char) ('a' + goals.peek().fromPeg) + " to " + (char) ('a' + goals.peek().toPeg) + ".\n";
   		 helper.push(goals.pop());
   	 }
   		 ui.sendMessage(s);
   	while(!helper.empty()) 
   		goals.push(helper.pop());
   	 
    }
    void solve () {
   	 goals.push(new Goal(nDisks, 0, 2));
   	 while(!goals.empty()) {
   		 int howMany = goals.peek().howMany;
   		 int source = goals.peek().fromPeg;
   		 int dest = goals.peek().toPeg;
   		 int transfer;
   		 if((source+dest) == 1)
   			 transfer = 2;
   		 else if((source + dest) == 3)
   			 transfer = 0;
   		 else 
   			 transfer = 1;
   		 displayGoals();
   		 goals.pop();
   		 if(howMany == 1) {
   			 move(source, dest);
   			 displayPegs();
   		 } else {
   			 goals.push(new Goal(howMany - 1, transfer, dest));
   			 goals.push(new Goal(1, source, dest ));
   			 goals.push(new Goal(howMany - 1, source, transfer));
   		 }
   	 }	  
}        
 // EXERCISE


}        
