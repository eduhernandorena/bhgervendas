package br.com.servlet;

import br.com.ejb.bean.Usuario;
import br.com.ejb.ejb.UsuarioDAORemote;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
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

    @EJB
    private UsuarioDAORemote userdao;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

            Usuario user = new Usuario();
            user.setNome("eduardo");
            user.setSenha("tcc");
            userdao.create(user);
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet teste ae ó</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet teste at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }
}
