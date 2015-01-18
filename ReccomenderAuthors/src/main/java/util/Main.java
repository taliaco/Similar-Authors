package util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

import recommendation.makeRecommendation;
import bl.RecommenderAuthorsBL;
public class Main {
	private static RecommenderAuthorsBL bl = new RecommenderAuthorsBL();
	
	
	public static void main(String[] args) {
		if(args.length != 1)
		{
			usage();
			return;
		}
		ArrayList<String> authorsNames = bl.getAuthors();
		ArrayList<recommendAuthor> topAuthors = new ArrayList<recommendAuthor>();
		ArrayList<Author> authors = new ArrayList<Author>();
		String name = args[0].trim();
		Author a = new Author(name);
		
		/** create author list*/
		for(int i = 0; i < authorsNames.size(); i++){
			authors.add(new Author(authorsNames.get(i)));
		}
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
