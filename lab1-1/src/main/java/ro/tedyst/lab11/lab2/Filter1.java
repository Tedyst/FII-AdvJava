package ro.tedyst.lab11.lab2;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = {"/input.jsp"})
public class Filter1 implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String ipAddress = request.getRemoteAddr();
        String host = request.getHeader("Host");
        String userAgent = request.getHeader("User-Agent");
        String path = request.getRequestURI();

        System.out.println("IP: " + ipAddress + " Host: " + host + " User-Agent: " + userAgent + " Path: " + path);

        filterChain.doFilter(request, servletResponse);
    }
}
