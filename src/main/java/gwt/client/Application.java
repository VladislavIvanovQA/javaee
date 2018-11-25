package gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.http.client.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import gwt.client.service.ApplicationServiceAsync;
import gwt.client.text.ApplicationConstants;
import gwt.client.widget.*;
import gwt.client.widget.NewsView;

import static gwt.client.gin.ApplicationInjector.INSTANCE;

public class Application implements EntryPoint {

    private static ApplicationServiceAsync service = INSTANCE.getService();
    private static ApplicationConstants dictionary = INSTANCE.getConstants();
    private static TextBox tb = new TextBox();
    private static TextArea ta = new TextArea();


    public void onModuleLoad() {
        initHeaderAndTitle();
        initMainSlot();
        searchText();
        jsExecutor();
    }

    private void searchText(){
        VerticalPanel mainPanel = new VerticalPanel();
        mainPanel.add(tb);
        tb.setName("search");
        tb.addKeyDownHandler(keyDownEvent -> {
            if (keyDownEvent.getNativeKeyCode() == KeyCodes.KEY_ENTER){
                Window.Location.replace("https://www.google.ru/search?" +
                        "newwindow=1&" +
                        "source=hp&ei=LR7wW7juOKWyrgT_6JnABw&" +
                        "q=" + tb.getValue() +
                        "&btnK=%D0%9F%D0%BE%D0%B8%D1%81%D0%BA+%D0%B2+Google&" +
                        "pq=" + tb.getValue() +
                        "&gs_l=");
            }
        });
        RootPanel.get("searchInput").add(mainPanel);
    }

    private void jsExecutor(){
        Button btn = new Button();
        Button clear = new Button();
        Label label = new Label();
        VerticalPanel panel = new VerticalPanel();
        panel.add(ta);
        panel.add(btn);
        panel.add(clear);
        panel.add(label);
        ta.setName("text");
        btn.setText("Отправить");
        clear.setText("Очистить");
        btn.addClickHandler(clickEvent -> {
           if (ta.getText().length() > 1){
               RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, "/js?text=" + ta.getText());
               try {
                   builder.sendRequest(null, new RequestCallback() {
                       @Override
                       public void onResponseReceived(Request request, Response response) {
                            label.setText(response.getText());
                       }

                       @Override
                       public void onError(Request request, Throwable throwable) {
                            Window.alert("Error js not found");
                       }
                   });
               } catch (RequestException e) {
                   e.printStackTrace();
               }
           }
        });
        clear.addClickHandler(clickEvent -> label.setText(""));
        final RootPanel admin = RootPanel.get("adminTools");
        if (admin != null){
            admin.add(panel);
        }

    }


    private void initHeaderAndTitle(){
        Document.get().getElementById("title").setInnerText(dictionary.form_titile());
        Document.get().getElementById("contact").setInnerText(dictionary.form_contact());
        Document.get().getElementById("footer1").setInnerText(dictionary.form_footer());
        Document.get().getElementById("footer2").setInnerText(dictionary.form_footer1());
    }


    private void initMainSlot(){
        RootPanel.get("right-menu").add(new MainView(service));

        final RootPanel news = RootPanel.get("news");
        if (news != null) {
            news.add(new NewsView(service));
        }

        final RootPanel authorization = RootPanel.get("authorization");
        if (authorization != null) {
            authorization.add(new AuthView(service));
        }

        final RootPanel registration = RootPanel.get("registration");
        if (registration != null) {
            registration.add(new RegistrationView(service));
        }

        final RootPanel createUser = RootPanel.get("createUser");
        if (createUser != null){
            createUser.add(new CreateUsersView(service));
        }

        final RootPanel listUsers = RootPanel.get("listUsers");
        if (listUsers != null) {
            listUsers.add(new UsersView(service));
        }
    }
}
