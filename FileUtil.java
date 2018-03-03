package cuncurrency;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
	
	static List<Thread> lstThread = new ArrayList<Thread>();
	public static void search(String path, String word){
		
		File file = new File(path);
		
		try {
			search(file,word);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private static void search(final File file, final String word) throws IOException{
		
		if(file.isDirectory()){
			File[] fileList = file.listFiles();
			
			if(null != fileList){
			for (final File file2 : fileList) {
				if(file2.isDirectory()){
					search(file2,word);
				}else{
					Thread t = new Thread(new Runnable() {
						
						public void run() {
							readWord(file2,word);
							
						}
					});
					lstThread.add(t);
					t.start();
					
					
				}
				
			}}
		}else{
			
			Thread t = new Thread(new Runnable() {
				
				public void run() {
					readWord(file,word);
					
				}
			});
			lstThread.add(t);
			t.start();
			
		}
		
	}
	private static void readWord(File file, String word){
		
		
		FileReader fR =null;
		BufferedReader br = null;
		try {
			fR = new FileReader(file);
			 br = new BufferedReader(fR);
		} catch (FileNotFoundException e) {
			
		}
		
		
		try{
		if(file.getName().endsWith(".txt")){
			String line = null;
		while((line = br.readLine())!= null ){
		//	System.out.println(file.getName() +" "+line);
			String[] words = line.split(" ");
			for (String fileWord : words) {
				
				if(fileWord.equalsIgnoreCase(word)){
					System.out.println(Thread.currentThread().getName());
					System.out.println("File :"+file +" : contains the word: "+word);
				}
			}
			
		}
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				br.close();
				
			} catch (Exception e) {
				
			}
			
		}
	}
	
	public static void main(String[] args) {
		
		String directory = "C:\\";
		long start = System.nanoTime();
		search(directory, "arvind");
		long end = System.nanoTime();
		
		for (Thread t : lstThread) {
			try {
				t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		System.out.println(" total time "+(end-start));
		System.out.println("done");
		
	}

}

class Processor implements Runnable{

	public void run() {
		// TODO Auto-generated method stub
		
	}
	
}