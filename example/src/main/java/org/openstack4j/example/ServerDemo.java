package org.openstack4j.example;

import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient;
import org.openstack4j.model.compute.Flavor;
import org.openstack4j.model.compute.Server;
import org.openstack4j.model.compute.ServerCreate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerDemo {

    public void createServer(){
        OSClient os = Util.getClient("network-test", "password", "api-test-1425885645787");
        List<String> networks = new ArrayList<String>(){
            {
                add("0dd0fd0c-c4db-40d8-9e81-e78ceaf78ce5"); //tenant private network
            }
        };

        ServerCreate sc = Builders.server()
                    .name("api-test-vm" + System.currentTimeMillis())
                    .flavor("c98aff4b-9937-4f74-be77-45074f74b1c5")
                    .image("38fa5e86-d219-483f-870e-11af14f55d08")
                    .networks(networks)

                    .build();
        Server server = os.compute().servers().boot(sc);

        System.out.println("created server is:" + server);
    }

    public void bindFloatingIP(){

    }

    public void attachVolume(){

    }

    public void editSecurityGroup(){

    }

    public void createServerWithNewFlavor(){
        OSClient os = Util.getAdminClient();
        Flavor flavor = Builders.flavor()
                .name("api-flavor-" + System.currentTimeMillis())
                .ram(128)
                .vcpus(1)
                .disk(1)
                .rxtxFactor(1.0f)
                .build();

        flavor = os.compute().flavors().create(flavor);
        Map<String, String> extra = new HashMap<String, String>() {
            {
                put("quota:vif_inbound_average","1024"); //1024KB=1M带宽
                put("quota:vif_outbound_average","1024"); //1024KB=1M带宽
            }
        };
        Map<String, String> kv = os.compute().flavors().createAndUpdateExtraSpecs(flavor.getId(), extra);

        //Util.getClient(user, password, tenantName)
        OSClient tenantOS = Util.getClient("network-test", "password", "api-test-1425885645787");
        List<String> networks = new ArrayList<String>(){
            {
                add("6e8cd106-a8eb-4f52-b1b2-e2028cc6b682"); //tenant private network
            }
        };

        ServerCreate sc = Builders.server()
                .name("api-test-vm-" + System.currentTimeMillis())
                .flavor(flavor.getId()) //
                .image("38fa5e86-d219-483f-870e-11af14f55d08") //
                .networks(networks)
                //.addMetadata()
                .build();
        sc.isConfigDrive();
        Server server = tenantOS.compute().servers().boot(sc);


        System.out.println("created server is:" + server);
    }

}
