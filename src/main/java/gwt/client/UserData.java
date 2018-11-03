package gwt.client;

import gwt.shared.User;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class UserData {

    public String login;
    public String password;

    public UserData(){}

    @JsOverlay
    public static UserData as (User user){
        UserData data = new UserData();
        data.login = user.getLogin();
        data.password = user.getPassword();
        return data;
    }
}
