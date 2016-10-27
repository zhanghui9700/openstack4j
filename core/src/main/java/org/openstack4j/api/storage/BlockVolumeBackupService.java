package org.openstack4j.api.storage;

import org.openstack4j.common.RestService;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.storage.block.VolumeBackup;

import java.util.List;
import java.util.Map;

/**
 * OpenStack (Cinder) Volume Snapshot Operations API.
 *
 * @author Jeremy Unruh
 */
public interface BlockVolumeBackupService extends RestService {

	/**
	 * Lists detailed information for all Block Storage snapshots that the tenant who submits the request can access.
	 *
	 * @return List of VolumeSnapshot
	 */
	List<? extends VolumeBackup> list();

	/**
	 * Returns list of Block Storage snapshots filtered by parameters.
	 * 
	 * @param filteringParams map (name, value) of filtering parameters
	 * @return 
	 */
	List<? extends VolumeBackup> list(Map<String, String> filteringParams);

	/**
	 * Shows information for a specified snapshot.
	 *
	 * @param backupId the snapshot identifier
	 * @return the volume snapshot or null
	 */
	VolumeBackup get(String backupId);
	
	/**
	 * Deletes a specified snapshot
	 *
	 * @param backupId the snapshot identifier
	 * @return the action response
	 */
	ActionResponse delete(String backupId);

	/**
	 * Creates a backup, which is a point-in-time copy of a volume. You can restore a volume from the backup.
	 * 
	 * NOTE: Volume to be backed up must be available or in-use,
	 *
	 * @param backup the backup to create
	 * @return the newly created backup
	 */
	VolumeBackup create(VolumeBackup backup);
	
}
