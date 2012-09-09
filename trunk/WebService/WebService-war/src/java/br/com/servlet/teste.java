package br.com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Eduardo Hernandorena
 */
@WebServlet(name = "teste", urlPatterns = {"/teste"})
public class teste extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            System.out.println("Inserido " );
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet teste ae ó</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet teste at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
