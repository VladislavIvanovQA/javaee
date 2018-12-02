package gwt.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GwtApplicationException extends Exception implements IsSerializable {

    public GwtApplicationException(){
        super();
    }

    public GwtApplicationException(String message){
        super(message);
    }

    public GwtApplicationException(Exception ex){
        super(ex);
    }
}
