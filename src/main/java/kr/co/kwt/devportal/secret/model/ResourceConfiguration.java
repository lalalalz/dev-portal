package kr.co.kwt.devportal.secret.model;

import kr.co.kwt.devportal.secret.model.register.ProvisioningAccount;
import kr.co.kwt.devportal.secret.model.template.ResourceConfigurationTemplate;

public interface ResourceConfiguration<T> {

    ResourceConfigurationTemplate<T> getResourceConfigurationTemplate();

    ProvisioningAccount getProvisioningAccount();

    String getService();
}
