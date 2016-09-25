package org.openstack4j.example;

import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient.OSClientV2;
import org.openstack4j.model.identity.v2.Tenant;
import org.openstack4j.model.identity.v2.User;

import java.util.List;


public class TenantDemo {

    public void listTenant(){
        OSClientV2 os = Util.getAdminClient();
        List<? extends Tenant> tenants =  os.identity().tenants().list();
        for(int i=0; i<tenants.size(); i++)
        {
            System.out.println("get tenant: " + tenants.get(i));
        }
    }

    public void createTenant()
    {
        String newTenantName = "api-test-" + System.currentTimeMillis();
        OSClientV2 os = Util.getAdminClient();
        Tenant tenant =  os.identity()
                            .tenants()
                            .create(Builders.identityV2().tenant()
                                    .name(newTenantName)
                                    .description("create by java-sdk")
                                    .enabled(true)
                                    .build());

        System.out.println("created tenant is: " + tenant);

        User user = os.identity()
                    .users()
                    .create(Builders.identityV2().user()
                            .name("network-test" + System.currentTimeMillis())
                            .password("password")
                            .email("test@mail.com")
                            .enabled(true)
                            .tenant(tenant)
                            .build());

        System.out.println("created tenant user is:" + user);
    }

    public void getByName(){
        OSClientV2 os = Util.getAdminClient();
        Tenant tenant =  os.identity()
                .tenants().getByName("api-test-1426236737834");

        System.out.println("get tenant is: " + tenant);

        Tenant tenant2 =  os.identity()
                .tenants().getByName("not-exit-tenant");

        System.out.println("get tenant is: " + tenant2);
    }
}
