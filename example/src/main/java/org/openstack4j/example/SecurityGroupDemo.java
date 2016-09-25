package org.openstack4j.example;

import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient;
import org.openstack4j.model.compute.IPProtocol;
import org.openstack4j.model.compute.SecGroupExtension;
import org.openstack4j.model.compute.SecGroupExtension.Rule;

import java.util.List;


public class SecurityGroupDemo {
    public void addRuelToDefaultGroup(){
        OSClient os = Util.getClient("network-test", "password", "api-test-1425885645787");

        List<? extends SecGroupExtension> secGroupList = os.compute().securityGroups().list();

        System.out.println("[0] name:" + secGroupList.get(0).getName().toLowerCase().trim());
        if(secGroupList.size() == 1 && secGroupList.get(0).getName().toLowerCase().trim().equals("default")){
            /*
            Rule rule = os.compute().securityGroups()
                    .createRule(Builders.secGroupRule()
                                    .parentGroupId(secGroupList.get(0).getId())
                                    .protocol(IPProtocol.TCP)
                                    .cidr("0.0.0.0/0")
                                    .range(80, 80).build()
                    );

            Rule rule2 = os.compute().securityGroups()
                    .createRule(Builders.secGroupRule()
                                    .parentGroupId(secGroupList.get(0).getId())
                                    .protocol(IPProtocol.ICMP)
                                    .cidr("0.0.0.0/0")
                                    .range(-1, -1).build()
                    );
                    */
            Rule rule3 = os.compute().securityGroups()
                    .createRule(Builders.secGroupRule()
                                    .parentGroupId(secGroupList.get(0).getId())
                                    .protocol(IPProtocol.UDP)
                                    .cidr("0.0.0.0/0")
                                    .range(80, 90)

                                    .build()
                    );

        }


    }
}
