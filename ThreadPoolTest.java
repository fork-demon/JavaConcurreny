package cuncurrency;

import java.util.ArrayList;
import java.util.List;

public class ThreadPoolTest {
	
	public static void main(String[] args) {
		
		List<Runnable> taskList = new ArrayList<Runnable>();
		
		MyTask1 task1 = new MyTask1();
		MyTask2 task2 = new MyTask2();
		MyTask3 task3 = new MyTask3();
		
		taskList.add(task1);
		taskList.add(task2);
		taskList.add(task3);
		
		ThreadPool pool = new ThreadPool(2);
		
		for (Runnable runnable : taskList) {
			pool.addTask(runnable);
		}
		
		
		
		
	}
	
	private static class MyTask1 implements Runnable{

		public void run() {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(" Task 1 is running");
			
		}
		
	}
	private static class MyTask2 implements Runnable{

		public void run() {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(" Task 2 is running");
			
		}
		
	}
	private static class MyTask3 implements Runnable{

		public void run() {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(" Task 3 is running");
			
		}
		
	}

}
