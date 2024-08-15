package com.frey.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class StudentServlet implements Servlet {

    public void init(ServletConfig config) throws ServletException {
        // Initialization code
    }

    public ServletConfig getServletConfig() {
        return null; // Not needed for this example
    }

    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json"); // Set content type to JSON
        res.setCharacterEncoding("UTF-8"); // Set encoding to UTF-8

        PrintWriter out = res.getWriter();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/servletstart";
            String user = "root";
            String password = "mypassword";

            conn = DriverManager.getConnection(url, user, password);
            String sql = "SELECT username, email FROM user";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            // Create a StringBuilder to construct JSON response
            StringBuilder jsonResponse = new StringBuilder();
            jsonResponse.append("["); // Start of JSON array

            boolean first = true;

            while (rs.next()) {
                if (!first) {
                    jsonResponse.append(","); // Add a comma for all but the first item
                }
                first = false;

                String name = rs.getString("username");
                String email = rs.getString("email");

                // Append user data as JSON object
                jsonResponse.append("{")
                            .append("\"username\":\"").append(name).append("\",")
                            .append("\"email\":\"").append(email).append("\"")
                            .append("}");
            }

            jsonResponse.append("]"); // End of JSON array

            // Write JSON response
            out.print(jsonResponse.toString());

        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception - you might want to return an error message in JSON format
        } finally {
            // Close resources
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getServletInfo() {
        return "StudentServlet";
    }

    public void destroy() {
        // Cleanup code
    }
}
