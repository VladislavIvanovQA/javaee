package servlet;

import entity.xml.UserJaxb;
import entity.xml.UsersJaxb;
import entity.xml.util.RandomUser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.*;

import static entity.xml.util.DOMUtil.getDocument;

@WebServlet("/jaxb")
public class JAXBServlet extends HttpServlet{

    private static final String fileName = "/WEB-INF/classes/xml/employee.xml";
    private Document doc;
    private List<Integer> salaryList = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String file = getServletContext().getRealPath(fileName);
        List<UserJaxb> users = new RandomUser().getRandomUser(10);

        UsersJaxb user = new UsersJaxb();
        user.setUser(users);

        try {
            JAXBContext context = JAXBContext.newInstance(user.getClass());
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.marshal(user, new File(file));
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            doc = getDocument(JAXBServlet.class.getResourceAsStream("/xml/employee.xml"));
        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }

//        doc.getDocumentElement().normalize();

        NodeList list = doc.getElementsByTagName("user");

        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            Element el = (Element) node;
            salaryList.add(Integer.valueOf(el.getElementsByTagName("sal").item(0).getTextContent()));
        }
        Double average = salaryList.stream().mapToInt(Integer::intValue).average().getAsDouble();

        for (int i = 0; i < salaryList.size(); i++) {
            Node node = list.item(i).getAttributes().getNamedItem("maxAverage");
            String val = node.getNodeValue();
            if (average.intValue() <= salaryList.get(i)){
                node.setNodeValue(val.replaceAll("none", "up"));
            }else {
                node.setNodeValue(val.replaceAll("none", "down"));
            }
        }


        try {
            Transformer xformer = TransformerFactory.newInstance().newTransformer();
            xformer.transform(new DOMSource(doc), new StreamResult(new File(getServletContext().getRealPath(fileName))));
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        StreamSource stylesource = new StreamSource(getServletContext().getResourceAsStream("/WEB-INF/classes/xml/employee.xsl"));
        StreamSource xmlsource = new StreamSource(getServletContext().getResourceAsStream(fileName));
        resp.setContentType("text/html;charset=UTF-8");

        try(PrintWriter pw = resp.getWriter()) {
            pw.println(new File(file).getAbsolutePath());
            pw.println("<br>");
            pw.println("Среднее значение запрлаты: " + average.intValue());

            Transformer transformer = TransformerFactory.newInstance().newTransformer(stylesource);
            // Transform the document and store it in a file
            transformer.transform(xmlsource, new StreamResult(pw));
        } catch (Exception e) {
            e.printStackTrace();
        }
        users.clear();
        salaryList.clear();
    }
}
