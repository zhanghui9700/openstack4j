package org.openstack4j.openstack.compute.internal;

import java.util.List;

import org.openstack4j.api.compute.HostService;
import org.openstack4j.model.compute.Host;
import org.openstack4j.openstack.compute.domain.NovaHost;
import org.openstack4j.openstack.compute.domain.NovaHost.Hosts;
import org.openstack4j.openstack.compute.domain.actions.ServiceAction;


public class HostServiceImpl extends BaseComputeServices implements HostService {

	@Override
	public List<? extends Host> list() {
		return get(Hosts.class, uri("/os-services"))
				.execute().getList();
	}

	@Override
	public Host.Service serviceEnable(String hostName, String serviceName){
		return put(NovaHost.HostService.class, uri("/os-services/enable"))
				.entity(ServiceAction.enable(serviceName, hostName))
				.execute();
	}

	@Override
	public Host.Service serviceDisable(String hostName, String serviceName){
		return put(NovaHost.HostService.class, uri("/os-services/disable"))
				.entity(ServiceAction.disable(serviceName, hostName))
				.execute();
	}
}
