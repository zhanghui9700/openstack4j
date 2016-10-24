package org.openstack4j.example;


public class Main {
    public static void main( String[] args ){
        System.out.println("demo start!!!!!");

        // 租户管理
        // tenantDemo();

        // 网络管理
        // networkDemo();

        // 安全组管理
        // securigyGroupDemo();

        // 云主机管理
        // serverDemo();

        // 规格管理
        // flavorDemo();

        // 物理机管理
        // hostDemo();

        // 监控指标
        // monitorDemo();

        // 云硬盘创建/删除/list/get
        volumeDemo();

        System.out.println("demo end!!!!!");

    }

    private  static void  volumeDemo(){
        VolumeDemo volumeDemo = new VolumeDemo();
        volumeDemo.listVolume();

        // String volumeID = volumeDemo.createVolume();
        //volumeDemo.getVolume(volumeID);
        //volumeDemo.deleteVolume(volumeID);

        //volumeDemo.deleteVolume("d5679fb4-c824-40c6-8c83-97adb00ed7e6");

        //volumeDemo.listVolume();


        String instanceID = "6f3facb6-2e28-45b6-b3ae-a425b0611597";
        String volumeID = "5f007f55-e64b-4bd7-8859-b9ad49df6c4a";
        //volumeDemo.volumeAttach(instanceID, volumeID);
        volumeDemo.volumeDetach(instanceID, volumeID);
    }

    private static void monitorDemo(){
        MonitorDemo monitor = new MonitorDemo();
        String instanceId = "f2b181aa-ad91-45d5-a651-43381e776d4d"; // 云主机UUID

        // CPU使用率
        //monitor.getCPU(instanceId);

        // 内存使用量
        // monitor.getMemoryUsage(instanceId);

        // 网卡0,入口流量
        monitor.getNetworkMonitor(instanceId, "network.incoming.bytes.rate");
        // 网卡0,出口流量
        monitor.getNetworkMonitor(instanceId, "network.outgoing.bytes.rate");

        // 磁盘, 读速率
        monitor.getDiskMonitor(instanceId, "disk.read.bytes.rate");
        // 磁盘, 写速率
        monitor.getDiskMonitor(instanceId, "disk.write.bytes.rate");

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
        networkDemo.createNetwork("50a03bd3a88e487dbdf3a8fdc98c9c5e");
        //networkDemo.routerUpdate();
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
