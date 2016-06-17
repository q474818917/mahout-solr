package com.dwarf.mahout.mongo;

import com.mongodb.DB;

public interface MongoOperations {
	
	DB getDatabase(String dbName);
	
	void close();
	
}
