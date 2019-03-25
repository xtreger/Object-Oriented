import java.util.Scanner;

public class CounterDriver {
	Counter counter = new Counter();
	int numT;
	int numR;
	public  CounterDriver() throws InterruptedException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter the number of threads you want to run.");
		numT = sc.nextInt();
		System.out.println("Please enter the number of times you want to run the threads.");
		numR = sc.nextInt();
		MyThread[] arr = new MyThread[numT];
		sc.close();
		for(int x= 0;x<numT;x++) {
			arr[x] = new MyThread(counter,numR);
			arr[x].start();
		}
		for(int x= 0;x<numT;x++) {
			arr[x].join();
		}
		System.out.println("Threads*Times: "+counter.getCount());
	}
	public static void main(String[]args) throws InterruptedException {
		new CounterDriver();
	}
}
