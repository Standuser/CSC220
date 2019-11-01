package prog04;

public class SortedDLLPD extends DLLBasedPD {
	public String addOrChangeEntry(String name, String number) {
		DLLEntry entry = find(name);
		if (entry != null && entry.getName().equals(name)) {
			String oldNumber = entry.getNumber();
			entry.setNumber(number);
			return oldNumber;
		}

		DLLEntry newEntry = new DLLEntry(name, number);
		if (head == null) {
			head = newEntry;
			tail = newEntry;
			return null;
			//got something wrong with the tail
		}

		if (entry == null) {
			tail.setNext(newEntry);
			newEntry.setPrevious(tail);
			tail = newEntry;
		} else if (head == tail || entry == head) {
			newEntry.setNext(head);
			head.setPrevious(newEntry);
			head = newEntry;
		} else {
			newEntry.setNext(entry);
			newEntry.setPrevious(entry.getPrevious());
			entry.getPrevious().setNext(newEntry);
			entry.setPrevious(newEntry);
		}
		return null;
	}
	protected DLLEntry find(String name) {
		for (DLLEntry entry = head; entry != null; entry = entry.getNext()) {
			int comp = entry.getName().compareTo(name);
			if (comp >= 0)
				return entry;
		}
		return null;
	}


	public String removeEntry(String name) {
		DLLEntry entry = find(name);
		if (entry == null || !entry.getName().equals(name))
			return null;

		DLLEntry next = entry.getNext();
		DLLEntry previous = entry.getPrevious();
		if (previous == null && next == null) {
			head = null;
			tail = null;
		}
		else if (entry.getPrevious() == null) {
			head = next;
			head.setPrevious(null);
			entry.setPrevious((null));
			entry.setNext(null);
		}
		else if (entry.getNext() == null) {
			tail = previous;
			tail.setNext(null);
			entry.setPrevious((null));
			entry.setNext(null);
		} else {
			previous.setNext(next);
			next.setPrevious(previous);
			entry.setPrevious((null));
			entry.setNext(null);
		}

		boolean modified = true;
		return entry.getNumber();
	}
}