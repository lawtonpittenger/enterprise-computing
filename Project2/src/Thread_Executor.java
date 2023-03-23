/*   Name: Lawton Pittenger   
     Course: CNT 4714 Spring 2023 
     Assignment title: Project 2 – Synchronized, Cooperating Threads Under Locking 
     Due Date: February 12, 2023 
*/ 


// Class that will create and start all deposit, withdraw, and auditor threads needed
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class Thread_Executor
{
	public static final int MAX_AGENTS = 16;
	// Main method
	public static void main(String[] args)
	{
		
		ExecutorService application = Executors.newFixedThreadPool(MAX_AGENTS);
		BankAccount bankAccount = new BankAccount();
		
		try {
			// Print the initial column headings
			System.out.println("Deposit Agents" + "\t\t\t\t\t\t" + " Withdrawal Agents" + "\t\t\t\t\t\t\t" + "     Balance" + "\t\t\t\t\t\t\t" + "   Transaction Number" + "\n" + "---------------" + "\t\t\t\t\t\t" + "-------------------" + "\t\t\t\t\t\t\t" + "-----------------" + "\t\t\t\t\t\t" + "------------------------");
			
			// Execute all threads
			application.execute(new Withdraw(bankAccount, "Agent WT3")); ;
			application.execute(new Withdraw(bankAccount, "Agent WT4"));
			application.execute(new Withdraw(bankAccount, "Agent WT5"));
			application.execute(new Withdraw(bankAccount, "Agent WT6"));
			application.execute(new Deposit(bankAccount, "Agent DT3"));
			application.execute(new Deposit(bankAccount, "Agent DT1"));
			application.execute(new Deposit(bankAccount, "Agent DT4"));
			application.execute(new Deposit(bankAccount, "Agent DT2"));
			application.execute(new Withdraw(bankAccount, "Agent WT8"));
			application.execute(new Withdraw(bankAccount, "Agent WT7"));
			application.execute(new Withdraw(bankAccount, "Agent WT9"));
			application.execute(new Deposit(bankAccount, "Agent DT5"));
			application.execute(new Withdraw(bankAccount, "Agent WT1"));
			application.execute(new Withdraw(bankAccount, "Agent WT2"));
			application.execute(new Withdraw(bankAccount, "Agent WT10"));
			application.execute(new Auditor(bankAccount, "AUDITOR"));
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
		application.shutdown();
	}
}