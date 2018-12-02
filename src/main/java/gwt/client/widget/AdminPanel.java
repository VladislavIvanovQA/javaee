package gwt.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.http.client.*;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import gwt.client.service.ApplicationService;
import gwt.client.service.ApplicationServiceAsync;

import static gwt.client.gin.ApplicationInjector.INSTANCE;

public class AdminPanel extends Composite {
    private static ApplicationServiceAsync service = INSTANCE.getService();
    private static AdminPanelUiBinder ourUiBinder = GWT.create(AdminPanelUiBinder.class);

    @UiField
    TextArea text;

    @UiField
    Button submit, clear;

    @UiField
    Label result;

    interface AdminPanelUiBinder extends UiBinder<FlowPanel, AdminPanel>{
    }

    public AdminPanel(MainView parent){
        initWidget(ourUiBinder.createAndBindUi(this));
    }


    @UiHandler("submit")
    void clickHandler(ClickEvent env){
        if (text.getText().length() > 1){
            RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, "/js?text=" + text.getText());
            try {
                builder.sendRequest(null, new RequestCallback() {
                    @Override
                    public void onResponseReceived(Request request, Response response) {
                        result.setText(response.getText());
                    }

                    @Override
                    public void onError(Request request, Throwable throwable) {
                        Window.alert("Error js not found");
                    }
                });
            } catch (RequestException e) {
                e.printStackTrace();
            }
        }
    }

    @UiHandler("clear")
    void clickHanlder(ClickEvent event){
        result.setText("");
    }


}
