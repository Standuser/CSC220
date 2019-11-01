package prog06;

import prog02.GUI;
import prog02.UserInterface;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WordStep {

	public static UserInterface ui = new GUI("game");

	public static void main(String[] args) {

		WordStep game = new WordStep();
		String file = ui.getInfo(" what is the name of the file ");
		if (file == null) {
			return;
		}
		while (!game.loadWords(file)) {

			file = ui.getInfo(" what is the name of the file ");
			if (file == null) {
				return;
			}
		}
		
		ui.sendMessage(" File Load Success");
		String in = ui.getInfo("begin");
		if (in == null) {
			return;
		}
		while (in.length() == 0 || game.find(in) == -1) {
			ui.sendMessage("not word");
			
			in = ui.getInfo("try agin");
			if (in == null) {
				return;
			}
		}
		String tar = ui.getInfo("target");
		if (tar == null) {
			return;
		}
		while (tar.length() != in.length()) {
			tar = ui.getInfo("the length of target is not the same as begian// new length");
		}
		while (tar.length() == 0 || game.find(tar) == -1) {
			ui.sendMessage("not word");
			tar = ui.getInfo("try agin");
			if (tar == null) {
				return;
			}
		}
		String[] commands = { "Human plays.", "Computer plays." };
		int a = ui.getCommand(commands);
		switch (a) {
		case 0:
			game.play(in, tar);
			break;
		case 1:
			game.solve(in, tar);
			break;
			default: break;
		}
	}

	 void solve( String in , String tar) {
			int [] parents =new int[words.size()];
			for ( int i = 0 ; i<parents.length; i++) {
				parents[i]=-1;
			}
			LinkedList list = new LinkedList();
			int Queue=find(in);
			list.offer(Integer.valueOf(Queue));
			while ( !list.isEmpty()) {
				int number =(int) ( list.remove());
			String s1 = words.get(number);
			for ( int i =0;i<words.size();i++) {
				String s2 =words.get(i);
				if (i!=Queue&&parents[i]==-1) {
					if (offBy1(s1,s2)) {
						parents[i]= number;
						list.offer(i);
						
						if (s2.equals(tar)) {
							String s3 = s2+"\n";
						while (i!=Queue) {
							i=parents[i];
							s3=(String)words.get(i)+"\n"+s3;
						
						}ui.sendMessage(s3);
						return ;
						}
					}
					}
				
			}
			 
		 }
		 
	 }
			

	 void play(String in, String tar) {
		// TODO Auto-generated method stub
		String pre = in;
		String tem = in;
		while (true) {
			ui.sendMessage(" current word : " + tem + "\n" + " target word : " + tar);
			tem = ui.getInfo(" what is your next word ?");
			if (tem == null) {
				return;
			}
			if (tem.length() == 0 || find(tem) == -1) {
				ui.sendMessage(tem + " is not word");
				// ui.sendMessage(" current word : " + tem + "\n" + " target word : " + tar);
				tem = pre;
				if (in == null) {
					return;
				}
			}

			if (!offBy1(pre, tem)||tem.equals(pre)) {
				if (find(tem) != -1) {
					ui.sendMessage(" current word is more than 1 different ");

				}
				tem = pre;
			} else
				pre = tem;
			if (tem.equals(tar)) {
				ui.sendMessage(" you win ");
				return;
			}
		}

	}
	 
	
	

	static boolean offBy1(String pre, String temp) {
		boolean check = true;
		int number = 0;
		if (pre.length() != temp.length())
			return false;
		for (int i = 0; i < pre.length(); i++) {
			if (pre.charAt(i) != temp.charAt(i))
				number++;
			if (number > 1)
				check = false;

		}
		return check;
	}

	List<String> words = new ArrayList<String>();

	boolean loadWords(String str) {
		try {
			Scanner sc = new Scanner(new File(str));
			while (sc.hasNextLine()) {
				this.words.add(sc.nextLine());
			}
		} catch (Exception e) {
			ui.sendMessage(" file Not Found");
			return false;
		}
		return true;

	}

	int find(String word) {
		for (int i = 0; i < this.words.size(); i++) {
			if (word.equals(this.words.get(i))) {
				return i;
			}
		}
		return -1;

	}
	// play 里面有一部取消时候抱错
	
}
