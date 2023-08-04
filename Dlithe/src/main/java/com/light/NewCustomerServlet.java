package com.light;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class NewCustomerServlet extends HttpServlet {
	 
	private static final long serialVersionUID = 1L;
	String jdbcURL = "jdbc:mysql://localhost:3306/payment";
	String dbUser = "root";
	String dbPassword = "Dhanush1309*";

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int userid = (int)session.getAttribute("user_Id");
	   //response.getWriter().println("user ID"+userid);
	    String Name=request.getParameter("name");
		 String Occupation=request.getParameter("occupation");
         String date_of_birth=request.getParameter("date");
         String address=request.getParameter("address");
         String gender=request.getParameter("gender");
         String bank_name=request.getParameter("bank_Name");
         String account_number=request.getParameter("ac_num");
         String ifsc_code=request.getParameter("ifsc");
         String exp=request.getParameter("exp");
        
         String mobile_num = request.getParameter("mobile");
         String card_number = request.getParameter("card_num");
         String cvv =request.getParameter("cvv");
         try {
         Class.forName("com.mysql.jdbc.Driver");
         Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
	     String sql = "UPDATE user SET name = ?, occupation = ?,date_of_birth=?,address=?,gender = ?,mobile_num=? WHERE user_id = ?";
	       

	     PreparedStatement statement = connection.prepareStatement(sql);
         statement.setString(1,Name);
         statement.setString(2,Occupation);
         statement.setString(3,date_of_birth);
         statement.setString(4,address);
         statement.setString(5,gender);
         statement.setString(6,mobile_num);
         statement.setInt(7, userid);
         statement.executeUpdate();
         statement.close();
         connection.close();
         
	}catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
    }try {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
        String instsql = "INSERT INTO account (account_number, bank_name, ifsc_code, card_number, cvv, cardexp, user_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
	       

	     PreparedStatement stmt = connection.prepareStatement(instsql);
	     stmt.setString(1,account_number);
         stmt.setString(2,bank_name);
         stmt.setString(3,ifsc_code);
         stmt.setString(4,card_number);
         stmt.setString(5,cvv);
         stmt.setString(6,exp);
         stmt.setInt(7, userid);
         stmt.executeUpdate();
         stmt.close();
         connection.close();
         response.setContentType("text/html");
         PrintWriter out = response.getWriter();
         out.println("<script type=\"text/javascript\">");
         out.println("alert('Your succesfully registered with the details Now please login');");
         out.println("window.location.href='form.html';");
         out.println("</script>");
        
	}catch (ClassNotFoundException | SQLException e) {
       e.printStackTrace();
   }
	}
}