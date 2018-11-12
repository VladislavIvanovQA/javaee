package gwt.client.widget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.http.client.*;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import gwt.client.service.ApplicationServiceAsync;

import javax.inject.Inject;

import static gwt.client.gin.ApplicationInjector.INSTANCE;

public class RegistrationView extends Composite {
    @UiTemplate("RegistrationPart.ui.xml")
    public interface RegistrationViewUiBinder extends UiBinder<VerticalPanel, RegistrationView> {
    }

    @UiField
    HorizontalPanel loginPanel, passwordPanel, FSPanel, numberPanel, emailPanel, sexPanel;

    @UiField
    TextBox loginTextField, fsTextField, numberTextField, emailTextField;

    @UiField
    PasswordTextBox passwordTextField;

    @UiField
    ListBox listSex;

    @UiField
    Label responseLabel;


    @UiHandler("submit")
    void clickHandler(ClickEvent env){
        StringBuilder string = new StringBuilder();
        string.append("login=" + loginTextField.getValue());
        string.append("&password=" + passwordTextField.getValue());
        string.append("&io=" + fsTextField.getValue());
        string.append("&phone=" + numberTextField.getValue());
        string.append("&email=" + emailTextField.getValue());
        string.append("&sex=" + listSex.getSelectedItemText());
        RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, "/registration");
        try {
            builder.setHeader("Content-Type", "application/x-www-form-urlencoded");
            builder.setRequestData(string.toString());
            builder.sendRequest(String.valueOf(string), new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    if (201 == response.getStatusCode()){
                        responseLabel.setText(response.getText());
                    }
                }

                @Override
                public void onError(Request request, Throwable throwable) {
                    Window.alert("Error!");
                }
            });
        } catch (RequestException e) {
            e.printStackTrace();
        }
    }

    private static RegistrationViewUiBinder ourUiBinder = INSTANCE.getUI();

    private ApplicationServiceAsync service;

    @Inject
    public RegistrationView(ApplicationServiceAsync service) {
        initWidget(ourUiBinder.createAndBindUi(this));
        this.service = service;
    }

}