package kr.co.kwt.devportal.secret.v2.model.register;


import kr.co.kwt.devportal.secret.v2.model.Environment;
import kr.co.kwt.devportal.secret.v2.model.ResourceType;

public interface ProvisioningAccountRegister {

    ProvisioningAccount register(String service, Environment environment);

    boolean supports(ResourceType resourceType);

}
