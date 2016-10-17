package org.openstack4j.model.compute;

import org.openstack4j.model.ModelEntity;

public interface Host extends ModelEntity {

    String getAvaiablityZone();

    String getHostName();

    String getService();


    public interface Service extends ModelEntity {


		String getHost();

		String getBinary();

        String getStatus();
	}

}
