package org.openstack4j.openstack.compute.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.openstack4j.openstack.common.ListResult;
import org.openstack4j.model.compute.Host;
import org.openstack4j.model.compute.Host.Service;
import com.google.common.base.Objects;

@JsonRootName("host")
@JsonIgnoreProperties(ignoreUnknown=true)
public class NovaHost implements Host {
    private static final long serialVersionUID = 1L;

	@JsonProperty("host_name")
    private String hostname;
	private String service;
	@JsonProperty("zone")
	private String zone;

	@Override
	public String getHostName() {
		return hostname;
	}

	@Override
	public String getService() {
		return service;
	}

	@Override
	public String getAvaiablityZone() {
		return zone;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).omitNullValues()
						.add("hostname", hostname)
						.add("service", service)
						.add("avaiablityZone", zone)
						.toString();
	}

	public static class Hosts extends ListResult<NovaHost> {

		private static final long serialVersionUID = 1L;

		@JsonProperty("hosts")
		List<NovaHost> hosts;

		@Override
        public List<NovaHost> value() {
			return hosts;
		}
	}

	@JsonRootName("service")
    public static class  HostService implements Service {

        private static final long serialVersionUID = 1L;
        private String host;
        private String binary;
		private String status;

        @Override
        public String getHost() {
            return host;
        }

        @Override
        public String getBinary() {
            return binary;
        }

		@Override
        public String getStatus() {
            return status;
        }

        @Override
        public String toString() {
            return Objects.toStringHelper(this).omitNullValues()
					.add("host", host)
					.add("binary", binary)
					.add("status", status)
					.toString();
        }
    }
}
