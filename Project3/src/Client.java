/*
Name: Lawton Pittenger
Course: CNT 4714 Spring 2023
Assignment title: Project 3 – A Two-tier Client-Server Application
Date: March 9, 2023
Class: Client.java
*/

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.mysql.cj.jdbc.*;

import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.awt.Color;


public class Client{
	private ResultSetTableModel tableModel = null;
	private Connection conn;
	private Boolean dbStatus = false;
	
	public static void main(String[] args)
	{
		new Client();
	}
	
	public Client() {
		//create Frame
		JFrame clientFrame = new JFrame();
		clientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		clientFrame.setTitle("SQL Client GUI");
		clientFrame.setSize(1200,750);
		clientFrame.setLocationRelativeTo(null);
		
		//create Panel for clientInfo/inputs
		JPanel clientPanel = new JPanel();
		clientPanel.setLayout(new GridLayout(4,2));
		
		//create clientPanel elements
		JLabel jdbcDriver = new JLabel("JDBC Driver");
		jdbcDriver.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		JLabel propertiesFile = new JLabel("Properties File");
		propertiesFile.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		JLabel dbURL = new JLabel("Database URL");
		dbURL.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		JLabel userName = new JLabel("Username");
		userName.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		JLabel userPWD = new JLabel("Password");
		userPWD.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		String[] clientProperties = {"client.properties", "root.properties"};
		String[] databaseURL = {"jdbc:mysql://localhost:3306/project3"};
		JComboBox propertiesFileChoice = new JComboBox(clientProperties);
		JComboBox dbURLList = new JComboBox(databaseURL);
		JTextField userNameField = new JTextField();
		JPasswordField userPWDField = new JPasswordField();
		
		
		//add elements to Panel
		clientPanel.add(propertiesFile);
		clientPanel.add(propertiesFileChoice);
		clientPanel.add(userName);
		clientPanel.add(userNameField);
		clientPanel.add(userPWD);
		clientPanel.add(userPWDField);
		
		//create commandPanel
		JPanel commandPanel = new JPanel();
		commandPanel.setLayout(new BorderLayout(20,2));
		
		//create elements for Panel
		JLabel sqlCommandLabel = new JLabel("Enter An SQL Command");
		sqlCommandLabel.setForeground(Color.BLUE);
		JTextArea sqlCommandsArea = new JTextArea(10,75);
		sqlCommandsArea.setLineWrap(true);
		sqlCommandsArea.setWrapStyleWord(true);
		
		//add elements to Panel
		commandPanel.add(sqlCommandLabel, BorderLayout.NORTH);
		commandPanel.add(sqlCommandsArea, BorderLayout.SOUTH);
		
		//create Panel to dbInfo
		JPanel dbInfoPanel = new JPanel();
		dbInfoPanel.setLayout(new BorderLayout(15,0));
		
		//create elements for dbInfo
		JLabel dbInfoLabel = new JLabel("Enter Database Information");
		dbInfoLabel.setForeground(Color.BLUE);
		
		//add elements to dbInfo
		dbInfoPanel.add(dbInfoLabel, BorderLayout.NORTH);
		dbInfoPanel.add(clientPanel, BorderLayout.SOUTH);
		//create Panel for north of GUI
		JPanel north = new JPanel();
		north.setLayout(new GridLayout(1,2));
		
		//add elements to panel
		north.add(dbInfoPanel);
		north.add(commandPanel);
		
		
		//create Panel for buttons
		JPanel clientButtonPanel = new JPanel();
		clientButtonPanel.setLayout(new GridLayout(1,4));
		clientButtonPanel.setBorder(new EmptyBorder(5,5,5,5));
		
		//create clientButtonPanel elements
		JButton connectButton = new JButton("Connect to Database");
		connectButton.setBackground(Color.BLUE);
		connectButton.setForeground(Color.YELLOW);
		JButton clearCommand = new JButton("Clear SQL Command");
		clearCommand.setBackground(Color.WHITE);
		clearCommand.setForeground(Color.RED);
		JButton executeCommand = new JButton("Execute SQL Command");
		executeCommand.setBackground(Color.GREEN);
		executeCommand.setForeground(Color.BLACK);
		JTextField connectionStatus = new JTextField("No Connection Now");
		connectionStatus.setEditable(false);
		connectionStatus.setBackground(Color.BLACK);
		connectionStatus.setForeground(Color.RED);
		
		//add elements to Panel
		clientButtonPanel.add(connectionStatus);
		clientButtonPanel.add(connectButton);
		clientButtonPanel.add(clearCommand);
		clientButtonPanel.add(executeCommand);
		
		//create Panel for Results
		JPanel resultPanel = new JPanel();
		resultPanel.setLayout(new BorderLayout(20,0));
		resultPanel.setBorder(new EmptyBorder(5,5,5,5));
		
		//create Result panel elements
		JLabel resultLabel = new JLabel("SQL Execution Result Window");
		resultLabel.setForeground(Color.BLUE);
		JTable resultTable = new JTable(tableModel);
		JButton clearResults = new JButton("Clear Result Window");
		clearResults.setBackground(Color.YELLOW);
		clearResults.setForeground(Color.BLACK);
		
		//add elements to panel
		resultPanel.add(resultLabel,BorderLayout.NORTH);
		resultPanel.add( new JScrollPane( resultTable ), BorderLayout.CENTER);
		resultPanel.add(clearResults, BorderLayout.SOUTH);
		
		//add panels to frame
		clientFrame.add(north, BorderLayout.NORTH);
		clientFrame.add(clientButtonPanel, BorderLayout.CENTER);
		clientFrame.add(resultPanel, BorderLayout.SOUTH);
		
		clientFrame.setVisible(true);
		
		//actionListener for connecting to database with button pressed
		
		connectButton.addActionListener(new ActionListener() {
            @SuppressWarnings("unlikely-arg-type")
			public void actionPerformed(ActionEvent event) {
                boolean usernameMisMatch; 
                boolean passwordMisMatch; 
                boolean userCredentialsOK = false;

                Properties properties = new Properties();
                FileInputStream fileIS = null;
                MysqlDataSource dSource = null;


                try {
                    if (conn != null)
                        conn.close();

                    connectionStatus.setText("No Connection.");

                    try {
                        fileIS = new FileInputStream((String) propertiesFileChoice.getSelectedItem());
                        properties.load(fileIS);
                        dSource = new MysqlDataSource();
                        dSource.setURL(properties.getProperty("MYSQL_DB_URL"));
                        char[] pass = userPWDField.getPassword();
                        String passString = new String(pass);

                        if (userNameField.getText().equals((String) properties.getProperty("MYSQL_DB_USERNAME"))) {
                            usernameMisMatch = false;
                            boolean userLoginWorks = true;
                            
                            if (passString.equals(properties.getProperty("MYSQL_DB_PASSWORD"))) {
                                boolean passwordNoMatch = false;
                                userCredentialsOK = true;
                            }
                        }

                        

                        if (userCredentialsOK) {
                            dSource.setUser(properties.getProperty("MYSQL_DB_USERNAME"));
                            dSource.setPassword(properties.getProperty("MYSQL_DB_PASSWORD"));
                            conn = dSource.getConnection();
                            dbStatus = true;
                            connectionStatus.setText("Connected to: " + (String) properties.getProperty("MYSQL_DB_URL"));
                        }

                        else {
                            connectionStatus.setText("NOT CONNECTED - USER CREDENTIALS DO NOT MATCH PROPERTIES FILE");
                        }
                    }

                    catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "DATABASE ERROR", JOptionPane.ERROR_MESSAGE);
    					//if a exception is thrown alert user there is an issue and show that a connection has not been made
    					connectionStatus.setText("No Connection now");
    					JOptionPane.showMessageDialog(null, "Database Connection Issue");
    					dbStatus = false;
    					//clearing the table
    					resultTable.setModel(new DefaultTableModel());
    					tableModel = null;
    					e.printStackTrace();
                    }

                }

                catch (IOException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "DATABASE ERROR", JOptionPane.ERROR_MESSAGE);
                }

                catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "DATABASE ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
			
			
		//actionListener for clearCommand
		clearCommand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//resets the sqlCommandsArea to empty
				sqlCommandsArea.setText("");
			}
		});
		
		//actionListener for executeCommand
		executeCommand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//checking if tableModel has not been created and we are connected to a db
				if(dbStatus == true && tableModel == null){
					try {
						//performing the query sent by user using ResultSetTableModel
						tableModel = new ResultSetTableModel(sqlCommandsArea.getText(), conn, dbStatus);
						//providing the results from the query
						resultTable.setModel(tableModel);
					}catch(ClassNotFoundException | SQLException exception) {
						//clear the table
						resultTable.setModel(new DefaultTableModel());
						tableModel = null;
						JOptionPane.showMessageDialog(null, exception.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
						exception.printStackTrace();
					}
					//case for which a tableModel has been created and we are connected to a db
				}else if(dbStatus == true && tableModel != null) {
					String query = sqlCommandsArea.getText();
					//checking if this is a search query
					if(query.contains("SELECT") || query.contains("select")) {
						try {
							tableModel.setQuery(query);
						}catch(IllegalStateException | SQLException exception) {
							//clear the table
							resultTable.setModel(new DefaultTableModel());
							tableModel = null;
							JOptionPane.showMessageDialog(null, exception.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
							exception.printStackTrace();
						}
					}else {
						//attempting an update query
						try {
							tableModel.setUpdate(query);
							//clearing the table
							resultTable.setModel(new DefaultTableModel());
							tableModel = null;
						}catch(IllegalStateException | SQLException exception) {
							//clearing the table
							resultTable.setModel(new DefaultTableModel());
							tableModel = null;
							JOptionPane.showMessageDialog(null, exception.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
							exception.printStackTrace();
						}
					}
				}
			}
		});
		
		//actionListener for clearResults
		clearResults.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//clear Table
				resultTable.setModel(new DefaultTableModel());
				tableModel = null;
			}
		});
	}
}