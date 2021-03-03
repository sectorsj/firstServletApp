package ru.geekbrains.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet (name = "FirstServlet", urlPatterns = "/first_servlet")
public class FirstServlet extends HttpServlet {
    public static Logger logger = LoggerFactory.getLogger(FirstServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws
            ServletException, IOException {
        logger.info("New GET request");
        resp.getWriter().printf("<h1>New GET request</h1>");

        logger.info("User agent: {}", req.getHeader("User-agent"));
        resp.getWriter().printf("<h1>New GET request with parameters param1 = %s; param2 = %s</h1>",
                req.getParameter("param1"),
                req.getParameter("param2"));

        resp.setHeader("Content-Type", "text/html; charset=utf-8");
        resp.getWriter().printf("<h1>New GET request</h1>");

        logger.info("New GET request with redirection");
        resp.sendRedirect(req.getContextPath() + "/basic_servlet");

        //example absolute link
        //resp.sendRedirect("https:/ya.ru");

        logger.info("New GET request with forward");
        req.setAttribute("newAttr", "attrValue");
        getServletContext().getRequestDispatcher("/index.html")
        .forward(req, resp);

        getServletContext().getRequestDispatcher("/header.html").include(req, resp);
            resp.getWriter().printf("<p>Response body from servlet</p>");

        getServletContext().getRequestDispatcher("/footer.html").include(req, resp);

        logger.info("New GET request with includes");
        resp.getWriter().println("<p>Response with cookies</p>");
        Cookie cookie = new Cookie("user", "");
        cookie.setMaxAge(0);
        resp.addCookie(cookie);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws
            ServletException, IOException {
        logger.info("New POST request");
        resp.getWriter().printf("<h1>New POST request with body %s</h1>", readAllLines(req.getReader()));
    }

    public String readAllLines(BufferedReader reader) throws IOException {
        StringBuilder content = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            content.append(line);
            content.append(System.lineSeparator());
        }
        return content.toString();
    }
}