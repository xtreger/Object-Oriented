public class MyThread extends Thread {
	
	 Counter counter; 
	 int noOfIn;
	 
	public MyThread(Counter c, int x) {
		this.counter = c;
		this.noOfIn = x;
	}

	public synchronized void run() {
		for(int y=0; y<noOfIn;y++) {
			counter.inc();
		}
	}
}
