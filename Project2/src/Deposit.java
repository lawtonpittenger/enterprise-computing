/*   Name: Lawton Pittenger   
     Course: CNT 4714 Spring 2023 
     Assignment title: Project 2 – Synchronized, Cooperating Threads Under Locking 
     Due Date: February 12, 2023 
*/ 


// Class that will implement the running of deposit threads
import java.util.Random;
import java.lang.Thread;

// Deposit class
public class Deposit implements Runnable
{
	// Data types that hold passed parameters from Deposit method call
	private String name = "";
	private BankAccount bankAccount = new BankAccount();

	// Public constructor that takes passed params from Deposit call
	public Deposit (BankAccount bankAccount, String name)
	{
		this.bankAccount = bankAccount;
		this.name = name;
	}

	// Run statement containing infinite while loop
	public void run()
	{
		// Calculates a random sleep value ranging from 3000 - 6500  milliseconds
		int randomSleep = (int)(Math.random() * 6500 + 3000);
		
		try
		{
			while(true)
			{
				bankAccount.Deposits(name);
				// Put thread to sleep for randomSleep amount of time
				Thread.sleep(randomSleep);
			}
		}
		catch(InterruptedException e)
		{
			System.out.println("Interrupted Exception has been caught");
		}
	}
}