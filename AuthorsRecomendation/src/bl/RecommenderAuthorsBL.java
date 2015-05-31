package bl;

import java.util.ArrayList;

import util.Log;
import util.Main;
import Authors.Author;
import Authors.Book;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

import dal.DataBase_API;

public class RecommenderAuthorsBL {
	public ArrayList<String> getAllAuthors(){
		DataBase_API db = new DataBase_API("dba", "dba", "localhost", "1111", "http://hujilinkeddata/");
		//		String sql = "SELECT DISTINCT ?creator "
		//				+ "WHERE { "
		//				+ "?s <http://purl.org/dc/terms/type> 'book'. "
		//				+ "?s <http://purl.org/dc/terms/creator> ?e. "
		//				+ "?s <http://purl.org/dc/terms/title> ?book. "
		//				+ "?e <http://www.w3.org/2000/01/rdf-schema#label> ?creator "
		//				+ "}";
		String sql = "SELECT DISTINCT ?creator "
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
	public ArrayList<Author> getAuthorsTitleSubject(){
		long start = System.currentTimeMillis();
		DataBase_API db = new DataBase_API("dba", "dba", "localhost", "1111", "http://hujilinkeddata/");
		String sql = "SELECT DISTINCT ?creator ?title ?topic "
				+ "WHERE {  "
				+ "?s <http://purl.org/dc/terms/type> <http://purl.org/dc/dcmitype/Text>. "
				+ "?s <http://purl.org/dc/terms/creator> ?e. "
				+ "?s <http://purl.org/dc/terms/title> ?title. "
				+ "?s<http://purl.org/dc/terms/subject> ?topic. "
				+ "?e <http://www.w3.org/2000/01/rdf-schema#label> ?creator "
				+ "}"
				+ "order by ?creator";

		ResultSet results = db.getQuery(sql);
		ArrayList<Author> resultArr = new ArrayList<Author>();
		int i=0,j=0;
		while (results.hasNext()) {
			Main.COUNTER++;
			QuerySolution rs = results.nextSolution();
			
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
		System.err.println("Total time build basic straucture: " + (System.currentTimeMillis() - start)/1000 + " seconds");
		return resultArr;
	}
}

