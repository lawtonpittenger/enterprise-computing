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
import java.sql.SQLException;
import model.CountClass;
import model.DistinctClass;
import model.Job;
import model.Part;
import model.Shipment;
import model.Supplier;
import model.SupplierShort;
import model.SupplierSingle;

@WebServlet(name = "RootServlet", urlPatterns = {"/root"})
public class RootServlet extends HttpServlet {

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
           
        request.setAttribute("list", null);
        request.setAttribute("error", null);
        
        ServletContext context = getServletContext();
        String filePath = context.getRealPath("/root-level.properties");
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
                     
                    
                    request.setAttribute("list", list);
                    request.setAttribute("error", null);
                    //System.out
                    RequestDispatcher rd = request.getRequestDispatcher("rootuser.jsp");
                    rd.forward(request, response);
                    
                    
                    
                    
                    
                
                
            }else if(sql.startsWith("select distinct suppliers.snum, suppliers.sname, shipments.pnum") ||
                    sql.startsWith("SELECT distinct suppliers.snum, suppliers.sname, shipments.pnum") || 
                    sql.startsWith("Select distinct suppliers.snum, suppliers.sname, shipments.pnum")){
                
                
                
                        Statement st = con.createStatement();
                        ResultSet rs = st.executeQuery(sql);
                   
                        List<DistinctClass> list = new ArrayList<>();
                         while(rs.next()){

                             DistinctClass d = new DistinctClass();

                         d.setSnum(rs.getString(1));
                         d.setSname(rs.getString(2));
                         d.setPnum(rs.getString(3));

                       list.add(d);



                        }

                        
                        request.setAttribute("list", list);
                        request.setAttribute("error", null);
                        RequestDispatcher rd = request.getRequestDispatcher("rootuser.jsp");
                        rd.forward(request, response);
                        
             }else if(sql.startsWith("select sname") ||
                    sql.startsWith("SELECT sname") || 
                    sql.startsWith("Select sname")){  
                 
                 
                        Statement st = con.createStatement();
                        ResultSet rs = st.executeQuery(sql);
                   
                        List<SupplierSingle> list = new ArrayList<>();
                         while(rs.next()){

                             SupplierSingle d = new SupplierSingle();

                         
                         d.setSname(rs.getString(1));
                         
                       list.add(d);



                        }

                        
                        request.setAttribute("list", list);
                        request.setAttribute("error", null);
                        RequestDispatcher rd = request.getRequestDispatcher("rootuser.jsp");
                        rd.forward(request, response);
                 
            }else if(sql.startsWith("select count(*) as total_shipments") ||
                    sql.startsWith("SELECT count(*) as total_shipments") || 
                    sql.startsWith("Select count(*) as total_shipments")){  
                
                        Statement st = con.createStatement();
                        ResultSet rs = st.executeQuery(sql);
                   
                        List<CountClass> list = new ArrayList<>();
                         while(rs.next()){

                             CountClass d = new CountClass();

                         
                         d.setTotal_shipments(rs.getInt(1));
                         
                       list.add(d);



                        }

                        
                        request.setAttribute("list", list);
                        request.setAttribute("error", null);
                        RequestDispatcher rd = request.getRequestDispatcher("rootuser.jsp");
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
                     
                     request.setAttribute("list", list);
                     request.setAttribute("error", null);
                    RequestDispatcher rd = request.getRequestDispatcher("rootuser.jsp");
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
                     
                     request.setAttribute("list", list);
                     request.setAttribute("error", null);
                    RequestDispatcher rd = request.getRequestDispatcher("rootuser.jsp");
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
                     
                     request.setAttribute("list", list);
                     request.setAttribute("error", null);
                    RequestDispatcher rd = request.getRequestDispatcher("rootuser.jsp");
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
                     
                    request.setAttribute("list", list);
                    request.setAttribute("error", null);
                    RequestDispatcher rd = request.getRequestDispatcher("rootuser.jsp");
                    rd.forward(request, response);
                    
                }
                
               
                
                
            }else if(sql.startsWith("Insert") || sql.startsWith("INSERT") || sql.startsWith("insert")){
                
                
                if(sql.contains("shipments")){
                    
                    
                    
                    
                    String [] vals = sql.split("values");
                    String str = vals[1];
                    String[] parts = str.replaceAll("[()']", "").split(",");

                    String snum = parts[0].trim(); // s5
                    String part2 = parts[1].trim(); // p6
                    String part3 = parts[2].trim(); // j4
                    int quantity = Integer.parseInt(parts[3].trim()); // 400
           
                    PreparedStatement ps = con.prepareStatement(sql);
                    int rowsAffected = ps.executeUpdate();
                    
                    
                     if(quantity >= 100){
                        
                        Connection con1 = DriverManager.getConnection(dbUrl,usernameDB,passwordDB);
                        String sql1 = "UPDATE suppliers SET status=status + 5 WHERE snum=?";
                        PreparedStatement ps1 = con1.prepareStatement(sql1);
                        ps1.setString(1,snum);
                        int statusAddected = ps1.executeUpdate();
                        con1.close();
                        
                        Connection con2 = DriverManager.getConnection(dbUrl,usernameDB,passwordDB);
                        String sql2 = "UPDATE suppliers \n" +
                                        "JOIN (SELECT snum, SUM(quantity) AS total_quantity \n" +
                                        "      FROM shipments \n" +
                                        "      GROUP BY snum) AS shipment_totals \n" +
                                        "ON suppliers.snum = shipment_totals.snum \n" +
                                        "SET suppliers.status = suppliers.status + 5 \n" +
                                        "WHERE shipment_totals.total_quantity >= 100;";
                        PreparedStatement ps2 = con2.prepareStatement(sql2);
                        int statusAffected2 = ps2.executeUpdate();
                        
                        
                        int affected = statusAddected + statusAffected2;
                        con2.close();
                        
                        List<String> list = new ArrayList<>();

                        list.add("The statement executed successfully");
                        list.add(rowsAffected+" row(s) affected" );
                        list.add("Business Logic Detected! - Updating Supplier Status");
                        list.add("Business Logic updated "+affected+" supplier status mark");
                        request.setAttribute("list", list);
                        request.setAttribute("error", null);
                        RequestDispatcher rd = request.getRequestDispatcher("rootuser.jsp");
                        rd.forward(request, response);
                    }else{
                        List<String> list = new ArrayList<>();

                       list.add("The statement executed successfully");
                       list.add(rowsAffected+" row(s) affected" );
                       list.add("Business Logic Not Triggered");
                       request.setAttribute("list", list);
                       request.setAttribute("error", null);
                       RequestDispatcher rd = request.getRequestDispatcher("rootuser.jsp");
                       rd.forward(request, response);
                    }
                    

                    
                    
                }
                
                PreparedStatement ps = con.prepareStatement(sql);
                int rowsAffected = ps.executeUpdate();
                
                List<String> list = new ArrayList<>();
                
                list.add("The statement executed successfully");
                list.add(rowsAffected+" row(s) affected" );
                list.add("Business Logic Not Triggered");
                
                request.setAttribute("list", list);
                request.setAttribute("error", null);    
                RequestDispatcher rd = request.getRequestDispatcher("rootuser.jsp");
                rd.forward(request, response);               
            }else if(sql.startsWith("Update") || sql.startsWith("UPDATE") || sql.startsWith("update")){
                
  
                if(sql.contains("shipments") && sql.contains("quantity")){
                    Pattern pattern = Pattern.compile("quantity=([0-9]+)");
                    Matcher matcher = pattern.matcher(sql);
                    
                    int q=0;
                    if (matcher.find()) {
                        String quantityString = matcher.group(1);                     
                        q = Integer.parseInt(quantityString);     
                    }
                   
                    
                    
                    
                    if(q >= 100){
                       Connection con2 = DriverManager.getConnection(dbUrl,usernameDB,passwordDB);
                        String sql2 = "UPDATE suppliers \n" +
                                        "JOIN (SELECT snum, SUM(quantity) AS total_quantity \n" +
                                        "      FROM shipments \n" +
                                        "      GROUP BY snum) AS shipment_totals \n" +
                                        "ON suppliers.snum = shipment_totals.snum \n" +
                                        "SET suppliers.status = suppliers.status + 5 \n" +
                                        "WHERE shipment_totals.total_quantity >= 100;";
                        PreparedStatement ps2 = con2.prepareStatement(sql2);
                        int statusAffected2 = ps2.executeUpdate();                
                        
                        con2.close();
                        
                        List<String> list = new ArrayList<>();

                        list.add("The statement executed successfully");
                        //list.add(rowsAffected+" row(s) affected" );
                        list.add("Business Logic Detected! - Updating Supplier Status");
                        list.add("Business Logic updated "+statusAffected2+" supplier status mark");
                        request.setAttribute("list", list);
                        request.setAttribute("error", null);
                        RequestDispatcher rd = request.getRequestDispatcher("rootuser.jsp");
                        rd.forward(request, response); 
                    }else{
                        Connection con2 = DriverManager.getConnection(dbUrl,usernameDB,passwordDB);
                        PreparedStatement ps2 = con2.prepareStatement(sql);
                        int rowsAffected = ps2.executeUpdate();
                        con2.close();
                        List<String> list = new ArrayList<>();
                        list.add("The statement executed successfully");
                        list.add(rowsAffected+" row(s) affected" );
                       list.add("Business Logic Detected! - Updating Supplier Status");
                        list.add("Business Logic updated 0 supplier status mark");
                        request.setAttribute("list", list);
                        request.setAttribute("error", null);
                        RequestDispatcher rd = request.getRequestDispatcher("rootuser.jsp");
                        rd.forward(request, response);
                    }
                    
                }else{
                        Connection con2 = DriverManager.getConnection(dbUrl,usernameDB,passwordDB);
                        PreparedStatement ps2 = con2.prepareStatement(sql);
                        int rowsAffected = ps2.executeUpdate();
                        con2.close();
                        List<String> list = new ArrayList<>();
                        list.add("The statement executed successfully");
                        list.add(rowsAffected+" row(s) affected" );
                        list.add("Business Logic Not Triggered");
                        request.setAttribute("list", list);
                        request.setAttribute("error", null);
                        RequestDispatcher rd = request.getRequestDispatcher("rootuser.jsp");
                        rd.forward(request, response);
                }


            }else if(sql.startsWith("Delete") || sql.startsWith("DELETE") || sql.startsWith("delete")){
                Connection con2 = DriverManager.getConnection(dbUrl,usernameDB,passwordDB);
                PreparedStatement ps2 = con2.prepareStatement(sql);
                int rowsAffected = ps2.executeUpdate();
                con2.close();
                List<String> list = new ArrayList<>();
                list.add("The statement executed successfully");
                list.add(rowsAffected+" row(s) affected" );
                list.add("Business Logic Not Triggered");
                request.setAttribute("list", list);
                request.setAttribute("error", null);
                RequestDispatcher rd = request.getRequestDispatcher("rootuser.jsp");
                rd.forward(request, response);
                
            }
            
                          
			
        } catch (SQLException e) {
            request.setAttribute("list", null);         
            request.setAttribute("error", e.getMessage());
            RequestDispatcher rd = request.getRequestDispatcher("rootuser.jsp");
            rd.forward(request, response);

             
        }catch (Exception e) {
           
        }
        
        
        
    }

 

}
