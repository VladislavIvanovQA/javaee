package gwt.client.gin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import gwt.client.service.ApplicationServiceAsync;
import gwt.client.text.ApplicationConstants;
import gwt.client.validation.ValidatorFactory.GwtValidator;
import gwt.client.widget.AuthView.AuthViewUiBinder;
import gwt.client.widget.MainView.MainViewUiBinder;
import gwt.client.widget.RegistrationView.RegistrationViewUiBinder;
import gwt.client.widget.image.ApplicationImages;

@GinModules(ApplicationGinModule.class)
public interface ApplicationInjector extends Ginjector {

    ApplicationInjector INSTANCE = GWT.create(ApplicationInjector.class);

    ApplicationServiceAsync getService();
    ApplicationConstants getConstants();
    MainViewUiBinder getUiBinder();
    RegistrationViewUiBinder getUI();
    AuthViewUiBinder getUIAuth();
    GwtValidator getValidator();
    ApplicationImages getImages();
}
