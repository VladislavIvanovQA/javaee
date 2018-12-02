package gwt.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.*;
import com.google.gwt.json.client.*;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import gwt.client.service.ApplicationServiceAsync;
import gwt.shared.User;
import gwt.shared.dto.UserDTO;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static gwt.client.gin.ApplicationInjector.INSTANCE;

public class UsersView extends Composite {

    @UiTemplate("UsersViewPart.ui.xml")
    public interface UsersViewUiBinder extends UiBinder<FlowPanel, UsersView> {
    }

    @UiField
    FlexTable table;

    private List<UserDTO> users = new ArrayList<>();

    @Override
    protected void onLoad() {
        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, "/users");
        try {
            builder.sendRequest("", new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    toList(response.getText());

                    table.getFlexCellFormatter();
                    table.setHTML(0, 0, "<b>Number</b>");
                    table.setHTML(0, 1, "<b>Login</b>");
                    table.setHTML(0, 2, "<b>Password</b>");
                    table.setHTML(0, 3, "<b>Name</b>");
                    table.setHTML(0, 4, "<b>Phone</b>");
                    table.setHTML(0, 5, "<b>Email</b>");
                    table.setHTML(0, 6, "<b>Sex</b>");

                    for (int i = 0; i < users.size(); i++) {
                        table.setText(i + 1, 0, String.valueOf(users.get(i).getNumber()));
                        table.setText(i + 1, 1, users.get(i).getLogin());
                        table.setText(i + 1, 2, users.get(i).getPassword());
                        table.setText(i + 1, 3, users.get(i).getName());
                        table.setText(i + 1, 4, users.get(i).getPhone());
                        table.setText(i + 1, 5, users.get(i).getEmail());
                        table.setText(i + 1, 6, users.get(i).getSex());
                    }
                }

                @Override
                public void onError(Request request, Throwable throwable) {

                }
            });
        } catch (RequestException e) {
            e.printStackTrace();
        }
    }

    private static UsersViewUiBinder ourUiBinder = GWT.create(UsersViewUiBinder.class);

    @Inject
    public UsersView(MainView parent) {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    private void toList(String jsonStr) {

        JSONValue value = JSONParser.parseStrict(jsonStr);

        JSONObject productsObj = value.isObject();

        JSONArray productsArray = productsObj.get("users").isArray();

        if (productsArray != null) {
            for (int i = 0; i < productsArray.size(); i++) {
                JSONObject jsonValue = productsArray.get(i).isObject();
                long id = (long) jsonValue.get("number").isNumber().doubleValue();
                String login = jsonValue.get("login").isString().stringValue();
                String pass = jsonValue.get("password").isString().stringValue();
                String name = jsonValue.get("name").isString().stringValue();
                String phone = jsonValue.get("phone").isString().stringValue();
                String email = jsonValue.get("email").isString().stringValue();
                String sex = jsonValue.get("sex").isString().stringValue();
                UserDTO user = new UserDTO(id, login, pass, name, phone, email, sex);
                users.add(user);
            }
        }
    }
}
