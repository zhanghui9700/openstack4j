package org.openstack4j.example;

import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient;
import org.openstack4j.model.network.*;

public class NetworkDemo {

    public void createNetwork(String tenantId)
    {
        OSClient os = Util.getClient();

        Network network = os.networking()
                .network()
                .create(Builders.network()
                        .name("private_net")
                        .tenantId(tenantId)
                        .adminStateUp(true)
                        .build());

        System.out.println("created tenant network is:" + network);

        Subnet subnet = os.networking()
                .subnet()
                .create(Builders.subnet()
                        .name("private_subnet")
                        .networkId(network.getId())
                        .tenantId(tenantId)
                        .ipVersion(IPVersionType.V4)
                        .cidr("172.31.0.0/24")
                        .enableDHCP(true)
                        .build());

        System.out.println("created tenant subnet is:" + subnet);

        /**
        Router router = os.networking()
                        .router()
                        .create(Builders.router()
                                .name("route-name")
                                .adminStateUp(true)
                                .tenantId(tenantId)
                                        //.externalGateway("1a0fe585-c132-4138-bd5f-8e2408e27cc7") //这个从配置里读取
                                .build());

        System.out.println("created tenant router is:" + router);

        RouterInterface iface = os.networking().router()
                .attachInterface(router.getId(),
                        AttachInterfaceType.SUBNET, subnet.getId());


        System.out.println("created tenant iface is:" + iface);
        */
    }

    public void routerUpdate(){
        OSClient os = Util.getClient();
        Router router = os.networking().router().get("57e9226b-28ca-4f07-8fcf-bc2052794bb8");
        router = os.networking().router().update(router.toBuilder().externalGateway("1a0fe585-c132-4138-bd5f-8e2408e27cc7").build());

        System.out.println("created tenant router is:" + router);
    }
}
