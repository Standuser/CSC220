package prog10;

import java.io.File;
import java.util.Scanner;

import prog02.UserInterface;
import prog02.ConsoleUI;
import prog02.GUI;
import java.util.Map;
import java.util.TreeMap;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class Jumble {
	/**
	 * Sort the letters in a word.
	 * 
	 * @param word
	 *            Input word to be sorted, like "computer".
	 * @return Sorted version of word, like "cemoptru".
	 */
	public static String sort(String word) {
		char[] chars = word.toCharArray();
		Arrays.sort(chars);
		return new String(chars);
	}

	public static void main(String[] args) {
		UserInterface ui = new GUI("Jumble Solver");
		// EXERCISE:
		// Need to change this to allow multiple words with the same key.
		Map<String, List<String>> map = new ChainedHashTable<String, List<String>>();

		Scanner in = null;
		
		do {
			try {
				in = new Scanner(new File(ui.getInfo("Enter word file.")));
			} catch (Exception e) {
				System.out.println(e);
				System.out.println("Try again.");
			}
		} while (in == null);

		int n = 0;
		while (in.hasNextLine()) {
			String word = in.nextLine();
			if (n++ % 1000 == 0)
				System.out.println(word + " sorted is " + sort(word));

			String sorted = sort(word);

			// EXERCISE
			// Comment this out.
			// map.put(sorted, word);

			if (!map.containsKey(sorted)) {
				// EXERCISE
				List<String> list1 = new ArrayList<String>();
				list1.add(word);
				map.put(sorted, list1);
				// Suppose word is "dare" (the first word with a,d,e, and r).
				// Create empty list.

				// Add "dare" to it.

				// key is "ader", value is the list ["dare"]
				// Store that value for that key.

			} else {
				List<String> list2 = map.get(sorted);
				list2.add(word);
				map.put(sorted, list2);
				// EXERCISE
				// Suppose word is "read" (another word with a,d,e and r).
				// Key is "ader" and value is ["dare","dear"]
				// Get the value.

				// Add "read" to the list.

				// List is now ["dare","dear","read"]
				// Update the value for that key.

			}
		}

		String jumble = ui.getInfo("Enter jumble.");
		while (jumble != null) {
			List<String> word = map.get(sort(jumble));
			if (word == null)
				ui.sendMessage("No match for " + jumble);
			else
				ui.sendMessage(jumble + " unjumbled is " + word);

			// EXERCISE:
			// Get the list of words for that jumble.

			// Send a message if there is no list:
			// "No match for " + jumble
			// Send a message if there is
			// jumble + " unjumbled is " + listOfWords

			jumble = ui.getInfo("Enter jumble.");
		}

		while (true) {
			String letters = ui.getInfo("Enter letters from clues in any order.");
			if (letters == null)
				return;
			String sortedLetters = sort(letters);

			int l = 0;
			do {
				String number = ui.getInfo("How many letters in the first word?");
				try {
					l = Integer.parseInt(number);
					if (l <= 0)
						ui.sendMessage(number + " is not positive");
				} catch (Exception e) {
					ui.sendMessage(number + " is not a number");
				}
			} while (l <= 0);

			for (String key1 : map.keySet()) {
				int index1 = 0;

				// EXERCISE:
				// Check if key1 has the right length.

				String key2 = "";
				if (key1.length() == l) {
					for (int i = 0; i < sortedLetters.length(); i++) {
						char letter = sortedLetters.charAt(i);
						if (index1 == key1.length()) {
							key2 = key2 + Character.toString(letter);
						

						} else if (key1.charAt(index1) == letter) {
							index1++;
						} else if (key1.charAt(index1) < letter) {
							break;

						} else
							key2 = key2 + Character.toString(letter);
					}
				}
					if (index1 ==key1.length()&&key2!= null) {
						List<String> list3 = map.get(key1);
						List<String> list4 = map.get(key2);
						if (list3!=null&&list4!=null) {
							ui.sendMessage(list3+"   "+ list4);
						}
					}
					// Add the letters in sortedLetters that aren't in key1 to key2.

					// If sortedLetters had all the letters in key1 and
					// if key2 has a list of words,
					// show the lists for key1 and key2.

				
			}
		}
	}
}
