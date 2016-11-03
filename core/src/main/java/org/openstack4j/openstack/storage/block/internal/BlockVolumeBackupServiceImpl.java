package org.openstack4j.openstack.storage.block.internal;

import org.openstack4j.api.Builders;
import org.openstack4j.api.storage.BlockVolumeBackupService;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.storage.block.VolumeBackup;

import org.openstack4j.openstack.storage.block.domain.CinderVolumeBackup;
import org.openstack4j.openstack.storage.block.domain.CinderVolumeBackup.VolumeBackups;
import org.openstack4j.openstack.storage.block.domain.RestoreBackupAction;

//import org.openstack4j.openstack.storage.block.domain.CinderVolumeSnapshot;
//import org.openstack4j.openstack.storage.block.domain.CinderVolumeSnapshot.VolumeSnapshots;

import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * OpenStack (Cinder) Volume Snapshot Operations API Implementation.
 *
 * @author Jeremy Unruh
 */
public class BlockVolumeBackupServiceImpl extends BaseBlockStorageServices implements BlockVolumeBackupService {


	@Override
	public List<? extends VolumeBackup> list() {
		return get(VolumeBackups.class, uri("/backups/detail")).execute().getList();
	}

	@Override
	public List<? extends VolumeBackup> list(Map<String, String> filteringParams) {
		return null;
	}

	@Override
	public VolumeBackup get(String backupId) {
		checkNotNull(backupId);
		return get(CinderVolumeBackup.class, uri("/backups/%s", backupId)).execute();
	}

	@Override
	public ActionResponse delete(String backupId) {
		checkNotNull(backupId);
		return deleteWithResponse(uri("/backups/%s", backupId)).execute();
	}

	@Override
	public VolumeBackup create(VolumeBackup backup) {
		checkNotNull(backup);
		checkNotNull(backup.getVolumeId());
		return post(CinderVolumeBackup.class, uri("/backups")).entity(backup).execute();
	}

	@Override
	public ActionResponse restore(String backupId, String volumeId) {
		checkNotNull(backupId);
		checkNotNull(volumeId);

		return post(ActionResponse.class, uri("/backups/%s/restore", backupId))
				.entity(new RestoreBackupAction(volumeId))
				.execute();
	}
}
