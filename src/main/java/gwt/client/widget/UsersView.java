package gwt.client.widget;

import com.google.gwt.http.client.*;
import com.google.gwt.json.client.*;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import gwt.client.service.ApplicationServiceAsync;
import gwt.shared.User;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static gwt.client.gin.ApplicationInjector.INSTANCE;

public class UsersView extends Composite {

    @UiTemplate("UsersViewPart.ui.xml")
    public interface UsersViewUiBinder extends UiBinder<FlexTable, UsersView> {
    }

    @UiField
    FlexTable table;

    private List<User> users = new ArrayList<>();

    @Override
    protected void onLoad() {
        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, "/users");
        try {
            builder.sendRequest("", new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    toList(response.getText());
                    table.setBorderWidth(10);
                    table.setCellSpacing(10);

                    table.getFlexCellFormatter();

                    table.setText(0, 0, "Number");
                    table.setText(0, 1, "Login");
                    table.setText(0, 2, "Password");
                    table.setText(0, 3, "Name");
                    table.setText(0, 4, "Phone");
                    table.setText(0, 5, "Email");
                    table.setText(0, 6, "Sex");

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

    private static UsersViewUiBinder ourUiBinder = INSTANCE.getUIList();

    private ApplicationServiceAsync service;


    @Inject
    public UsersView(ApplicationServiceAsync service) {
        initWidget(ourUiBinder.createAndBindUi(this));
        this.service = service;
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
                User user = new User(id, login, pass, name, phone, email, sex);
                users.add(user);
            }
        }
    }
}
