<%-- 
    Document   : it351
    Created on : Dec 14, 2017, 11:30:45 AM
    Author     : Powell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="it351web" class="it351web.webapp" scope="session"/>
<%@ page import="java.util.*" %>
<%@ page import = "java.net.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.sql.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Records Lookup Page</title>
    </head>
    <body>
        <style type="text/css">
            body {
            color: #F1BE2B;
            background-color: #333131 }
            
            input.button {
                position: relative;
                left: 750px;
                top: 500px;
                z-index: 0;
            }
            
            input.text1{
                position: absolute;
                left: 880px;
                top: 510px;
                z-index: 0;
            }
            
            input.text2{
                position: absolute;
                left: 785px;
                top: 550px;
                z-index: 0;
            }
            
            h4.one {
                position: absolute;
                left: 858px;
                top: 470px;
                z-index: 0;
            }
            
            h4.two {
                position: absolute;
                left: 855px;
                top: 510px;
                z-index: 0;
            }
        </style>
        <%      
                String casted = request.getParameter("button");
                if (casted == null){
                    casted = "";
                }
                // if and else if loops dependent on the string assigned to the casted variable
                // logic was copied from my worker class in the server application
                if(casted.equals("Next")){
                    it351web.setRowIndicatorMain(it351web.nextHighestRow());
                    String record = it351web.readRecord();
                    if(record == null)
                        record = "record returned null";
                    if(record.equals("empty")){
                        it351web.setRowIndicatorMain(it351web.getRowIndicatorMain() - 1);
                        record = it351web.readRecord();
                    }
                    
                    it351web.setCustomerRecord(record);
                }
                
                else if(casted.equals("Previous")){
                    it351web.setRowIndicatorMain(it351web.nextLowestRow());
                    String record = it351web.readRecord();
                    if(record == null)
                        record = "record returned null";
                    if(record.equals("empty")){
                        record = "Empty Entry - Please Search Next";
                        if(it351web.getRowIndicatorMain() < 1){
                        it351web.setRowIndicatorMain(0);
                        record = "End of Rows - Please Search Next Record";
                        }
                        
                    }
                    
                    it351web.setCustomerRecord(record);
                }
                
                else if(casted.equals("Update")){
                    String data = "test update";
                    it351web.updateRecord(data);
                    
                    it351web.setCustomerRecord(data);
                }
                
                else if(casted.equals("Delete")){
                    it351web.deleteRecord();
                    
                    it351web.setCustomerRecord("Row Deleted");
                }
                
                else if(casted.equals("Create")){
                    it351web.createRecord("test");
                    it351web.setCustomerRecord("Test record created! Hit next to view!");
                }
                
        %>
        <form action="database.jsp" method="POST">
            <h4 class="one">Record Number</h4>
         <input class="text1" type="text" name="rowIndicatorMain" size="5" value="<%= it351web.getRowIndicatorMain() %>"/> 
         <br>
         <br>
            <h4 class="two">Customer Record</h4>
         <input class="text2" type="text" name="customerRecord" size="40" value="<%= it351web.getCustomerRecord() %>"/>
         <br>
         <br>
         <input class="button" type="submit" name="button" value="Previous"/>
         <input class="button" type="submit" name="button" value="Next"/>
         <input class="button" type="submit" name="button" value="Update"/>
         <input class="button" type="submit" name="button" value="Create"/>
         <input class="button" type="submit" name="button" value="Delete"/>
        </form>

    </body>
</html>
