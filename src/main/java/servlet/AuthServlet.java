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
import java.util.logging.Logger;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
    private Logger log = Logger.getLogger(MaxNumberPhoneServlet.class.getName());

    @Resource(name = "jdbc/OracleDS")
    private DataSource ds;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection conn = ds.getConnection()){
            PreparedStatement ps = conn.prepareStatement(
                    "select * from USERS where ULOGIN = ? and UPASSWORD = ?");
            ps.setString(1, req.getParameter("login"));
            ps.setString(2, req.getParameter("password"));
            ResultSet resultSet = ps.executeQuery();
            resp.setContentType("text/html;charset=UTF8");
            if (resultSet.next()) {
                resp.getWriter().print(resultSet.getString(1));
                resp.getWriter().print(resultSet.getString(2));
                resp.getWriter().print(resultSet.getString(3));
                resp.getWriter().print(resultSet.getString(4));
                resp.getWriter().print(resultSet.getString(5));
                resp.getWriter().print(resultSet.getString(6));
                resp.getWriter().print(resultSet.getString(7));
            }else{
                resp.getWriter().println("Not Found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
