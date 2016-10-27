package org.openstack4j.model.storage.block.builder;

import org.openstack4j.common.Buildable.Builder;
import org.openstack4j.model.storage.block.VolumeBackup;

import java.util.Map;

/**
 * A Builder which creates a Volume Snapshot.
 *
 * NOTE: The only <B>REQUIRED</B> field when creating a snapshot is
 * {@link #volume(String)} . All other fields are optional
 *
 * @author Jeremy Unruh
 */
public interface VolumeBackupBuilder extends Builder<VolumeBackupBuilder, VolumeBackup> {
	VolumeBackupBuilder name(String name);

	VolumeBackupBuilder description(String description);

	VolumeBackupBuilder volume(String volumeId);

	VolumeBackupBuilder incremental(boolean incremental);

	VolumeBackupBuilder force(boolean force);
}
