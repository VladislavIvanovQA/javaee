package servlet;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static javax.servlet.http.HttpServletResponse.*;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {

    @Resource(name = "jdbc/OracleDS")
    private DataSource ds;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try (Connection conn = ds.getConnection()){
            boolean isLogin = false;
            boolean isPassword = false;
            PreparedStatement ps = conn.prepareStatement("select * from USERS");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                if (req.getParameter("login").equals(
                        resultSet.getString("ULOGIN"))) {
                    isLogin = true;
                    if (req.getParameter("password").equals(
                            resultSet.getString("UPASSWORD"))) {
                        isPassword = true;
                    }
                }
            }
            if (isLogin) {
                if (isPassword){
                    resp.setStatus(SC_OK);
                    resp.getWriter().println("Login success! With " + req.getParameter("login"));
                }else {
                    resp.setStatus(SC_BAD_REQUEST);
                    resp.getWriter().println("Password abort! Try again!");
                }
            }else {
                resp.setStatus(SC_BAD_REQUEST);
                resp.getWriter().println("Login abort! Try again!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
