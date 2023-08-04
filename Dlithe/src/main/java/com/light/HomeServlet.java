package com.light;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class HomeServlet extends HttpServlet{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 String jdbcURL = "jdbc:mysql://localhost:3306/payment";
     String dbUser = "root";
     String dbPassword = "Dhanush1309*";
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
		 
	   HttpSession session=request.getSession();
	   int userid=(int)session.getAttribute("userId");
	   //response.getWriter().println("user ID"+userid);
	   String Name=request.getParameter("Name");
	   String Occupation=request.getParameter("Occupation");
	   int income=Integer.parseInt(request.getParameter("Income"));
	  
	   try {
	        Class.forName("com.mysql.jdbc.Driver");
	        Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
	        String sql = "UPDATE user SET name = ?, occupation = ?, income = ? WHERE userid = ?";
	       

	        PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,Name);
            statement.setString(2,Occupation);
            statement.setInt(3,income);
            statement.setInt(4, userid);
            statement.executeUpdate();
            statement.close();
             connection.close();
	   }catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    }

}
}