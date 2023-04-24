/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

/* Name:
Course: CNT 4714 – Spring 2023 – Project Four
Assignment title: A Three-Tier Distributed Web-Based Application
Date: April 23, 2023
*/


package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.CountClass;
import model.DistinctClass;
import model.Job;
import model.Part;
import model.Shipment;
import model.Supplier;
import model.SupplierShort;
import model.SupplierSingle;


@WebServlet(name = "ClientServlet", urlPatterns = {"/client"})
public class ClientServlet extends HttpServlet {

  
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setAttribute("list", null);
        request.setAttribute("error", null);
        ServletContext context = getServletContext();
        String filePath = context.getRealPath("/user-level.properties");
        String sql = request.getParameter("sql");
        
        
        if (sql.contains(";")) {
            sql = sql.replace(";", "");
       }
        
        Properties properties = new Properties();
        InputStream inputStream = new FileInputStream(filePath);
        properties.load(inputStream);
        
        
        try {
            final String usernameDB =  properties.getProperty("database.username");
            final String passwordDB = properties.getProperty("database.password");
            final String dbUrl = properties.getProperty("database.url");
            final String dbDriver =  properties.getProperty("database.driver");
            //String sql = "";
            Class.forName(dbDriver);
            Connection con = DriverManager.getConnection(dbUrl,usernameDB,passwordDB);
             
            
            if(sql.startsWith("select snum, sname") || sql.startsWith("SELECT snum, sname") || sql.startsWith("Select snum, sname")){
                
                
                   Statement st = con.createStatement();
                   ResultSet rs = st.executeQuery(sql);
                   
                    List<SupplierShort> list = new ArrayList<>();
                     while(rs.next()){
                         
                     SupplierShort s = new SupplierShort();
                     
                     s.setSnum(rs.getString(1));
                     s.setSname(rs.getString(2));
                     
                    
                   list.add(s);
                    
                    
                    
                    }
                     
                    con.close();
                    request.setAttribute("list", list);
                    request.setAttribute("error", null);
                    RequestDispatcher rd = request.getRequestDispatcher("clientlevel.jsp");
                    rd.forward(request, response);
                    
                    
       
                 
            }else if(sql.startsWith("select count(*) as total_shipments_with_quantity_over_100") ||
                    sql.startsWith("SELECT count(*) as total_shipments_with_quantity_over_100") || 
                    sql.startsWith("Select count(*) as total_shipments_with_quantity_over_100")){  
                
                        Statement st = con.createStatement();
                        ResultSet rs = st.executeQuery(sql);
                   
                        List<CountClass> list = new ArrayList<>();
                         while(rs.next()){

                             CountClass d = new CountClass();

                         
                         d.setTotal_shipments(rs.getInt(1));
                         
                       list.add(d);



                        }

                        con.close();
                        request.setAttribute("list", list);
                        request.setAttribute("error", null);
                        RequestDispatcher rd = request.getRequestDispatcher("clientlevel.jsp");
                        rd.forward(request, response);
                

            }else if(sql.startsWith("Select") || sql.startsWith("SELECT") || sql.startsWith("select")){
                
                
                
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql);
                
                
                if(sql.contains("suppliers")){
                    List<Supplier> list = new ArrayList<>();
                     while(rs.next()){
                         
                     Supplier s = new Supplier();
                     
                     s.setSnum(rs.getString(1));
                     s.setSname(rs.getString(2));
                     s.setStatus(rs.getInt(3));
                     s.setCity(rs.getString(4));
                    
                   list.add(s);
                    
                    
                    
                    }
                     con.close();
                     request.setAttribute("list", list);
                     request.setAttribute("error", null);
                    RequestDispatcher rd = request.getRequestDispatcher("clientlevel.jsp");
                    rd.forward(request, response);
                     
                }else if(sql.contains("shipments")){
                    
                     List<Shipment> list = new ArrayList<>();
                     while(rs.next()){
                         
                     Shipment s = new Shipment();
                     
                     s.setSnum(rs.getString(1));
                     s.setPnum(rs.getString(2));
                     s.setJnum(rs.getString(3));
                     s.setQuantity(rs.getInt(4));
                    
                   list.add(s);
                    
                    
                    
                    }
                     con.close();
                     request.setAttribute("list", list);
                     request.setAttribute("error", null);
                    RequestDispatcher rd = request.getRequestDispatcher("clientlevel.jsp");
                    rd.forward(request, response);
                    
                }else if(sql.contains("parts")){
                    
                    List<Part> list = new ArrayList<>();
                     while(rs.next()){
                         
                     Part p = new Part();
                     
                     p.setPnum(rs.getString(1));
                     p.setPname(rs.getString(2));
                     p.setColor(rs.getString(3));
                     p.setWeight(rs.getInt(4));
                     p.setCity(rs.getString(5));
                    
                   list.add(p);
                    
                    
                    
                    }
                     con.close();
                     request.setAttribute("list", list);
                     request.setAttribute("error", null);
                    RequestDispatcher rd = request.getRequestDispatcher("clientlevel.jsp");
                    rd.forward(request, response);
                    
                }else if(sql.contains("jobs")){
                    
                    List<Job> list = new ArrayList<>();
                     while(rs.next()){
                         
                     Job j = new Job();
                     
                    j.setJnum(rs.getString(1));
                    j.setJname(rs.getString(2));
                    j.setNumworkers(rs.getInt(3));
                    j.setCity(rs.getString(4));
                    list.add(j);
                    
                    
                    
                    }
                     con.close();
                    request.setAttribute("list", list);
                    request.setAttribute("error", null);
                    RequestDispatcher rd = request.getRequestDispatcher("clientlevel.jsp");
                    rd.forward(request, response);
                    
                }
                
               
                
                
            }else{
                List<String> list = new ArrayList<>();
                
                
                list.add("Error Executing the SQL statement:");
                
                
                if(sql.contains("INSERT") || sql.contains("Insert") || sql.contains("insert")){
                    String statemets [] = sql.split(" ");
                    list.add("INSERT command denied to user 'client'@'localhost' for table '"+statemets[2]+"'");
                    
                }else if(sql.contains("REPLACE") || sql.contains("Replace") || sql.contains("replace")){
                    String statemets [] = sql.split(" ");
                    list.add("REPLACE command denied to user 'client'@'localhost' for table '"+statemets[2]+"'");
                }else if(sql.contains("DELETE") || sql.contains("Delete") || sql.contains("delete")){
                    String statemets [] = sql.split(" ");
                    list.add("DELETE command denied to user 'client'@'localhost' for table '"+statemets[2]+"'");
                }else if(sql.contains("UPDATE") || sql.contains("Update") || sql.contains("update")){
                    String statemets [] = sql.split(" ");
                    list.add("UPDATE command denied to user 'client'@'localhost' for table '"+statemets[1]+"'");
                }
                
                request.setAttribute("list", list);
                request.setAttribute("error", null);
                RequestDispatcher rd = request.getRequestDispatcher("clientlevel.jsp");
                rd.forward(request, response);
            }
            
                          
			
        } catch (Exception e) {
            request.setAttribute("list", null);         
            request.setAttribute("error", e.getMessage());
            RequestDispatcher rd = request.getRequestDispatcher("clientlevel.jsp");
            rd.forward(request, response);

              e.printStackTrace();
        }
        
        
    }

   

}
