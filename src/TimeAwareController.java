import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;

public class TimeAwareController {

	public static void main(String[] args) throws FileNotFoundException {
		System.setOut(new PrintStream ("ticks.csv"));
		ArrayList<Tick> ticks = Utilities.parseTickData("C:\\Users\\minxu\\Desktop\\keesun\\Tick Sample\\Tick Sample\\7.16.csv");
		ArrayList<TickBar> tbs = Utilities.generateBars(ticks);
		ArrayList<PeriodTick> pts = Utilities.movingAverages(tbs);
		for(PeriodTick pt : pts) {
			System.out.println(pt.closePrice + "," + pt.sma + "," + pt.ema + "," + pt.dema);
		}
	}
}
