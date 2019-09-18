package server;

public class Timer {
	private long start;
	private long end;
	
	public void timeStart() {
		start = System.currentTimeMillis();
	}
	
	public void timeEnd() {
		end = System.currentTimeMillis();
	}
	public double Check() {
		return ( end - start );
	}
}