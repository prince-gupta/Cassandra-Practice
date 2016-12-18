package handlers;

import java.util.Optional;

import com.datastax.driver.core.Session;
import com.practice.utility.CustomStringUtility;

public class KeyspaceHandler {

	public static void createWithSimpleStartegy(String keyspaceName, String cluster_inet, String replication) {
		String query = "CREATE KEYSPACE <keyspaceName> WITH replication "
				+ "= {'class':'SimpleStrategy', 'replication_factor':<replication>};";
		query = CustomStringUtility.replace.replace(query, keyspaceName, "<keyspaceName>");
		query = CustomStringUtility.replace.replace(query, replication, "<replication>");

		// creating Cluster object
		Optional<Session> session = SessionHandler.INSTANCE.getSession(cluster_inet);
		if(session.isPresent()){
			session.get().execute(query);
			System.out.println("Keyspace created");
		}
		else{
			System.err.println("Keyspace not created");
		}
	}

	public static void useKeyspace(String keyspaceName, String cluster_inet) {
		String query = "USE <keyspaceName>";
		query = CustomStringUtility.replace.replace(query, keyspaceName, "<keyspaceName>");
		Optional<Session> session = SessionHandler.INSTANCE.getSession(cluster_inet);
		if(session.isPresent()){
			session.get().execute(query);
			System.out.println("Keyspace switched");
		}
		else{
			System.err.println("Keyspace not switched");
		}
	}
	
	public static void main(String args[]){
		String cluster_inet = "localhost";
		String keyspaceName = "practice-java";
		KeyspaceHandler.createWithSimpleStartegy(keyspaceName, cluster_inet,"1");
		KeyspaceHandler.useKeyspace(keyspaceName, cluster_inet);
		
	}

}
