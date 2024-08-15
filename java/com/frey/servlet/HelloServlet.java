package com.frey.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class HelloServlet implements Servlet {
    
    public void init(ServletConfig config) throws ServletException {
        // Initialization code
    }

    public ServletConfig getServletConfig() {
        return null; // Not needed for this example
    }

    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
       // Set content type to application/json
       res.setContentType("application/json");
       res.setCharacterEncoding("UTF-8");
       
       // Get the output writer
       PrintWriter out = res.getWriter();
       
       // Write JSON response
       out.println("{");
       out.println("  \"message\": \"Hello from HelloServlet\",");
       out.println("  \"status\": \"success\"");
       out.println("}");
       
       // Close writer
       out.close();
    }

    public String getServletInfo() {
        return "HelloServlet";
    }

    public void destroy() {
        // Cleanup code
    }
}
