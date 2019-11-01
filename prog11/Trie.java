package prog11;

import java.util.*;

import javax.xml.soap.Node;

import prog02.*;

public class Trie<V> extends AbstractMap<String, V> {

	private class Entry<V> implements Map.Entry<String, V> {
		String key;
		V value;

		Entry(String key, V value) {
			this.key = key;
			this.value = value;
			this.sub = key;
		}

		Entry(String sub, String key, V value) {
			this.key = key;
			this.value = value;
			this.sub = sub;
		}

		Entry(String sub, String key, V value, List<Entry<V>> list) {
			this.key = key;
			this.value = value;
			this.sub = sub;
			this.list = list;

		}

		public String getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}

		public V setValue(V newValue) {
			V oldValue = value;
			value = newValue;
			return oldValue;
		}

		String sub;
		List<Entry<V>> list = new ArrayList<Entry<V>>();
	}

	private List<Entry<V>> list = new ArrayList<Entry<V>>();
	private int size;

	public int size() {
		return size;
	}

	/**
	 * Find the entry with the given key.
	 * 
	 * @param key
	 *            The key to be found.
	 * @param iKey
	 *            The current starting character index in the key.
	 * @param list
	 *            The list of entrys to search for the key.
	 * @return The entry with that key or null if not there.
	 */
	private Entry<V> find(String key, int iKey, List<Entry<V>> list) {
		// EXERCISE:
		int iEntry = 0;
		// Set iEntry to the index of the entry in list such that the first
		// character (charAt(0)) of its sub (NOT key) is the same as the
		// character at index iKey in key.
		// If no such entry, return null.
		///
		char kc = key.charAt(iKey);

		while (iEntry < list.size() && kc > list.get(iEntry).sub.charAt(0))
			iEntry++;
		if (iEntry ==list.size() || kc < list.get(iEntry).sub.charAt(0)) 
			return null;
		
		///

		Entry<V> entry = list.get(iEntry);
		int iSub = 0;
		// If we are here, then character iKey of key and iSub of entry.sub
		// must match. Increment both iKey and iSum until that is not true.
		///
		do {
			iKey++;
			iSub++;
		} while (iKey < key.length() && iSub < entry.sub.length() && key.charAt(iKey) == entry.sub.charAt(iSub));

		///

		// If we have not matched all the characters of entry.sub, the key
		// is not in the Trie.
		///
		if (iSub < entry.sub.length()) {
			return null;

		}
		///

		// If we have matched all the characters of key, then entry might
		// be the one we want (to return). But only if its key is not
		// null. If it is null, the key is not in the Trie.
		///

		if (iKey == key.length()) {
			if (entry.key == null)
				return null;
			else
				return entry;

		}

		///

		// If we have not returned yet, we need to recurse, but if
		// entry.list is null, we cannot so the key is not in the Trie.
		///

		if (entry.list != null) {
			return find(key, iKey, entry.list);
		}

		///
		return null;
	}

	public boolean containsKey(Object key) {
		Entry<V> entry = find((String) key, 0, list);

		return entry != null && entry.key != null;
	}

	public V get(Object key) {
		Entry<V> entry = find((String) key, 0, list);
		if (entry != null && entry.key != null)
			return entry.value;
		return null;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	private V put(String key, int iKey, V value, List<Entry<V>> list) {
		// EXERCISE:3
		// V tem= null;
		int iEntry = 0;

		char kc = key.charAt(iKey);
		while (iEntry < list.size() && kc > list.get(iEntry).sub.charAt(0))
			iEntry++;
		if (iEntry == list.size()) {
			Entry<V> New = new Entry<V>(key.substring(iKey), key, value);
			list.add(New);
			size++;
			return null;
		} else if (list.get(iEntry).sub.charAt(0) > key.charAt(iKey)) {
			Entry<V> New = new Entry<V>(key.substring( iKey), key, value);
			list.add(iEntry, New);
			size++;
			return null;
		}

		// Set iEntry to the index of the entry in list such that the first
		// character (charAt(0)) of its sub (NOT key) is the same as the
		// character at index iKey in key.
		// If no such entry, set iEntry to the index where it should be.
		///

		///

		// If no such entry, create a entry whose sub is the unmatched part
		// of key, key is key, and value is value. Add it at index
		// iEntry. Increment size. Return null.
		///

		///
		// V tem= null;
		Entry<V> entry = list.get(iEntry);
		int iSub = 0;
		do {
			iSub++;
			iKey++;
		} while (iKey < key.length() && iSub < entry.sub.length() && key.charAt(iKey) == entry.sub.charAt(iSub));

		// If we are here, then character iKey of key and iSub of entry.sub
		// must match. Increment both iKey and iSum until that is not true.
		///

		///

		if (iSub < entry.sub.length()) {
			// String sub entry.sub;
			String first = entry.sub.substring(0, iSub);
			String last = entry.sub.substring(iSub, entry.sub.length());
			// System.out.println(entry.sub+"这是第一个 " + first+"这是最后一个 "+last);
			Entry<V> Second = new Entry<V>(last, entry.key, entry.value, entry.list);
			entry.sub = first;
			entry.key = null;
			entry.value = null;
			entry.list = new ArrayList<Entry<V>>();
			entry.list.add(Second);
		}
		// list.set(iEntry,Second);

		// If we have not matched all the characters of entry.sub, we need
		// to split entry. Create a second entry with everything (key,
		// value, list) the same except sub is the unmatched part of
		// entry.sub. Set entry.sub (of the original entry) to the matched
		// part. Set its key and value to null. Give a new list. Add
		// the second entry to that list.
		/// 3

		///
		if (iKey == key.length()) {
			if (entry.key == null) {
				entry.key = key;
				// tem= entry.value;
				entry.value = value;
				size++;
				return null;

			} else {
				// tem= entry.value;
				return entry.setValue(value);
			}

		}
		// If we have matched all the characters of key, then we will use
		// the current entry. If its key is null, set its key and value,
		// increment size and return null. Otherwise, just return
		// entry.setValue(value).
		///

		///

		if (entry.list == null) {

			entry.list = new ArrayList<Entry<V>>();
		}
		return put(key, iKey, value, entry.list);
		// If we have not returned yet, we need to recurse, but first if
		// entry.list is null, set it to a new list. Then recurse.
		///

		///

	}

	public V put(String key, V value) {
		return put(key, 0, value, list);
	}

	public V remove(Object keyAsObject) {
		return null;
	}

	private V remove(String key, int iKey, List<Entry<V>> list) {
		return null;
	}

	private Iterator<Map.Entry<String, V>> entryIterator() {
		return new EntryIterator();
	}

	private class EntryIterator implements Iterator<Map.Entry<String, V>> {
		// EXERCISE
		Stack<Iterator<Entry<V>>> stack = new Stack<Iterator<Entry<V>>>();

		EntryIterator() {
			stack.push(list.iterator());
		}

		public boolean hasNext() {
			while (!stack.isEmpty() && !stack.peek().hasNext()) {
				stack.pop();

				// EXERCISE
				// While the iterator at the top of the stack has not next, pop it.
				// Return true if you have not popped all the iterators.
				///
			}
			///
			return !stack.isEmpty();

		}

		public Map.Entry<String, V> next() {
			if (!hasNext())
				throw new NoSuchElementException();
			Entry<V> entry = null;
			if (stack.peek().hasNext())
				entry = stack.peek().next();
			if (!entry.list.isEmpty())
				stack.push(entry.list.iterator());
			if (entry.key == null) {
				return next();
			}
			// EXERCISE
			// Set entry to the next of the top of the stack.
			// If its list is not null, push its iterator.
			// Repeat those two steps if entry.key is null.
			///

			///
			return entry;
		}

		public void remove() {
		}
	}

	public Set<Map.Entry<String, V>> entrySet() {
		return new EntrySet();
	}

	private class EntrySet extends AbstractSet<Map.Entry<String, V>> {
		public int size() {
			return size;
		}

		public Iterator<Map.Entry<String, V>> iterator() {
			return entryIterator();
		}

		public void remove() {
		}
	}

	public String toString() {
		return toString(list, 0);
	}
static  int n =0;
	private String toString(List<Entry<V>> list, int indent) {
		String ind = "";
		// Add index number of spaces to ind:
		///
		for (int i = 0; i < indent; i++) {
			ind = ind + " ";
		}

		///
		String s = "";
	for ( int i=0;i< list.size();i++) {
		System.out.println(list.get(i).key+" "+list.get(i).value+"       "+list.get(i).sub);
	}
		n++;
		for (Entry<V> entry : list) {
			s += ind + entry.sub + " " + entry.key + " " + entry.value + "\n";
			if (entry.sub != null) {
				s = s + toString(entry.list, indent + entry.sub.length());
			
			}

			// Append indented entry (sub, key, and value) and newline ("\n") to s.
			///

			///
			// If there is a sublist, append its toString to s.
			// What value of indent should you used?
			// bob null null
			// by bobby 7
			// cat bobcat 8
			// What additional indent would make by and cat line up just past bob?
			///

			///
		}
		return s;
	}

	void test() {
		Entry<Integer> bob = new Entry<Integer>("bob", null, null);
		list.add((Entry<V>) bob);
		Entry<Integer> by = new Entry<Integer>("by", "bobby", 0);
		bob.list.add(by);
		Entry<Integer> ca = new Entry<Integer>("ca", null, null);
		bob.list.add(ca);
		Entry<Integer> lf = new Entry<Integer>("lf", "bobcalf", 1);
		ca.list.add(lf);
		Entry<Integer> t = new Entry<Integer>("t", "bobcat", 2);
		ca.list.add(t);
		Entry<Integer> catdog = new Entry<Integer>("catdog", "catdog", 3);
		list.add((Entry<V>) catdog);
		size = 4;
	}

	public static void main(String[] args) {
		Trie<Integer> trie = new Trie<Integer>();
		trie.test();
		System.out.println(trie);

		UserInterface ui = new ConsoleUI();

		String[] commands = { "toString", "containsKey", "get", "put", "size", "entrySet", "remove", "quit" };
		String key = null;
		int value = -1;

		while (true) {
			int command = ui.getCommand(commands);
			// int[] command = {0,1,2,3,0,3,0,3,0,3,0,4,5,7};
			// int q =0;
			// while ( command[q] >=0) {
			switch (command) {
			case 0:
				ui.sendMessage(trie.toString());
				break;
			case 1:
				key = ui.getInfo("key: ");
				if (key == null) {
					ui.sendMessage("null key");
					break;
				}
				ui.sendMessage("containsKey(" + key + ") = " + trie.containsKey(key));
				break;
			case 2:
				key = ui.getInfo("key: ");
				if (key == null) {
					ui.sendMessage("null key");
					break;
				}
				ui.sendMessage("get(" + key + ") = " + trie.get(key));
				break;
			case 3:
				key = ui.getInfo("key: ");
				if (key == null) {
					ui.sendMessage("null key");
					break;
				}
				try {
					value = Integer.parseInt(ui.getInfo("value: "));
				} catch (Exception e) {
					ui.sendMessage(value + "not an integer");
					break;
				}
				ui.sendMessage("put(" + key + "," + value + ") = " + trie.put(key, value));
				break;
			case 4:
				ui.sendMessage("size() = " + trie.size());
				break;
			case 5: {
				String s = "";
				for (Map.Entry<String, Integer> entry : trie.entrySet())
					s += entry.getKey() + " " + entry.getValue() + "\n";
				ui.sendMessage(s);
				break;
			}
			case 6:
				break;
			case 7:
				return;
			}

		}
	}
}
