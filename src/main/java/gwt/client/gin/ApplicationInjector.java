package gwt.client.gin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import gwt.client.service.LoginServiceAsync;
import gwt.client.service.ApplicationServiceAsync;
import gwt.client.text.ApplicationConstants;
import gwt.client.validation.ValidatorFactory.GwtValidator;
import gwt.client.widget.CurrenciesView.CurrenciesViewUIBinder;
import gwt.client.widget.MainView.MainUIBinder;
import gwt.client.widget.Item.ItemUiBinder;
import gwt.client.widget.NewsView.NewsViewUiBinder;
import gwt.client.widget.RegistrationView.RegistrationViewUiBinder;
import gwt.client.widget.UsersView.UsersViewUiBinder;
import gwt.client.widget.image.ApplicationImages;

@GinModules(ApplicationGinModule.class)
public interface ApplicationInjector extends Ginjector {

    ApplicationInjector INSTANCE = GWT.create(ApplicationInjector.class);

    ApplicationServiceAsync getService();
    LoginServiceAsync getUIAuth();
    MainUIBinder getUiBinder();
    GwtValidator getValidator();
    ApplicationConstants getConstants();
    ApplicationImages getImages();
//
//    NewsViewUiBinder getNews();
//    ItemUiBinder getItem();
//    CurrenciesViewUIBinder getCur();
//    RegistrationViewUiBinder getUI();
//    UsersViewUiBinder getUIList();
//
}
