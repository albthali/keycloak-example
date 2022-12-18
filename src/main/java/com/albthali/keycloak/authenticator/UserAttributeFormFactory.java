package com.albthali.keycloak.authenticator;

import org.keycloak.Config;
import org.keycloak.authentication.FormAction;
import org.keycloak.authentication.FormActionFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.ArrayList;
import java.util.List;

public class UserAttributeFormFactory implements FormActionFactory {
    private static AuthenticationExecutionModel.Requirement[] REQUIREMENT_CHOICES = {
            AuthenticationExecutionModel.Requirement.REQUIRED,
            AuthenticationExecutionModel.Requirement.DISABLED

    };
    private static List<ProviderConfigProperty> config = new ArrayList<>();
    static {
        ProviderConfigProperty paramName = new ProviderConfigProperty();
        paramName.setName("paramName");
        paramName.setLabel("Parameter name");
        paramName.setHelpText("Adds the parameter to the form when its built and assumes that the form will have an input field with the same name");
        paramName.setType(ProviderConfigProperty.STRING_TYPE);
        config.add(paramName);
        ProviderConfigProperty allowEmpty = new ProviderConfigProperty();
        allowEmpty.setName("allowEmpty");
        allowEmpty.setLabel("Allow Empty");
        allowEmpty.setHelpText("Allow Empty");
        allowEmpty.setType(ProviderConfigProperty.BOOLEAN_TYPE);
        allowEmpty.setDefaultValue(Boolean.TRUE);
        config.add(allowEmpty);
    }
    @Override
    public String getDisplayType() {
        return "User Attribute Form";
    }

    @Override
    public String getReferenceCategory() {
        return "User Attribute Form";
    }

    @Override
    public boolean isConfigurable() {
        return true;
    }

    @Override
    public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
        return REQUIREMENT_CHOICES;
    }

    @Override
    public boolean isUserSetupAllowed() {
        return true;
    }

    @Override
    public String getHelpText() {
        return "Sends a form attribute with the parameter name and adds the form value to the user's attributes";
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return config;
    }

    @Override
    public FormAction create(KeycloakSession keycloakSession) {
        return new UserAttributeForm();
    }

    @Override
    public void init(Config.Scope scope) {
    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {

    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return "user-attribute-form";
    }
}
