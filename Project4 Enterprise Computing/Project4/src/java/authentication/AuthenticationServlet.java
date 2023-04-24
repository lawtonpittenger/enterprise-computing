/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

/* Name:
Course: CNT 4714 – Spring 2023 – Project Four
Assignment title: A Three-Tier Distributed Web-Based Application
Date: April 23, 2023
*/

package authentication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "AuthenticationServlet", urlPatterns = {"/auth"})
public class AuthenticationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        ServletContext context = getServletContext();
        String filePath = context.getRealPath("/credentials.txt");
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");       
        
        Random rand = new Random();
        int randomNum = rand.nextInt(3) + 1;
        

 try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            boolean inputMatch = false;
            while ((line = reader.readLine()) != null) {
                if (line.contains(username) && line.contains(password)) {
                    // input values match a line in the text file
                    inputMatch = true;
                    break;
                }
            }
            if (inputMatch) {
                    if(randomNum == 1){
                       response.sendRedirect("RootUserApp");
                    }else if(randomNum == 2){
                      response.sendRedirect("ClientUserApp");
                    }else{
                      response.sendRedirect("DataEntryApp"); 
                    }
            } else {
                response.sendRedirect("errorpage.html");
            }
            reader.close();
        } catch (IOException e) {
            
        }
    
        
    }

   
}
