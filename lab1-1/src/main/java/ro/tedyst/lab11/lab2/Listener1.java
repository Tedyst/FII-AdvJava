package ro.tedyst.lab11.lab2;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Listener1 implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        var context = sce.getServletContext();

        String prelude = context.getInitParameter("prelude");
        String coda = context.getInitParameter("coda");

        context.setAttribute("prelude", prelude);
        context.setAttribute("coda", coda);

        ServletContextListener.super.contextInitialized(sce);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
    }
}
