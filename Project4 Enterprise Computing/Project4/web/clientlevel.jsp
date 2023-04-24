<%@page import="model.SupplierSingle"%>
<%@page import="model.CountClass"%>
<%@page import="model.DistinctClass"%>
<%@page import="model.SupplierShort"%>
<%@page import="model.Shipment"%>
<%@page import="model.Job"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Supplier"%>
<%@page import="java.util.List"%>
<!--/* Name:
Course: CNT 4714 – Spring 2023 – Project Four
Assignment title: A Three-Tier Distributed Web-Based Application
Date: April 23, 2023
*/-->


<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%! 
    List<Object> lists = null;
    List<Supplier> suppliers = null;
    List<SupplierShort> supplierShorts = null;
    List<model.Part> parts = null;  
    List<Job> jobs = null;
    List<Shipment> shipments = null;
    List<DistinctClass> distincts = null;
     List<SupplierSingle> supplierSingle = null;
    List<CountClass> counts = null;
    List<String> results = null;
    
    
    String error = "";
    Class<?> childClass;
   
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CNT 4714 Enterprise Database System</title>
        <link href="style.css" rel="stylesheet">
    </head>
    <body>
        
        
        
        <%
            
            if (request.getAttribute("list") != null) {
                lists = (List<Object>) request.getAttribute("list");
                childClass = lists.get(0).getClass();
                if (childClass == Supplier.class) {
                   suppliers = new ArrayList<>();
                    for (Object obj : lists) {
                        suppliers.add((Supplier) obj);
                    }
                    // Do something with the List of Supplier objects
                } else if(childClass == model.Part.class){
                    
                    parts = new ArrayList<>();
                    for (Object obj : lists) {
                        parts.add((model.Part) obj);
                    }
                }else if(childClass == Job.class){
                    
                    jobs = new ArrayList<>();
                    for (Object obj : lists) {
                        jobs.add((Job) obj);
                    }
                }else if(childClass == Shipment.class){
                    
                    shipments = new ArrayList<>();
                    for (Object obj : lists) {
                        shipments.add((Shipment) obj);
                    }
                }else if(childClass == SupplierShort.class){
                    
                    supplierShorts = new ArrayList<>();
                    for (Object obj : lists) {
                        supplierShorts.add((SupplierShort) obj);
                    }
                }else if(childClass == DistinctClass.class){
                    
                    distincts = new ArrayList<>();
                    for (Object obj : lists) {
                        distincts.add((DistinctClass) obj);
                    }
                    
                }else if(childClass == SupplierSingle.class){
                
                    supplierSingle = new ArrayList<>();
                    for (Object obj : lists) {
                        supplierSingle.add((SupplierSingle) obj);
                    }
                
                }else if(childClass == CountClass.class){
                
                
                    counts = new ArrayList<>();
                    for (Object obj : lists) {
                        counts.add((CountClass) obj);
                    }
                
                }else{
                    results = new ArrayList<>();
                    for(Object obj: lists){
                        results.add((String) obj);
                     }
                }
            }else if(request.getAttribute("error") != null){
                error = String.valueOf(request.getAttribute("error"));   
            }
        
          


        %>
        
        
        
        
        
         <section >
             
            <h1 style="color: red; font-size: 45px;">Welcome to the Spring 2023 Project 4 Enterprise System
               
            
            </h1>
            <h1 style="color: #00FFFF; font-size: 40px;">A Servlet/JSP-based Multi-tiered Enterprise Application Using A Tomcat Container</h1>
        </section>    
        <hr>
         <section style="height: 50vh; display: flex; flex-direction: column; align-items: center; justify-content: center;">
             <p style="color: white; font-size: 21px;">You are connected to the Project 4 Enterprise System database as a <span style="color:red;">client-level</span> user </p>
             <p style="color: white; font-size: 21px;">Please enter any valid SQL query or update command in the box below</p>
            <form action="client" method="post" id="sqlForm">    
                
                <textarea style="height:250px; width: 1000px; resize: none; font-size: 25px; background-color: blue; color: white;" name="sql" id="sql"></textarea>
                <br>
                 <br>
                <button type="submit" style="font-size: 25px; background-color: #655D1E; color: #00FF00;">Execute Command</button>
                <button  type="reset" style="font-size: 25px; background-color: #655D1E; color: red;" id="resetButton">Reset Form</button>
                <button  type="button" style="font-size: 25px; background-color: #655D1E; color: yellow;" id="clearBtn">Clear Results</button>
            </form>
            <p style="color: white; font-size: 21px;">All execution results will appear below this line.</p>
        </section>
         <hr>
        
         <section style="display: flex; flex-direction: column; align-items: center; justify-content: center;" id="results-section">
             
            <p style="color: white; font-size: 21px;" id="p-head">Database Results:</p>
            
            
            
            <% if(lists != null){ %>
            
            
            
            <% if(childClass.getName().equals("model.SupplierShort")) { %>
            
                
                    <table style="margin-bottom: 50px;">
                    <tr>
                      <th style="background-color: red; color: black;">snum</th>
                      <th style="background-color: red; color: black;">sname</th>
                      
                    </tr>
                    
                   <% for(SupplierShort s : supplierShorts){ %>
                    <tr>      
                        <td style="background-color: white; color: black;"> <%= s.getSnum()%>  </td>
                        <td style="background-color: white; color: black;"> <%= s.getSname() %>  </td>
                        
                    </tr>
                    <% } %>
                  </table>
            
             <%}else if(childClass.getName().contains("SupplierSingle")) { %>     
                  
                  <table style="margin-bottom: 50px;">
                    <tr>
                      <th style="background-color: red; color: black;">sname</th>
                      
                    </tr>
                    
                   <% for(SupplierSingle s : supplierSingle){ %>
                    <tr>      
                        <td style="background-color: white; color: black;"> <%= s.getSname() %>  </td>
                    </tr>
                    <% } %>
                  </table>
                  
             <%}else if(childClass.getName().contains("CountClass")) { %>   
             
             
                    <table style="margin-bottom: 50px;">
                    <tr>
                      <th style="background-color: red; color: black;">total_shipments_with_quantity_over_100</th>
                      
                    </tr>
                    
                   <% for(CountClass s : counts){ %>
                    <tr>      
                        <td style="background-color: white; color: black;"> <%= s.getTotal_shipments() %>  </td>
                    </tr>
                    <% } %>
                  </table>
             
             
             
                  
             <%}else if(childClass.getName().contains("DistinctClass")) { %>
                
                    
                    <table style="margin-bottom: 50px;">
                    <tr>
                      <th style="background-color: red; color: black;">snum</th>
                      <th style="background-color: red; color: black;">sname</th>
                      <th style="background-color: red; color: black;">pname</th>
                    </tr>
                    
                   <% for(DistinctClass s : distincts){ %>
                    <tr>      
                        <td style="background-color: white; color: black;"> <%= s.getSnum()%>  </td>
                        <td style="background-color: white; color: black;"> <%= s.getSname() %>  </td>
                        <td style="background-color: white; color: black;"> <%= s.getPnum() %>  </td>
                    </tr>
                    <% } %>
                  </table>
          
            <% }else if(childClass.getName().contains("Supplier")){ %>
                
              
            
            <table style="margin-bottom: 50px;">
                    <tr>
                      <th style="background-color: red; color: black;">snum</th>
                      <th style="background-color: red; color: black;">sname</th>
                      <th style="background-color: red; color: black;">status</th>
                      <th style="background-color: red; color: black;">city</th>
                    </tr>
                    
                   <% for(Supplier s : suppliers){ %>
                    <tr>      
                        <td style="background-color: white; color: black;"> <%= s.getSnum()%>  </td>
                        <td style="background-color: white; color: black;"> <%= s.getSname() %>  </td>
                        <td style="background-color: white; color: black;"> <%= s.getStatus()%>  </td>
                        <td style="background-color: white; color: black;"> <%= s.getCity()%>  </td>
                    </tr>
                    <% } %>
                  </table>
   
             <%}else if(childClass.getName().contains("Part")){ %>
                
                    <table style="margin-bottom: 50px;">
                    <tr>
                      <th style="background-color: red; color: black;">pnum</th>
                      <th style="background-color: red; color: black;">pname</th>
                      <th style="background-color: red; color: black;">color</th>
                      <th style="background-color: red; color: black;">weight</th>
                      <th style="background-color: red; color: black;">city</th>
                    </tr>
                    
                   <% for(model.Part p : parts){ %>
                    <tr>      
                        <td style="background-color: white; color: black;"> <%= p.getPnum() %>  </td>
                        <td style="background-color: white; color: black;"> <%= p.getPname() %>  </td>
                        <td style="background-color: white; color: black;"> <%= p.getColor() %>  </td>
                        <td style="background-color: white; color: black;"> <%= p.getWeight() %>  </td>
                        <td style="background-color: white; color: black;"> <%= p.getCity() %>  </td>
                    </tr>
                    <% } %>
                  </table>
                  
             
                    <%}else if(childClass.getName().contains("Job")){ %>
                        
                        
                    <table style="margin-bottom: 50px;">
                        <tr>
                          <th style="background-color: red; color: black;">jnum</th>
                          <th style="background-color: red; color: black;">jname</th>
                          <th style="background-color: red; color: black;">numworkers</th>
                          <th style="background-color: red; color: black;">city</th>
                        </tr>

                       <% for(Job p : jobs){ %>
                        <tr>      
                            <td style="background-color: white; color: black;"> <%= p.getJnum()%>  </td>
                            <td style="background-color: white; color: black;"> <%= p.getJname()%>  </td>
                            <td style="background-color: white; color: black;"> <%= p.getNumworkers()%>  </td>
                            <td style="background-color: white; color: black;"> <%= p.getCity() %>  </td>
                        </tr>
                        <% } %>
                  </table>
                            
                    
                    
                    
                    <%}else if(childClass.getName().contains("Shipment")){ %>
                        
                        
                    <table style="margin-bottom: 50px;">
                        <tr>
                          <th style="background-color: red; color: black;">snum</th>
                          <th style="background-color: red; color: black;">pnum</th>
                          <th style="background-color: red; color: black;">jnum</th>
                          <th style="background-color: red; color: black;">quantity</th>
                        </tr>

                       <% for(Shipment p : shipments){ %>
                        <tr>      
                            <td style="background-color: white; color: black;"> <%= p.getSnum() %>  </td>
                            <td style="background-color: white; color: black;"> <%= p.getPnum() %>  </td>
                            <td style="background-color: white; color: black;"> <%= p.getJnum() %>  </td>
                            <td style="background-color: white; color: black;"> <%= p.getQuantity() %>  </td>
                        </tr>
                        <% } %>
                  </table>
                            
                    
                    
                    
                    <%}else{ %>
                        
                    <div style="background-color: red; display: flex; flex-direction: column; align-items: center; justify-content: center;">
                        
                         <% for(String s : results){ %>
                            <p style="color: white; font-size: 21px; margin: 5px;"> <%=s %> </p>    
                        <% } %>
                        
                        
                        
                    </div>
                    
                    <%} %>
       
                    <%}else if(request.getAttribute("error") != null){ %>
                    <div style="background-color: red; display: flex; flex-direction: column; align-items: center; justify-content: center;">
                        
                        
                         <p style="color: white; font-size: 21px; margin: 5px;">Error Executing the SQL statement: </p> 
                         <p style="color: white; font-size: 21px; margin: 5px;"> <%=error %> </p>    
                        
                        
                        
                        
                    </div>
                         
                <%} %>
            
        </section>
         
         
         
         <script>
          
        const section = document.getElementById("results-section");
        const clearBtn = document.getElementById("clearBtn");
        
        clearBtn.addEventListener("click", () => {
            if (section.children[1].tagName.toLowerCase() === "table" || section.children[1].tagName.toLowerCase() === "div") {
            section.removeChild(section.children[1]);
            }
          });

             
             // Get the form and its input elements
        const form = document.getElementById("sqlForm");
        const sql = document.getElementById("sql");
        

        // Load the saved values from local storage, if they exist
        sql.value = localStorage.getItem("areaVal") || "";
        

        // Save the input values to local storage when the form is submitted
        form.addEventListener("submit", function(event) {
          event.preventDefault();
          localStorage.setItem("areaVal", sql.value);
          
          // Submit the form
          this.submit();
        });

        // Clear the saved values from local storage when the reset button is clicked
        document.getElementById("resetButton").addEventListener("click", function(event) {
          event.preventDefault();
          localStorage.removeItem("areaVal");
         

          // Clear the input fields
          sql.value = "";
         
        });
             
         </script>
    </body>
</html>
