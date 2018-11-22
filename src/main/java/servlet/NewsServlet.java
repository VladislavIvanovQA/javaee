package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@WebServlet("/news")
public class NewsServlet extends HttpServlet {
    String text;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpURLConnection httpConnection = (HttpURLConnection)
                new URL("https://www.rbc.ru/v10/ajax/main/region/world/publicher/main_main").openConnection();
        BufferedReader stream = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), "Cp1251"));
        resp.setContentType("application/json;charset=UTF-8");
        while ((text = stream.readLine()) != null) {
            resp.getWriter().print(new String(text.getBytes("Cp1251"), "UTF-8"));
        }
        stream.close();
    }
}
