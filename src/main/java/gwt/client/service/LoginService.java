package gwt.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import gwt.shared.GwtApplicationException;
import gwt.shared.User;

@RemoteServiceRelativePath("LoginService")
public interface LoginService extends RemoteService {
    User authorize(User user) throws GwtApplicationException;
    User authorize_from_session() throws GwtApplicationException;
    void logout() throws GwtApplicationException;
}

