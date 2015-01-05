package util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

import bl.ReccomenderAuthorsBL;
public class Main {
	private static ReccomenderAuthorsBL bl = new ReccomenderAuthorsBL();
	
	
	public static void main(String[] args) {
		if(args.length != 1)
		{
			usage();
			return;
		}
		String name = args[0].trim();
//		ArrayList<Author> authors = new ArrayList<Author>();
//		ArrayList<String> authorsNames = bl.getBooksAuthors();
		Author a = new Author(name);
		System.out.println(a);
//		for(int i = 0; i < authorsNames.size(); i++){
//			authors.add(new Author(authorsNames.get(i)));
//		}
//		authors.sort(new Comparator<Author>(){
//			public int compare(Author a1, Author a2) {
//				return a1.getName().compareTo(a2.getName());
//			}
//		});
//		
//		System.out.println(authors.get(45));

	}

	private static void usage() {	  
		System.err.println("Usage: <author name>");      
		System.exit(1);	   	 
	}
	
	
}
