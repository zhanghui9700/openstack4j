package org.openstack4j.openstack.storage.block.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.openstack4j.model.ModelEntity;

@JsonRootName("restore")
public class RestoreBackupAction implements ModelEntity  {
    private static final long serialVersionUID = 1L;

	@JsonProperty("volume_id")
	private final String  volumeId;

	public RestoreBackupAction(String volumeId) {
		this.volumeId = volumeId;
	}

	public static RestoreBackupAction create(String volumeId) {
		return new RestoreBackupAction(volumeId);
	}

	public String getVolumeId() {
		return volumeId;
	}
}
