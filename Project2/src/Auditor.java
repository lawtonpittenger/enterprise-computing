/*   Name: Lawton Pittenger   
     Course: CNT 4714 Spring 2023 
     Assignment title: Project 2 – Synchronized, Cooperating Threads Under Locking 
     Due Date: February 12, 2023 
*/ 


import java.util.Random;

public class Auditor implements Runnable {

	private static Random sleeptime = new Random();
	private BankAccount bankAccount = new BankAccount();
	String tName;
	
	public Auditor(BankAccount shared, String name) {
		bankAccount = shared;
		tName = name;
	}
	public void run()
	{
		//calculates a random sleep value ranging from .5-5 seconds
		int randomSleep = (int)(Math.random() * 8500 + 500);
		try
		{
			while(true)
			{
				bankAccount.AuditorMethod("AUDITOR");
				//put thread to sleep for randomSleep amount of time
				Thread.sleep(randomSleep);
			}
		}
		catch(InterruptedException e)
		{
			System.out.println("Interrupted Exeception has been caught");
		}
	}
}
