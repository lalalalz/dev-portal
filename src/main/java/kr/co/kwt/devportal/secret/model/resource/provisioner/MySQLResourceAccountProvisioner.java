package kr.co.kwt.devportal.secret.model.resource.provisioner;

import kr.co.kwt.devportal.secret.model.resource.ResourceType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MySQLResourceAccountProvisioner implements ResourceAccountProvisioner {

    @Override
    public boolean isSupported(ResourceType resourceType) {
        return ResourceType.MYSQL.equals(resourceType);
    }

    @Override
    public UsernameAndPassword provision(String service, ResourceType resourceType) {
        return new UsernameAndPassword("test1", "test1");
    }
}
