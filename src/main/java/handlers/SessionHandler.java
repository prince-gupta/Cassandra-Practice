package handlers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;

import static java.lang.System.out;

public enum SessionHandler {
	INSTANCE;

	private Map<String, Optional<Session>> sessions = new HashMap<>();

	private PrepareSession prepare = (inet) -> {
		Cluster cluster = Cluster.builder().addContactPoint(inet).withPort(9000).build();
		final Metadata metadata = cluster.getMetadata();

		out.printf("Connected to cluster: %s\n", metadata.getClusterName());

		for (final Host host : metadata.getAllHosts())

		{
			out.printf("Datacenter: %s; Host: %s; Rack: %s\n",
					host.getDatacenter(), host.getAddress(), host.getRack());
		}
		Session session = cluster.connect();
		return Optional.of(session);
	};

	private Function<String, Optional<Session>> prepareFn = (inet) -> {
		return prepare.connect(inet);
	};

	public Optional<Session> getSession(String cluster_inet) {
		return sessions.computeIfAbsent(cluster_inet, prepareFn);
	}

	private

	interface PrepareSession {
		Optional<Session> connect(String cluster_inet);
	}
}
