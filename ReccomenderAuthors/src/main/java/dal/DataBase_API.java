package dal;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import util.RDFTripleModel;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

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
	public Collection<RDFTripleModel> GetTripples(String sparqlQry) {
		if(sparqlQry == null || sparqlQry.isEmpty())
		{
			return null;
		}

		Query sparql = QueryFactory.create(sparqlQry);
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create (sparql, this.graph);
		ResultSet results = vqe.execSelect();
		ArrayList<RDFTripleModel> resultArr = new ArrayList<RDFTripleModel>();
        while (results.hasNext()) {
        	String s, p,o;
        	s= "";
        	p="create by";
        	o="";
            QuerySolution rs = results.nextSolution();
            if (rs.get("book") != null){
            	s = rs.get("book").toString();
            }
            if (rs.get("p") != null){
            	p = rs.get("p").toString();
            }
            if (rs.get("creator") != null){
            	o = rs.get("creator").toString();
            }
        
            RDFTripleModel temp = new RDFTripleModel(s, p, o);
            resultArr.add(temp);
        }
        try {
			FileOutputStream writer = new FileOutputStream("Log.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        for (RDFTripleModel rdfTripleModel : resultArr) {
        	addLog(rdfTripleModel.toString());
		}
        System.out.println("finish");
		return null;

	}
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
	
}
