package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.UserEntity;
import servlet.util.*;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;

import static javax.servlet.http.HttpServletResponse.SC_CREATED;

@WebServlet("/upload")
@MultipartConfig
public class FileUploadServlet extends HttpServlet {

    @Resource(name = "jdbc/OracleDS")
    private DataSource ds;

    public static final String FILE_INPUT_NAME = "file";

    public static final String AVAILABLE_FOR_UPLOADING_FILE_EXTENSION = "txt";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "insert into USERS(USERNO, ULOGIN, UPASSWORD, UIO, UPHONE, UEMAIL, USEX)" +
                             "values (?, ?, ?, ?, ?, ?, ?)")){
            final HashMap<Integer, String[]> allLines = new HashMap<>();
            // process all files from multiple file input
            request.getParts().stream()
                    .filter(part -> FILE_INPUT_NAME.equals(part.getName())
                            && part.getSubmittedFileName().toLowerCase().endsWith(AVAILABLE_FOR_UPLOADING_FILE_EXTENSION))// Retrieves <input type="file" name="file" multiple="true">
                    .forEach(part -> {
                        // Scanner тоже неплох :)
                        try (BufferedReader buffer = new BufferedReader(
                                new InputStreamReader(part.getInputStream(), "Cp1251"))) {
                            int i = 1;
                            String line;
                            while ((line = buffer.readLine()) != null){
                                allLines.put(i, line.split(","));
                                i++;
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

            String[] results = new String[allLines.size()];

            for (int i = 1; i <= allLines.size(); i++) {
                long number;
                String login;
                String password;
                String io;
                String phone;
                String email;
                String sex;

                ps.setLong(1, number = Long.parseLong(allLines.get(i)[0].trim()));
                ps.setString(2, login = allLines.get(i)[1].trim());
                ps.setString(3, password = allLines.get(i)[2].trim());
                ps.setString(4, io = allLines.get(i)[3].trim());
                ps.setString(5, phone = allLines.get(i)[4].trim());
                ps.setString(6, email = allLines.get(i)[5].trim());
                ps.setString(7, sex = allLines.get(i)[6].trim());
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
                results[i-1] = result;
            }
            response.setStatus(SC_CREATED);
            response.setContentType("text/html;charset=UTF8");
            response.setHeader("Content-type", "application/json");
            try (PrintWriter pw = response.getWriter()){
                pw.println(Arrays.toString(results));
            }
        } catch (SQLException e) {
        e.printStackTrace();
        }
    }
}