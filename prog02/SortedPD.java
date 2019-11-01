package prog02;



/**
 * This is an implementation of PhoneDirectory that uses a sorted array to store
 * the entries.
 * 
 * @author vjm
 * @param <implement>
 */
public class SortedPD extends ArrayBasedPD {

	protected int find(String name) {
	int first =0,
		last =size-1 ; 
		int comp;
		int middle;;
		while (first <=last) {
			middle = (first + last ) / 2;
			comp=theDirectory[middle].getName().compareTo(name);
			if ( comp==0) 
				return middle;
			 if (comp <0) 
					first = middle +1;
			 if (comp >0)  
					last = middle-1 ;
			
				}
		return -first -	1;
		
	}
	
	protected DirectoryEntry add(int index, String name, String number) {

		if (size == theDirectory.length)
			reallocate();
		for(int i=size-1;i>=index;i--) {  
			theDirectory[i+1] = theDirectory[i];
		}
		theDirectory[index] = new DirectoryEntry(name, number);
		size++;
		return theDirectory[index];

	}

	protected DirectoryEntry remove(int index) {
			DirectoryEntry entry = theDirectory[index];
		for (int i = index; i < size - 1; i++) {
			theDirectory[i] = theDirectory[i + 1];
		}
		size--;
		return entry;
	}
}