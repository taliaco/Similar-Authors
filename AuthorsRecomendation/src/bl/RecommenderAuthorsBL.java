package bl;

import java.util.ArrayList;

import util.Log;
import util.Main;
import Authors.Author;
import Authors.Book;
import Authors.TopicWeight;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

import dal.DataBase_API;

public class RecommenderAuthorsBL {
	String username = "dba"; 
	String password = "dba";
	String hostname ="localhost";
	String port = "1111";
	String graphName = "http://wwu_bibs/";
	//	String graphName = "http://hujilinkeddata/";
	//	String graphName = "test";
	DataBase_API db = new DataBase_API(username, password, hostname, port, graphName);
	public ArrayList<String> getAllAuthors(){
		//DataBase_API db = new DataBase_API(username, password, hostname, port, graphName);
		String sql = "SELECT DISTINCT ?e ?creator "
				+ "WHERE { "
				+ "?s <http://purl.org/dc/terms/type> <http://purl.org/dc/dcmitype/Text>. "
				+ "?s <http://purl.org/dc/terms/creator> ?e. "
				+ "?s <http://purl.org/dc/terms/title> ?book. "
				+ "?e <http://www.w3.org/2000/01/rdf-schema#label> ?creator "
				+ "}";
		ResultSet results = db.getQuery(sql);
		ArrayList<String> resultArr = new ArrayList<String>();
		while (results.hasNext()) {
			QuerySolution rs = results.nextSolution();
			resultArr.add(rs.get("creator").toString());
		}
		return resultArr;
	}
	//return all authors name and they dublin core code
	public ResultSet getAllAuthorsResultSet(){
		//DataBase_API db = new DataBase_API(username, password, hostname, port, graphName);
		String sql = "SELECT DISTINCT ?name ?dcCreator "
				+ "WHERE { "
				+ "?s <http://purl.org/dc/terms/type> <http://purl.org/dc/dcmitype/Text> . "
				+ "?s <http://purl.org/dc/terms/creator> ?dcCreator . "
				+ "?dcCreator <http://www.w3.org/2000/01/rdf-schema#label> ?name ."
				+ "}"
				+ "ORDER BY ?name";
		ResultSet results = db.getQuery(sql);
		return results;
	}
	public ArrayList<String> getAuthorsSubjects (String creator){
		//		DataBase_API db = new DataBase_API(username, password, hostname, port, graphName);
		String sql = "SELECT ?topic "
				+ "WHERE { " 
				+ "?creatorID ?p '" + creator + "'. " 
				+ "?book ?pr ?creatorID. "
				+ "?book <http://purl.org/dc/terms/subject> ?topic ."
				+ "}";
		return db.getTopics(sql);
	}
	//	public ResultSet getBooksByAuthor (String author){
	//		//DataBase_API db = new DataBase_API(username, password, hostname, port, graphName);
	//		String sql = "SELECT DISTINCT ?title ?subject "
	//				+ "WHERE { " 
	//				+ "?creatorID ?p '" + author + "'. " 
	//				+ "?book ?pr ?creatorID. "
	//				+ "?book <http://purl.org/dc/terms/title> ?title."
	//				+ "?book <http://purl.org/dc/terms/subject> ?subject."
	//				+ "}"
	//				+ "ORDER BY ?title";
	//		//		System.out.println(sql);
	//		ResultSet results = db.getQuery(sql);
	//		return results;
	//	}
	public ResultSet getBooksByAuthor (String dcCreator){
		//DataBase_API db = new DataBase_API(username, password, hostname, port, graphName);
		String sql = "SELECT DISTINCT ?title ?subject "
				+ "WHERE { " 
				+ "?book <http://purl.org/dc/terms/creator> <"+ dcCreator + ">."
				+ "?book <http://purl.org/dc/terms/title> ?title."
				+ "?book <http://purl.org/dc/terms/subject> ?subject."
				+ "}"
				+ "ORDER BY ?title";
		//		System.out.println(sql);
		ResultSet results = db.getQuery(sql);
		return results;
	}
	public ArrayList<String> getTopicsByBook (String book){
		//DataBase_API db = new DataBase_API(username, password, hostname, port, graphName);
		String sql = "SELECT ?topic "
				+ "WHERE { " 
				+ "?book ?p '" + book + "'. "
				+ "?book <http://purl.org/dc/terms/subject> ?topic."
				+ "}";
		ResultSet results = db.getQuery(sql);
		ArrayList<String> resultArr = new ArrayList<String>();
		while (results.hasNext()) {
			QuerySolution rs = results.nextSolution();
			resultArr.add(rs.get("topic").toString());
		}
		return resultArr;
	}
	public ArrayList<Author> getAuthorsTitleSubject(){
		long start = System.currentTimeMillis();
		//DataBase_API db = new DataBase_API(username, password, hostname, port, graphName);
		String sql = "SELECT DISTINCT ?creator ?title ?topic "
				+ "WHERE {  "
				+ "?s <http://purl.org/dc/terms/type> <http://purl.org/dc/dcmitype/Text>. "
				+ "?s <http://purl.org/dc/terms/creator> ?e. "
				+ "?s <http://purl.org/dc/terms/title> ?title. "
				+ "?s<http://purl.org/dc/terms/subject> ?topic. "
				+ "?e <http://www.w3.org/2000/01/rdf-schema#label> ?creator ."
				+ "}";
		//+ "order by ?creator";

		ResultSet results = db.getQuery(sql);
		ArrayList<Author> resultArr = new ArrayList<Author>();
		int i=0,j=0;
		while (results.hasNext()) {
			Main.COUNTER++;
			QuerySolution rs = results.nextSolution();
			if ((rs.get("creator")!= null) || (rs.get("topic")!= null)|| (rs.get("title")!= null)){
				i = resultArr.size()-1;
				if(i>-1 && resultArr.get(i).getName().equals(rs.get("creator").toString())){
					Log.AddLog(rs.get("creator").toString());
					j = resultArr.get(i).getBookList().size()-1;
					if (j > -1 && resultArr.get(i).getBookList().get(j).getBookName().equals(rs.get("title").toString())){
						//					System.out.println(rs.get("title").toString());
						resultArr.get(i).addTopicToBook(j,rs.get("topic").toString());
					}
					else{
						Book tmpB = new Book(rs.get("title").toString(),rs.get("topic").toString());
						resultArr.get(i).addBook(tmpB);
					}
				}
				else{
					Book book = new Book(rs.get("title").toString(),rs.get("topic").toString());
					Author a = new Author(rs.get("creator").toString(),book);
					resultArr.add(a);
				}
			}
		}
		System.err.println("Total time build basic straucture: " + (System.currentTimeMillis() - start)/1000 + " seconds");
		return resultArr;
	}

	public long getNumOfAuthors(){
		//DataBase_API db = new DataBase_API(username, password, hostname, port, graphName);
		long numOfAuthor = -1;
		String sql = "SELECT (count (DISTINCT ?dcCreator) as ?countCreator) " 
				+ " WHERE {"  
				+ " ?s <http://purl.org/dc/terms/type> <http://purl.org/dc/dcmitype/Text>." 
				+ " ?s <http://purl.org/dc/terms/creator> ?dcCreator." 
				+ " }";
		try{
			ResultSet results = db.getQuery(sql);
			QuerySolution rs = results.nextSolution();
			//			System.err.println(rs.get("countCreator").asLiteral().getLong());
			if (rs.get("countCreator") != null){
				numOfAuthor = rs.get("countCreator").asLiteral().getLong();
			}
		}
		catch(Exception e)
		{
			System.err.println("error - getNumOfAuthors\n\t"+ e.getMessage());
		}
		return numOfAuthor;
	}

	public long getNumOfAuthorsWithTopic(String topic){
		//DataBase_API db = new DataBase_API(username, password, hostname, port, graphName);
		long NumOfAuthorsWithTopic = -1;
		String sql = "SELECT  (count( DISTINCT ?dcCreator) as ?countCreator)"
				+ " WHERE {"  
				+ " ?s <http://purl.org/dc/terms/type> <http://purl.org/dc/dcmitype/Text>." 
				+ " ?s <http://purl.org/dc/terms/creator> ?dcCreator." 
				+ " ?s <http://purl.org/dc/terms/subject> '" + topic +"'." 
				+ " }";
		try{
			ResultSet results = db.getQuery(sql);
			QuerySolution rs = results.nextSolution();
			if (rs.get("countCreator") != null){
				NumOfAuthorsWithTopic = rs.get("countCreator").asLiteral().getLong();
			}
		}
		catch(Exception e)
		{
			System.err.println("error - getNumOfAuthorsWithTopic - " + topic +"\n\t" + e.getMessage());

		}
		return NumOfAuthorsWithTopic;
	}

	public long getNumOfAuthorTopics(String dcCreator){
		//DataBase_API db = new DataBase_API(username, password, hostname, port, graphName);
		long NumOfAuthorTopics = -1;
		String sql = "SELECT (count(?topic) as ?countTopic) WHERE {"
				+ " SELECT DISTINCT ?topic "
				+ " WHERE {  "
				+ " ?s <http://purl.org/dc/terms/type> <http://purl.org/dc/dcmitype/Text>." 
				+ " ?s <http://purl.org/dc/terms/creator> <" + dcCreator +">."
				+ " ?s <http://purl.org/dc/terms/subject> ?topic." 
				+ " }"
				+ " }";
		try{
			ResultSet results = db.getQuery(sql);
			QuerySolution rs = results.nextSolution();
			if (rs.get("countTopic")!= null){
				NumOfAuthorTopics = rs.get("countTopic").asLiteral().getLong();;
			}
		}
		catch(Exception e)
		{
			System.err.println("error - getNumOfAuthorTopics - " + dcCreator +"\n\t" + e.getMessage());

		}
		return NumOfAuthorTopics;
	}

	public long getMaxTopicToAuthor(String dcCreator){
		//DataBase_API db = new DataBase_API(username, password, hostname, port, graphName);
		long NumOfAuthorTopics = -1;
		String sql = "SELECT (sum(?countT) as ?maxCount){"
				+ " SELECT DISTINCT ?topic (count(?topic) as ?countT)"
				+ " WHERE {"  
				+ " ?s <http://purl.org/dc/terms/type> <http://purl.org/dc/dcmitype/Text>." 
				+ " ?s <http://purl.org/dc/terms/creator> <" + dcCreator + ">."
				+ " ?s <http://purl.org/dc/terms/subject> ?topic." 
				+ " }group by ?topic"
				+ " }";
		try{
			ResultSet results = db.getQuery(sql);
			QuerySolution rs = results.nextSolution();
			if (rs.get("maxCount") != null){
				NumOfAuthorTopics = rs.get("maxCount").asLiteral().getLong();
			}
		}
		catch(Exception e)
		{
			System.err.println("error - getMaxTopicToAuthor - " + dcCreator +"\n\t" + e.getMessage());

		}
		return NumOfAuthorTopics;
	}
	public ArrayList<TopicWeight> getTopicWeightVecForAuthor(String dcCreator){
		//DataBase_API db = new DataBase_API(username, password, hostname, port, graphName);
		ArrayList<TopicWeight> resultArr = new ArrayList<TopicWeight>();

		String sql = "SELECT DISTINCT  (count(?topic) as ?count) ?topic"
				+ " WHERE {  "
				+ " ?s <http://purl.org/dc/terms/type> <http://purl.org/dc/dcmitype/Text>." 
				+ " ?s <http://purl.org/dc/terms/creator> <" + dcCreator + ">."
				+ " ?s <http://purl.org/dc/terms/subject> ?topic." 
				+ " }"
				+ "group by ?topic";

		try{
			ResultSet results = db.getQuery(sql);

			while (results.hasNext()) {
				QuerySolution rs = results.nextSolution();
				String topic = rs.get("topic").toString();
				double count = rs.get("count").asLiteral().getDouble();
				resultArr.add(new TopicWeight(topic, count));
			}
		}
		catch(Exception e)
		{
			System.err.println("error - getTopicWeightVecForAuthor - " + dcCreator +"\n\t" + e.getMessage());
			return null;
		}
		return resultArr;
	}

	public ArrayList<TopicWeight> getTFIDFTopic(String dcCreator){
		//DataBase_API db = new DataBase_API(username, password, hostname, port, graphName);
		ArrayList<TopicWeight> resultArr = new ArrayList<TopicWeight>();

		String sql = "select ?TFIDFTopic "
				+ " where {"
				+ " <" + dcCreator + "> <TF-IDF-TopicWithWeight> ?TFIDFTopic."
				+ " }";

		try{
			ResultSet results = db.getQuery(sql);

			while (results.hasNext()) {
				QuerySolution rs = results.nextSolution();
				String topicWeight = rs.get("TFIDFTopic").toString();
				String topic = topicWeight.substring(0,topicWeight.lastIndexOf(" -- "));
				double count = Double.parseDouble(topicWeight.substring(topicWeight.lastIndexOf(" -- ")+(" -- ").length()));
				resultArr.add(new TopicWeight(topic, count));
			}
		}
		catch(Exception e)
		{
			System.err.println("error - getTFIDFTopic - " + dcCreator +"\n\t" + e.getMessage());
			return new ArrayList<TopicWeight>();
		}
		return resultArr;
	}
	public long getNumOfTopics(){
		//DataBase_API db = new DataBase_API(username, password, hostname, port, graphName);
		long NumOfTopics = -1;
		String sql = "SELECT (count (DISTINCT ?topic) as ?countTopic) " 
				+ " WHERE {"  
				+ " ?s <http://purl.org/dc/terms/type> <http://purl.org/dc/dcmitype/Text>." 
				+ " ?s <http://purl.org/dc/terms/subject> ?topic." 
				+ " }";
		try{
			ResultSet results = db.getQuery(sql);
			QuerySolution rs = results.nextSolution();
			//			System.err.println(rs.get("countCreator").asLiteral().getLong());
			if (rs!= null){
				NumOfTopics = rs.get("countTopic").asLiteral().getLong();
			}
		}
		catch(Exception e)
		{
			System.err.println("error - getNumOfTopics\n\t"+ e.getMessage());
		}
		return NumOfTopics;
	}
	public void deleteALLTFIDF(){
		String sql = "DELETE FROM GRAPH <" + this.graphName + ">{  ?o ?p ?s }"
				+ " WHERE {"
				+ " ?o <TF-IDF-TopicWithWeight> ?s ."
				+ " ?o ?p ?s. "
				+ " }";

		db.deleteQuery(sql);
	}
}

