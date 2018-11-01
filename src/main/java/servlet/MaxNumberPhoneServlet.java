package servlet;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Logger;

@WebServlet("/maxPhone")
public class MaxNumberPhoneServlet extends HttpServlet {
    private Logger log = Logger.getLogger(MaxNumberPhoneServlet.class.getName());
    
    @Resource(name = "jdbc/OracleDS")
    private DataSource ds;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection conn = ds.getConnection()){
            File file = new File("log/maxSalary.txt");
            CallableStatement ps = conn.prepareCall("{call GETNAMEFORMAXPHONE(?)}");
            ps.registerOutParameter(1, Types.VARCHAR);
            ps.executeUpdate();
            String IOUser = ps.getString(1);
            resp.setContentType("text/html;charset=UTF8");
            resp.getWriter().println(IOUser);
            try (FileOutputStream fos = new FileOutputStream(file)){
                byte[] buffer = IOUser.getBytes();
                fos.write(buffer);
                log.info("Результат файла записан в файл: " +  file.getAbsolutePath());
            }catch (IOException ex){
                ex.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
