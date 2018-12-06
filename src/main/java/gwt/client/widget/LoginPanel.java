package gwt.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.http.client.*;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import gwt.client.service.LoginServiceAsync;
import gwt.shared.User;
import gwt.shared.validation.ValidationRule;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static gwt.client.gin.ApplicationInjector.INSTANCE;

public class LoginPanel extends Composite {
    public interface LoginPanelUiBinder extends UiBinder<VerticalPanel, LoginPanel> {
    }

    private static LoginServiceAsync service = INSTANCE.getUIAuth();
    private static LoginPanelUiBinder ourUiBinder = GWT.create(LoginPanelUiBinder.class);

    private MainView parent;

    @UiField
    TextBox login;

    @UiField
    PasswordTextBox password;

    @UiField
    HorizontalPanel loginpanel;

    @UiField
    HorizontalPanel passwordpanel;

    @UiField
    Label completionLabel1, completionLabel2;

    @UiField
    Button loginsubmit;

    private Image loginInvalidFieldImage;
    private Image passwordInvalidFieldImage;

    private Boolean tooShort = false;

    public LoginPanel(MainView parent) {
        initWidget(ourUiBinder.createAndBindUi(this));
        this.parent = parent;
    }

    @UiHandler("login")
    void handleLoginChange(ValueChangeEvent<String> event){
        if (event.getValue().length() < 2){
            completionLabel1.setText("Логин короткий. Введите более 2 символов.");
            loginsubmit.setEnabled(false);
        }else {
            tooShort = true;
            completionLabel1.setText("");
        }
    }


    @UiHandler("password")
    void handlePasswordChange(ValueChangeEvent<String> event){
        if (event.getValue().length() < 2){
            completionLabel2.setText("Пароль короткий. Введите более 2 символов.");
            loginsubmit.setEnabled(false);
        }else {
            tooShort = true;
            completionLabel2.setText("");
            loginsubmit.setEnabled(true);
        }
    }

    @UiHandler("loginsubmit")
    void clickHandler(ClickEvent env) {
        if (tooShort){
            User user = new User(login.getValue(), password.getValue(), null, null);
            Set<ConstraintViolation<User>> errors = ValidationRule.getErrors(user);
            clearErrors();
            if (errors.isEmpty()){
                StringBuilder string = new StringBuilder();
                string.append("login=" + user.getLogin());
                string.append("&password=" + user.getPassword());
                RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, "/auth");
                try {
                    builder.setHeader("Content-Type", "application/x-www-form-urlencoded");
                    builder.setRequestData(string.toString());
                    builder.sendRequest(String.valueOf(string), new RequestCallback() {
                        @Override
                        public void onResponseReceived(Request request, Response response) {
                            if (202 == response.getStatusCode()) {
                                setupSession(user);
                                parent.updateLoggedInMenu();
                                parent.menuItemAdmin.getScheduledCommand().execute();
                            }else {
                                Window.alert("Try again!");
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
            }else {
                errors.stream().forEach(e->{
                    String propertyName = e.getPropertyPath().toString();
                    if (propertyName.equals(User.LOGIN)) {
                        loginInvalidFieldImage=showError(login,loginpanel,e.getMessage());
                    }else
                    {
                        passwordInvalidFieldImage=showError(password,passwordpanel,e.getMessage());
                    }
                });
            }
        }
    }

    private void setupSession(User user){
        String session = user.getSession();
        Cookies.setCookie("sid", session, user.getExpires(), null, "/", false);
    }

    public Image showError(TextBox textBox, Panel panel, String msg){
        textBox.getElement().getStyle().setBorderColor("red");
        final Image img=new  Image(INSTANCE.getImages().field_invalid());
        Style style=img.getElement().getStyle();
        style.setCursor(Style.Cursor.POINTER);
        style.setMargin(6, Style.Unit.PX);
        img.setTitle(msg);
        panel.add(img);

        return img;

    }

    public void clearErrors(){
        login.getElement().getStyle().clearBorderColor();
        if (loginInvalidFieldImage!=null){
            loginInvalidFieldImage.removeFromParent();
            loginInvalidFieldImage=null;
        }
        password.getElement().getStyle().clearBorderColor();

        if (passwordInvalidFieldImage!=null){
            passwordInvalidFieldImage.removeFromParent();
            passwordInvalidFieldImage=null;
        }
    }

}