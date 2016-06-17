package com.dwarf.mahout.mongo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class MongoStore {
	
	static DB db;
	
	public MongoStore(String dbName){
		db = MongoDBServer.getInstance().getDatabase(dbName);
	}
	
	public static void main(String[] args) throws Exception {
		MongoStore store = new MongoStore("blog");
		DBCollection collection = db.getCollection("items");
		List<DBObject> objectList = new ArrayList<>();
		FileReader reader = new FileReader("C:\\Users\\jiyu\\Desktop\\dataset.csv");
		BufferedReader br = new BufferedReader(reader);
		String str = null;
		while ((str = br.readLine()) != null) {
			
			String[] strs = str.split(",");
			DBObject dbObject = new BasicDBObject();
			dbObject.put("user_id", strs[0]);
			dbObject.put("item_id", Integer.parseInt(strs[1]));
			dbObject.put("preference", Float.parseFloat(strs[2]));
			objectList.add(dbObject);
		}
		br.close();
		reader.close();
		collection.insert(objectList);
	}

}
