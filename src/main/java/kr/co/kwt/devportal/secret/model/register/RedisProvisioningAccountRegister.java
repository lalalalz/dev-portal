package kr.co.kwt.devportal.secret.model.register;

import kr.co.kwt.devportal.secret.model.ResourceType;
import org.springframework.stereotype.Component;

@Component
public class RedisProvisioningAccountRegister implements ProvisioningAccountRegister {

    @Override
    public ProvisioningAccount register(String service, ResourceType resourceType) {
        return new ProvisioningAccount("test", "test");
    }

    @Override
    public boolean supports(ResourceType resourceType) {
        return ResourceType.REDIS.equals(resourceType);
    }
}
