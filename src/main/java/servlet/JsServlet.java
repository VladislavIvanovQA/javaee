package servlet;

import jdk.nashorn.api.scripting.NashornScriptEngineFactory;

import javax.script.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/js")
public class JsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        NashornScriptEngineFactory factory = new NashornScriptEngineFactory();
        ScriptEngine engine = factory.getScriptEngine(new String[] { "-scripting" });

        Bindings bindings = engine.createBindings();
        bindings.put("count", 3);

        try(PrintWriter pw = response.getWriter()){
            String programForExecution = request.getParameter("text");
            pw.println(engine.eval(programForExecution, bindings));
        }
        catch(ScriptException e){
            e.printStackTrace();
        }
    }
}