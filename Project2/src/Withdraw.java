/*   Name: Lawton Pittenger   
     Course: CNT 4714 Spring 2023 
     Assignment title: Project 2 – Synchronized, Cooperating Threads Under Locking 
     Due Date: February 12, 2023 
*/ 


//java class that will implement the frunning of withdraw threads
import java.util.Random;
import java.lang.Thread;

//Withdraw class
public class Withdraw implements Runnable
{
	private String name = "";
	private BankAccount bankAccount = new BankAccount();

	//public constructor that takes passed params from Withdraw call
	public Withdraw (BankAccount bankAccount, String name)
	{
		this.bankAccount = bankAccount;
		this.name = name;
	}

	//run statement containing infinite loop
	public void run()
	{
		//calculates a random sleep value ranging from .5-5 seconds
		int randomSleep = (int)(Math.random() * 4500 + 500);
		try
		{
			while(true)
			{
				bankAccount.Withdraws(name);
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