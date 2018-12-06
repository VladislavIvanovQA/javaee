package gwt.client.widget;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import gwt.client.News;
import gwt.client.service.ApplicationServiceAsync;
import gwt.client.service.LoginServiceAsync;
import gwt.client.text.ApplicationConstants;
import gwt.client.widget.image.ApplicationImages;
import gwt.shared.User;

import java.util.ArrayList;
import java.util.List;

import static gwt.client.gin.ApplicationInjector.INSTANCE;

public class MainView extends Composite {
    @UiTemplate("MainViewPart.ui.xml")
    public interface MainUIBinder extends UiBinder<HTMLPanel, MainView> {}

    private static ApplicationServiceAsync service = INSTANCE.getService();
    private static LoginServiceAsync loginService = INSTANCE.getUIAuth();

    private static MainUIBinder ourUiBinder = INSTANCE.getUiBinder();

    @UiField MenuBar menuBar;
//    @UiField MenuItem menuItemMain;
    @UiField MenuItem menuItemAdmin;
    @UiField MenuItem menuItemCreate;
    @UiField MenuItem menuItemUsers;
    @UiField MenuItem menuItemLogin;
    @UiField MenuItem menuItemLogout;
    @UiField MenuItem menuItemRegistration;

    @UiField
    DeckPanel deckPanel;


    @UiField(provided = true) VerticalPanel formSearch;

    @UiField TextBox searchBox;

    @UiField CurrenciesView currencies;
    @UiField NewsView news;

    @UiField
    ApplicationImages res;

    private void updateUI(){
        formSearch = new VerticalPanel();
//        searchBox.addKeyDownHandler(keyDownEvent -> {
//            if (keyDownEvent.getNativeKeyCode() == KeyCodes.KEY_ENTER){
//                Window.Location.replace("https://www.google.ru/search?" +
//                        "newwindow=1&" +
//                        "source=hp&ei=LR7wW7juOKWyrgT_6JnABw&" +
//                        "q=" + searchBox.getValue() +
//                        "&btnK=%D0%9F%D0%BE%D0%B8%D1%81%D0%BA+%D0%B2+Google&" +
//                        "pq=" + searchBox.getValue() +
//                        "&gs_l=");
//            }
//        });
//
//        formSearch=new FormPanel("_blank");
//        formSearch.setMethod("GET");
//        formSearch.setAction("http://google.com/search");

        initWidget(ourUiBinder.createAndBindUi(this));

        res.style().ensureInjected();

        List<Widget> widgets=new ArrayList<>();
        Label l=new Label();
        l.setText("Label text");

//        widgets.add(new MainView());
        widgets.add(new UsersView(this));
//        widgets.add(new RnkFaqPanel(this));
        widgets.add(new AdminPanel(this));
        widgets.add(new RegistrationView(this));
        widgets.add(new LoginPanel(this));
        widgets.add(new LogoutPanel(this));
//        Window.addResizeHandler(event -> {
//            deckPanel.getWidget(
//                    deckPanel.getVisibleWidget())
//                    .getParent()
////                    .getParent()
////                    .getParent()
//                    .setHeight(
//                            (Window.getClientHeight() -
//                                    menuBar.getOffsetHeight() +
//                                    Style.Unit.PX.getType()));
//        });
        widgets.stream().forEach(w->{
            deckPanel.add(w);
        });

//        menuItemMain.setScheduledCommand(new MainMenuCommand(deckPanel, widgets.get(0)));
//        menuItemCreate.setScheduledCommand(new MainMenuCommand(deckPanel, widgets.get(2)));
//        menuItemAdmin.setScheduledCommand(new MainMenuCommand(deckPanel, widgets.get(4)));
        menuItemUsers.setScheduledCommand(new MainMenuCommand(deckPanel, widgets.get(0)));
        menuItemAdmin.setScheduledCommand(new MainMenuCommand(deckPanel, widgets.get(1)));
        menuItemRegistration.setScheduledCommand(new MainMenuCommand(deckPanel, widgets.get(2)));
        menuItemLogin.setScheduledCommand(new MainMenuCommand(deckPanel, widgets.get(3)));
        menuItemLogout.setScheduledCommand(new MainMenuCommand(deckPanel, widgets.get(4)));

        selectPanel();
    }


    public void updateLoggedInMenu(){
        menuItemUsers.setVisible(true);
        menuItemAdmin.setVisible(true);
        menuItemLogout.setVisible(true);
        menuItemLogin.setVisible(false);
    }

    public void updateLoggedOutMenu(){
        menuItemUsers.setVisible(false);
        menuItemLogout.setVisible(false);
        menuItemAdmin.setVisible(false);
        menuItemLogin.setVisible(true);
    }

    private void displayMainPanel(){
        deckPanel.showWidget(3);
    }

    private void checkSessionIsLegal(String session){
        try{
            loginService.authorize_from_session(new AsyncCallback<User>() {
                @Override
                public void onFailure(Throwable caught) {
                    updateLoggedOutMenu();
                    displayMainPanel();
                }

                @Override
                public void onSuccess(User result) {
                    if (result==null){
                        updateLoggedOutMenu();
                        displayMainPanel();
                    }else{
                        if (result.getSession() !=null){
                            updateLoggedInMenu();
                            menuItemAdmin.getScheduledCommand().execute();
                        }else{
                            updateLoggedOutMenu();
                            displayMainPanel();
                        }
                    }
                }
            });
        }catch(Exception ex){
            Window.alert("Ошибка входа");
        }
    }

    private void selectPanel(){
        String session= Cookies.getCookie("sid");
        if (session == null){
            displayMainPanel();
        }else{
            checkSessionIsLegal(session);
        }
    }

    @Inject
    public MainView() {

        updateUI();
    }

    @UiHandler("currencies")
    public void attachCurrenciesHandler(AttachEvent event){
        try{
//            service.getCurrencies(new AsyncCallback<String>() {
//                @Override
//                public void onSuccess(String result) {
//                    currencies.parseMessage(result);
//                }
//                @Override
//                public void onFailure(Throwable caught) {
//                    Window.alert(caught.getLocalizedMessage());
//                }
//            });
        }catch(Exception ex){
            Window.alert(ex.getLocalizedMessage());
        }
    }

    @UiHandler("news")
    public void attachNewsHandler(AttachEvent event){
        String url = "/news";
        JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
        jsonp.setTimeout(10000);
//        jsonp.requestObject(url, new AsyncCallback<String>() {
//            @Override
//            public void onFailure(Throwable throwable) {
//
//            }
//
//            @Override
//            public void onSuccess(JavaScriptObject javaScriptObject) {
//
//            }
//        }
    }
}
