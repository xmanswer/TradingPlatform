import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


public class Utilities {
	//get time stamp based on string
	public static Calendar getTimestamp(String time) {
		String[] str = time.split("[^0-9.]");
		int[] t = new int[6];
		int i = 0;
		for(String s : str) {
			if(!s.equals("")) {
				t[i++] = Integer.parseInt(s);
			}
		}
		Calendar timestamp = Calendar.getInstance();
		timestamp.set(t[2], t[0], t[1], t[3], t[4], t[5]);
		return timestamp;
	}
	
	//parse tick data from csv file given the format
	public static ArrayList<Tick> parseTickData(String file) 
			throws FileNotFoundException {
		ArrayList<Tick> list = new ArrayList<Tick>();
		FileInputStream in = new FileInputStream(file);
		Scanner scanner = new Scanner(in);
		int i = 0;
		String prevtime = "";
		while(scanner.hasNext()) {
			if(i != 0) {
				String[] line = scanner.nextLine().split(",");
				if(line.length == 0) break;
				Calendar timestamp = getTimestamp(line[0]);
				// if the time string is the same, need to set 
				// millisecond to maintain the time in relative order
				if(!line[0].equals(prevtime)) i = 1;
				timestamp.set(Calendar.MILLISECOND, i);
				prevtime = line[0];
				double price = Double.parseDouble(line[1]);
				int share = Integer.parseInt(line[2]);
				list.add(new Tick(timestamp, price, share));				
			} else {
				scanner.nextLine();
			}
			i++;
		}
		return list;
	}
	
	//generate OHLC bars for given raw tick data
	public static ArrayList<TickBar> generateBars(ArrayList<Tick> ticks) {
		ArrayList<TickBar> list = new ArrayList<TickBar>();
		double o = ticks.get(0).price;
		double h = 0;
		double l = Double.MAX_VALUE;
		
		for(Tick tick : ticks) {
			h = Math.max(h, tick.price);
			l = Math.min(l, tick.price);
			TickBar tickBar = new TickBar(tick.timestamp, o, tick.price, h, l);
			list.add(tickBar);
		}
		return list;
	}
	
	//generate a list of PeriodTick based on a list of TickBars
	//calculating SMA, EMA and DEMA
	public static ArrayList<PeriodTick> movingAverages(ArrayList<TickBar> tickBars) {
		ArrayList<PeriodTick> list = new ArrayList<PeriodTick>();
		Queue<Double> q = new LinkedList<Double>();
		double prevSMA = 0;
		double prevEMA = 0;
		double prevEMAEMA = 0;
		for(int i = 0; i < tickBars.size(); i++) {
			TickBar tickBar = tickBars.get(i);
			double cp = tickBar.closePrice;
			if(i < Constants.PERIOD - 1) {
				q.add(cp);
				prevSMA += cp;
				prevEMA = (cp - prevEMA) * Constants.EMA_WEIGHT + prevEMA;
				prevEMAEMA = (prevEMA - prevEMAEMA) * Constants.EMA_WEIGHT + prevEMAEMA;
			} else {
				PeriodTick pt = new PeriodTick(tickBar);
				prevSMA += cp;
				prevEMA = (cp - prevEMA) * Constants.EMA_WEIGHT + prevEMA;
				prevEMAEMA = (prevEMA - prevEMAEMA) * Constants.EMA_WEIGHT + prevEMAEMA;
				pt.sma = prevSMA / Constants.PERIOD;
				pt.ema = prevEMA;
				pt.emaema = prevEMAEMA;
				pt.dema = 2 * pt.ema - pt.emaema;
				//maintain a sliding window of size PERIOD
				prevSMA = prevSMA - q.poll();
				q.add(cp);
				
				list.add(pt);
			}
		}
		return list;
	}
}
