package gwt.server;

import com.google.gwt.http.client.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import gwt.client.service.ApplicationService;
import gwt.shared.GwtApplicationException;

public class ApplicationServiceImpl extends RemoteServiceServlet implements ApplicationService {
    // Implementation of sample interface method
    private String text = "";

    @Override
    public String getCurrencies() {
        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, "https://www.cbr-xml-daily.ru/daily_utf8.xml");
        try{
            builder.sendRequest(null, new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    if (200 == response.getStatusCode()){
                        text = response.getText();
                    }
                }
                @Override
                public void onError(Request request, Throwable throwable) {
                    Window.alert("Error");
                }
            });
        }catch (RequestException e){
            e.printStackTrace();
        }
        return text;
    }

    @Override
    public String getNews()  throws GwtApplicationException {
        return "Hello from server news!";
    }
}