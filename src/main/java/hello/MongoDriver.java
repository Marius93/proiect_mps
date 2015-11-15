package hello;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.WriteResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.concurrent.TimeUnit.SECONDS;

public class MongoDriver {

	Mongo mongoClient;
	DB database ;
	DBCollection collection;
	public MongoDriver(){
		mongoClient= new MongoClient("localhost", 27017);
		database = mongoClient.getDB("innovation");
		collection = database.getCollection("users");
	}
	@SuppressWarnings("deprecation")
	public MongoDriver(String ip,int port,String database){
		mongoClient =  new MongoClient(ip,port);
		this.database = mongoClient.getDB(database);
	}
	private void selectCollection(String collection){
		this.collection = database.getCollection(collection);
	}
	public void insertDocumentInCollectionTest(BasicDBObject o,DBCollection col) {
		BasicDBObject doc = new BasicDBObject("name", "MongoDB")
        .append("type", "database")
        .append("count", 1)
        .append("info", new BasicDBObject("x", 203).append("y", 102));
		collection = database.getCollection("users");
		collection.insert(doc);
		DBObject myDoc = collection.findOne();
		System.out.println(myDoc);
	}
	public WriteResult insertDocumentInCollection(BasicDBObject o,String col){
		selectCollection(col);
		return collection.insert(o);
	}
	public WriteResult deleteDocumentFromCollection(BasicDBObject o,String col){
		selectCollection(col);
		return collection.remove(o);
	}
	public ArrayList<BasicDBObject> findDocumentByQuery(BasicDBObject o,String col){
		selectCollection(col);
		DBCursor cursor = collection.find(o);
		ArrayList<BasicDBObject> list = new ArrayList<BasicDBObject>();
		while(cursor.hasNext()) {
	             list.add((BasicDBObject)cursor.next());
	    }
		return list;
	}
	public void deleteAllDocumentsFromCollection(){
		DBCursor cursor = collection.find();
		while(cursor.hasNext()){
			collection.remove(cursor.next());
		}
	}
}
