package util;

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
		ArrayList<String> authorsNames = bl.getAllAuthors();
		ArrayList<recommendAuthor> topAuthors = new ArrayList<recommendAuthor>();
		ArrayList<Author> authors = new ArrayList<Author>();
		String name = args[0].trim();
		Author a = new Author(name);
		
		/** create author list*/
		for(int i = 0; i < authorsNames.size(); i++){
			authors.add(new Author(authorsNames.get(i)));
		}
		
//		System.out.println(authors);
		/** create recommender top list*/
		topAuthors = makeRecommendation.topMatches(authors, a, 5);
		/** print the top list*/
		System.out.println(a);
		System.out.println();
		for(int i=0; i<topAuthors.size(); i++){
			System.out.println(topAuthors.get(i));
		}

	}

	private static void usage() {	  
		System.err.println("Usage: <author name>");      
		System.exit(1);	   	 
	}
	
	
}
