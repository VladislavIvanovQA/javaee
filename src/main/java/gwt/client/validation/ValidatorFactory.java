package gwt.client.validation;

import com.google.gwt.validation.client.AbstractGwtValidatorFactory;
import com.google.gwt.validation.client.GwtValidation;
import com.google.gwt.validation.client.impl.AbstractGwtValidator;
import gwt.shared.User;

import javax.validation.Validator;

import static gwt.client.gin.ApplicationInjector.INSTANCE;

public class ValidatorFactory extends AbstractGwtValidatorFactory {

    @GwtValidation(User.class)
    public interface GwtValidator extends Validator{

    }

    @Override
    public AbstractGwtValidator createValidator() {
        return (AbstractGwtValidator) INSTANCE.getValidator();
    }
}
