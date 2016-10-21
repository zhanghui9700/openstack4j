package org.openstack4j.example;

import org.openstack4j.api.OSClient;
import org.openstack4j.model.compute.Host;
import org.openstack4j.model.compute.ext.Hypervisor;

import java.util.List;

public class HostDemo {

    public void listHost(){
        System.out.println("HostDemo.listHost start.");
        OSClient os = Util.getAdminClient();
        List<? extends Host> hosts =  os.compute().hosts().list();
        for(int i=0; i < hosts.size(); i++)
        {
            /**
             * NovaHost{
             *      hostname=node-4.suninfo.com,
             *      service=nova-compute,
             *      avaiablityZone=nova,
             *      status=enabled,
             *      state=up}
             * */
            System.out.println("get host state: " + hosts.get(i));
        }

        System.out.println("HostDemo.listHost end.");
    }

    public void computeDisableEnable(){
        String hostname = "node-4.suninfo.com";
        System.out.println("HostDemo.computeDisableEnable start.");
        OSClient os = Util.getAdminClient();
        Host.Service service = os.compute().hosts()
                .serviceDisable(hostname, "nova-compute");

        System.out.println("disable service: " + service);

        Host.Service enable_service = os.compute().hosts()
                .serviceEnable(hostname, "nova-compute");

        System.out.println("enable service: " + enable_service);
        System.out.println("HostDemo.computeDisableEnable end.");
    }

    public void listHypervisor() {
        System.out.println("HostDemo.listHypervisor start.");

        OSClient os = Util.getAdminClient();
        List<? extends Hypervisor> hypervisors = os.compute().hypervisors().list();

        for(Hypervisor hypervisor: hypervisors){
            /**
            * ExtHypervisor{id=1,
            * hypervisor_hostname=mitaka.fx-dev.com,
            * version=0,
            * type=QEMU,
            * host_ip=172.16.173.78,
            * running=3,
            * freeDisk=38,
            * freeRam=3249,
            * vcpus=1,
            * usedVcpu=3,
            * localDisk=38,
            * localDiskUsed=0,
            * localMemory=3953,
            * localMemoryUsed=704,
            * currentWorkload=0,
            * leastDiskAvail=32,
            * running_vms=3,
            * service=HypervisorService{id=7, host=mitaka},
            * cpuInfo=HypervisorCPUInfo{
            *                   vendor=Intel,
            *                   model=Westmere,
            *                   arch=x86_64,
            *                   features=[pge, avx, clflush, sep, syscall, vme, msr, xsave, cmov, fpu, pat, monitor, lm, tsc, nx, fxsr, sse4.1, pae, sse4.2, pclmuldq, mmx, osxsave, cx8, mce, de, rdtscp, mca, pse, pni, popcnt, apic, sse, lahf_lm, aes, sse2, ssse3, cx16, pse36, mtrr, rdrand],
            *                   topology=HypervisorCPUTopology{
            *                                   cores=1,
            *                                   threads=1,
            *                                   sockets=1}
            *                   }
            * }
            * */
            System.out.println("get hypervisor: " + hypervisor.getHypervisorHostname()); //get hypervisor: mitaka.fx-dev.com
            System.out.println("get hypervisor: " + hypervisor.getService().getHost()); //get hypervisor: mitaka
        }


        System.out.println("HostDemo.listHypervisor end.");
    }
}
