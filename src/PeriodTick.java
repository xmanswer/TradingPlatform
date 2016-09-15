import java.util.*;

public class PeriodTick extends TickBar {
	public double sma;
	public double ema;
	public double emaema;
	public double dema;

	//construct from all parameters
	public PeriodTick(Calendar timestamp, 
			double o, double c, double h, double l) {
		super(timestamp, o, c, h, l);
	}
	
	//construct directly from the tickBar
	public PeriodTick(TickBar tickBar) {
		super(tickBar.timestamp, tickBar.openPrice, 
				tickBar.closePrice, tickBar.highPrice, tickBar.lowPrice);
	}
}
