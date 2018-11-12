package gwt.client.widget;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.http.client.*;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.xml.client.*;
import gwt.client.service.ApplicationServiceAsync;
import gwt.shared.User;
import gwt.shared.exception.WrongCredentialException;
import gwt.shared.validation.ValidationRule;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import java.util.Set;

import static gwt.client.gin.ApplicationInjector.INSTANCE;

public class MainView extends Composite {
    @UiTemplate("MainPart.ui.xml")
    public interface MainViewUiBinder extends UiBinder<VerticalPanel, MainView> {
    }

    @UiField
    HorizontalPanel infoPanelUSD, infoPanelEUR, infoPanelCNY, infoPanelJPY;

    @UiField
    Label textUSD, textEUR, textCNY, textJPY;

    @UiField
    Label valueUSD, valueEUR, valueCNY, valueJPY;

    private Document doc;

    @Override
    protected void onLoad() {
        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, "https://www.cbr-xml-daily.ru/daily_utf8.xml");
        try{
//            builder.setHeader("Access-Control-Allow-Origin", "*");
            builder.sendRequest(null, new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    if (200 == response.getStatusCode()){
                        parseMessage(response.getText());
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
    }

    private void parseMessage(String messageXml){
        try {
            doc = XMLParser.parse(messageXml);
            NodeList list = doc.getElementsByTagName("Valute");
            for (int i = 0; i < list.getLength(); i++) {
                switch (doc.getElementsByTagName("Name").item(i).getFirstChild().getNodeValue()){
                    case "Доллар США" :
                        textUSD.setText(doc.getElementsByTagName("Name").item(i).getFirstChild().getNodeValue());
                        valueUSD.setText(doc.getElementsByTagName("Value").item(i).getFirstChild().getNodeValue());
                        break;
                    case "Евро" :
                        textEUR.setText(doc.getElementsByTagName("Name").item(i).getFirstChild().getNodeValue());
                        valueEUR.setText(doc.getElementsByTagName("Value").item(i).getFirstChild().getNodeValue());
                        break;
                    case "Китайских юаней" :
                        textCNY.setText(doc.getElementsByTagName("Name").item(i).getFirstChild().getNodeValue());
                        valueCNY.setText(doc.getElementsByTagName("Value").item(i).getFirstChild().getNodeValue());
                        break;
                    case "Японских иен" :
                        textJPY.setText(doc.getElementsByTagName("Name").item(i).getFirstChild().getNodeValue());
                        valueJPY.setText(doc.getElementsByTagName("Value").item(i).getFirstChild().getNodeValue());
                        break;
                }
            }
        }catch (DOMException e){
            Window.alert("Could not parse XML document.");
        }
    }

    private static MainViewUiBinder ourUiBinder = INSTANCE.getUiBinder();

    private ApplicationServiceAsync service;

    @Inject
    public MainView(ApplicationServiceAsync service) {
        initWidget(ourUiBinder.createAndBindUi(this));
        this.service = service;
    }
}