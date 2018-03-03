package cuncurrency;

public class Worker implements Runnable {
	
	private static final int LIMIT = 10;
	private static final int DELAY = 4000;
	
	private String greetings;
	private boolean flag;
	
	public Worker(boolean b, String greet){
		greetings = greet;
		flag = b;
	}

	public void run() {
		for(int i =0; i<LIMIT ;i++){
			System.out.println(getTab() + Thread.currentThread().getName() + ": "+greetings +" "+i);
		}
		
	}
	
	private String getTab(){
		String tab = "\t\t";
		if(flag){
			tab = tab + "\t\t\t";
		}
		return tab;
	}
	
	public static void main(String[] args) {
		
		Runnable run1 = new Worker(false, "Hello GM");
		System.out.println(Thread.currentThread().getName() +"starting thread1");
		Thread t1 = new Thread(run1);
		t1.start();
		System.out.println(Thread.currentThread().getName()+" thread 1 started");
		
		t1.interrupt();
		
		Runnable run2 = new Worker(true, "Good Bye GM");
		System.out.println(Thread.currentThread().getName() +"starting thread2");
		Thread t2 = new Thread(run2);

		t2.start();
		System.out.println(Thread.currentThread().getName()+" thread 2 started");
		
	}

}
