package recommendation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import launch.Main;
import Authors.Author;
import Authors.TopicWeight;
@WebServlet("/makeRecommendation")
public class makeRecommendation extends HttpServlet {
	private static final long serialVersionUID = 1L;
//	public static boolean testSearch(String name){
//		System.out.println(name);
//
//		/** find top match*/
//		//		String name = args[0].trim();
//		Author a = new Author(name);
//		recommendAuthor a1 = new recommendAuthor(a, Main.authors);
//		int index = Main.topAuthors.indexOf(a1);
//		if (index < 0)
//			return false;
//		a1 = Main.topAuthors.get(index);
//		System.out.println(a1);
//		for(int i = 0; i < Main.topAuthors.size(); i++){
//			Main.topAuthors.get(i).cosineSimilarity(a1);
//		}
//		Main.topAuthors.sort(null);
//		for (int i=0; i< 5; i++	){
//			System.out.println(i + ". " + Main.topAuthors.get(i));
//		}
//		return true;
//	}
	public makeRecommendation() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("testGET");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		Object nameObj = request.getParameter("name");
		String name = nameObj.toString();
		System.out.println(name);
		String str="";
		if(name !=null)
		{
			Author a = new Author(name);
			recommendAuthor a1 = new recommendAuthor(a, Main.authors);
			int index = Main.topAuthors.indexOf(a1);
			if (index < 0)
			{
				response.getWriter().write("0");
			}
			a1 = Main.topAuthors.get(index);
			System.out.println(a1);
			for(int i = 0; i < Main.topAuthors.size(); i++){
				Main.topAuthors.get(i).cosineSimilarity(a1);
			}
			Main.topAuthors.sort(null);
			for (int i=0; i< 5; i++){
				str += Main.topAuthors.get(i)+",";
				System.out.println(i + ". " + Main.topAuthors.get(i));
			}

			response.getWriter().write(str);

		}
		else
			response.getWriter().write("0");
	}
//	/** calculate recommender score between two authors*/
//	public static double similarTopics (Author A1, Author A2){
//		ArrayList<Double> si = new ArrayList<Double>();
//		TopicWeight t1, t2;
//		double sum = 0;
//		boolean exist = false;
//		int tmp=0;
//		/**count number of equal items*/
//		for (int i=0; i< A1.getTopics().size(); i++)
//		{
//			t1 = A1.getTopics().get(i);
//			for(int j=0; j<A2.getTopics().size(); j++){
//				t2 = A2.getTopics().get(j);
//				if(t2.equals(t1)){
//					tmp++;
//				}				
//			}		
//		}
//		/**if no equals item then return 0*/
//		if (tmp==0){
//			return 0;
//		}
//		/**Add up the squares of all the differences*/
//		for (int i=0; i< A1.getTopics().size(); i++)
//		{
//			exist = false;
//			t1 = A1.getTopics().get(i);
//			for(int j=0; j<A2.getTopics().size(); j++){
//				t2 = A2.getTopics().get(j);
//				if(t2.equals(t1)){
//					double num = t1.getTopicWeight()-t2.getTopicWeight();
//					si.add(Math.pow(num,2));
//					exist = true;
//				}				
//			}
//			if(!exist){
//				double num = t1.getTopicWeight();
//				si.add(Math.pow(num,2));
//			}
//		}
//
//		for(int i=0; i<si.size(); i++){
//			sum+=(double)si.get(i);
//		}
//		return (double)1/(1+sum);
//	}
//	/** calculate score for each author and return top n*/
//	public static ArrayList<recommendAuthor> topMatches (ArrayList<Author> authorsList, Author a, int n){
//		Author b;
//		ArrayList<recommendAuthor> sortList = new ArrayList<recommendAuthor>();
//		ArrayList<recommendAuthor> topList = new ArrayList<recommendAuthor>();
//		double score=0;
//		/**calculate score for each author*/
//		for(int i=0; i<authorsList.size(); i++){
//			b = authorsList.get(i);
//			if (!b.equals(a)){
//				if (b.getTopics().size() > a.getTopics().size()){
//					score = (double)makeRecommendation.similarTopics(b, a);
//				}
//				else
//				{
//					score = (double)makeRecommendation.similarTopics(a, b);
//				}
//				recommendAuthor tmpRecAuthor = new recommendAuthor(b, (double)score);
//				sortList.add(tmpRecAuthor);
//				//				System.out.println(tmpRecAuthor);
//			}
//		}
//		/**sort the recommender authors from the highest to lowest*/
//		sortList.sort(new Comparator<recommendAuthor>(){
//			public int compare(recommendAuthor a1, recommendAuthor a2) {
//				if (a2.getScore()-a1.getScore()>=0)
//					return 1;
//				return -1;
//			}
//		});
//		/**return the 'n' top authors*/
//		for(int i=0; i<n; i++){
//			topList.add(sortList.get(i));
//		}
//		return topList;
//	}

}
