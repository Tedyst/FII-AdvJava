package ro.tedyst.lab11.lab2;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

@WebServlet(name = "file-upload", value = "/file-upload")
@MultipartConfig(location="/tmp", fileSizeThreshold=1024*1024,
        maxFileSize=1024*1024*5, maxRequestSize=1024*1024*5*5)
public class FileUploadServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String contentType = request.getContentType();

        if(!(contentType.indexOf("multipart/form-data") == 0)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "You must send a file");
            return;
        }

        request.setCharacterEncoding("UTF-8");
        Part file = request.getPart("file");
        BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
        List<String> data = new java.util.ArrayList<>(br.lines().toList());

        Collections.shuffle(data);

        request.setAttribute("lines", data);
        request.getRequestDispatcher("/result.jsp").forward(request, response);
    }
}
