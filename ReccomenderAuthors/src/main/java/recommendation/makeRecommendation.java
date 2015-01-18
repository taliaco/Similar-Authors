package recommendation;

import java.util.ArrayList;
import java.util.Comparator;

import util.Author;
import util.Topic;
import util.recommendAuthor;

public class makeRecommendation {
	/** calculate recommender score between two authors*/
	public static double similarTopics (Author A1, Author A2){
		ArrayList<Double> si = new ArrayList<Double>();
		Topic t1, t2;
		double sum = 0;
		for (int i=0; i< A1.getTopics().size(); i++)
		{
			t1 = A1.getTopics().get(i);
			for(int j=0; j<A2.getTopics().size(); j++){
				t2 = A2.getTopics().get(j);
				if(t2.equals(t1)){
					double num = t1.getWeight()-t2.getWeight();
					si.add(Math.pow(num,2));
				}
				
			}
		}
		if (si.size() == 0){
			return 0;
		}
		
		for(int i=0; i<si.size(); i++){
			sum+=(double)si.get(i);
		}
//		System.out.println(A1+" " +(double)1/(1+sum));
		return (double)1/(1+sum);
	}
	/** calculate score for each author and return top n*/
	public static ArrayList<recommendAuthor> topMatches (ArrayList<Author> authorsList, Author a, int n){
		Author b;
		ArrayList<recommendAuthor> sortList = new ArrayList<recommendAuthor>();
		ArrayList<recommendAuthor> topList = new ArrayList<recommendAuthor>();
		double score=0;
		/**calculate score for each author*/
		for(int i=0; i<authorsList.size(); i++){
			b = authorsList.get(i);
			if (!b.equals(a)){
				score = (double)makeRecommendation.similarTopics(b, a);
				recommendAuthor tmpRecAuthor = new recommendAuthor(b, (double)score);
				sortList.add(tmpRecAuthor);
//				System.out.println(tmpRecAuthor);
			}
		}
		/**sort the recommender authors from the highest to lowest*/
		sortList.sort(new Comparator<recommendAuthor>(){
			public int compare(recommendAuthor a1, recommendAuthor a2) {
					if (a2.getScore()-a1.getScore()>0)
						return 1;
				return -1;
			}
		});
		/**return the 'n' top authors*/
		for(int i=0; i<n; i++){
			topList.add(sortList.get(i));
		}
		return topList;
	}
}
