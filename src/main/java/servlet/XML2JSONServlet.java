package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXB;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.jayway.jsonpath.JsonPath;
import entity.xml.UserJaxb;
import entity.xml.UsersJaxb;
import entity.xml.util.RandomUser;
import org.json.JSONObject;
import org.json.XML;
import org.xml.sax.InputSource;

@WebServlet("/xml2json")
public class XML2JSONServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(XML2JSONServlet.class.getName());

    private static final String fileXml = "/WEB-INF/classes/xml/employee.xml";
    private static final String fileJson = "/WEB-INF/classes/xml/employee.json";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserJaxb> users = new RandomUser().getRandomUser(10);

        UsersJaxb user = new UsersJaxb();
        user.setUser(users);

        String XMLfile;

        try(StringWriter sw = new StringWriter()) {
            JAXB.marshal(user, sw);
            XMLfile = sw.toString();
        }

        File file = new File(getServletContext().getRealPath(fileJson));

        JSONObject json = XML.toJSONObject(XMLfile);
        try (FileOutputStream fos = new FileOutputStream(file)){
            byte[] buffer = json.toString().getBytes();
            fos.write(buffer);
        }

        InputStream resource = XML2JSONServlet.class.getResourceAsStream("/xml/employee.json");
        String js = new BufferedReader(new InputStreamReader(resource,
                StandardCharsets.UTF_8)).lines().collect(Collectors.joining());
        List<Integer> useno = JsonPath.read(js, "$.users.user[*].userno");

        List<Integer> badNum = new ArrayList<>();

        List<String> listJsonObj = new ArrayList<>();

        for (Integer anUseno : useno) {
            if ((anUseno % 2) != 0) {
                badNum.add(anUseno);
            }
        }
        for (Integer aBadNum : badNum) {
            listJsonObj.add(JsonPath.read(js, "$.users.user[?(@.userno == " + aBadNum + ")]"));
        }

        resp.setContentType("text/html;charset=UTF8");
        resp.setHeader("Content-type", "application/json");

        try (PrintWriter pw = resp.getWriter()){
            pw.println(js);
            pw.println("Результат файла записан в файл: " +  file.getAbsolutePath());
            pw.println(listJsonObj);

        }

//        users.clear();
//        useno.clear();
//        badNum.clear();
//        listJsonObj.clear();
    }
}
