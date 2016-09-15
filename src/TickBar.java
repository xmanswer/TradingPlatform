import java.util.*;

public class TickBar {
	public Calendar timestamp;
	public double openPrice;
	public double closePrice;
	public double highPrice;
	public double lowPrice;
	
	public TickBar(Calendar t, double o, double c, double h, double l) {
		this.timestamp = t;
		this.openPrice = o;
		this.closePrice = c;
		this.highPrice = h;
		this.lowPrice = l;
	}
}
