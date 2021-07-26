package no.ntnu.remotecode.slave.control;

import no.ntnu.remotecode.model.enums.ContainerStatus;

import javax.ejb.Singleton;
import java.util.UUID;

@Singleton
public class ContainerStateManager {


    public void RequestNewState(UUID containerKey, ContainerStatus newState) {

    }

}
