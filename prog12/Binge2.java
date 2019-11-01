package prog12;

import java.util.*;
//import java.util.List;

//import Tinge2.PageComparator;

public class Binge2 implements SearchEngine {
	HardDisk<PageFile> pageDisk = new HardDisk<>();
	PageTrie urlToIndex = new PageTrie();
	HardDisk<List<Long>> wordDisk = new HardDisk<>();
	WordTable wordToIndex = new WordTable();

	@Override
	public void gather(Browser browser, List<String> startingURLs) {
		// TODO Auto-generated method stub
		urlToIndex.read(pageDisk);
		wordToIndex.read(wordDisk);
	}

	@Override
	public String[] search(List<String> keyWords, int numResults) {
		Iterator<Long>[] wordFileIterators = (Iterator<Long>[]) new Iterator[keyWords.size()];
		long[] currentPageIndices = new long[keyWords.size()];
		PriorityQueue<Long> bestPageIndices = new PriorityQueue<Long>(numResults, new pageComparator());
		for (int i = 0; i < keyWords.size(); i++) {
			String word = keyWords.get(i);
			if (!wordToIndex.containsKey(word)) {
				return new String[0];
			}
			long index = wordToIndex.get(word);
			wordFileIterators[i] = wordDisk.get(index).iterator();
		}
		// TODO Auto-generated method stub
		while (getNextPageIndices(currentPageIndices, wordFileIterators)) {

			if (allEqual(currentPageIndices)) {
				long index = currentPageIndices[0];
				int ref = pageDisk.get(index).getRefCount();

				if (bestPageIndices.size() < numResults ||ref> pageDisk.get(bestPageIndices.peek()).getRefCount()) {
					if (bestPageIndices.size()==numResults) 
						bestPageIndices.poll();		
				bestPageIndices.offer(index);
			}
			}
		}
		String[] string = new String[bestPageIndices.size()];
		for (int i = string.length - 1; i >= 0; i--) {
			string[i] = pageDisk.get(bestPageIndices.poll()).url;

		}
		return string;
	}

	private boolean getNextPageIndices(long[] currentPageIndices, Iterator<Long>[] wordFileIterators) {
		try {
		boolean equals = allEqual(currentPageIndices);
		if(equals) {
			for (int i = 0; i < currentPageIndices.length ; i++) {
				Iterator<Long> iter = wordFileIterators[i];
			     currentPageIndices[i] = iter.next();
			}
			    return true;
		}else {

		long biggest = -1;
		for (int i = 0; i < currentPageIndices.length; i++) {
			if (currentPageIndices[i] > biggest)
				biggest = currentPageIndices[i];
		}
		for (int i = 0; i < wordFileIterators.length; i++) {
		     Iterator<Long> iterator = wordFileIterators[i];
		     if (currentPageIndices[i] < biggest) {
		      currentPageIndices[i] = iterator.next();
		     }
		    }
		    return true;
		   }
	
		}catch(NoSuchElementException e) {
		
		return false;
		}
	}

	private boolean allEqual(long[] array) {
		boolean result = true;
		for (int i = 0; i < array.length - 1; i++) {
			if (array[i] != array[i + 1]) {
				return false;
			}
		}
		return true;
	}

	private class pageComparator implements Comparator<Long> {

		@Override
		public int compare(Long a, Long b) {
			// TODO Auto-generated method stub

			return pageDisk.get(a).getRefCount() - pageDisk.get(b).getRefCount();

		}

	}

}
