import java.util.*;

public class Features {
	public static final int DOC_FEAT = 10;
	public static final int TICKBAR_FEAT = 4;
	public static final int PERIOD_FEAT = 3;
	public static final int DOC_INTERVAL_TYPE = 8;
	public double[] features;
	public Calendar timestamp;
	
	public Features() {
		features = new double[DOC_FEAT * DOC_INTERVAL_TYPE 
		                      + TICKBAR_FEAT + PERIOD_FEAT];
	}

	//extract tick bar features
	public void extractTickBar(TickBar tickBar) {
		timestamp = tickBar.timestamp;
		features[0] = tickBar.closePrice;
		features[1] = tickBar.openPrice;
		features[2] = tickBar.highPrice;
		features[3] = tickBar.lowPrice;
	}
	
	//extract period tick features
	public void extractPeriodTick(PeriodTick pt) {
		features[4] = pt.sma;
		features[5] = pt.ema;
		features[6] = pt.dema;
	}
	
	//extract nlp features from doc summations with different intervals
	public void extractNlpFeatures(DocumentSummation[] docSums) {
		int offset = TICKBAR_FEAT + PERIOD_FEAT;
		for(DocumentSummation docSum : docSums) {
			extractDocFeat(offset, docSum);
			offset += DOC_FEAT;
		}
	}
	
	//extract nlp features from one doc summation
	public void extractDocFeat(int offset, DocumentSummation docSum) {
		
	}
	
	//one-click generate features
	public void getFeatures(DocumentSummation[] docSums, 
			TickBar tickBar, PeriodTick pt) {
		extractTickBar(tickBar);
		extractPeriodTick(pt);
		extractNlpFeatures(docSums);
	}
}
