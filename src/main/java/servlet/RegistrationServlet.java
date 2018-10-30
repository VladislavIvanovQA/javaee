package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.UserEntity;
import servlet.util.*;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static javax.servlet.http.HttpServletResponse.*;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    @Resource(name = "jdbc/OracleDS")
    private DataSource ds;

    private long number;
    private String login;
    private String password;
    private String io;
    private String phone;
    private String email;
    private String sex;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try (Connection conn = ds.getConnection();
         PreparedStatement ps = conn.prepareStatement(
                 "insert into USERS(USERNO, ULOGIN, UPASSWORD, UIO, UPHONE, UEMAIL, USEX)" +
                         "values (?, ?, ?, ?, ?, ?, ?)")) {
        ps.setLong(1, number = Generator.generateDigits(4));
        ps.setString(2, login = req.getParameter("login"));
        ps.setString(3, password = req.getParameter("password"));
        ps.setString(4, io = req.getParameter("io"));
        ps.setString(5, phone = req.getParameter("phone"));
        ps.setString(6, email = req.getParameter("email"));
        ps.setString(7, sex = req.getParameter("sex"));
        ps.executeUpdate();

        UserEntity user = new UserEntity();
        user.setNumber(number);
        user.setLogin(login);
        user.setPassword(password);
        user.setName(io);
        user.setPhone(phone);
        user.setEmail(email);
        user.setSex(sex);
        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(user);

        resp.setStatus(SC_CREATED);
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Content-type", "application/json");
        try (PrintWriter pw = resp.getWriter()){
            pw.println(result);
        }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
