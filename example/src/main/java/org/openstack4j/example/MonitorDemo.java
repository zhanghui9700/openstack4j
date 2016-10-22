package org.openstack4j.example;

import org.openstack4j.api.OSClient;
import org.openstack4j.model.compute.Server;
import org.openstack4j.model.network.Port;
import org.openstack4j.model.network.options.PortListOptions;
import org.openstack4j.model.telemetry.Sample;
import org.openstack4j.model.telemetry.SampleCriteria;


import java.util.Date;
import java.util.List;

public class MonitorDemo {

    /**
     * 云主机CPU利用率
     * */
    public void getCPU(String instanceID){
        System.out.println("MonitorDemo.getCPU start");
        OSClient os = Util.getClient();

        SampleCriteria filter = new SampleCriteria();
        filter.add("meter", SampleCriteria.Oper.EQUALS, "cpu_util");
        filter.add("resource", SampleCriteria.Oper.EQUALS, instanceID); // 云主机ID
        //filter.add("timestamp", SampleCriteria.Oper.GTE, "2016-10-21T15:11:17.897000"); //获取几分钟的数据,当前时间-N分钟,时间格式: 2016-10-21T16:20:08.921000
        filter.timestamp(SampleCriteria.Oper.GTE, Util.beforeHour(1));

        List<? extends Sample> samples =  os.telemetry().samples().list(filter);
        for(Sample sample: samples)
        {
            /**
             * CeiloMeterSample{
             *      type=gauge,
             *      unit=%,
             *      volume=2.6248682,
             *      timestamp=2016-10-21T15:11:18.844000,
             *      source=openstack,
             *      project=19c30c87134d45c78884f42d52dfe30d,
             *      user=d22a95f560c54f189511d5ea3b6d05ba,
             *      resource=f2b181aa-ad91-45d5-a651-43381e776d4d,
             *      metadata={instance_host=node-4.suninfo.com, ephemeral_gb=0, flavor.vcpus=1, flavor.ephemeral=0, instance_id=f2b181aa-ad91-45d5-a651-43381e776d4d, display_name=appcheckin-01, state=active, flavor.ram=64, status=active, ramdisk_id=None, flavor.name=m1.micro, disk_gb=0, kernel_id=None, image.id=f8126493-8c51-4337-a1b7-b1f3e05fbaf3, flavor.id=fdb865c5-8ee5-43cf-8ed7-e9e8a67d9be9, host=89a117b08e31330d9a1b88767c9bd23aa27c4c8b400f6026d87548df, OS-EXT-AZ.availability_zone=nova, image.name=appcheckin-01-image, image_ref_url=http://192.168.33.3:8774/images/f8126493-8c51-4337-a1b7-b1f3e05fbaf3, name=instance-000000ff, flavor.disk=0, root_gb=0, image.links=[{'href': 'http://192.168.33.3:8774/images/f8126493-8c51-4337-a1b7-b1f3e05fbaf3', 'rel': 'bookmark'}], memory_mb=64, instance_type=m1.micro, vcpus=1, image_ref=f8126493-8c51-4337-a1b7-b1f3e05fbaf3, flavor.links=[{'href': 'http://192.168.33.3:8774/flavors/fdb865c5-8ee5-43cf-8ed7-e9e8a67d9be9', 'rel': 'bookmark'}]},
             *      recorded_at=2016-10-21T15:11:18.942000
             *      }
             *      #recorded_at 是采点时间
             * */
            System.out.println("get cpu_util: " + sample.getVolume() + " " + sample.getUnit()); //2.5342567 %
        }

        System.out.println("MonitorDemo.getCPU end");
    }

    public void getMemoryUsage(String instanceID) {

        System.out.println("MonitorDemo.getMemoryUsage start");
        OSClient os = Util.getClient();

        SampleCriteria filter = new SampleCriteria();
        filter.add("meter", SampleCriteria.Oper.EQUALS, "memory.usage");
        filter.add("resource", SampleCriteria.Oper.EQUALS, instanceID); // 云主机ID
        filter.timestamp(SampleCriteria.Oper.GTE, Util.beforeHour(1));


        List<? extends Sample> samples =  os.telemetry().samples().list(filter);
        for(Sample sample: samples)
        {
            /**
             * CeiloMeterSample{
             *      type=gauge,
             *      unit=MB,
             *      volume=20.0,
             *      timestamp=2016-10-21T15:11:18.860000,
             *      source=openstack,
             *      project=19c30c87134d45c78884f42d52dfe30d,
             *      user=d22a95f560c54f189511d5ea3b6d05ba,
             *      resource=f2b181aa-ad91-45d5-a651-43381e776d4d,
             *      metadata={instance_host=node-4.suninfo.com, ephemeral_gb=0, flavor.vcpus=1, flavor.ephemeral=0, instance_id=f2b181aa-ad91-45d5-a651-43381e776d4d, display_name=appcheckin-01, state=active, flavor.ram=64, status=active, ramdisk_id=None, flavor.name=m1.micro, disk_gb=0, kernel_id=None, image.id=f8126493-8c51-4337-a1b7-b1f3e05fbaf3, flavor.id=fdb865c5-8ee5-43cf-8ed7-e9e8a67d9be9, host=89a117b08e31330d9a1b88767c9bd23aa27c4c8b400f6026d87548df, OS-EXT-AZ.availability_zone=nova, image.name=appcheckin-01-image, image_ref_url=http://192.168.33.3:8774/images/f8126493-8c51-4337-a1b7-b1f3e05fbaf3, name=instance-000000ff, flavor.disk=0, root_gb=0, image.links=[{'href': 'http://192.168.33.3:8774/images/f8126493-8c51-4337-a1b7-b1f3e05fbaf3', 'rel': 'bookmark'}], memory_mb=64, instance_type=m1.micro, vcpus=1, image_ref=f8126493-8c51-4337-a1b7-b1f3e05fbaf3, flavor.links=[{'href': 'http://192.168.33.3:8774/flavors/fdb865c5-8ee5-43cf-8ed7-e9e8a67d9be9', 'rel': 'bookmark'}]},
             *      recorded_at=2016-10-21T15:11:19.207000
             *      }
             *      #recorded_at 是采点时间
             * */
            System.out.println("内存使用量: " + sample.getVolume() + " " + sample.getUnit()); //  内存使用量: 20.0 MB
            System.out.println("内存使用占总内存百分比: " + sample.getVolume()/Float.parseFloat(sample.getMetadata().get("flavor.ram").toString()) * 100 + "%"); //内存使用占总内存百分比: 31.25%
        }


        System.out.println("MonitorDemo.getMemoryUsage end");
    }




    public void getNetworkMonitor(String instanceID, String meter) {
        System.out.println("MonitorDemo.getNetworkMonitor start");
        OSClient os = Util.getClient();

        PortListOptions filters = PortListOptions.create();
        filters.deviceId(instanceID);
        List<? extends Port>  instancePorts = os.networking().port().list(filters);
        if(instancePorts.size() < 1){
            System.out.println("云主机没有网卡信息");
            return;
        }

        Server instance = os.compute().servers().get(instanceID);

        // eg: instance-000000f6-5cba0f80-b21e-477d-91b8-ea76ae542eb8-tap1dd3b788-f1
        // interfaceMeter = "{instanceName}-{instanceUUID}-tap{portID}11位"

        String portPrefix = instancePorts.get(0).getId().substring(0, 11);

        String interfaceMeter = String.format("%s-%s-tap%s",
                instance.getInstanceName(), instance.getId(), portPrefix);

        System.out.println(interfaceMeter);

        SampleCriteria filter = new SampleCriteria();
        filter.add("meter", SampleCriteria.Oper.EQUALS, meter);
        filter.add("resource", SampleCriteria.Oper.EQUALS, interfaceMeter);
        filter.timestamp(SampleCriteria.Oper.GTE, Util.beforeHour(1));

        List<? extends Sample> samples =  os.telemetry().samples().list(filter);

        for(Sample sample: samples){
            /**
             * CeiloMeterSample{
             *      type=gauge,
             *      unit=B/s,
             *      volume=0.0,
             *      timestamp=2016-10-22T08:36:36.589000,
             *      source=openstack,
             *      project=19c30c87134d45c78884f42d52dfe30d,
             *      user=d22a95f560c54f189511d5ea3b6d05ba,
             *      resource=instance-000000ff-f2b181aa-ad91-45d5-a651-43381e776d4d-tapb76502ee-5f,
             *      metadata={instance_host=node-4.suninfo.com, ephemeral_gb=0, flavor.vcpus=1, flavor.ephemeral=0, memory_mb=64, display_name=appcheckin-01, state=active, flavor.ram=64, status=active, ramdisk_id=None, flavor.name=m1.micro, disk_gb=0, kernel_id=None, image.id=f8126493-8c51-4337-a1b7-b1f3e05fbaf3, fref=None, flavor.id=fdb865c5-8ee5-43cf-8ed7-e9e8a67d9be9, host=89a117b08e31330d9a1b88767c9bd23aa27c4c8b400f6026d87548df, OS-EXT-AZ.availability_zone=nova, image.name=appcheckin-01-image, image_ref_url=http://192.168.33.3:8774/images/f8126493-8c51-4337-a1b7-b1f3e05fbaf3, name=tapb76502ee-5f, flavor.disk=0, root_gb=0, image.links=[{'href': 'http://192.168.33.3:8774/images/f8126493-8c51-4337-a1b7-b1f3e05fbaf3', 'rel': 'bookmark'}], mac=fa:16:3e:55:85:14, vnic_name=tapb76502ee-5f, instance_id=f2b181aa-ad91-45d5-a651-43381e776d4d, instance_type=m1.micro, vcpus=1, image_ref=f8126493-8c51-4337-a1b7-b1f3e05fbaf3, flavor.links=[{'href': 'http://192.168.33.3:8774/flavors/fdb865c5-8ee5-43cf-8ed7-e9e8a67d9be9', 'rel': 'bookmark'}]},
             *      recorded_at=2016-10-22T08:36:36.696000}
             * */
           System.out.println("网卡流量 B/s:" + sample);
        }

        System.out.println("MonitorDemo.getNetworkMonitor end");
    }

    public void getDiskMonitor(String instanceID, String meter){
        System.out.println("MonitorDemo.getDiskMonitor start");
        OSClient os = Util.getClient();

        SampleCriteria filter = new SampleCriteria();
        filter.add("meter", SampleCriteria.Oper.EQUALS, meter);
        filter.add("resource", SampleCriteria.Oper.EQUALS, instanceID); // 云主机ID
        filter.timestamp(SampleCriteria.Oper.GTE, Util.beforeHour(1));


        List<? extends Sample> samples =  os.telemetry().samples().list(filter);
        for(Sample sample: samples) {
            /**
             * CeiloMeterSample{
             *      type=gauge,
             *      unit=B/s,
             *      volume=0.0,
             *      timestamp=2016-10-22T07:48:25.560000,
             *      source=openstack, project=19c30c87134d45c78884f42d52dfe30d,
             *      user=d22a95f560c54f189511d5ea3b6d05ba,
             *      resource=f2b181aa-ad91-45d5-a651-43381e776d4d,
             *      metadata={instance_host=node-4.suninfo.com, ephemeral_gb=0, flavor.vcpus=1, OS-EXT-AZ.availability_zone=nova, memory_mb=64, display_name=appcheckin-01, state=active, flavor.id=fdb865c5-8ee5-43cf-8ed7-e9e8a67d9be9, status=active, ramdisk_id=None, flavor.name=m1.micro, disk_gb=0, kernel_id=None, image.id=f8126493-8c51-4337-a1b7-b1f3e05fbaf3, flavor.ram=64, host=89a117b08e31330d9a1b88767c9bd23aa27c4c8b400f6026d87548df, device=['vda', 'vdz'], flavor.ephemeral=0, image.name=appcheckin-01-image, image_ref_url=http://192.168.33.3:8774/images/f8126493-8c51-4337-a1b7-b1f3e05fbaf3, name=instance-000000ff, flavor.disk=0, root_gb=0, image.links=[{'href': 'http://192.168.33.3:8774/images/f8126493-8c51-4337-a1b7-b1f3e05fbaf3', 'rel': 'bookmark'}], instance_id=f2b181aa-ad91-45d5-a651-43381e776d4d, instance_type=m1.micro, vcpus=1, image_ref=f8126493-8c51-4337-a1b7-b1f3e05fbaf3, flavor.links=[{'href': 'http://192.168.33.3:8774/flavors/fdb865c5-8ee5-43cf-8ed7-e9e8a67d9be9', 'rel': 'bookmark'}]},
             *      recorded_at=2016-10-22T07:48:25.841000}
             * */
            System.out.println("磁盘IO B/s:" + sample);
        }

        System.out.println("MonitorDemo.getDiskMonitor end");
    }
}