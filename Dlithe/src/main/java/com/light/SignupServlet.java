package com.light;

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

public class SignupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database credentials
    String JDBC_URL = "jdbc:mysql://localhost:3306/payment";
    String DB_USER = "root";
    String DB_PASSWORD = "Dhanush1309*";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String password1 = request.getParameter("password1");
        try {
            if (!username.endsWith("@gmail.com")) {
                // Display a popup message and redirect to signup.html
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Enter a valid email address.');");
                out.println("window.location.href='form.html';");
                out.println("</script>");
                return; // Stop further execution
            }
            if (password.length() < 8) {
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Password length must be minimum 8 characters.');");
                out.println("window.location.href='form.html';");
                out.println("</script>");
                return; // Stop further execution
            }
            if (!password.equals(password1)) {
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Enter the same password in both fields.');");
                out.println("window.location.href='form.html';");
                out.println("</script>");
                return; // Stop further execution
            }

            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);

            // Check if the username already exists in the database
            String checkSql = "SELECT * FROM user WHERE username = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkSql);
            checkStatement.setString(1, username);
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                // The username already exists in the database, display prompt message
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("<script type=\"text/javascript\">");
                out.println("alert('This email is already registered.');");
                out.println("window.location.href='form.html';");
                out.println("</script>");
                return; // Stop further execution
            }

            // Insert the new user into the database
            String insertSql = "INSERT INTO user(username, password) VALUES (?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertSql,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, username);
            insertStatement.setString(2, password);
            int rowsAffected = insertStatement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = insertStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int userId = generatedKeys.getInt(1);
                    HttpSession session = request.getSession();
                    session.setAttribute("user_Id", userId);
                    response.setContentType("text/html");
                    PrintWriter out = response.getWriter();
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Successfully Signed up.');");
                    out.println("</script>");
                    response.sendRedirect("newcustomer.html");
                    
                }
            }
            insertStatement.close();
            checkStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Error: Failed to insert data into the database.');");
            out.println("window.location.href='form.html';");
            out.println("</script>");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
