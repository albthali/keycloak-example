package com.albthali.keycloak.authenticator;

import org.jboss.logging.Logger;
import org.keycloak.authentication.FormAction;
import org.keycloak.authentication.FormContext;
import org.keycloak.authentication.ValidationContext;
import org.keycloak.forms.login.LoginFormsProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.utils.FormMessage;

import javax.ws.rs.core.MultivaluedMap;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class UserAttributeForm implements FormAction {
    final static Logger logger = Logger.getLogger(UserAttributeForm.class);
    @Override
    public void buildPage(FormContext formContext, LoginFormsProvider loginFormsProvider) {
        var config = formContext.getAuthenticatorConfig().getConfig();
        var paramName = config.get("paramName");
        loginFormsProvider.setAttribute(paramName,true);

    }

    @Override
    public void validate(ValidationContext validationContext) {
        var config = validationContext.getAuthenticatorConfig().getConfig();
        var allowEmpty = Boolean.parseBoolean(config.get("allowEmpty"));
        var paramName = config.get("paramName");
        var form = validationContext.getHttpRequest().getDecodedFormParameters();
        if (allowEmpty) {
            validationContext.success();
            return;
        }
        if (Optional.ofNullable(form.getFirst(paramName)).orElse("").isEmpty()){
            validationContext.validationError(form, List.of(new FormMessage(paramName,String.format("Missing field:%s",paramName))));
            return;
        }



    }

    @Override
    public void success(FormContext formContext) {
        var user = formContext.getUser();
        var formData = formContext.getHttpRequest().getDecodedFormParameters();
        var config = formContext.getAuthenticatorConfig().getConfig();
        var paramName = config.get("paramName");
        var value = formData.getFirst(paramName);
        user.setSingleAttribute(paramName,value);

    }

    @Override
    public boolean requiresUser() {
        return false;
    }

    @Override
    public boolean configuredFor(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {
        return true;
    }

    @Override
    public void setRequiredActions(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {

    }

    @Override
    public void close() {

    }
}
