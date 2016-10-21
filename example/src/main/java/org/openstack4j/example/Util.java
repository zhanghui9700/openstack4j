package org.openstack4j.example;

import org.openstack4j.api.OSClient.OSClientV2;
import org.openstack4j.api.types.Facing;
import org.openstack4j.openstack.OSFactory;

public class Util {

    private static String AUTH_URL = "http://192.168.32.6:5000/v2.0";
    private static String OS_USER = "admin";
    private static String OS_PASSWORD = "admin";
    private static String OS_TENANT_NAME = "admin";
    private static OSClientV2 CLIENT = null;
    private static OSClientV2 ADMIN_CLIENT = null;

    public static OSClientV2 getClient()
    {
        if(CLIENT == null) {
            CLIENT = OSFactory.builderV2()
                    .endpoint(AUTH_URL)
                    .credentials(OS_USER, OS_PASSWORD)
                    .tenantName(OS_TENANT_NAME)
                    .authenticate();
        }

        return CLIENT;
    }

    public static OSClientV2 getAdminClient()
    {
        if(ADMIN_CLIENT == null) {
            ADMIN_CLIENT = OSFactory.builderV2()
                    .endpoint(AUTH_URL)
                    .credentials(OS_USER, OS_PASSWORD)
                    .tenantName(OS_TENANT_NAME)
                    .perspective(Facing.ADMIN)
                    .authenticate();
        }

        return ADMIN_CLIENT;
    }

    public static OSClientV2 getClient(String osUser, String osPassword, String osTenantName)
    {
        if(CLIENT == null) {
            CLIENT = OSFactory.builderV2()
                    .endpoint(AUTH_URL)
                    .credentials(osUser, osPassword)
                    .tenantName(osTenantName)
                    .authenticate();
        }

        return CLIENT;
    }
}
