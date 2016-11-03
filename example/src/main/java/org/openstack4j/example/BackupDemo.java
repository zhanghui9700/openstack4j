package org.openstack4j.example;

import java.lang.Thread;
import java.util.List;

import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.storage.block.VolumeBackup;
import org.openstack4j.model.storage.block.builder.VolumeBackupBuilder;
import org.openstack4j.openstack.heat.domain.HeatSoftwareConfig;

public class BackupDemo {


    public List<? extends VolumeBackup>  list(){
        System.out.println("BackupDemo.list start...");
        OSClient os = Util.getClient();
        List<? extends VolumeBackup> backups = os.blockStorage().backups().list();
        for(VolumeBackup backup: backups){
            /**
             * CinderVolumeBackup{
             *      id=59a7ccca-fba9-4351-8d0e-3b03d79f24b8,
             *      status=available,
             *      size=1,
             *      zone=nova,
             *      created=Fri Oct 21 09:27:22 CST 2016,
             *      volumeId=3f2cf9d5-d888-45df-84f5-c0a042143887,
             *      container=backups,
             *      objectCount=0,
             *      isIncremental=false,
             *      hasDependent=false}
             * */
            System.out.println("get backup info: " + backup);
        }

        System.out.println("BackupDemo.list end...");
        return backups;
    }

    public VolumeBackup  get(String backupId){
        System.out.println("BackupDemo.get start...backupID=" + backupId);
        OSClient os = Util.getClient();
        VolumeBackup backup = os.blockStorage().backups().get(backupId);
        System.out.println("get backup info: " + backup);

        System.out.println("BackupDemo.get end...");
        return backup;
    }

    public void delete(String backupId){
        System.out.println("BackupDemo.delete start...backupID=" + backupId);
        OSClient os = Util.getClient();
        ActionResponse response = os.blockStorage().backups().delete(backupId);
        /**
         * ActionResponse{success=true, code=200}
         * ActionResponse{success=false, fault=Backup e5e0f8b7-d973-4fc6-8aa7-15ca240cc5f0 could not be found., code=404}
         * */
        System.out.println("delete backup return: " + response);

        System.out.println("BackupDemo.delete end...");
    }


    public VolumeBackup create(String volumeId){
        System.out.println("BackupDemo.create start...");
        OSClient os = Util.getClient();

        VolumeBackup backup = Builders.volumeBackup()
                                .name("api-backup-test-" + volumeId)
                                .volume(volumeId)
                                .incremental(false)
                                .force(true)
                                .build();

        backup = os.blockStorage().backups().create(backup);
        /**
         * CinderVolumeBackup{
         *      id=3876b24c-1dd8-4130-a346-0590096b2c6a,
         *      name=api-backup-test-45948040-7df6-476e-b984-6fb6e9ed50ab,
         *      objectCount=0}
          */
        System.out.println("BackupDemo.create end...create backup: " + backup);

        return backup;
    }



    public void resotre(String backupId, String volumeId){
        System.out.println("BackupDemo.restore start...backupID=" + backupId);
        OSClient os = Util.getClient();
        ActionResponse response = os.blockStorage().backups().restore(backupId, volumeId);
        /**
         * ActionResponse{success=true, code=200}
         * ActionResponse{success=false, fault=Backup 45948040-7df6-476e-b984-6fb6e9ed50ab could not be found., code=404}
         * */
        System.out.println("restore action return: " + response);

        System.out.println("BackupDemo.restore end...");
    }


    public void run(){

        /*
        System.out.println(Util.getSpliter());
        List<? extends VolumeBackup> backups = this.list();

        if(backups.size() > 0) {
            System.out.println(Util.getSpliter());
            this.get(backups.get(0).getId());
        }
        */

        System.out.println(Util.getSpliter());
        /*
        VolumeBackup backup = this.create("45948040-7df6-476e-b984-6fb6e9ed50ab");

        int i = 0;
        while(true) {
            try {
                Thread.sleep(5 * 1000);
            }
            catch (InterruptedException e){}

            backup = this.get(backup.getId());
            System.out.println("backup status: "+ backup.getStatus().value());

            if(backup.getStatus().value().equals("creating")){
                continue;
            }

            if (backup.getStatus().value().equals("available") ||
                    backup.getStatus().value().equals("error")) {
                this.delete(backup.getId());
                break;
            }

            i ++;

            if(i > 10){
                break;
            }
        }
        */

        this.resotre("26af1a20-f056-4f97-af94-24c3ad50b968", "45948040-7df6-476e-b984-6fb6e9ed50ab");

        System.out.println(Util.getSpliter());
    }
}
