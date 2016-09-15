import java.io.IOException;
import java.util.*;

public class DocumentSummation extends Document {
	public Calendar start;
	public Calendar end;
	//in minute, can be 15min, 30min, 1hr, 4hr, 12hr, 1day, 5day, 7day
	public int duration; 
	public HashMap<String, Integer> termFreq;
	
	public DocumentSummation(Calendar start, Calendar end, 
			int duration) throws IOException {
		super("", "");
		this.start = start;
		this.end = end;
		this.duration = duration;
	}
	
	
	@Override
	//get document time
	public Calendar getDocTime() {
		return end;
	}
	
	//get summation from a document source based on start and end date
	public void summationFromSource(DocumentSource source) throws IOException {
		ArrayList<String> entire = new ArrayList<String>();
		NavigableSet<Document> set = source.getDocSetByRange(start, true, end, true);
		for(Document doc : set) {
			doc.loadDoc();
			for(String term : doc.termFreq.keySet()) {
				addTermFreq(term, doc.termFreq.get(term));
			}
		}
	}
	
	//update this summation with a new start and end date
	public void shiftSummation(DocumentSource source, 
			Calendar newStart, Calendar newEnd) throws IOException {
		NavigableSet<Document> toRemove = source.getDocSetByRange(start, true, newStart, false);
		NavigableSet<Document> toAdd= source.getDocSetByRange(end, false, newEnd, true);
		//remove out-of-date documents from this summation
		for(Document doc : toRemove) {
			doc.loadDoc();
			for(String term : doc.termFreq.keySet()) {
				addTermFreq(term, -doc.termFreq.get(term));
			}
		}
		//add new docuemnts to this summation
		for(Document doc : toAdd) {
			doc.loadDoc();
			for(String term : doc.termFreq.keySet()) {
				addTermFreq(term, doc.termFreq.get(term));
			}
		}
		
		this.start = newStart;
		this.end = newEnd;
	}
}
