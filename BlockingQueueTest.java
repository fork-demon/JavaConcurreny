package cuncurrency;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingQueueTest {
	
	public static void main(String[] args) {
		
		// shared queue between consumer and producer
		BlockingQueue<String> sharedQueue = new LinkedBlockingQueue<String>();
		
		Producer producer = new Producer(sharedQueue);
		Consumer consumer = new Consumer(sharedQueue);
		
		new Thread(producer).start();
		new Thread(consumer).start();
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	

}

 class Consumer implements Runnable{
	 
	 private BlockingQueue<String> queue;
	 
	 public Consumer(BlockingQueue<String> commonQueue) {
		queue = commonQueue;
	}

	public void run() {
		
	// producer will block until queue becomes empty again, if the queue is full
		while(true){
			try {
				System.out.println("taking the task"+queue.take());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	 
 }
 
 class Producer implements Runnable{
	 
 private BlockingQueue<String> queue;
	 
	 public Producer (BlockingQueue<String> commonQueue) {
		queue = commonQueue;
	}

	public void run() {
		int i=0;
		// if the queue is empty, consumer will block until something comes into the queue.
		try{
		while(i<20){
			System.out.println("producing output"+i);
			queue.put(i +"");
			i++;
			Thread.sleep(1000);
		}
		}catch (InterruptedException ie){
			
		}
		
	}
	 
 }