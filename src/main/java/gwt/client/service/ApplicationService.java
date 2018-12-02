package gwt.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import gwt.shared.GwtApplicationException;

@RemoteServiceRelativePath("ApplicationService")
public interface ApplicationService extends RemoteService {
    String getCurrencies() throws GwtApplicationException;

    String getNews() throws GwtApplicationException;
}
