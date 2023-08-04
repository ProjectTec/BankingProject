package com.light;

import java.beans.Statement;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	  String jdbcURL = "jdbc:mysql://localhost:3306/payment";
      String dbUser = "root";
      String dbPassword = "Dhanush1309*";

   
      public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	    String username = request.getParameter("username");
    	    String password = request.getParameter("password");
    	    PrintWriter out = response.getWriter();

		    if (!username.endsWith("@gmail.com")) {
		        // Display a popup message and redirect to login.html
		        response.setContentType("text/html");
		        out.println("<script type=\"text/javascript\">");
		        out.println("alert('This is not a valid email address.');");
		        out.println("window.location.href='form.html';");
		        out.println("</script>");
		        return; // Stop further execution
		    }

    	    try {
    	        Class.forName("com.mysql.jdbc.Driver");
    	        Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
    	        String sql = "SELECT * FROM user WHERE username = ?";
    	        PreparedStatement statement = connection.prepareStatement(sql);
    	        statement.setString(1, username);
    	        ResultSet ret = statement.executeQuery();

    	        if (ret.next()) {
    	            if (ret.getString("password").equals(password)) {
	            	       int userId = retrieveUserId(username);
	            	       HttpSession session=request.getSession();
     	                   session.setAttribute("userId", userId);
     	                   
                           response.sendRedirect("home.html");
    	            } else {
    	                // The password is wrong, so pop up an error message.
    	               
    	                out.println("<script type=\"text/javascript\">");
    			        out.println("alert('Password is invalid.');");
    			        out.println("window.location.href='form.html';");
    			        out.println("</script>");
    			        return;
    	            }
    	        } else {
    	        	out.println("<script type=\"text/javascript\">");
 			        out.println("alert('This email is not registered with us kindly Signup first.');");
 			        out.println("window.location.href='form.html';");
 			        out.println("</script>");
 			       
 			        return;
    	           
    	        }

    	        statement.close();
    	        connection.close();
    	        out.println("window.location.href='home.html';");
    	    } catch (ClassNotFoundException | SQLException e) {
    	        e.printStackTrace();
    	    }
    	}
      
      public int retrieveUserId(String username) {
          int userId = 0;

          try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword)) {
              // Create SQL query to retrieve user ID
              String query = "SELECT user_id FROM user WHERE username = ?";
              PreparedStatement statement = conn.prepareStatement(query);
              statement.setString(1, username);

              ResultSet resultSet = statement.executeQuery();
              if (resultSet.next()) {
                  userId = resultSet.getInt("user_id");
              }

              resultSet.close();
              statement.close();
          } catch (SQLException e) {
              e.printStackTrace();
          }
          return userId;
  }



}

