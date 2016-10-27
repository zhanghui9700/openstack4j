package org.openstack4j.model.storage.block;


import com.google.common.base.CaseFormat;
import static com.google.common.base.Preconditions.checkNotNull;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


import org.openstack4j.common.Buildable;
import org.openstack4j.model.ModelEntity;
import org.openstack4j.model.storage.block.builder.VolumeBackupBuilder;


import java.util.Date;
import java.util.Map;

/**
 * An OpenStack Volume Snapshot which is a point-in-time copy of a volume
 *
 * @author Jeremy Unruh
 */
public interface VolumeBackup extends ModelEntity, Buildable<VolumeBackupBuilder> {

	public enum Status {
		AVAILABLE,
		ERROR,
		CREATING,
		RESTORING,
		DELETING,
		ERROR_RESTORING,
		ERROR_DELETING,
		UNRECOGNIZED;

		@JsonValue
		public String value() {
			return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_HYPHEN, name());
		}

		@Override
		public String toString() {
			return value();
		}

		@JsonCreator
		public static Status fromValue(String status) {
			try {
				return valueOf(CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_UNDERSCORE, checkNotNull(status, "status")));
			} catch (IllegalArgumentException e) {
				return UNRECOGNIZED;
			}
		}
	}

	String getId();
	String getName();
	String getDescription();
	String getVolumeId();
	String getSnapshotId();
	Status getStatus();

	Boolean getIsIncremental();
	Boolean getHasDependentBackups();
	String getFailReason();
	String getZone();
	String getContainer();
	int getObjectCount();

	/**
	 * Size in GBs
	 *
	 * @return the size of the snapshot in GB
	 */
	int getSize();

	/**
	 * @return the created data of the snapshot
	 */
	Date getCreated();
}
