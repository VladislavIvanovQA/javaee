package gwt.client.gin;

import com.google.gwt.inject.client.AbstractGinModule;
import gwt.client.service.ApplicationService;
import gwt.client.text.ApplicationConstants;
import gwt.client.validation.ValidatorFactory.GwtValidator;
import gwt.client.widget.AuthView.AuthViewUiBinder;
import gwt.client.widget.CreateUsersView;
import gwt.client.widget.MainView.MainViewUiBinder;
import gwt.client.widget.NewsView.NewsViewUiBinder;
import gwt.client.widget.RegistrationView.RegistrationViewUiBinder;
import gwt.client.widget.image.ApplicationImages;
import gwt.client.widget.Item.ItemUiBinder;

public class ApplicationGinModule extends AbstractGinModule {
    protected void configure() {
        bind(ApplicationService.class);
        bind(ApplicationConstants.class);
        bind(ItemUiBinder.class);
        bind(NewsViewUiBinder.class);
        bind(MainViewUiBinder.class);
        bind(CreateUsersView.class);
        bind(RegistrationViewUiBinder.class);
        bind(AuthViewUiBinder.class);
        bind(GwtValidator.class);
        bind(ApplicationImages.class);
    }
}
