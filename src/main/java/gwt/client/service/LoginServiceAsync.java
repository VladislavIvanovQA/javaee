package gwt.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import gwt.shared.GwtApplicationException;
import gwt.shared.User;

public interface LoginServiceAsync {
    void authorize(User user, AsyncCallback<User> async) throws GwtApplicationException;
    void authorize_from_session(AsyncCallback<User> async) throws GwtApplicationException;
    void logout(AsyncCallback<Void> async) throws GwtApplicationException;
    void get_user_from_session(AsyncCallback<User> async) throws GwtApplicationException;
    void save_user_in_session(User user, AsyncCallback<Void> async) throws GwtApplicationException;
    void remove_user_from_session(AsyncCallback<Void> async) throws GwtApplicationException;
}

