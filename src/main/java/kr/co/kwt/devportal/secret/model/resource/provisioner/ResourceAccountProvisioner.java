package kr.co.kwt.devportal.secret.model.resource.provisioner;

import kr.co.kwt.devportal.secret.model.resource.ResourceType;
import lombok.Value;

public interface ResourceAccountProvisioner {

    boolean isSupported(ResourceType resourceType);

    UsernameAndPassword provision(String service, ResourceType resourceType);

    @Value
    class UsernameAndPassword {
        String username;
        String password;
    }
}
