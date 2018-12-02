package gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import gwt.client.widget.*;

public class Application implements EntryPoint {


    private static TextBox tb = new TextBox();
    private static TextArea ta = new TextArea();

    private void initBody() {
        RootPanel.get("id-main-container").add(new MainView());
    }

    public void onModuleLoad() {
        initBody();
//        initHeaderAndTitle();
//        initMainSlot();
//        searchText();
//        jsExecutor();
//        FileUpload();
    }

    public void FileUpload() {
        final FormPanel form = new FormPanel();
        form.setAction("/upload");
        form.setEncoding(FormPanel.ENCODING_MULTIPART);
        form.setMethod(FormPanel.METHOD_POST);

        VerticalPanel panel = new VerticalPanel();
        form.setWidget(panel);

        FileUpload upload = new FileUpload();
        upload.setName("file");
        panel.add(upload);

        panel.add(new Button("Submit", (ClickHandler) event -> form.submit()));

        form.addSubmitCompleteHandler(event -> Window.alert(event.getResults()));

        final RootPanel createUser = RootPanel.get("createUser");
        if (createUser != null){
            createUser.add(form);
        }
    }



    private void initHeaderAndTitle(){
//        Document.get().getElementById("title").setInnerText(dictionary.form_titile());
//        Document.get().getElementById("contact").setInnerText(dictionary.form_contact());
//        Document.get().getElementById("footer1").setInnerText(dictionary.form_footer());
//        Document.get().getElementById("footer2").setInnerText(dictionary.form_footer1());
    }


    private void initMainSlot(){
//        RootPanel.get("right-menu").add(new CurrenciesView(service));

//        final RootPanel news = RootPanel.get("news");
//        if (news != null) {
//            news.add(new NewsView(service));
//        }

//        final RootPanel authorization = RootPanel.get("authorization");
//        if (authorization != null) {
//            authorization.add(new LoginPanel(service));
//        }
//
//        final RootPanel registration = RootPanel.get("registration");
//        if (registration != null) {
//            registration.add(new RegistrationView(service));
//        }
//
//        final RootPanel listUsers = RootPanel.get("listUsers");
//        if (listUsers != null) {
//            listUsers.add(new UsersView(service));
//        }
    }
}
