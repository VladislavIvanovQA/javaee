package gwt.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import gwt.shared.GwtApplicationException;

public interface ApplicationServiceAsync {
    void getCurrencies(AsyncCallback<String> async) throws GwtApplicationException;
    void getNews(AsyncCallback<String> async) throws GwtApplicationException;
}
