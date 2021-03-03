package ru.geekbrains.servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;


@WebFilter(urlPatterns = "/*",dispatcherTypes = {DispatcherType.REQUEST,DispatcherType.FORWARD})
public class HeaderFooterFilter implements Filter {
    private transient FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(
            ServletRequest req, ServletResponse resp,
            FilterChain chain) throws IOException, ServletException {
        filterConfig.getServletContext().
                getRequestDispatcher("/header.html").include(req, resp);
        chain.doFilter(req, resp);

        filterConfig.getServletContext().
                getRequestDispatcher("/footer.html").include(req, resp);
    }

    @Override
    public void destroy() {

    }
}
