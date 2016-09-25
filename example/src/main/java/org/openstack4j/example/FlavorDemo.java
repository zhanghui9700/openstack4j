package org.openstack4j.example;

import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient;
import org.openstack4j.model.compute.Flavor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlavorDemo {
    public void listFlavor(){
        OSClient os = Util.getAdminClient();
        List<? extends Flavor> flavors =  os.compute().flavors().list();
        for(int i=0; i < flavors.size(); i++)
        {
            System.out.println("get flavor: " + flavors.get(i));
        }
    }

    public void createFlavorNormal(){
        OSClient os = Util.getAdminClient();
        Flavor flavor = Builders.flavor()
                .name("api-flavor-" + System.currentTimeMillis())
                .ram(4096)
                .vcpus(6)
                .disk(120)
                .rxtxFactor(1.0f)
                .build();

        flavor = os.compute().flavors().create(flavor);

        System.out.println("new flavor: " + flavor);
    }

    // 电科院项目暂时可以不用, 扩展属性使用(Qos)
    public void createFlavorWithExtra(){
        OSClient os = Util.getAdminClient();
        Flavor flavor = Builders.flavor()
                .name("api-flavor-" + System.currentTimeMillis())
                .ram(4096)
                .vcpus(6)
                .disk(120)
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

        System.out.println("new flavor: " + flavor);
        System.out.println("extra: " + kv);
    }
}
