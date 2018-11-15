package gwt.client;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import gwt.shared.User;
import lombok.Data;

@Data
@JsType(isNative=true, namespace= JsPackage.GLOBAL, name="Object")
public class UserData {

    public long number;
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
        data.number = user.getNumber();
        data.login = user.getLogin();
        data.password = user.getPassword();
        data.name = user.getName();
        data.phone = user.getPhone();
        data.email = user.getEmail();
        data.sex = user.getSex();
        return data;
    }
}
