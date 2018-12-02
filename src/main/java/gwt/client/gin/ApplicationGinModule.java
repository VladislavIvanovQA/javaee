package gwt.client.gin;

import com.google.gwt.inject.client.AbstractGinModule;
import gwt.client.service.LoginService;
import gwt.client.service.ApplicationService;
import gwt.client.text.ApplicationConstants;
import gwt.client.validation.ValidatorFactory.GwtValidator;
import gwt.client.widget.MainView.MainUIBinder;
import gwt.client.widget.image.ApplicationImages;

public class ApplicationGinModule extends AbstractGinModule {
    protected void configure() {
        bind(ApplicationService.class);
        bind(LoginService.class);
        bind(MainUIBinder.class);
        bind(GwtValidator.class);
        bind(ApplicationConstants.class);
        bind(ApplicationImages.class);
//        bind(ItemUiBinder.class);
//        bind(NewsViewUiBinder.class);

//        bind(CurrenciesViewUIBinder.class);
//        bind(RegistrationViewUiBinder.class);



    }
}
