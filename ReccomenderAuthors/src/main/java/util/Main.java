package util;

import java.sql.Time;
import java.util.ArrayList;

import recommendation.makeRecommendation;
import recommendation.recommendAuthor;
import Authors.Author;
import bl.RecommenderAuthorsBL;
public class Main {
	private static RecommenderAuthorsBL bl = new RecommenderAuthorsBL();
	public static boolean WITH_WEIGHT = true;
	
	public static void main(String[] args) {
		if(args.length != 1)
		{
			usage();
			return;
		}
		long start = System.currentTimeMillis();

		//System.out.println("test");
		ArrayList<String> authorsNames = bl.getAllAuthors();
		authorsNames.sort(null);
		ArrayList<recommendAuthor> topAuthors = new ArrayList<recommendAuthor>();
		ArrayList<Author> authors = new ArrayList<Author>();
		String name = args[0].trim();
		Author a = new Author(name);
		
		/** create author list*/
		for(int i = 0; i < authorsNames.size(); i++){
			authors.add(new Author(authorsNames.get(i)));
		}
		/** create weight vector for each author */
		for(int i = 0; i < authors.size(); i++){
			topAuthors.add(new recommendAuthor(authors.get(i), authors));
		}
		/** find top match*/
		recommendAuthor a1 = new recommendAuthor(a, authors);
		a1 = topAuthors.get(topAuthors.indexOf(a1));
		System.out.println(a1);
		for(int i = 0; i < topAuthors.size(); i++){
			topAuthors.get(i).cosineSimilarity(a1);
		}
		topAuthors.sort(null);
		for (int i=0; i< 5; i++){
			System.out.println(i + ". " + topAuthors.get(i));
		}
//		System.out.println(topAuthors.get(57));
//		System.err.println("Total time: " + (System.currentTimeMillis() - start) + " miliseconds");
		System.err.println("Total time: " + (System.currentTimeMillis() - start)/1000 + " seconds");

	}

	private static void usage() {	  
		System.err.println("Usage: <author name>");      
		System.exit(1);	   	 
	}
	
	
}
