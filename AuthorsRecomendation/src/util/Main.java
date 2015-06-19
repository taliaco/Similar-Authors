package util;

import java.io.File;
import java.util.ArrayList;

import org.apache.catalina.startup.Tomcat;

//import dal.AuthorDal;
import Authors.Author;
import Authors.recommendAuthor;
import bl.RecommenderAuthorsBL;
public class Main {
	private static RecommenderAuthorsBL bl = new RecommenderAuthorsBL();
	public static boolean WITH_WEIGHT = true;
	public static ArrayList<Authors.recommendAuthor> topAuthors = new ArrayList<Authors.recommendAuthor>();
	public static ArrayList<Author> authors = new ArrayList<Author>();
	public static int COUNTER = 0;
    public static void main(String[] args) throws Exception {
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

        tomcat.start();
        long start = System.currentTimeMillis();

		authors = bl.getAuthorsTitleSubject();
		System.err.println("Number Of Records (Topics) proccessed: "+ COUNTER);
		System.err.println("Total Authors: "+ authors.size());

		/** create weight vector for each author */
//		for(int i = 0; i < authors.size(); i++){
		for(int i = 0; i < 10000; i++){
			System.out.println(i+ ". " + authors.get(i));
//			authorDal.persist(new recommendAuthor(authors.get(i), authors));
			topAuthors.add(new recommendAuthor(authors.get(i), authors));
		}
//		topAuthors=authorDal.getAllAuthor();
		System.err.println("Number Of Records (Topics) proccessed: "+ COUNTER);
		System.err.println("Total Authors: "+ authors.size());
		System.err.println("Total time: " + (System.currentTimeMillis() - start)/1000 + " seconds");
        tomcat.getServer().await();
    }
}