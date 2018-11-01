package servlet;

import entity.UserEntity;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;

import static javax.servlet.http.HttpServletResponse.SC_OK;

@WebServlet("/replaceIOUser")
public class ReplaceIOUserServlet extends HttpServlet {

    @Resource(name = "jdbc/OracleDS")
    private DataSource ds;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection conn = ds.getConnection()){
            PreparedStatement ps = conn.prepareStatement("select * from USERS");
            ResultSet resultSet = ps.executeQuery();
            resp.setStatus(SC_OK);
            resp.setContentType("text/html;charset=UTF8");
            while (resultSet.next()) {
                resp.getWriter().println(new UserEntity(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection conn = ds.getConnection()){
            CallableStatement ps = conn.prepareCall("{call SETRANDOMIOUSER(?)}");
            ps.setString(1, req.getParameter("newIO"));
            ps.executeUpdate();
            resp.setContentType("text/html;charset=UTF8");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
