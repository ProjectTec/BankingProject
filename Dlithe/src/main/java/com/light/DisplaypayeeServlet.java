package com.light;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import jakarta.servlet.RequestDispatcher;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//
//public class DisplaypayeeServlet extends HttpServlet {
//
//	private static final long serialVersionUID = 1L;
//	String jdbcURL = "jdbc:mysql://localhost:3306/payment";
//	String dbUser = "root";
//	String dbPassword = "Dhanush1309*";
//
//	@Override
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		HttpSession session = request.getSession();
//		Integer userId = (Integer) session.getAttribute("userId");
//		PrintWriter out = response.getWriter();
//		try {
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection connection = DriverManager.getConnection( jdbcURL,dbUser,dbPassword);
//   	        String sql =  "SELECT payee_name FROM payee where user_id=?";
// 	        PreparedStatement statement = connection.prepareStatement(sql);
// 	        statement.setInt(1,userId);
// 	       ResultSet resultSet = statement.executeQuery();
// 	      while (resultSet.next()) {
// 	         String payee = resultSet.getString("payee_name");
// 	         out.println("Payee: " + payee);
// 	     }
//
////           List<String> payeeNames = new ArrayList<>();
////           while (resultSet.next()) {
////               payeeNames.add(resultSet.getString("payee_name"));
////           }
//           resultSet.close();
//           statement.close();
//           connection.close();
//          
//
////          Store the payee names in the request attribute
////           request.setAttribute("payeeNames", payeeNames);
////
////           // payeelist1.html kalsu
////           RequestDispatcher dispatcher = request.getRequestDispatcher("/payeelist1.html");
////           dispatcher.forward(request, response);
//       } catch (SQLException e) {
//           e.printStackTrace();
//           response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//       } catch (ClassNotFoundException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//}
//	}



import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class DisplaypayeeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    String jdbcURL = "jdbc:mysql://localhost:3306/payment";
    String dbUser = "root";
    String dbPassword = "Dhanush1309*";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
            String sql = "SELECT payee_name FROM payee where user_id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            StringBuilder payeeNames = new StringBuilder();
            while (resultSet.next()) {
                String payee = resultSet.getString("payee_name");
                payeeNames.append(URLEncoder.encode(payee, "UTF-8")).append("|");
            }

            resultSet.close();
            statement.close();
            connection.close();

            // Remove trailing delimiter if present
            if (payeeNames.length() > 0) {
                payeeNames.deleteCharAt(payeeNames.length() - 1);
            }

            // Set the payee names as a cookie
            Cookie cookie = new Cookie("payeeNames", payeeNames.toString());
            cookie.setPath("/");
            response.addCookie(cookie);

            response.sendRedirect("payeelist1.html");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}


