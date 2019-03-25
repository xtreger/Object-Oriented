public class Counter {
	int count;
	public synchronized void inc() {
		count=count+1;
	}
	public synchronized int getCount() {
		return count;
	}
}
