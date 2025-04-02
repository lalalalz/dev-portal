package kr.co.kwt.devportal.secret.service.resource;

import kr.co.kwt.devportal.secret.model.resource.MySQLResource;
import kr.co.kwt.devportal.secret.model.resource.Resource;
import kr.co.kwt.devportal.secret.model.resource.ResourceType;
import kr.co.kwt.devportal.secret.repository.resource.ResourceRepository;
import kr.co.kwt.devportal.secret.service.resource.command.AddMySQLResourceCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ResourceServiceTest {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ResourceRepository resourceRepository;

    @Test
    void MySQL_리소스를_추가한다() {
        // given
        String host = "testHost";
        String port = "testPort";
        AddMySQLResourceCommand addMySQLResourceCommand = new AddMySQLResourceCommand(host, port);

        // when
        Resource resource = resourceService.addPresetResource(addMySQLResourceCommand);

        //then
        assertThat(resource).isExactlyInstanceOf(MySQLResource.class);
        assertThat(resource.getResourceType()).isEqualTo(ResourceType.MYSQL);
        assertThat(((MySQLResource) resource).getHost()).isEqualTo(host);
        assertThat(((MySQLResource) resource).getPort()).isEqualTo(port);
        assertThat(((MySQLResource) resource).getUsername()).isNull();
        assertThat(((MySQLResource) resource).getPassword()).isNull();
    }

    @Test
    void MySQL_계정을_프로비저닝한다() {
        // given
        Resource mysqlResource = resourceRepository
                .findAll()
                .stream()
                .filter(resource -> resource.getResourceType().equals(ResourceType.MYSQL))
                .findFirst()
                .orElseThrow();
        // when
        Resource resource = resourceService.provisionAccount("testService", mysqlResource);

        // then
        System.out.println("resource = " + resource);
    }
}