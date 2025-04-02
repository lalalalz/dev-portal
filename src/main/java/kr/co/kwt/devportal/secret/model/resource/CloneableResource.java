package kr.co.kwt.devportal.secret.model.resource;

import static kr.co.kwt.devportal.secret.model.resource.provisioner.ResourceAccountProvisioner.UsernameAndPassword;

public interface CloneableResource {

    Resource cloneWithAccount(UsernameAndPassword usernameAndPassword);
}
