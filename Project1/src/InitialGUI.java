/*
 * Name: Lawton Pittenger
 * Date: 1/15/2023
 * Course: CNT 4714 - Spring 2023
 * Assignment title: Program 1 - Event-driven Enterprise Simulation
*/


// Import required packages. s
import javax.swing.*;				
import javax.swing.border.EmptyBorder;
import java.awt.*;					
import java.io.*;					
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;			
import java.awt.event.*;			


public class InitialGUI
{
	
	// Declare required Scanner, Strings, Integers, Doubles, JTextFields, and JButtons.
	private static Scanner csvReader;
	String filePath = "inventory.txt", itemSearch = "", title = "", stock = "", price = "", viewOrderString = "", invoice = "", currentDate = "", transactionString = "", transactionHelperString = "", permutation = "";
	Integer numOrderCurrent = 0, numSpecificItem = 0, tax = 6;
	Double subTotal = 0.0, discount = 0.0, pWithDiscount = 0.0;
	JTextField itemInfoField, itemIDField, totalSpecificField;
	JButton processItem, purchaseItem;
	
	// Formats decimals to have a ones, tenths, and hundreths place.
	DecimalFormat twoPlaces = new DecimalFormat("0.00");
	
	// Formats discount decimal to a minimum of a ones and tenths places, and any non zero hundreths place.
	DecimalFormat transactionFileDiscount = new DecimalFormat("0.0#");

	
	public static void main(String[] args)
	{
		new InitialGUI();		
	}

	public InitialGUI()
	{
		// Create Frame
		JFrame StoreFrame = new JFrame();
		
		// Close frame when program is exited
		StoreFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		StoreFrame.setTitle("NileStore");
		StoreFrame.setSize(900,200);
		StoreFrame.setLocationRelativeTo(null);
		
		// Create Panel
		JPanel NileStorePanel = new JPanel();
		NileStorePanel.setLayout(new GridLayout(5,1,10,0));
		NileStorePanel.setBorder(new EmptyBorder(10,10,0,10));
		// Create Button Panel
		JPanel NileStoreButtonsPanel = new JPanel();

		// Item ID label and text field
		JLabel itemIDLabel = new JLabel("Enter item ID for Item #1: ");
		itemIDField = new JTextField(30);
		
		// Number of specific items label and text field
		JLabel totalSpecificLabel = new JLabel("Enter quantity for Item #1: ");
		totalSpecificField = new JTextField(30);
		
		// Item information label and text field
		JLabel itemInfoLabel = new JLabel("Details for Item #1: ");
		itemInfoField = new JTextField(30);
		
		// Sub total label and text field
		JLabel subtotalLabel = new JLabel("Order subtotal for 0 item(s): ");
		JTextField subtotalField = new JTextField(30);
		
		// Process Item
		processItem = new JButton("Find Item #1");
		
		// Confirm Item
		purchaseItem = new JButton("Purchase Item #1");
		
		// View Order
		JButton viewOrder = new JButton("View Current Order");
		
		// Finish Order
		JButton finishOrder = new JButton("Complete Order - Check Out");
		
		// New Order
		JButton newOrder = new JButton("Start New Order");
		
		// Exit
		JButton exit = new JButton("Exit (Close App)");

		// Add all NileStorePanel content to the Panel
		NileStorePanel.add(itemIDLabel);
		NileStorePanel.add(itemIDField);
		NileStorePanel.add(totalSpecificLabel);
		NileStorePanel.add(totalSpecificField);
		NileStorePanel.add(itemInfoLabel);
		NileStorePanel.add(itemInfoField);
		NileStorePanel.add(subtotalLabel);
		NileStorePanel.add(subtotalField);

		// Add all NileStoreButtonsPanel content to Panel
		NileStoreButtonsPanel.add(processItem);
		NileStoreButtonsPanel.add(purchaseItem);
		NileStoreButtonsPanel.add(viewOrder);
		NileStoreButtonsPanel.add(finishOrder);
		NileStoreButtonsPanel.add(newOrder);
		NileStoreButtonsPanel.add(exit);

		// Add all Panels to Frame
		StoreFrame.add(NileStorePanel, BorderLayout.NORTH);
		StoreFrame.add(NileStoreButtonsPanel, BorderLayout.SOUTH);

		// Setting Frame to visible
		StoreFrame.setVisible(true);

		// Disable buttons and Textfields that are required on start-up
		purchaseItem.setEnabled(false);
		itemInfoField.setEditable(false);
		subtotalField.setEditable(false);
		finishOrder.setEnabled(false);
		viewOrder.setEnabled(false);
		
		// Find Item ActionListener
		processItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//obtaining the number of specific items for the current item
				numSpecificItem = Integer.parseInt(totalSpecificField.getText());	

				if(numSpecificItem < 5)
				{
					discount = 1.0;
				}
				
				if(numSpecificItem >= 5 && numSpecificItem <= 9)
				{
					discount = 0.90;
				}
				
				if(numSpecificItem >= 10 && numSpecificItem <= 14)
				{
					discount = 0.85;
				}
				
				if(numSpecificItem >= 15)
				{
					discount = 0.80;
				}
				
				csvSearch(itemIDField.getText(), filePath);
			}
		});
		
		// Purchase Item ActionListener
		purchaseItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				finishOrder.setEnabled(true);
				viewOrder.setEnabled(true);
				numOrderCurrent++;
				//string that constantly has item info of current item append to it as each item is confirmed
				viewOrderString = viewOrderString + numOrderCurrent + ". " + itemInfoField.getText() + "\n";
				subTotal = subTotal + pWithDiscount;
				subtotalField.setText("$" + twoPlaces.format(subTotal));
					processItem.setEnabled(true);
					purchaseItem.setEnabled(false);
					processItem.setText("Find Item " + (numOrderCurrent+1));
					purchaseItem.setText("Purchase Item " + (numOrderCurrent+1));
					itemIDLabel.setText("Enter item ID for Item #" + (numOrderCurrent+1) + ": ");
					totalSpecificLabel.setText("Enter quantity for Item #" + (numOrderCurrent+1) + ": ");
					itemInfoLabel.setText("Item #" + numOrderCurrent + " info: ");
					subtotalLabel.setText("Order subtotal for " + numOrderCurrent + " item(s): ");
					itemIDField.setText("");
					totalSpecificField.setText("");
				
				// Confirmation message of the item being confirmed to the order
				JOptionPane.showMessageDialog(null, "Item #" + numOrderCurrent + " accepted. Added to your cart.");
			}
		});
		
		// View Order ActionListener
		viewOrder.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// Create pane to display up-to-date items in cart
				// Populates a message box that shows the current items that have already been confirmed
				JOptionPane.showMessageDialog(null, viewOrderString);
			}
		});
		
		// Finish Order ActionListener
		finishOrder.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// Disable finish order button once it is pressed
				finishOrder.setEnabled(false);	
				// Disable view order button once finish order has been pressed
				viewOrder.setEnabled(false);
				// Disable find item button once finish order has been pressed
				processItem.setEnabled(false);
				// Date format for invoice
				currentDate = new SimpleDateFormat("MM/dd/YY, h:mm:ss a zzz").format(new Date());
				// Permutation format for transactions file
				permutation = new SimpleDateFormat("ddmmyyyyhhmmss").format(new Date());				
				invoice = "Date: " + currentDate 
						+ "\n\n" 
						+ "Number of line items: " 
						+ numOrderCurrent + "\n\n" 
						+ " Item# / ID / Title / Price / Qty / Disc % / Subtotal:" 
						+ "\n\n" + viewOrderString + "\n\n\n" 
						+ "Order subtotal: $" + twoPlaces.format(subTotal) + "\n\n" 
						+ "Tax rate: 	" + tax + "%" + "\n\n" 
						+ "Tax amount:  $" + twoPlaces.format(((subTotal * tax)/100)) 
						+ "\n\n" + "ORDER TOTAL:  $" 
						+ twoPlaces.format((subTotal + ((subTotal * tax)/100))) + "\n\n" 
						+ "Thanks for shopping at Nile Dot Com!"; 
				// Populate message box with invoice
				JOptionPane.showMessageDialog(null, invoice);
				
					String [] tL = transactionHelperString.split("\n");
					
					for(Integer x = 0; x < numOrderCurrent; x++)
					{
						transactionString = transactionString + permutation + ", " + tL[x] + " " + currentDate + "\n";
					}
				
				// Complete the order by adding all contents to transaction log file
				try {
					FileWriter fW = new FileWriter("transactions.txt", true);
					BufferedWriter bW = new BufferedWriter(fW);
					PrintWriter out = new PrintWriter(bW);
					out.print(transactionString);
					out.close();
					} 
				catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		//New Order ActionListener
		newOrder.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// Clear fields
				itemIDField.setText("");
				totalSpecificField.setText("");
				itemInfoField.setText("");
				subtotalField.setText("");
				// Clear view order
				viewOrderString = "";
				// Clear invoice
				invoice = "";
				// Clear transaction string
				transactionString = "";
				// Clear transaction helper string
				transactionHelperString = "";
				// Reset buttons and labels to first item
				processItem.setText("Find Item 1");
				purchaseItem.setText("Confirm Item 1");
				itemIDLabel.setText("Enter item ID for Item #1: ");
				totalSpecificLabel.setText("Enter quantity for Item #1: ");
				itemInfoLabel.setText("Item #1 info: ");
				subtotalLabel.setText("Order subtotal for 0 item(s): ");
				// Reset counters to 1 or 0 depending on what it is
				numOrderCurrent = 0;
				// Enable and disable needed buttons, fields and labels
				processItem.setEnabled(true);
				purchaseItem.setEnabled(false);
				itemIDField.setEditable(true);
				totalSpecificField.setEditable(true);
				finishOrder.setEnabled(false);
				viewOrder.setEnabled(false);
				subTotal = 0.0;
				pWithDiscount = 0.0;
			}
		});
		
		// Exit ActionListener
		exit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
	}
	
	// CSV Search
	public void csvSearch(String itemID, String filePath)
	{
		boolean exists = false;
		String fileLine = "";

		try
		{
			csvReader = new Scanner(new File(filePath));
			while(csvReader.hasNextLine() && !exists)
			{
				// Read passed file line by line
				fileLine = csvReader.nextLine();										
				
				// Split the passed line into separate strings using a comma as the delimiter
				String[] tokens = fileLine.split(",");									
				
				// The first string of each entry is the item ID which is equal to the first array element
				itemSearch = tokens[0];													

				// Check if the passed lines first element is equal to the desired item id passed
				if(itemSearch.equals(itemID))											
				{
					exists = true;
					title = tokens[1];
					stock = tokens[2];
					price = tokens[3];
					
					if(Arrays.asList(stock).contains(" false")) {
						JOptionPane.showMessageDialog(null, "Sorry... that item is out of stock, please try another item");
						itemIDField.setText("");
						totalSpecificField.setText("");
					}
					else {
						// Make pWithDiscount limited to two decimal places
						pWithDiscount = (numSpecificItem) * (discount) * (Double.parseDouble(price));
						itemInfoField.setText(itemSearch + title +  " $" + price + " " + numSpecificItem +" " + (Math.round(100 * (1 - discount))) +"% " + "$" + twoPlaces.format(pWithDiscount));
						// Change discount from percentage to decimal in transactionHelperString
						transactionHelperString = transactionHelperString + itemSearch + ", " + title + ", " + price + ", " + numSpecificItem + ", " + transactionFileDiscount.format((1 - discount)) + ", " + "$" + twoPlaces.format(pWithDiscount) +","+ "\n";
						// Disable find item and purchase item buttons
						processItem.setEnabled(false);
						purchaseItem.setEnabled(true);
					}
				}
			}
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		// Account for item not being in file
		if(exists == false)
		{
			JOptionPane.showMessageDialog(null, "Item ID " + itemID + " not in file");
			itemIDField.setText("");
			totalSpecificField.setText("");
		}
		
	}
}