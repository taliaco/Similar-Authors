package dal;

import java.util.ArrayList;
import java.util.Collection;

import dal.IRepository;
import util.RDFTripleModel;

import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;
import virtuoso.jena.driver.VirtuosoUpdateFactory;
import virtuoso.jena.driver.VirtuosoUpdateRequest;

import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

public class JenaVirtuosoDriver implements IRepository{

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

	public JenaVirtuosoDriver(String username, String password,
			String hostname, String port, String graphName, Boolean clearGraphOnStart) {
		super();
		this.username = username;
		this.password = password;
		this.hostname = hostname;
		this.port = port;
		this.graphName = graphName;
		String conString = "jdbc:virtuoso://%s:%s/charset=UTF-8/log_enable=2";
		this.graph = new VirtGraph(String.format(conString, hostname, port), username, password);
		if(clearGraphOnStart!= null && clearGraphOnStart == true)
		{
			System.out.println("Clearing Graph");
			this.ClearGraph();
		}
	}

	@Override
	public <T extends RDFTripleModel> boolean InsertTriple(T tripple) {
		if (tripple != null)
		{
			try {

				String str = "INSERT INTO GRAPH <" + this.graphName + "> { "
						+ tripple.toString() + " }";
				VirtuosoUpdateRequest vur = VirtuosoUpdateFactory.create(str,
						this.graph);
				vur.exec();
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean InsertTripleCollection(Collection<? extends RDFTripleModel> collection) {
		if (collection != null && collection.isEmpty() == false)
		{
			try {
				StringBuilder sb = new StringBuilder();
				for (RDFTripleModel triple : collection) {
					sb.append(triple.toString());
					sb.append('.');
				}
				String s = sb.toString();
				String str = "INSERT INTO GRAPH <" + this.graphName + "> { "
						+ s + " }";
				VirtuosoUpdateRequest vur = VirtuosoUpdateFactory.create(str,
						this.graph);
				vur.exec();
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}

	@Override
	public <T extends RDFTripleModel> boolean DeleteTriple(T tripple) {
		if(tripple != null){
			 try {
				String str = "DELETE FROM GRAPH <" + this.graphName + "> {"
						+ tripple.toString() + " }";
				VirtuosoUpdateRequest vur = VirtuosoUpdateFactory.create(str,
						this.graph);
				vur.exec();
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean DeleteTripleCollection(
			Collection<? extends RDFTripleModel> collection) {
		if (collection != null && collection.isEmpty() == false)
		{
			try {
				StringBuilder sb = new StringBuilder();
				for (RDFTripleModel triple : collection) {
					sb.append(triple.toString());
					sb.append('.');
				}
				String s = sb.toString();
				String str = "DELETE FROM GRAPH <" + this.graphName + "> { "
						+ s + " }";
				VirtuosoUpdateRequest vur = VirtuosoUpdateFactory.create(str,
						this.graph);
				vur.exec();
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean ClearGraph() {
		try{
			String str = "CLEAR GRAPH <" + this.graphName + ">";
			VirtuosoUpdateRequest vur = VirtuosoUpdateFactory.create(str, this.graph);
			vur.exec();
			return true;
		}
		catch(Exception ex)
		{
			return false;
		}

	}

	@Override
	public Collection<RDFTripleModel> GetTripples(String subject) {
		if(subject == null || subject.isEmpty())
		{
			return null;
		}

		Query sparql = QueryFactory.create("DESCRIBE " +  subject + " FROM "+ graphName);
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create (sparql, this.graph);
		Model model = vqe.execDescribe();
		Graph g = model.getGraph();
        System.out.println("\nDESCRIBE results:");
        ArrayList<RDFTripleModel> result = new ArrayList<>();
        for (ExtendedIterator<Triple> i = g.find(Node.ANY, Node.ANY, Node.ANY); i.hasNext();)
        {
        	Triple t = (Triple)i.next();
        	RDFTripleModel temp = new RDFTripleModel(t.getSubject().toString(), t.getPredicate().toString(), t.getObject().toString());
        	System.out.println(" { " + t.getSubject() + " " +
        			t.getPredicate() + " " +
        			t.getObject() + " . }");
        	result.add(temp);
        }
		return null;

	}
}
