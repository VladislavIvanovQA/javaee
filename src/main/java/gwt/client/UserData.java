package gwt.client;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import gwt.shared.User;

@JsType(isNative=true, namespace= JsPackage.GLOBAL, name="Object")
public class UserData {

    public String login;
    public String password;
    public String name;
    public String phone;
    public String email;
    public String sex;

    public UserData(){}

    @JsOverlay
    public static UserData as(User user){
        UserData data = new UserData();
        data.login = user.getLogin();
        data.password = user.getPassword();
        data.name = user.getName();
        data.phone = user.getPhone();
        data.email = user.getEmail();
        data.sex = user.getSex();
        return data;
    }
}
