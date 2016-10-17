package org.openstack4j.example;

import org.openstack4j.api.OSClient;
import org.openstack4j.model.compute.Host;

import java.util.List;

public class HostDemo {

    public void listHost(){
        System.out.println("HostDemo.listHost start.");
        OSClient os = Util.getAdminClient();
        List<? extends Host> hosts =  os.compute().hosts().list();
        for(int i=0; i < hosts.size(); i++)
        {
            System.out.println("get host zone: " + hosts.get(i));
        }


        Host.Service service = os.compute().hosts()
                .serviceDisable("mitaka", "nova-compute");

        System.out.println("disable service: " + service);

        Host.Service enable_service = os.compute().hosts()
                .serviceEnable("mitaka", "nova-compute");

        System.out.println("enable service: " + enable_service);

        System.out.println("HostDemo.listHost end.");
    }
}
