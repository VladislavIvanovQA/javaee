package gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.*;
import gwt.client.service.ApplicationServiceAsync;
import gwt.client.text.ApplicationConstants;
import gwt.client.widget.AuthView;
import gwt.client.widget.MainView;
import gwt.client.widget.RegistrationView;
import gwt.client.widget.UsersView;

import static gwt.client.gin.ApplicationInjector.INSTANCE;

public class Application implements EntryPoint {

    private static ApplicationServiceAsync service = INSTANCE.getService();
    private static ApplicationConstants dictionary = INSTANCE.getConstants();

    public void onModuleLoad() {
        initHeaderAndTitle();
        initMainSlot();
    }


    private void initHeaderAndTitle(){
        Document.get().getElementById("title").setInnerText(dictionary.form_titile());
        Document.get().getElementById("contact").setInnerText(dictionary.form_contact());
        Document.get().getElementById("footer1").setInnerText(dictionary.form_footer());
        Document.get().getElementById("footer2").setInnerText(dictionary.form_footer1());
    }


    private void initMainSlot(){
        RootPanel.get("right-menu").add(new MainView(service));

        final RootPanel authorization = RootPanel.get("authorization");
        if (authorization != null) {
            authorization.add(new AuthView(service));
        }

        final RootPanel registration = RootPanel.get("registration");
        if (registration != null) {
            registration.add(new RegistrationView(service));
        }

        final RootPanel listUsers = RootPanel.get("listUsers");
        if (listUsers != null) {
            listUsers.add(new UsersView(service));
        }
    }
}
