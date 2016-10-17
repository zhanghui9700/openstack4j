package org.openstack4j.api.compute;

import java.util.List;

import org.openstack4j.common.RestService;
import org.openstack4j.model.compute.Host;



public interface HostService extends RestService {

    /**
     * List all hosts by services
     *
     * @return List of Flavor
     */
    List<? extends Host> list();


    Host.Service serviceEnable(String hostName, String serviceName);

    Host.Service serviceDisable(String hostName, String serviceName);
}
