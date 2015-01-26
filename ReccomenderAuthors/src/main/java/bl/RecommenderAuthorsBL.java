package bl;

import java.util.ArrayList;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

import dal.DataBase_API;

public class RecommenderAuthorsBL {
	public ArrayList<String> getAllAuthors(){
		DataBase_API db = new DataBase_API("dba", "dba", "localhost", "1111", "http://hujilinkeddata/");
		String sql = "SELECT DISTINCT ?creator "
				+ "WHERE { "
				+ "?s <http://purl.org/dc/terms/type> 'book'. "
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
	public ResultSet getBooksByAuthor (String author){
		DataBase_API db = new DataBase_API("dba", "dba", "localhost", "1111", "http://hujilinkeddata/");
		String sql = "SELECT DISTINCT ?title ?subject "
				+ "WHERE { " 
				+ "?creatorID ?p '" + author + "'. " 
				+ "?book ?pr ?creatorID. "
				+ "?book <http://purl.org/dc/terms/title> ?title."
				+ "?book <http://purl.org/dc/terms/subject> ?subject."
				+ "}"
				+ "ORDER BY ?title";
//		System.out.println(sql);
		ResultSet results = db.getQuery(sql);
		return results;
	}
	public ArrayList<String> getTopicsByBook (String book){
		DataBase_API db = new DataBase_API("dba", "dba", "localhost", "1111", "http://hujilinkeddata/");
		String sql = "SELECT ?topic "
				+ "WHERE { " 
				+ "?book ?p '" + book + "'. "
				+ "?book <http://purl.org/dc/terms/subject> ?topic"
				+ "}";
		ResultSet results = db.getQuery(sql);
		ArrayList<String> resultArr = new ArrayList<String>();
        while (results.hasNext()) {
            QuerySolution rs = results.nextSolution();
            resultArr.add(rs.get("topic").toString());
        }
		return resultArr;
	}
}
