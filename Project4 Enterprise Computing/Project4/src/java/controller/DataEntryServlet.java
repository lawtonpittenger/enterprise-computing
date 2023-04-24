/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Bern Ard
 */
@WebServlet(name = "DataEntryServlet", urlPatterns = {"/DataEntry"})
public class DataEntryServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        request.setAttribute("list", null);
        request.setAttribute("error", null);
        ServletContext context = getServletContext();
        String filePath = context.getRealPath("/data-entry-level.properties");
       
        
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
            
            
            
            if(request.getParameter("snum") != null){
            
            
                String snum = request.getParameter("snum");
                String sname = request.getParameter("sname");
                int status = Integer.parseInt( request.getParameter("status"));
                String city = request.getParameter("city");


                String sql = "INSERT INTO suppliers(snum,sname,status,city) VALUES(?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(sql);


                ps.setString(1, snum);
                ps.setString(2, sname);
                ps.setInt(3, status);
                ps.setString(4, city);

                ps.executeUpdate();
                
                
                List<String> list = new ArrayList<>();
                list.add("New suppliers record: ("+snum+", "+sname+", "+status+", "+city+") - successfully entered into database.");
                request.setAttribute("list", list);
                request.setAttribute("error", null);
                RequestDispatcher rd = request.getRequestDispatcher("entrylevel.jsp");
                rd.forward(request, response);
     
            
        }else if(request.getParameter("pnum") != null){
                String pnum = request.getParameter("pnum");
                String pname = request.getParameter("pname");
                String color = request.getParameter("color");   
                int weight = Integer.parseInt( request.getParameter("weight"));
                String city = request.getParameter("part_city");
                
                
                String sql = "INSERT INTO parts(pnum,pname,color,weight,city) VALUES(?,?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(sql);


                ps.setString(1, pnum);
                ps.setString(2, pname);
                ps.setString(3, color);
                ps.setInt(4, weight);
                ps.setString(5, city);

                ps.executeUpdate();
                
                
                List<String> list = new ArrayList<>();
                list.add("New parts record: ("+pnum+", "+pname+", "+color+", "+weight+", "+city+") - successfully entered into database.");
                request.setAttribute("list", list);
                request.setAttribute("error", null);
                RequestDispatcher rd = request.getRequestDispatcher("entrylevel.jsp");
                rd.forward(request, response);
                
        }else if(request.getParameter("jnum") != null){
                String jnum = request.getParameter("jnum");
                String jname = request.getParameter("jname");
                int numworkers = Integer.parseInt( request.getParameter("numworkers"));
                String city = request.getParameter("job_city");
                
                
                String sql = "INSERT INTO jobs(jnum,jname,numworkers,city) VALUES(?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(sql);


                ps.setString(1, jnum);
                ps.setString(2, jname);
                ps.setInt(3, numworkers);
                ps.setString(4, city);

                ps.executeUpdate();
                
                
                List<String> list = new ArrayList<>();
                list.add("New jobs record: ("+jnum+", "+jname+", "+numworkers+", "+city+") - successfully entered into database.");
                request.setAttribute("list", list);
                request.setAttribute("error", null);
                RequestDispatcher rd = request.getRequestDispatcher("entrylevel.jsp");
                rd.forward(request, response);
                
        }else if(request.getParameter("ship_snum") != null){
                String snum = request.getParameter("ship_snum");
                String pnum = request.getParameter("ship_pnum");
                String jnum = request.getParameter("ship_jnum");
                int quantity = Integer.parseInt( request.getParameter("quantity"));
               
                
                
                String sql = "INSERT INTO shipments(snum,pnum,jnum,quantity) VALUES(?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(sql);


                ps.setString(1, snum);
                ps.setString(2, pnum);
                ps.setString(3, jnum);
                ps.setInt(4, quantity);

                ps.executeUpdate();
                
                List<String> list = new ArrayList<>();
               
                
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
                     list.add("New shipments record: ("+snum+", "+pnum+", "+jnum+", "+quantity+") - successfully entered into database. Business logic triggered");   
                      
                }else{
                    list.add("New shipments record: ("+snum+", "+pnum+", "+jnum+", "+quantity+") - successfully entered into database.");
                }
                
                
               
                request.setAttribute("list", list);
                request.setAttribute("error", null);
                RequestDispatcher rd = request.getRequestDispatcher("entrylevel.jsp");
                rd.forward(request, response);
        }   
        
            
        }catch(Exception e){
            request.setAttribute("list", null);         
            request.setAttribute("error", e.getMessage());
            RequestDispatcher rd = request.getRequestDispatcher("entrylevel.jsp");
            rd.forward(request, response);

              e.printStackTrace();
        }
            
        
        
        
        
    }

   

}
