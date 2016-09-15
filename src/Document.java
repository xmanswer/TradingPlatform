import java.io.*;
import java.util.*;

import net.sf.classifier4J.summariser.SimpleSummariser;


public class Document implements Comparable<Document>{
	public String fileName;
	public String fileDir;
	//public ArrayList<String> words;
	public HashMap<String, Integer> termFreq;
	public Calendar timestamp;
	
	public Document(String fileName, String fileDir) 
			throws IOException {
		this.fileName = fileName;
		this.fileDir = fileDir;
		this.termFreq = new HashMap<String, Integer>();
		//loadDoc();
		this.timestamp = getDocTime();
	}
	
	public Document(File file, String fileDir) 
			throws IOException {
		this(file.getName(), fileDir);
	}
	
	//dummpy document for range query
	public Document(Calendar timestamp) {
		this.timestamp = timestamp;
	}
	
	//get document time
	public Calendar getDocTime() {
		return null;
	}
	
	//load document into memory
	public void loadDoc() throws IOException {
		FileInputStream in = new FileInputStream(fileDir + '/' + fileName);
		Scanner scanner = new Scanner(in);
		while(scanner.hasNextLine()) {
			tokenize(scanner.nextLine());
		}
		in.close();
	}
	
	//load a line into memory and do processing
	public void loadLine(String line) {
		ArrayList<String> words = tokenize(line);
		for(String term : words) {
			addTermFreq(term, 1);
		}
		
		for(int i = 2; i <= Constants.MAX_NGRAM; i++) {
			ArrayList<String> ngrams = ngram(words, i);
			for(String term : ngrams) {
				addTermFreq(term, 1);
			}
		}
	}
	
	//update the term frequency in term dictionary
	public void addTermFreq(String term, int num) {
		if(termFreq.containsKey(term)) {
			termFreq.put(term, termFreq.get(term) + num);
		} else {
			termFreq.put(term, num);
		}
	}
	
	//simple tokenizer
	public ArrayList<String> tokenize(String line) {
		ArrayList<String> words = new ArrayList<String>();
		return words;
	}
	
	//stem tokens
	public String stem(String token) {
		return token;
	}
	
	//generate ngram, add to words
	public ArrayList<String> ngram(ArrayList<String> words, int n) {
		ArrayList<String> ngrams = new ArrayList<String>();
		return ngrams;
	}

	@Override
	public int compareTo(Document that) {
		return this.timestamp.before(that.timestamp) ? -1 : 1;
	}
}
