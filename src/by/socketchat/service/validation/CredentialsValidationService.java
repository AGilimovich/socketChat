package by.socketchat.service.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Администратор on 22.12.2016.
 */
public class CredentialsValidationService implements IValidationService {
    private String namePattern;
    private final String DEFAULT_NAME_PATTERN = "\\w{1,20}";
    private String passwordPattern;
    private final String DEFAULT_PASSWORD_PATTERN = "\\w{5,}";

    @Override
    public ValidationStatus validate(String name, String password) {
        if (namePattern == null || passwordPattern == null) {
            namePattern = DEFAULT_NAME_PATTERN;
            passwordPattern = DEFAULT_PASSWORD_PATTERN;
        }

        Matcher nameMatcher = Pattern.compile(namePattern).matcher(name);
        Matcher passwordMatcher = Pattern.compile(passwordPattern).matcher(password);
        if (!nameMatcher.matches()) {
            if (!passwordMatcher.matches()) {
                return ValidationStatus.ILLEGAL_CREDENTIALS;
            } else return ValidationStatus.ILLEGAL_NAME;
        } else {
            if (!passwordMatcher.matches()) {
                return ValidationStatus.ILLEGAL_PASSWORD;
            } else return ValidationStatus.VALID;
        }
    }


    @Override
    public void setNamePattern(String namePattern) {
        this.namePattern = namePattern;
    }

    @Override
    public void setPasswordPattern(String passwordPattern) {
        this.passwordPattern = passwordPattern;
    }
}
