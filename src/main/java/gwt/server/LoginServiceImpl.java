package gwt.server;

import com.google.gwt.http.client.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import gwt.client.service.LoginService;
import gwt.shared.GwtApplicationException;
import gwt.shared.User;
import gwt.shared.validation.ValidationRule;


public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {

    @Override
    public User authorize(User user) throws GwtApplicationException {
        if (ValidationRule.isValid(user)){

        }
        return user;
    }

    @Override
    public User authorize_from_session() throws GwtApplicationException {
        return null;
    }

    @Override
    public void logout() throws GwtApplicationException {

    }
}