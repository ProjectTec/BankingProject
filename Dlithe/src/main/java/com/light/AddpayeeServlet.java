package com.light;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AddpayeeServlet extends HttpServlet {
	/**
	 * 
	 */
	String JDBC_URL = "jdbc:mysql://localhost:3306/payment";
    String DB_USER = "root";
    String DB_PASSWORD = "Dhanush1309*";
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		int userid = (int)session.getAttribute("userId");
        String name=request.getParameter("name");
        String account_number=request.getParameter("account-number");
    	String ifsc_code=request.getParameter("ifsc-code");
    	try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
   	        String sql =  "INSERT INTO payee (payee_name,payee_account_num,payee_ifsccode,user_id) VALUES (?, ?, ?, ?)";
 	       
   	       

   	     PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,name);
            statement.setString(2,account_number);
            statement.setString(3,ifsc_code);
            statement.setInt(4,userid);
            statement.executeUpdate();
            statement.close();
            connection.close();
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Addded Successfully');");
            out.println("window.location.href='home.html';");
            out.println("</script>");
            
   	}catch (ClassNotFoundException | SQLException e) {
           e.printStackTrace();
	}

}
}
