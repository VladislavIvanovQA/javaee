package servlet;

import entity.UserEntity;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import static java.nio.charset.StandardCharsets.UTF_8;
import static javax.servlet.http.HttpServletResponse.SC_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_OK;

@WebServlet("/users")
public class UsersServlet extends HttpServlet implements UsersEntities{
    private Logger log = Logger.getLogger(UsersServlet.class.getName());

    @Resource(name = "jdbc/OracleDS")
    private DataSource ds;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        File file = new File("log/result.txt");
        try (Connection conn = ds.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("select * from USERS");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                UserEntity user = new UserEntity(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7));

                userEntity.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.setStatus(SC_OK);
        userEntity.sort(new SortById());
        // TODO: Не могу понять как вевести нормально русские символы..
        for (UserEntity userEntity : userEntity) {
            response.getWriter().println(userEntity);
        }
        try (FileOutputStream fos = new FileOutputStream(file)){
            byte[] buffer = userEntity.toString().getBytes(UTF_8);
            fos.write(buffer);
            log.info("Результат файла записан в файл: " +  file.getAbsolutePath());
        }catch (IOException ex){
            ex.printStackTrace();
        }
        userEntity.clear();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "update USERS " +
                             "set UIO = ?, UEMAIL = ? " +
                             "where ULOGIN = ?")){
            ps.setString(1, "Vladislav Ivanov");
            ps.setString(2, "ivanovvladislav@mail.ru");
            ps.setString(3, req.getParameter("login"));
            ps.executeUpdate();
            resp.setStatus(SC_FOUND);
            resp.getWriter().println("User '" + req.getParameter("login") + "' has been updated");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "delete from USERS " +
                             "where ULOGIN = ?")) {
            ps.setString(1, req.getParameter("login"));
            ps.executeUpdate();
            resp.setStatus(SC_OK);
            resp.getWriter().println("User '" + req.getParameter("login") + "' has been deleted");
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

class SortById implements Comparator<UserEntity>{

    public int compare(UserEntity a, UserEntity b){
        return (int) (a.getNumber() - b.getNumber());
    }
}
