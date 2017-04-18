package org.openstack4j.example;

import java.awt.image.ImagingOpException;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient.OSClientV2;
import org.openstack4j.api.types.Facing;
import org.openstack4j.model.image.Image;
import org.openstack4j.model.compute.Flavor;
import org.openstack4j.model.identity.v2.Tenant;
import org.openstack4j.model.network.Network;
import org.openstack4j.model.network.Subnet;
import org.openstack4j.model.network.IPVersionType;
import org.openstack4j.openstack.OSFactory;

public class Util {

    private static String AUTH_URL = "http://192.168.10.3:5000/v2.0";
    private static String OS_PASSWORD = "admin";

    /*
    private static String AUTH_URL = "http://172.16.173.78:5000/v2.0";
    private static String OS_PASSWORD = "password";
    */

    private static String OS_USER = "admin";
    private static String OS_TENANT_NAME = "admin";

    private static Tenant ADMIN_TENANT = null;
    private static Image OS_IMAGE = null;
    private static Network OS_NETOWORK = null;
    private static Flavor OS_MICRO_FLAVOR = null;

    private static OSClientV2 CLIENT = null;
    private static OSClientV2 ADMIN_CLIENT = null;


    public static OSClientV2 getClient() {
        if (CLIENT == null) {
            CLIENT = OSFactory.builderV2()
                    .endpoint(AUTH_URL)
                    .credentials(OS_USER, OS_PASSWORD)
                    .tenantName(OS_TENANT_NAME)
                    .authenticate();
        }

        return CLIENT;
    }

    public static OSClientV2 getAdminClient() {
        if (ADMIN_CLIENT == null) {
            ADMIN_CLIENT = OSFactory.builderV2()
                    .endpoint(AUTH_URL)
                    .credentials(OS_USER, OS_PASSWORD)
                    .tenantName(OS_TENANT_NAME)
                    .perspective(Facing.ADMIN)
                    .authenticate();
        }

        return ADMIN_CLIENT;
    }

    public static OSClientV2 getClient(String osUser, String osPassword, String osTenantName) {
        if (CLIENT == null) {
            CLIENT = OSFactory.builderV2()
                    .endpoint(AUTH_URL)
                    .credentials(osUser, osPassword)
                    .tenantName(osTenantName)
                    .authenticate();
        }

        return CLIENT;
    }

    public static Date beforeHour(int hour) {
        Date now = new Date();
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(now);
        rightNow.add(Calendar.HOUR, -1 * hour);
        Date beforeHour = rightNow.getTime();
        return beforeHour;
    }


    public static String getSpliter() {
        return "=======================================";
    }

    public Flavor getMicroFlavorID() {
        OSClientV2 os = Util.getAdminClient();

        String microFlavorName = "api-flavor-C1-M64-D0";
        if (OS_MICRO_FLAVOR == null) {
            List<? extends Flavor> flavors = os.compute().flavors().list();
            for (Flavor flavor : flavors) {
                if (flavor.getName().equals(microFlavorName)) {
                    OS_MICRO_FLAVOR = flavor;
                    break;
                }
            }

            if (OS_MICRO_FLAVOR == null) {
                Flavor flavor = Builders.flavor()
                        .name(microFlavorName)
                        .ram(64)
                        .vcpus(1)
                        .disk(1)
                        .rxtxFactor(1.0f)
                        .build();

                OS_MICRO_FLAVOR = os.compute().flavors().create(flavor);
            }

        }

        if(OS_MICRO_FLAVOR != null){
            System.out.println("get micro flavor from cache.");
            return OS_MICRO_FLAVOR;
        }

        System.out.println("get micro flavor error.");
        return null;
    }

    public static String getNetworkID() {
        if (OS_NETOWORK == null) {
            OSClientV2 client = Util.getClient();
            List<? extends Network> networks = client.networking().network().list();
            for (Network network : networks) {
                if (network.getName().equals("admin-private-network")) {
                    OS_NETOWORK = network;
                    break;
                }
            }

            String tenantId = Util.getTenantID();
            if (OS_NETOWORK == null) {
                Network network = client.networking()
                        .network()
                        .create(Builders.network()
                                .name("admin-private-network")
                                .tenantId(tenantId)
                                .adminStateUp(true)
                                .build());

                System.out.println("created tenant network is:" + network);

                Subnet subnet = client.networking()
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

                OS_NETOWORK = network;
            }
        }

        if (OS_NETOWORK != null) {
            System.out.println("Get cached private network.");
            return OS_NETOWORK.getId();
        }

        System.out.println("Get private network error.");
        return null;
    }

    public static String getTenantID() {

        if (ADMIN_TENANT == null) {
            OSClientV2 client = Util.getAdminClient();
            List<? extends Tenant> tenants = client.identity().tenants().list();
            for (Tenant t : tenants) {
                if (t.getName().equals("admin")) {
                    ADMIN_TENANT = t;
                    break;
                }
            }
        }

        if (ADMIN_TENANT != null) {
            System.out.println("Get cached admin tenant.");
            return ADMIN_TENANT.getId();
        }

        System.out.println("Get admin tenant error.");
        return null;

    }

    public static String getImageID() {
        if (OS_IMAGE == null) {
            OSClientV2 client = Util.getClient();
            List<? extends Image> images = client.images().list();
            for (Image img : images) {
                if (img.getName().equals("cirros-0.3.4-x86_64-disk")) {
                    OS_IMAGE = img;
                    break;
                }
            }
        }

        if (OS_IMAGE != null) {
            System.out.println("Get cached image.");
            return OS_IMAGE.getId();
        }

        System.out.println("Get image error.");
        return null;
    }

}


