package ro.tedyst.lab11.lab2;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(urlPatterns = {"/*"})
public class Filter2 implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ServletContext sc  = servletRequest.getServletContext();

        String prelude = (String) sc.getAttribute("prelude");
        String coda = (String) sc.getAttribute("coda");

        HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper((HttpServletResponse) servletResponse);

        filterChain.doFilter(servletRequest, wrapper);

        String content = prelude + wrapper.toString() + coda;

        PrintWriter out = servletResponse.getWriter();
        out.write(content);
    }
}
