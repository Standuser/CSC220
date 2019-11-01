package prog07;

import java.io.*;
import java.util.*;
import prog02.GUI;
import prog02.UserInterface;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
//import prog06.WordStep;
//import prog06.WordStep.Node;
//import prog06.WordStep.Node;

//static UI ui = new GUI("Word Step!")
/*Review:
 * ui.get.info()
 * ui.sendMessage()
 * ui.getCommand()
 */
/*
 * main{
 * 	WordStep game= new WordStep();
 * get Start& target word
 * Ask if human or CPU plays
 */

//interface
import prog02.*;



public class WordStep {
	
	//list
	
	static List<String> words = new ArrayList<String>();
	//old list
	
	//static List<Node> node = new ArrayList<Node>();
	
	public static UserInterface ui = new GUI();

	
	//boolean
	/*public static boolean offByOne(String start, String next) {
		int difference = 0;
		if (start.length() == next.length()){
			int length = start.length();
			char startFrisk, targetChara;
		// yeah.. I am referencing UnderTale...
			for (int i = 0; i < length; i++) {
				startFrisk = start.charAt(i);
				targetChara = next.charAt(i);
				if (startFrisk != targetChara) {
					difference++;
				}return difference <= 1;
			}
		}return false;*/
	public static boolean offByOne(String start, String next) {
		if (start.length() != next.length()) {
			return false;
		}
		
		int difference = 0;
		int length = start.length();
		char startFrisk, targetChara;
		// yeah.. I am referencing UnderTale...
		for (int i = 0; i < length; i++) {
			startFrisk = start.charAt(i);
			targetChara = next.charAt(i);
			if (startFrisk != targetChara)
				difference++;
			if (difference > 1)
				return false;
		}
		return true;
	}
	

		
	

		
		static int numSteps (int[] parents, int index) {
			int count = 0;
			int i = index;
			while (parents[i]!= -1) {	
				i=parents[i];
				count++;	
				
			}
			return count;
		}
	

	
	//find
	public static int find(String wd) {
		for (int i = 0; i < words.size(); i++) {
			if ((words.get(i)).equals(wd))
				return i;
		}
		return -1;
	}
	
	//main
	public static void main(String[] args) {
		String start, target;
		int selection = 0;
		WordStep gameplay = new WordStep();
		
		gameplay.loadWords(ui.getInfo("Enter file name"));
		do {
		if(gameplay.words.size() == 0)
			return;
		
		}while(gameplay.words.size() == 0);
		while (true) {
			start = ui.getInfo("Enter start word: ");
			if (start == null)
				return;
			if(find(start) == -1) {
				ui.sendMessage("This word is not found");
			}
			else
				break;
		}
		while (true) {
			target = ui.getInfo("Enter target word: ");
			if (target == null)
				return;
			if(find(target) == -1) {
				ui.sendMessage("This word is not found");
			}
			else
				break;
		}
		
		
		String[] commands = { "Human plays.", "Computer plays." };
			selection = ui.getCommand(commands);
			switch (selection) {
			case 0:
				gameplay.play(start, target);
				break;
			case 1:
				gameplay.solve(start, target);
				break;
			default:
				break;
			}
		}


	// load	
	private boolean loadWords(String list) {
		// TODO Auto-generated  stub
		File filename = new File(list);
		Scanner selection;
	
		String word;
		try {
			selection = new Scanner(filename);
			while (selection.hasNextLine() == true) {
				word = selection.nextLine();
				words.add(word);
			}
		} catch (FileNotFoundException e) {
			ui.sendMessage("File not Found");
			ui.sendMessage("Try a new one plz");
			return false;
		}
		return true;
	}

	//humanplay
	void play(java.lang.String start, java.lang.String target) {
		// TODO Auto-generated method stub
		String next;
		while (true) {
		ui.sendMessage("The starting word is " + start + " while the Target is..." + target);
			next = ui.getInfo("Type in a word plz");
			if (next == null) {
				break;}
				if (find(next)==-1) {
				ui.sendMessage("This word is not found");
				ui.sendMessage("Let's try a new word");
				
			} else if (offByOne(start, next)) {
				start = next;
				ui.sendMessage("Good, a valid word");
			} else
				ui.sendMessage("Invalid word, try a new one plz ");
				// I guess the boolean part is done but the problem is I still have loops
			// can't turn off the window
			if (start.equals(target)) {
				ui.sendMessage("You Win the Game!");
				ui.sendMessage("Thank You For Playing!");
				return;
			}
			}
		}
	
		

		// if (!offByOne(start,next)) {
		//is how to test it
		// done
	
	
	//cpu play
	//ISSUE***:depth first---finding evrything available making the dequeue too big.
	void solve(java.lang.String start, java.lang.String target) {
		// TODO Auto-generated method stub
		List<String> list = words;
		int parents[] = new int [words.size()];
		for (int i=0; i< parents.length; i++ ) {
			parents[i]= -1;
		}
		
		/*for (int i=0; i< parents.length; i++ ) {
			parents[i]= -1;
		
		}*/
		
		Queue<Integer> queue = new PriorityQueue<Integer>();
		queue.offer(find(start));
		//if (queue.peek() != null) {
			//queue.remove();
		//} 
		int count=0;
			
		while(queue.size()>0){			
			int frontQue= queue.poll();
			String currentWord = words.get(frontQue);
			count++;
			String result;
			int index;
			System.out.println(" Dequeue- " + currentWord+ (IndexComparator.numDifferent(currentWord,target) + numSteps(parents,frontQue)));
			System.out.print("Enqueue-");
			
			for (int i=0; i < words.size();i++) {
				if(offByOne(currentWord, words.get(i)) && (parents[i]==-1) && !words.get(i).equals(start)) {
					parents[i] = frontQue;
					queue.offer(i);
					
					
					if (words.get(i).equals(target)) {
						ui.sendMessage("Get " + target + " from thefile");
						ui.sendMessage("Took " + count + " DEQUEUES");
						
						index = find(target);
						result = target + "\n";
						while (parents[index]!= -1) {
							result += words.get(parents[index]) +"\n";
							index = parents[index];
							
						}
						ui.sendMessage(result);
						return;
					}
				}
			}
		}
		}
		
	
	



	//IndexComparator
	private static class IndexComparator implements Comparator<Integer>{
		int[] parents;
		String target;
		IndexComparator(int[] parents,String target){
			this.parents= parents;
			this.target = target;
		}
		
		public int sumNums(int index) {
			return numSteps(parents, index) + numDifferent(words.get(index), target);
		}
		
		@Override
		public int compare(Integer index1, Integer index2 ) {
			if (sumNums(index1)<sumNums(index2)) {
				//<0
				return -1;
			}else if (sumNums(index1) == sumNums(index2)) {
				return 0;
			}
			return 1;
			//>0
		}    

	
	public static int numDifferent(String start, String target){
		int num = 0;
		if (start.length() != target.length())
			return -1;
		for (int i = 0; i < start.length(); i++) {
			if (start.charAt(i) != target.charAt(i))
				num++;
		}
		return num;
	}

	}
}




