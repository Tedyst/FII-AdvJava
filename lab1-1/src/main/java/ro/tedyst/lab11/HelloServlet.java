package ro.tedyst.lab11;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        String t = request.getParameter("str");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>\n<ul>");
        for (int i = 0; i < t.length(); i++) {
            out.println("<li>" + t.charAt(i) + "</li>");
        }
        out.println("</ul></body></html>");
    }

    public void destroy() {
    }
}