import java.io.*;
import java.util.*;


public class Tick {
	public Calendar timestamp;
	public double price;
	public int share;
	
	public Tick(Calendar timestamp2, double price, int share) {
		this.timestamp = timestamp2;
		this.price = price;
		this.share = share;
	}
}




