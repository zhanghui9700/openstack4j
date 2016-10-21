package org.openstack4j.example;


public class Main {
    public static void main( String[] args ){
        System.out.println("demo start!!!!!");

        //tenantDemo();

        //networkDemo();

        //securigyGroupDemo();

        //floatingDemo();

        //serverDemo();

        //flavorDemo();

        //hostDemo();

        monitorDemo();


        System.out.println("demo end!!!!!");

    }

    private static void monitorDemo(){
        MonitorDemo monitor = new MonitorDemo();
        String instanceId = "f2b181aa-ad91-45d5-a651-43381e776d4d"; // 云主机UUID
        // CPU使用率
        //monitor.getCPU(instanceId);
        // 内存使用量
        monitor.getMemoryUsage(instanceId);
    }

    private static void hostDemo()
    {
        HostDemo hostDemo = new HostDemo();
        hostDemo.listHost();
        hostDemo.listHypervisor();
        hostDemo.computeDisableEnable();
    }


    private static void tenantDemo()
    {
        TenantDemo tenantDemo = new TenantDemo();
        //tenantDemo.createTenant();
        tenantDemo.listTenant();
        tenantDemo.getByName();
    }

    private static void networkDemo(){
        NetworkDemo networkDemo = new NetworkDemo();
        //networkDemo.createNetwork("a56b14277c1442ba93962eb70f6c45d8");
        networkDemo.routerUpdate();
    }

    private static void serverDemo()
    {
        ServerDemo serverDemo = new ServerDemo();
        //serverDemo.createServer();
        //serverDemo.createServerWithNewFlavor();
        //serverDemo.listServer();
        serverDemo.listServerByHost("mitaka");
    }

    private static void securigyGroupDemo(){
        SecurityGroupDemo securityGroupDemo = new SecurityGroupDemo();
        securityGroupDemo.addRuelToDefaultGroup();
    }


    private static void flavorDemo(){
        FlavorDemo flavorDemo = new FlavorDemo();
        //flavorDemo.listFlavor();
        flavorDemo.createFlavorNormal();
        //flavorDemo.createFlavorWithExtra();
    }
}
