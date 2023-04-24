<%@page import="java.util.List"%>
<!--/* Name:
Course: CNT 4714 – Spring 2023 – Project Four
Assignment title: A Three-Tier Distributed Web-Based Application
Date: April 23, 2023
*/-->

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%! 
   
    
    List<String> lists = null;
     String error = "";
   
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
                lists = (List<String>) request.getAttribute("list");  
           }else if(request.getAttribute("error") != null){
                error = String.valueOf(request.getAttribute("error"));   
            }
        %>
     
        
         <section >
             
            <h1 style="color: red; font-size: 45px;">Welcome to the Spring 2023 Project 4 Enterprise System </h1>
            <h1 style="color: #00FFFF; font-size: 40px;">Data Entry Application</h1>
        </section>    
        <hr>
         <section style="height: 10vh; display: flex; flex-direction: column; align-items: center; justify-content: center;">
             <p style="color: white; font-size: 21px;">You are connected to the Project 4 Enterprise System database as a <span style="color:red;">data-entry-level</span> user </p>
             <p style="color: white; font-size: 21px;">Enter the data values in a form below to add a new record to the corresponding database table</p>
            
        </section>
         <hr>
        
        
        <fieldset style="height: 20vh; display: flex; flex-direction: column; align-items: center; justify-content: center;">
          <legend style="color: white;">Supplier Record Insert</legend>
          <form action="DataEntry" method="post">
            <table border="1" cellpadding="10" style="border-color: #FFFF00;">
                <colgroup>
              <col style="border-right: 1px solid white">
              <col style="border-right: 1px solid white">
              <col>
            </colgroup>
              <thead>
                <tr>
                  <th style="color: white;">snum</th>
                  <th style="color: white;">sname</th>
                  <th style="color: white;">status</th>
                  <th style="color: white;">city</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>
                      <input type="text" id="snum" name="snum" style="background-color: #655D1E; color: #FFFF00" required>
                  </td>
                  <td>
                    <input type="text" id="sname" name="sname" style="background-color: #655D1E; color: #FFFF00" required>
                  </td>
                  <td>
                    <input type="text" id="status" name="status" style="background-color: #655D1E; color: #FFFF00" oninput="this.value = this.value.replace(/[^0-9]/g, '')" required>
                  </td>
                   <td>
                    <input type="text" id="city" name="city" style="background-color: #655D1E; color: #FFFF00" required>
                  </td>
                </tr>
              </tbody>
            </table>
            <div class="submit-container" style="display: flex; justify-content: center; margin-top: 20px;">
             <button type="submit" style="font-size: 25px; background-color: #655D1E; color: #00FF00; margin-right: 20px;">Enter Supplier Record Into Database</button>
             <button  type="reset" style="font-size: 25px; background-color: #655D1E; color: red;" id="resetButton">Reset Form</button>

            </div>

           </form>
        </fieldset>



        <fieldset style="height: 20vh; display: flex; flex-direction: column; align-items: center; justify-content: center;">
          <legend style="color: white;">Parts Record Insert</legend>
          <form action="DataEntry" method="post">
            <table border="1" cellpadding="10" style="border-color: #FFFF00;">
                <colgroup>
              <col style="border-right: 1px solid white">
              <col style="border-right: 1px solid white">
              <col>
            </colgroup>
              <thead>
                <tr>
                  <th style="color: white;">pnum</th>
                  <th style="color: white;">pname</th>
                  <th style="color: white;">color</th>
                  <th style="color: white;">weight</th>
                  <th style="color: white;">city</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>
                      <input type="text" id="pnum" name="pnum" style="background-color: #655D1E; color: #FFFF00" required>
                  </td>
                  <td>
                    <input type="text" id="pname" name="pname" style="background-color: #655D1E; color: #FFFF00" required>
                  </td>
                  <td>
                    <input type="text" id="color" name="color" style="background-color: #655D1E; color: #FFFF00" required>
                  </td>
                   <td>
                    <input type="text" id="weight" name="weight" style="background-color: #655D1E; color: #FFFF00" oninput="this.value = this.value.replace(/[^0-9]/g, '')" required>
                  </td>
                  <td>
                    <input type="text" id="part_city" name="part_city" style="background-color: #655D1E; color: #FFFF00" required>
                  </td>
                </tr>
              </tbody>
            </table>
            <div class="submit-container" style="display: flex; justify-content: center; margin-top: 20px;">
             <button type="submit" style="font-size: 25px; background-color: #655D1E; color: #00FF00; margin-right: 20px;">Enter Part Record Into Database</button>
             <button  type="reset" style="font-size: 25px; background-color: #655D1E; color: red;" id="resetButton">Reset Form</button>

            </div>

           </form>
        </fieldset>         


        <fieldset style="height: 20vh; display: flex; flex-direction: column; align-items: center; justify-content: center;">
          <legend style="color: white;">Jobs Record Insert</legend>
          <form action="DataEntry" method="post">
            <table border="1" cellpadding="10" style="border-color: #FFFF00;">
                <colgroup>
              <col style="border-right: 1px solid white">
              <col style="border-right: 1px solid white">
              <col>
            </colgroup>
              <thead>
                <tr>
                  <th style="color: white;">jnum</th>
                  <th style="color: white;">jname</th>
                  <th style="color: white;">numworkers</th>
                  <th style="color: white;">city</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>
                      <input type="text" id="jnum" name="jnum" style="background-color: #655D1E; color: #FFFF00" required>
                  </td>
                  <td>
                    <input type="text" id="jname" name="jname" style="background-color: #655D1E; color: #FFFF00" required>
                  </td>
                  <td>
                    <input type="text" id="numworkers" name="numworkers" style="background-color: #655D1E; color: #FFFF00" oninput="this.value = this.value.replace(/[^0-9]/g, '')" required>
                  </td>
                  <td>
                    <input type="text" id="job_city" name="job_city" style="background-color: #655D1E; color: #FFFF00" required>
                  </td>
                </tr>
              </tbody>
            </table>
            <div class="submit-container" style="display: flex; justify-content: center; margin-top: 20px;">
             <button type="submit" style="font-size: 25px; background-color: #655D1E; color: #00FF00; margin-right: 20px;">Enter Job Record Into Database</button>
             <button  type="reset" style="font-size: 25px; background-color: #655D1E; color: red;" id="resetButton">Reset Form</button>

            </div>

           </form>
        </fieldset> 


        <fieldset style="height: 20vh; display: flex; flex-direction: column; align-items: center; justify-content: center;">
          <legend style="color: white;">Shipments Record Insert</legend>
          <form action="DataEntry" method="post">
            <table border="1" cellpadding="10" style="border-color: #FFFF00;">
                <colgroup>
              <col style="border-right: 1px solid white">
              <col style="border-right: 1px solid white">
              <col>
            </colgroup>
              <thead>
                <tr>
                  <th style="color: white;">snum</th>
                  <th style="color: white;">pnum</th>
                  <th style="color: white;">jnum</th>
                  <th style="color: white;">quantity</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>
                      <input type="text" id="ship_snum"  name="ship_snum" style="background-color: #655D1E; color: #FFFF00" required>
                  </td>
                  <td>
                    <input type="text" id="ship_pnum" name="ship_pnum" style="background-color: #655D1E; color: #FFFF00" required>
                  </td>
                  <td>
                    <input type="text" id="ship_jnum" name="ship_jnum" style="background-color: #655D1E; color: #FFFF00" required>
                  </td>
                  <td>
                    <input type="text" id="quantity" name="quantity" style="background-color: #655D1E; color: #FFFF00" oninput="this.value = this.value.replace(/[^0-9]/g, '')" required>
                  </td>
                </tr>
              </tbody>
            </table>
            <div class="submit-container" style="display: flex; justify-content: center; margin-top: 20px;">
             <button type="submit" style="font-size: 25px; background-color: #655D1E; color: #00FF00; margin-right: 20px;">Enter Shipments Record Into Database</button>
             <button  type="reset" style="font-size: 25px; background-color: #655D1E; color: red;" id="resetButton">Reset Form</button>
            </div>

           </form>
        </fieldset>          


        <section style="display: flex; flex-direction: column; align-items: center; justify-content: center; margin-bottom: 50px;" id="results-section">

            <p style="color: white; font-size: 21px;" id="p-head">Database Results:</p>
            
            <% if(lists != null){ %>
            
            
                        
                    <div style="background-color: green; display: flex; flex-direction: column; align-items: center; justify-content: center; border: 4px solid #FFFF00;">
                        
                         <% for(String s : lists){ %>
                            <p style="color: white; font-size: 21px; margin: 5px;"> <%=s %> </p>    
                        <% } %>
                              
                    </div>
                    
                   
       
                    <%}else if(request.getAttribute("error") != null){ %>
                    <div style="background-color: red; display: flex; flex-direction: column; align-items: center; justify-content: center; border: 4px solid #FFFF00;"">
     
                         <p style="color: white; font-size: 21px; margin: 5px;">Error Executing the SQL statement: </p> 
                         <p style="color: white; font-size: 21px; margin: 5px;"> <%=error %> </p>    
                        
                        
                        
                        
                    </div>
                         
                <%} %>

        </section>
         
         
         
         
         
     <script>
          
        

             
    
             
    </script>
         
  </body>
</html>
