package gwt.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import gwt.client.service.LoginServiceAsync;
import gwt.shared.GwtApplicationException;

import static gwt.client.gin.ApplicationInjector.INSTANCE;

public class LogoutPanel extends Composite {
    public interface LoginPanelUIBinder extends UiBinder<VerticalPanel, LogoutPanel> {
    }

    private MainView parent;

    private static LoginServiceAsync service = INSTANCE.getUIAuth();

    private static LoginPanelUIBinder ourUiBinder = GWT.create(LoginPanelUIBinder.class);

    public LogoutPanel(MainView parent){
        initWidget(ourUiBinder.createAndBindUi(this));
        this.parent = parent;
    }

    public void logout(){
        try {
            service.logout(new AsyncCallback<Void>() {
                @Override
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getLocalizedMessage());
                }

                @Override
                public void onSuccess(Void aVoid) {
                    parent.updateLoggedOutMenu();
                    parent.menuItemUsers.getScheduledCommand().execute();
                }
            });
        } catch (GwtApplicationException e) {
            Window.alert("Ошибка выхода");
        }
    }

}
