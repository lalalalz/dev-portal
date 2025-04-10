package kr.co.kwt.devportal.secret.model.register;

import kr.co.kwt.devportal.secret.model.Environment;
import kr.co.kwt.devportal.secret.model.ResourceType;

public interface ProvisioningAccountRegister {

    ProvisioningAccount register(String service, Environment environment);

    boolean supports(ResourceType resourceType);

}
