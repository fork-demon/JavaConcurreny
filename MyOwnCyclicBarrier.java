package cuncurrency;

public class MyOwnCyclicBarrier {
	
	private int totalThreads;
	private int waitingThreads;
	private Runnable optionalRunEvent;
	
   public MyOwnCyclicBarrier(int maxThread) {
		
		totalThreads = maxThread;
		waitingThreads = maxThread;
		
	}
	
	public MyOwnCyclicBarrier(int maxThread, Runnable runEvent) {
		
		totalThreads = maxThread;
		waitingThreads = maxThread;
		
		optionalRunEvent = runEvent;
		
	}
	
	public synchronized void await(){
		
		waitingThreads--;
		
		if(waitingThreads >0){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			waitingThreads = totalThreads;
			notifyAll();
			if(null != optionalRunEvent){
				optionalRunEvent.run();
			}
			
		}
		
	}
	
	public static void main(String[] args) {
		
		MyOwnCyclicBarrier cyclicBarrier = new MyOwnCyclicBarrier(3);
		
		System.out.println("Cyclic barrier with "+cyclicBarrier.totalThreads +" barrier threads has been created");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MyOwnRunnable task = new MyOwnRunnable(cyclicBarrier);
		new Thread(task, "Thread1").start();
		new Thread(task, "Thread2").start();
		new Thread(task, "Thread3").start();
		
		
	}

}

class MyOwnRunnable implements Runnable{
	
	private MyOwnCyclicBarrier cyclicBarrier;
	
	public MyOwnRunnable(MyOwnCyclicBarrier barrier) {
		this.cyclicBarrier = barrier;
	}

	public void run() {
		System.out.println("waiting for barrier "+Thread.currentThread().getName());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cyclicBarrier.await();
		System.out.println("Reached the barrier point "+Thread.currentThread().getName());
		
	}
	
}
