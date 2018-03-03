package cuncurrency;


public class ProducerConsumer {
	
	public static void main(String[] args) {
		
		// shared queue which is shared between the producer and consumer
		Queue<String> sharedQueue = new Queue<String>(10);
		
		new Thread(new Producer<String>(sharedQueue)).start();
		new Thread(new Consumer<String>(sharedQueue)).start();
		
		
		
	}


	private static class Producer<T> implements Runnable{
		
		private Queue<T> queue;
		
		public Producer(Queue<T> sharedQueue) {
			queue = sharedQueue;
			
		}

		public void run() {
			int i=0;
			while(true){
				synchronized (queue) {
					// if the queue is full producer will wait until something is put in queue 
					// by consumer
					if(queue.isFull()){
						try {
							queue.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
				}else{
					System.out.println("producer produced"+i);
					queue.add("" +i);
					i++;
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 queue.notifyAll();
				}
				
				
			}
			
		}
		
	}
	}
	private static class Consumer<T> implements Runnable{
		
		private Queue<T> queue;
		
		public Consumer(Queue<T> sharedQueue) {
			queue = sharedQueue;
			
		}

		public void run() {
			while(true){
				synchronized (queue) {
					
					// if the queue is empty then consumer will wait until 
					if(queue.isEmpty()){
						try {
							queue.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
				}else{
					System.out.println("consumer received::"+queue.take());
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 queue.notifyAll();
				}
				
				
			}
			
		}
			
		}
		
	
}
	
}
/*
 * Implementation of queue for producer and consumer
 */
class Queue <T> {
	private volatile int size;
	private T[] array;
	private volatile int capacity;
	
	public Queue(int capacity) {
		
		array = (T[]) new Object[capacity];
		this.capacity = capacity;
		size =0;
	}
	
	public synchronized void add(Object obj){
		
		if(isFull()){
			throw new IllegalStateException("queue size is full");
		}
		else{
			array[size++] = (T) obj; 
		}
		
	}
	
	public synchronized  T take(){
		if(isEmpty())
			 throw new IllegalStateException("queue is empty");
		return array[size--];
	}
	
	public synchronized boolean isFull(){
		return size >= capacity -1;
	}
	public synchronized boolean isEmpty(){
		return size == 0;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public Object[] getArray() {
		return array;
	}
	public void setArray(Object[] array) {
		this.array = (T[]) array;
	}
	
	
	
	
}

