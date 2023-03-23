/*   Name: Lawton Pittenger   
     Course: CNT 4714 Spring 2023 
     Assignment title: Project 2 – Synchronized, Cooperating Threads Under Locking 
     Due Date: February 12, 2023 
*/ 

//class that will keep track of the current balance of the bank account and contains the functionality of deposit and withdraw threads
import java.lang.Thread;
import java.text.DateFormat;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.*;
import java.io.*;
import java.lang.*;


public class BankAccount
{
	//int that holds the current balance
	private int currentBalance;
	//int that holds the transaction number
	private int transactionNumber = 0;
	//creating a lock
	private Lock lock = new ReentrantLock();
	//creating condition for overdrafts
	private Condition overDraft = lock.newCondition();
	private static final int DEPOSIT_ALERT_LEVEL = 350;
	private static final int WITHDRAWAL_ALERT_LEVEL = 75;
	private int auditTransactionNumber = 0;

	//constructor to initialize beginning balance to zero
	public BankAccount()
	{
		currentBalance = 0;
	}
	
	
	// Method used to log flagged transactions made against the bank account
	public void flagged_transaction(int value, String transaction_thread, String transaction_type) {
		Date today = new Date();
		DateFormat frenchDateTime = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG, Locale.FRANCE);
		FileWriter transactionFile;
		PrintWriter aPrintWriter = null;
		StringBuilder output_to_FTF = new StringBuilder();
		try
		{
			transactionFile = new FileWriter("transactions.txt", true);
			aPrintWriter = new PrintWriter(transactionFile);
			String timestampID = frenchDateTime.format(today);
			if (transaction_type.equals("D"))
			{
				output_to_FTF.append("\n Depositor " + transaction_thread + " issued deposit of $" + value + ".00 at " + timestampID + " Transaction Number: " + transactionNumber);
			}
			else {
				output_to_FTF.append("\n 		Withdrawal " + transaction_thread + " issued withdrawal of $" + value + ".00 at " + timestampID + " Transaction Number: " + transactionNumber);
			}
			aPrintWriter.print(output_to_FTF.toString());	
		}
		catch(IOException ioException) {
			System.out.println("\nError: Problem writing to transaction file.\n");
		}
		finally {
			aPrintWriter.close();
		}
	}

	// Method used to deposit money into the bank account
	public void Deposits(String name)
	{
		//generating a random deposit ranging from 1-500 dollars
		int deposit = (int)(Math.random() * 500 + 1);
		//obtaining the lock
		lock.lock();
		//attempting to make a deposit
		try
		{
			currentBalance = currentBalance + deposit;
			transactionNumber++;
			System.out.println(name + " deposits $" + deposit + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + " (+) Balance is $" + currentBalance + "\t\t\t\t\t\t" + (transactionNumber));
			if(deposit >= DEPOSIT_ALERT_LEVEL) {
				System.out.println("\n* * * Flagged Transaction - Depositor " + name + " Made A Deposit In Excess Of $" + DEPOSIT_ALERT_LEVEL + ".00 USD - See Flagged Transaction Log.\n");
				flagged_transaction(deposit, name, "D");
			}
			
			//signaling all blocked withdraw threads that a deposit has been made
			overDraft.signalAll();
		}
		catch(Exception e)
		{
			System.out.println("Exception thrown depositing");
		}
		finally
		{
			//release lock
			lock.unlock();
		}
	}

	// Method for withdrawing money from bank account
	public void Withdraws(String name)
	{
		//generating a random withdraw ranging from 1-99 dollars
		int withdraw = (int)(Math.random() * 99 + 1);
		//obtaining the lock
		lock.lock();
		//attempting to make a withdraw
		try
		{
			//checking if an overdraft will occur
			//if overdraft wont occur
			if(currentBalance >= withdraw)
			{
				currentBalance = currentBalance - withdraw;
				transactionNumber++;
				System.out.println("\t\t\t\t\t\t\t" + name + " withdraw $" + withdraw + "\t\t\t\t\t\t\t" + " (-) Balance is $" + currentBalance + "\t\t\t\t\t\t" + (transactionNumber));
			}

			//if an overdraft will occur
			else
			{
				System.out.println("\t\t\t\t\t\t\t" + name + " withdraws $" + withdraw + "\t\t\t\t\t\t\t" + "(****** WITHDRAWAL BLOCKED - INSUFFICIENT FUNDS!!!)");
				overDraft.await();
			}
				if(withdraw >= WITHDRAWAL_ALERT_LEVEL) {
					System.out.println("\n* * * Flagged Transaction - Withdrawal " + name + " Made A Withdrawal In Excess Of $" + WITHDRAWAL_ALERT_LEVEL + ".00 USD - See Flagged Transaction Log.\n");
					flagged_transaction(withdraw, name, "W");
				}
		}
		catch(InterruptedException e)
		{
			System.out.println("Interrupt Exception has been caught");
		}
		finally
		{
			//release lock
			lock.unlock();
		}
	}
	
	// Method used to periodically audit bank account balance 
	public void AuditorMethod(String name)
	{
		lock.lock();
		try
		{
			System.out.println("\n ***************************************************************************************************************************************************************************************  "
					+ "\n\n"
					+ "\t\t\t\t\t\tAUDITOR FINDS CURRENT ACCOUNT BALANCE TO BE: $" + currentBalance
					+ "\t\t Number of transactions since last audit is: " + (transactionNumber - auditTransactionNumber)
					+ "\n\n ***************************************************************************************************************************************************************************************    ");
			auditTransactionNumber = transactionNumber;
		}
		finally
		{
			//Release lock
			lock.unlock();
		}
	}
}