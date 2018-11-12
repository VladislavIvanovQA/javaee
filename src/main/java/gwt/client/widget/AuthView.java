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
import gwt.shared.User;
import gwt.shared.validation.ValidationRule;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import java.util.Set;

import static gwt.client.gin.ApplicationInjector.INSTANCE;

public class AuthView extends Composite {
    @UiTemplate("AuthPart.ui.xml")
    public interface AuthViewUiBinder extends UiBinder<VerticalPanel, AuthView> {
    }

    @UiField
    HorizontalPanel loginP, passwordP;

    @UiField
    TextBox loginText;

    @UiField
    PasswordTextBox passwordText;

    @UiHandler("submit")
    void clickHandler(ClickEvent env) {
        StringBuilder string = new StringBuilder();
        string.append("login=" + loginText.getValue());
        string.append("&password=" + passwordText.getValue());
        RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, "/auth");
        try {
            builder.setHeader("Content-Type", "application/x-www-form-urlencoded");
            builder.setRequestData(string.toString());
            builder.sendRequest(String.valueOf(string), new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    if (202 == response.getStatusCode()) {
                        Window.alert("Success");
                    }else {
                        Window.alert("Try again!");
                        loginText.setText("");
                        passwordText.setText("");
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

    private static AuthViewUiBinder ourUiBinder = INSTANCE.getUIAuth();

    private ApplicationServiceAsync service;

    @Inject
    public AuthView(ApplicationServiceAsync service) {
        initWidget(ourUiBinder.createAndBindUi(this));
        this.service = service;
    }

}