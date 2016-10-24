package org.openstack4j.example;


import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.compute.VolumeAttachment;
import org.openstack4j.model.storage.block.Volume;
import org.openstack4j.model.storage.block.builder.VolumeBuilder;

import java.util.List;

public class VolumeDemo {

    public void listVolume(){
        OSClient os = Util.getClient();
        List<? extends Volume> volumes =  os.blockStorage().volumes().list();
        for(Volume volume: volumes)
        {
            /**
             * CinderVolume{
             *      id=5f007f55-e64b-4bd7-8859-b9ad49df6c4a,
             *      name=volume-test-1GB,
             *      description=created by api,
             *      status=creating,
             *      size=1,
             *      zone=nova,
             *      created=Mon Oct 24 16:53:04 CST 2016,
             *      volumeType=lvmdriver-1,
             *      metadata={},
             *      bootable=false}
             * */
            System.out.println("云硬盘列表信息: " + volume);
        }
    }

    public String createVolume(){
        OSClient os = Util.getClient();
        Volume volume = Builders.volume()
                .name("volume-test-1GB")
                .description("created by api")
                .size(1)
                .bootable(false)
                .build();

        Volume newVolume = os.blockStorage().volumes().create(volume);
        /**
         *
         * */
        // 等待newVolume.getStatus() == available
        System.out.println("新建云硬盘返回: " + newVolume);

        return newVolume.getId();
    }

    public  void deleteVolume(String volumeID){

        OSClient os = Util.getClient();
        ActionResponse response = os.blockStorage().volumes().delete(volumeID);

        /**
         * */
        System.out.println("删除云硬盘返回: " + response);
    }

    public Volume getVolume(String volumeID){

        OSClient os = Util.getClient();
        Volume volume = os.blockStorage().volumes().get(volumeID);
        /**
         * */
        System.out.println("获取云硬盘返回: " + volume);
        return volume;
    }


    // 挂载云硬盘
    public  void volumeAttach(String instanceID, String volumeID){

        OSClient os = Util.getClient();
        /**
         * 挂载的volume状态必须为available
         * */
        VolumeAttachment attachment = os.compute().servers()
                                .attachVolume(instanceID, volumeID, null);

        /**
         * NovaVolumeAttachment{
         *      device=/dev/vdb,
         *      id=5f007f55-e64b-4bd7-8859-b9ad49df6c4a,
         *      serverId=6f3facb6-2e28-45b6-b3ae-a425b0611597,
         *      volumeId=5f007f55-e64b-4bd7-8859-b9ad49df6c4a}
         * */
        System.out.println("挂载云硬盘返回: " + attachment);
    }

    // 卸载云硬盘
    public void volumeDetach(String instanceID, String volumeID){

        OSClient os = Util.getClient();
        ActionResponse response = os.compute().servers()
                                .detachVolume(instanceID, volumeID);
        /**
         * 成功: ActionResponse{success=true, code=200}
         * 失败: ActionResponse{success=false, fault=volume_id not found: 5f007f55-e64b-4bd7-8859-b9ad49df6c4a, code=404}
         * */
        System.out.println("卸载云硬盘返回: " + response);
    }
}
