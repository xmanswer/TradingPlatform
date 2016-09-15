import java.io.File;
import java.io.IOException;
import java.util.*;


public class DocumentSource {
	public String documentFolder;
	private TreeSet<Document> documents; //maintain time order of docs
	private Iterator<Document> docIterator;
	
	public DocumentSource(String documentFolder) throws IOException {
		this.documentFolder = documentFolder;
		this.documents = new TreeSet<Document>();
		File folder = new File(documentFolder);
		File[] files = folder.listFiles();
		for(File file : files) {
			documents.add(new Document(file, documentFolder));
		}
		//assuming flat file structure, aka no sub folder
		this.docIterator = documents.iterator();
	}
	
	public Queue<Document> getDocQueueByRange(Calendar start, Calendar end) {
		return null;
	}
	
	//get list of docs lie in the time range
	public NavigableSet<Document> getDocSetByRange(Calendar start, 
			boolean includeStart, Calendar end, boolean includeEnd) {
		Document from = new Document(start);
		Document to = new Document(end);
		return documents.subSet(from, includeStart, to, includeEnd);
	}
	
	public boolean hasNextDoc() {
		return docIterator.hasNext();
	}
	
	public Document nextDoc() throws IOException {
		return docIterator.next();
	}

	public void resetIterator() {
		this.docIterator = documents.iterator();
	}
}
