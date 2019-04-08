import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumer{


  public static void main(String [] args) throws InterruptedException
  {
     Buffer b = new Buffer(1);
     Producer p = new Producer(b);
     Consumer c = new Consumer(b);
     p.start();
     c.start();

  }
}

class Buffer {
   private char [] buffer;
   private int count = 0, in = 0, out = 0;
   ReentrantLock lock = new ReentrantLock();
   final Condition condition  = lock.newCondition(); 

   Buffer(int size)
   {
      buffer = new char[size];
   }
 
   public void Put(char c) throws InterruptedException {
     lock.lock();
	   while(count == buffer.length) {
		  try {
			  condition.await();
				}catch(InterruptedException e) {	  
				  }
     };
     System.out.println("Producing " + c + " ...");
     buffer[in] = c; 
     in = (in + 1) % buffer.length; 
     count++;
     condition.signalAll();
     lock.unlock();
   }
    
   public char Get() throws InterruptedException {
	  lock.lock(); 
	  while (count == 0) {
		  try {
		  condition.await();
		  }catch(InterruptedException e) {
			  
		  }
     }
     char c = buffer[out]; 
     out = (out + 1) % buffer.length;
     count--;
     System.out.println("Consuming " + c + " ...");
     condition.signalAll();
     lock.unlock();
     return c;
   }
}
     

class Producer extends Thread {
   private Buffer buffer;
     
   Producer(Buffer b) { buffer = b; }
   public void run() {
	   //produce first 10 characters of the alphabet
     for(int i = 0; i < 10; i++) {
        try {
			buffer.Put((char)('A'+ i%26 ));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} }
   }
}    

class Consumer extends Thread {
   private Buffer buffer;
   
   Consumer(Buffer b) { buffer = b; }
   public void run() {
     for(int i = 0; i < 10; i++) {
        try {
			buffer.Get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} }
   }
}   

