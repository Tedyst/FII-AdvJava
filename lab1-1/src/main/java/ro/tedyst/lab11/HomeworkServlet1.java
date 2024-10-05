package ro.tedyst.lab11;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

@WebServlet(name = "homeworkServlet1", value = "/hw-servlet-1")
public class HomeworkServlet1 extends HttpServlet {
    Random rand = new Random();

    public void init() {

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("Method: " + request.getMethod());
        System.out.println("IP Address: " + request.getRemoteAddr());
        System.out.println("User Agent: " + request.getHeader("User-Agent"));
        System.out.println("Languages: " + request.getHeader("Accept-Language"));

        PrintWriter out = response.getWriter();
        int numEdges, numVertices;
        try {
            numVertices = Integer.parseInt(request.getParameter("numVertices"));
            numEdges = Integer.parseInt(request.getParameter("numEdges"));
        }
        catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            return;
        }

        int[][] adjacencyMatrix = new int[numVertices][numVertices];

        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                adjacencyMatrix[i][j] = 0;
                adjacencyMatrix[j][i] = 0;
            }
        }

        for (int i = 0; i < numVertices; i++) {
            int edge1 = rand.nextInt(numEdges);
            int edge2 = rand.nextInt(numEdges);

            if (edge1 == edge2 || adjacencyMatrix[edge1][edge2] == 1) {
                i --;
            }

            adjacencyMatrix[edge1][edge2] = 1;
            adjacencyMatrix[edge2][edge1] = 1;
        }

        out.println("<html><body><table>");
        for (int i = 0; i < numVertices; i++) {
            out.println("<tr>");
            for (int j = 0; j < numVertices; j++) {
                out.print("<th>" + adjacencyMatrix[i][j] + "</th>");
            }
            out.println("</tr>");
        }
        out.println("</table></body></html>");
    }

    public void destroy() {
    }

}
