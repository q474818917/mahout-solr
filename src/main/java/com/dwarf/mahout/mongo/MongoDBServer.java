package com.dwarf.mahout.mongo;

import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class MongoDBServer implements MongoOperations {
	
	private static Logger logger = Logger.getLogger(MongoDBServer.class);
	
	private static MongoDBServer _mongoDBUtils;
	
	private static Properties prop = new Properties();
	
	public MongoClient mongoClient;
	

	static {
		InputStream in = MongoDBServer.class.getClassLoader().getResourceAsStream("server.properties");
		try {
			prop.load(in);
			logger.info("server.properties loading succeed");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private MongoDBServer(){
		List<ServerAddress> addressList = new ArrayList<>();
		for(int i = 0; i < prop.getProperty("mongo_url").split(",").length; i++){
			try {
				addressList.add(new ServerAddress(prop.getProperty("mongo_url").split(",")[i],Integer.parseInt(prop.getProperty("mongo_port"))));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
		mongoClient = new MongoClient(addressList);
	}
	
	public static MongoDBServer getInstance(){
		if(_mongoDBUtils == null){
			_mongoDBUtils = new MongoDBServer();
		}
		return _mongoDBUtils;
	}

	public void close(){
		this.mongoClient.close();
	}

	@Override
	public DB getDatabase(String dbName) {
		return this.mongoClient.getDB(dbName);
	}

}
