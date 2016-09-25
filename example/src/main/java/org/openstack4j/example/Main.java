package org.openstack4j.example;


public class Main {
    public static void main( String[] args ){
        System.out.println("Hello World!!!!!");

        tenantDemo();

        //networkDemo();

        //securigyGroupDemo();

        //floatingDemo();

        //serverDemo();

        //flavorDemo();


        System.out.println("demo done.....");

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
        serverDemo.createServerWithNewFlavor();
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
