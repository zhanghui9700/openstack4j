package org.openstack4j.example;

import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient;
import org.openstack4j.model.compute.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerDemo {

    public  void listServer(){
        OSClient os = Util.getClient();
        List<? extends Server> servers = os.compute().servers().list();
        for(Server server: servers){
            System.out.println("get server is: " + server);
        }
    }

    public  void listServerByHost(final String hostName){
        OSClient os = Util.getClient();
        Map<String, String> filter = new HashMap<String,String>();
        filter.put("host", hostName);

        List<? extends Server> servers = os.compute().servers().list(filter);
        for(Server server: servers){
            /**
            * NovaServer{
            * id=14dd73b3-d7cb-4f1d-ad12-941b1cfcd3d0,
            * name=first-vm-3,
            * image={id=6efe873b-1bb9-41c1-a9a5-dc0ebe0a9ef0, links=[{href=http://172.16.173.78:8774/93098ed3a5d14632839c57197b675ac8/images/6efe873b-1bb9-41c1-a9a5-dc0ebe0a9ef0, rel=bookmark}]},
            * flavor=NovaFlavor{id=456f6915-5d93-42a5-b3dd-b09b757634cf, ephemeral=0, swap=0, rxtx_factor=1.0, links=[GenericLink{href=http://172.16.173.78:8774/93098ed3a5d14632839c57197b675ac8/flavors/456f6915-5d93-42a5-b3dd-b09b757634cf, rel=bookmark}],},
            * status=ACTIVE,
            * diskconfig=AUTO,
            * userId=6f4bc837ceab49f99b819e134224e7a4,
            * created=Fri Oct 21 10:54:50 CST 2016,
            * updated=Fri Oct 21 10:55:32 CST 2016,
            * launched at=Fri Oct 21 10:55:30 CST 2016,
            * tenantId=93098ed3a5d14632839c57197b675ac8,
            * hostId=2d4b5e0ca4d4678dc5d0655ecd2b222392d7a3ee867a1a23465bb3dc,
            * addresses=NovaAddresses{
            *               addresses={
            *                   net04=[
            *                       NovaAddress{address=10.10.0.3, type=fixed, version=4, macaddr=fa:16:3e:6d:33:ca, }
            *                   ]
            *               },},
            * hypervisor host=mitaka.fx-dev.com,
            * powerstate=1,
            * instanceName=instance-00000003,
            * vmState=active,
            * metadata={}}
            * */
            System.out.println("get server is: " + server);
        }
    }

    public void createServer(){
        OSClient os = Util.getClient();
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

    public Flavor createFalvor(){
        OSClient os = Util.getAdminClient();
        Flavor flavor = Builders.flavor()
                .name("api-flavor-" + System.currentTimeMillis())
                .ram(64)
                .vcpus(1)
                .disk(1)
                .rxtxFactor(1.0f)
                .build();

        flavor = os.compute().flavors().create(flavor);

        /*
        Map<String, String> extra = new HashMap<String, String>() {
            {
                put("quota:vif_inbound_average","1024"); //1024KB=1M带宽
                put("quota:vif_outbound_average","1024"); //1024KB=1M带宽
            }
        };
        Map<String, String> kv = os.compute().flavors().createAndUpdateExtraSpecs(flavor.getId(), extra);
        */

        return flavor;
    }

    public  void bootFromVolume(){
        Flavor flavor = this.createFalvor();
        OSClient tenantOS = Util.getClient();

        List<String> networks = new ArrayList<String>(){
            {
                add(Util.getNetworkID()); //tenant private network
            }
        };

        BlockDeviceMappingCreate blockMapping = Builders.blockDeviceMapping()
                            .deviceName(null)
                            .sourceType(BDMSourceType.IMAGE)
                            .destinationType(BDMDestType.VOLUME)
                            .volumeSize(flavor.getDisk())
                            .bootIndex(0)
                            .uuid(Util.getImageID())
                            .deleteOnTermination(false)
                            .build();

        ServerCreate sc = Builders.server()
                .name("api-test-vm-" + System.currentTimeMillis())
                .flavor(flavor.getId()) //
                .configDrive(false)
                .networks(networks)
                .blockDevice(blockMapping)
                .build();

        Server server = tenantOS.compute().servers().boot(sc);
        System.out.println("created server is:" + server);


        tenantOS.compute().flavors().delete(flavor.getId());
    }

    public void createServerWithNewFlavor(){
        Flavor flavor = this.createFalvor();

        OSClient tenantOS = Util.getClient();
        List<String> networks = new ArrayList<String>(){
            {
                add(Util.getNetworkID()); //tenant private network
            }
        };

        ServerCreate sc = Builders.server()
                .name("api-test-vm-" + System.currentTimeMillis())
                .flavor(flavor.getId()) //
                .image(Util.getImageID()) //
                .configDrive(false)
                .networks(networks)
                //.addMetadata()
                .build();
        Server server = tenantOS.compute().servers().boot(sc);


        System.out.println("created server is:" + server);

        tenantOS.compute().flavors().delete(flavor.getId());
    }


    public void createServerWithFixed(){
        OSClient os = Util.getClient();
        Map<String, String> networks = new HashMap<String, String>();
        networks.put("63fc5376-e861-4874-8e82-89593a6f0b06", "192.168.111.100");

        ServerCreate sc = Builders.server()
                .name("api-test-vm" + System.currentTimeMillis())
                .flavor("65b55b20-13ec-45a5-8004-280298f66433")
                .image("fb13b723-6f03-4051-90de-1cedaacfeb2e")
                .networks(networks)
                .build();

        Server server = os.compute().servers().boot(sc);

        System.out.println("created server is:" + server);
    }

}
