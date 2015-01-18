package bl;

import java.util.ArrayList;

import dal.DataBase_API;

public class RecommenderAuthorsBL {
	public ArrayList<String> getAuthors(){
		DataBase_API db = new DataBase_API("dba", "dba", "localhost", "1111", "http://hujilinkeddata/");
		String sql = "SELECT DISTINCT ?creator "
				+ "WHERE { "
				+ "?s <http://purl.org/dc/terms/type> 'book'. "
				+ "?s <http://purl.org/dc/terms/creator> ?e. "
				+ "?s <http://purl.org/dc/terms/title> ?book. "
				+ "?e <http://www.w3.org/2000/01/rdf-schema#label> ?creator "
				+ "}";
		return db.getAuthors(sql);
	}
	
	public ArrayList<String> getAuthorsSubjects (String creator){
		DataBase_API db = new DataBase_API("dba", "dba", "localhost", "1111", "http://hujilinkeddata/");
		String sql = "SELECT ?topic "
				+ "WHERE { " 
				+ "?creatorID ?p '" + creator + "'. " 
				+ "?book ?pr ?creatorID. "
				+ "?book <http://purl.org/dc/terms/subject> ?topic "
				+ "}";
		return db.getTopics(sql);
	}
}
