package dal;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

public class DataBase_API {
	
	@SuppressWarnings("unused")
	private final String username;

	@SuppressWarnings("unused")
	private final String password;

	@SuppressWarnings("unused")
	private final String hostname;

	@SuppressWarnings("unused")
	private final String port;

	private final String graphName;

	private VirtGraph graph;
	
	public void addLog (String str){
		try
		{
		    String filename= "Log.txt";
		    FileWriter fw = new FileWriter(filename,true); //the true will append the new data
		    fw.write(str+ "\n");//appends the string to the file
		    fw.close();
		}
		catch(IOException ioe)
		{
		    System.err.println("IOException: " + ioe.getMessage());
		}
	}
	
	public DataBase_API(String username, String password,
			String hostname, String port, String graphName) {
		
		this.username = username;
		this.password = password;
		this.hostname = hostname;
		this.port = port;
		this.graphName = graphName;
		String conString = "jdbc:virtuoso://%s:%s/charset=UTF-8/log_enable=2";
		this.graph = new VirtGraph(graphName,String.format(conString, hostname, port), username, password);
		
	
	}
	
	public ArrayList<String> getAuthors(String sparqlQry) {
		ResultSet results = getQuery(sparqlQry);
		ArrayList<String> resultArr = new ArrayList<String>();
        while (results.hasNext()) {
            QuerySolution rs = results.nextSolution();
            resultArr.add(rs.get("creator").toString());
        }
		return resultArr;
	}
	
	public ArrayList<String> getTopics(String sparqlQry){
		ResultSet results = getQuery(sparqlQry);
		ArrayList<String> resultArr = new ArrayList<String>();
        while (results.hasNext()) {
            QuerySolution rs = results.nextSolution(); 
            resultArr.add(rs.get("topic").toString());
        }
		return resultArr;
	}

	public ArrayList<String> getTopicsByBooks(String sql) {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getBooksByAuthor(String sparqlQry) {
		ResultSet results = getQuery(sparqlQry);
		return results;
//		ArrayList<String> resultArr = new ArrayList<String>();
//        while (results.hasNext()) {
//            QuerySolution rs = results.nextSolution(); 
//            resultArr.add(rs.get("title").toString());
//        }
//		return resultArr;
	}
	
	public ResultSet getQuery(String sparqlQry){
		if(sparqlQry == null || sparqlQry.isEmpty())
		{
			return null;
		}

		Query sparql = QueryFactory.create(sparqlQry);
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create (sparql, this.graph);
		return vqe.execSelect();
	}
	
}
