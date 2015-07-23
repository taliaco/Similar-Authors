package util;

import java.io.File;
import java.util.ArrayList;

import org.apache.catalina.startup.Tomcat;








import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;







import dal.JenaVirtuosoDriver;
//import dal.AuthorDal;
import Authors.Author;
import Authors.TopicWeight;
import Authors.recommendAuthor;
import bl.RecommenderAuthorsBL;
public class Main {
	public static boolean processNewRecords = false;
	
	public static ArrayList<recommendAuthor> a;
	public static JenaVirtuosoDriver jena;
	private static RecommenderAuthorsBL bl = new RecommenderAuthorsBL();
	public static long numOfAuthors;
//	public static ResultSet allAuthorsSet;
	public static boolean WITH_WEIGHT = true;
	public static ArrayList<Authors.recommendAuthor> topAuthors = new ArrayList<Authors.recommendAuthor>();
	public static ArrayList<Author> authors = new ArrayList<Author>();
	public static int COUNTER = 0;
	public static void main(String[] args) throws Exception {
//		bl.deleteALLTFIDF();
		numOfAuthors = bl.getNumOfAuthors();
		
		
		
//		processAuthor("http://hujilinkeddata/Authority/6b6ca876ac858164c9f5fa5c6674f1bf");
		//    	AuthorDal authorDal = new AuthorDal();
		String webappDirLocation = "war/";
		Tomcat tomcat = new Tomcat();
		//The port that we should run on can be set into an environment variable
		//Look for that variable and default to 8080 if it isn't there.
		String webPort = System.getenv("PORT");
		if(webPort == null || webPort.isEmpty()) {
			webPort = "8080";
		}
		tomcat.setPort(Integer.valueOf(webPort));
		tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());
		System.out.println("configuring app with basedir: " + new File("./" + webappDirLocation).getAbsolutePath());

		
		if (processNewRecords){
			ResultSet allAuthorsSet = bl.getAllAuthorsResultSet();
			long start = System.currentTimeMillis();
			jena = getJena();
			int i=0;
			while (allAuthorsSet.hasNext() ) {
				if (i % 1000 == 0){
					Thread.sleep(3000);
//					System.out.println("sleep");
				}
				QuerySolution rs = allAuthorsSet.nextSolution();
				//			System.out.println(rs.get("dcCreator").toString());
				boolean isProcess = processAuthor(rs.get("dcCreator").toString());
				i++;
			}

			System.err.println("Number Of Records (Topics) proccessed: "+ COUNTER);
			System.err.println("Total Authors: "+ numOfAuthors);
			System.err.println("Total time: " + (System.currentTimeMillis() - start)/1000 + " seconds");

		}
		ResultSet allAuthorsSet = bl.getAllAuthorsResultSet();
		//loop all authors get top 5 from cusine function
		long startBuildTopicVec = System.currentTimeMillis();
		a = new ArrayList<recommendAuthor>();
		while (allAuthorsSet.hasNext()) {
			if (a.size() % 1000 == 0){
				Thread.sleep(2000);
//				System.out.println("sleep");
			}
			QuerySolution rs = allAuthorsSet.nextSolution();
			String dcCreator =rs.get("dcCreator").toString();
			ArrayList<TopicWeight> topicVec = bl.getTFIDFTopic(dcCreator);
			a.add(new recommendAuthor( rs.get("name").toString(),  dcCreator,  0,  0,  topicVec));
//			System.out.println(rs.get("name").toString());
		}
		System.err.println("Total time: " + (System.currentTimeMillis() - startBuildTopicVec)/1000 + " seconds");
		
		tomcat.start();
		tomcat.getServer().await();
	}
	public static boolean processAuthor(String dcCreator){

		
//		long countOfTopics = bl.getNumOfAuthorTopics(dcCreator);
		long maxTopic = bl.getMaxTopicToAuthor(dcCreator);
		double TF = 0;
		if (maxTopic > 0)
		{
			
		}
		double IDF = 0;
		ArrayList<TopicWeight> topicVec = bl.getTopicWeightVecForAuthor(dcCreator);
//		System.out.println(topicVec);
		for(int i = 0; i < topicVec.size(); i++){
			TF = (double)topicVec.get(i).getTopicWeight()/(double)maxTopic;
			long NumOfAuthorsWithTopic = bl.getNumOfAuthorsWithTopic(topicVec.get(i).getTopicName());
			//			System.err.println(NumOfAuthorsWithTopic);
			if (NumOfAuthorsWithTopic>0){
				IDF = (double)java.lang.Math.log10((double)numOfAuthors/(double)NumOfAuthorsWithTopic);
			}
			double tfidf = (double)TF*(double)IDF;
//			System.out.println(dcCreator + " -- " + topicVec.get(i).getTopicName()+" -- " + tfidf);
			jena.InsertTriple(new RDFTripleModel(dcCreator,"TF-IDF-TopicWithWeight",topicVec.get(i).getTopicName()+" -- " + tfidf ));
			COUNTER++;
		}
		return true;
	}
	public static JenaVirtuosoDriver getJena(){
		String username = "dba"; 
		String password = "dba";
		String hostname ="localhost";
		String port = "1111";
		String graphName = "http://wwu_bibs/";
//		String graphName = "http://hujilinkeddata/";
//		String graphName = "test";
		Boolean clearGraphOnStart = false;
		JenaVirtuosoDriver jena = new JenaVirtuosoDriver(username, password, hostname, port, graphName, clearGraphOnStart);
		return jena;
	}
}














