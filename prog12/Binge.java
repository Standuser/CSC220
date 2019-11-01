package prog12;

import java.util.*;
import java.util.Map.Entry;

public class Binge implements SearchEngine {
	HardDisk<PageFile> pageDisk = new HardDisk<>();
	PageTrie urlToIndex = new PageTrie();
	HardDisk<List<Long>> wordDisk = new HardDisk<>();
	WordTable wordToIndex = new WordTable();

	public Long indexPage(String url) {
		long index = pageDisk.newFile();
		PageFile newPageFile = new PageFile(index, url);
		pageDisk.put(index, newPageFile);
		urlToIndex.put(url, index);
		System.out.println("indexing page " + newPageFile);
		return index;

	}

	public Long indexWord(String word) {
		// long indexword = urlToIndex.get(word);
		Long indexword = wordToIndex.get(word);
		if (indexword == null) {
			indexword = wordDisk.newFile();
			List<Long> WordFile1 = new ArrayList<>();
			wordDisk.put(indexword, WordFile1);
			wordToIndex.put(word, indexword);
			System.out.println("indexing word " + indexword + "(" + word + ")[]");
		}
		return indexword;
	}

	@Override
	public void gather(Browser browser, List<String> startingURLs) {
		// TODO Auto-generated method stub
		Queue<Long> queue = new LinkedList<>();

		for (String url : startingURLs) {
			System.out.println("gather [" + url + "]");
			if (!urlToIndex.containsKey(url)) {
				long index = indexPage(url);
				queue.add(index);
				// System.out.println("queue [" + index + "]");
			}
		}

		while (!queue.isEmpty()) {
			// System.out.println(connect(queue,0));

			System.out.println("queue "+queue);
			String URLs = "";
			Long index = queue.poll();

			// long pageIndex = queue.poll().index;
			PageFile pf = pageDisk.get(index);
			System.out.println("dequeued " + pf);

			if (browser.loadPage(pf.url)) {
				List<String> URLS = browser.getURLs();
				// for (String url : list) {

				System.out.println("urls" + " " + connect(URLS, 0));
				// Long index = urlToIndex.get(url);
				for (String url : URLS) {
					if (!urlToIndex.containsKey(url)) {
						long INDEX = indexPage(url);
						queue.add(INDEX);
					}
				}
				Set<Long> pageSet = new HashSet<Long>();
				for (String url : URLS) {
					long index1 = urlToIndex.get(url);
					PageFile pageFile = pageDisk.get(index1);
					if (!pageSet.contains(pageFile)) {
						pageSet.add(index1);
					}
				}

				for (Long pageindex : pageSet) {
					pageDisk.get(pageindex).incRefCount();
					System.out.println("inc ref " + pageDisk.get(pageindex));
				}
				// pageSet.clear();
			}
			List<String> wordList = browser.getWords();
			System.out.println("words" + " " + connect(wordList, 0));
			for (String word : wordList) {
				long indexword = indexWord(word);
				List<Long> list4 = wordDisk.get(wordToIndex.get(word));
				if (!list4.contains(pf.index)) {
					// wordDisk.put(indexword, list);
					// wordToIndex.put(word, indexword);
					list4.add(pf.index);

					System.out.println("add page " + indexword + "(" + word + ")" + connect(list4, 0));

				}

			}
			
		}
		System.out.println("pageDisk");
		String disks = "";
		for (Entry<Long, PageFile> entry: pageDisk.entrySet()) { 
			disks+=entry.toString()+", ";
		}System.out.println("{"+disks.substring(0, disks.length()-2)+"}");
		disks="";
		System.out.println("urlToIndex");
		for (Map.Entry<String, Long> entry : urlToIndex.entrySet()) {
			disks+=entry.toString()+", ";
		}System.out.println("{"+disks.substring(0, disks.length()-2)+"}");
		System.out.println("wordDisk");
		disks="";
		for ( Entry<Long, List<Long>> entry:wordDisk.entrySet()) {
			disks+=entry.toString()+", ";
		}System.out.println("{"+disks.substring(0, disks.length()-2)+"}");
		System.out.println("wordToIndex");
		disks="";
		for ( Entry<String,Long> entry:wordToIndex.entrySet()) {
			disks+=entry.toString()+", ";
		}System.out.println("{"+disks.substring(0, disks.length()-2)+"}");
		urlToIndex.write(pageDisk);
	    wordToIndex.write(wordDisk);

	}
	

	

	@Override
	public String[] search(List<String> keyWords, int numResults) {
		// TODO Auto-generated method stub
		return new String[0];
	}

	private String connect(List cl, int t) {
		if (cl.isEmpty()) {
			return "[]";
		}
		String connected = "[";
		int i = 0;
		for (Object string : cl) {
			connected += string + ", ";
			i++;

			switch (t) {
			case 1:
				if (i % 4 == 0)
					connected += "\n";

			}
		}

		return connected.substring(0, connected.length() - 2) + "]";
	}

}
