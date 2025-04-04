package kr.co.kwt.devportal.secret.model.register;

import kr.co.kwt.devportal.secret.model.ResourceType;

public interface ProvisioningAccountRegister {

    ProvisioningAccount register(String service, ResourceType resourceType);

    boolean supports(ResourceType resourceType);

}
