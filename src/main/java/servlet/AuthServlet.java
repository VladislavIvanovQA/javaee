package servlet;

import com.google.gson.Gson;
import entity.UserEntity;
import org.json.JSONObject;

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

import static com.google.gwt.http.client.Response.*;

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
            resp.setContentType("application/json;charset=UTF8");
            UserEntity user = new UserEntity();

            if (resultSet.next()) {
                resp.setStatus(SC_ACCEPTED);
                user.setNumber(resultSet.getLong(1));
                user.setLogin(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setName(resultSet.getString(4));
                user.setPhone(resultSet.getString(5));
                user.setEmail(resultSet.getString(6));
                user.setSex(resultSet.getString(7));

                Gson gson = new Gson();
                Object obj = gson.toJson(user);
                resp.getWriter().println(obj);
            }else{
                resp.setStatus(SC_NO_CONTENT);
                resp.getWriter().println("Not Found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
