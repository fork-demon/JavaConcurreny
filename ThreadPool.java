package cuncurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ThreadPool {
	
	private BlockingQueue<Runnable> taskQueue = null;
	private List<ProcessorThread> threadList = new ArrayList<ProcessorThread>();
	private volatile boolean isStopped = false;
	private int maxtTask = 10;
	
	public ThreadPool(int capacity) {

		taskQueue = new ArrayBlockingQueue<Runnable>(maxtTask);

		for (int i = 0; i < capacity; i++) {
			threadList.add(new ProcessorThread(taskQueue));
		}
		for (ProcessorThread t : threadList) {
			t.start();
		}
	}
	
	public void addTask(Runnable task){
		if(isStopped)
			throw new IllegalStateException("Threadpool is stopped");
				taskQueue.add(task);
		
	}
	
	public void shutDown(){
		isStopped = true;
		for (ProcessorThread t : threadList) {
			t.stopThread();
		}
		
	}
	
	
}

class ProcessorThread extends Thread{
	
	private BlockingQueue<Runnable> sharedQueue = null;
	
	private volatile boolean isStopped = false;
	
	public ProcessorThread(BlockingQueue<Runnable> queue) {
		sharedQueue = queue;
	}
	
	@Override
	public void run(){
		
		while(!isStopped){
			
			try {
				Runnable task = sharedQueue.take();
				task.run();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
	}
	public boolean getIsRunning(){
		
		return isStopped;
	}
	
	public void stopThread(){
		isStopped = true;
		Thread.currentThread().interrupt();
		
	}
	
}