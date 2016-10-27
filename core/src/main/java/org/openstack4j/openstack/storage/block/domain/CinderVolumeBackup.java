package org.openstack4j.openstack.storage.block.domain;


import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.openstack4j.model.storage.block.VolumeBackup;
import org.openstack4j.model.storage.block.builder.VolumeBackupBuilder;
import org.openstack4j.openstack.common.ListResult;

import com.google.common.base.Objects;

import java.util.Date;
import java.util.List;


@JsonRootName("backup")
public class CinderVolumeBackup implements VolumeBackup {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String description;
    private String container;
    private Status status;

    @JsonProperty
    private Boolean force;

    @JsonProperty("volume_id")
    private String volumeId;

    @JsonProperty("snapshot_id")
    private String snapshotId;

    @JsonInclude(Include.NON_DEFAULT)
    @JsonProperty("size")
    private Integer size;

    @JsonProperty("created_at")
    private Date created;

    @JsonProperty("is_incremental")
    private Boolean isIncremental;

    @JsonProperty("has_dependent_backups")
    private Boolean hasDependentBackups;

    @JsonProperty("fail_reason")
    private String failReason;

    @JsonProperty("availability_zone")
    private String zone;

    @JsonProperty("object_count")
    private int objectCount;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getVolumeId() {
        return volumeId;
    }

    @Override
    public String getSnapshotId() {
        return snapshotId;
    }

    @Override
    public VolumeBackup.Status getStatus() {
        return status;
    }

    @Override
    public Boolean getIsIncremental() {
        return isIncremental;
    }

    @Override
    public Boolean getHasDependentBackups() {
        return hasDependentBackups;
    }

    @Override
    public String getFailReason() {
        return failReason;
    }

    @Override
    public String getZone() {
        return zone;
    }

    @Override
    public String getContainer() {
        return container;
    }

    @Override
    public int getObjectCount() {
        return objectCount;
    }

    @Override
    public int getSize() {
        return (size != null) ? size : 0;
    }

    @Override
    public Date getCreated() {
        return created;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).omitNullValues()
                .add("id", id).add("name", name).add("description", description)
                .add("status", status).add("size", size).add("zone", zone)
                .add("created", created).add("volumeId", volumeId)
                .add("snapshotId", snapshotId).add("container", container)
                .add("failReason", failReason).add("objectCount", objectCount)
                .add("isIncremental", isIncremental).add("hasDependent", hasDependentBackups)
                .toString();
    }


    public static class VolumeBackups extends ListResult<CinderVolumeBackup> {

		private static final long serialVersionUID = 1L;

		@JsonProperty("backups")
		private List<CinderVolumeBackup> backups;

		@Override
		protected List<CinderVolumeBackup> value() {
			return backups;
		}
	}


    @Override
    public VolumeBackupBuilder toBuilder() {
        return new ConcreteVolumeBackupBuilder(this);
    }

    public static VolumeBackupBuilder builder() {
        return new ConcreteVolumeBackupBuilder();
    }


    public static class ConcreteVolumeBackupBuilder implements VolumeBackupBuilder {

        private CinderVolumeBackup m;

        ConcreteVolumeBackupBuilder() {
			this(new CinderVolumeBackup());
		}

		ConcreteVolumeBackupBuilder(CinderVolumeBackup m) {
			this.m = m;
            this.m.force = true;
		}

		@Override
		public VolumeBackupBuilder name(String name) {
			m.name = name;
			return this;
		}

        @Override
        public VolumeBackupBuilder description(String description) {
            m.description = description;
            return this;
        }

        @Override
        public VolumeBackupBuilder volume(String volumeId) {
            m.volumeId = volumeId;
            return this;
        }

        @Override
        public VolumeBackupBuilder incremental(boolean incremental) {
            m.isIncremental = incremental;
            return this;
        }

        @Override
        public VolumeBackupBuilder force(boolean force) {
            m.force = force;
            return this;
        }

        @Override
		public VolumeBackup build() {
			return m;
		}

		@Override
		public VolumeBackupBuilder from(VolumeBackup in) {
			m = (CinderVolumeBackup) in;
			return this;
		}
    }
}
