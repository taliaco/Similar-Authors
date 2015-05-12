package recommendation;

import java.util.ArrayList;

import util.Main;
import Authors.Author;
import Authors.Book;
import Authors.TopicWeight;

public class recommendAuthor implements Comparable<recommendAuthor> {
	private Author _author;
	private double _TF;
	private ArrayList<TopicWeight> _authorTopicsVec;
	private double _score;

	public recommendAuthor (Author a, double s){
		_author = a;
		_TF = 0;
		_score = s;
	}
	public recommendAuthor (Author a, ArrayList<Author> authorsList){
		_author = a;
		_authorTopicsVec = createTopicVecWithCounter();
		_TF = TF(_authorTopicsVec);
		//System.out.println(_TF);
		_authorTopicsVec = TFIDF(_authorTopicsVec, authorsList);

		//_score = makeRecommendation.similarTopics(A1, A2);
	}

	public Author getAuthor() {
		return _author;
	}

	public double getScore() {
		return _score;
	}
	public  ArrayList<TopicWeight> getAuthorTopicsVec() {
		ArrayList<TopicWeight> toReturn = new ArrayList<TopicWeight>();
		for (int i=0; i< _authorTopicsVec.size(); i++){
			toReturn.add(new TopicWeight(_authorTopicsVec.get(i)));
		}
		return toReturn;
	}

	//1 - count topics
	public ArrayList<TopicWeight> createTopicVecWithCounter(){
		ArrayList<TopicWeight> list = new ArrayList<TopicWeight>();
		boolean exist = false;
		for(int i=0; i<_author.getBookList().size(); i++){
			ArrayList<TopicWeight> topics = _author.getBookList().get(i).getBookTopics();
			for(int j=0; j<topics.size(); j++){
				exist = false;
				for(int k=0; k<list.size(); k++){
					if(topics.get(j).equals(list.get(k))){
						list.get(k).addWeight();
						exist=true;
					}
				}
				if(!exist){
					list.add(new TopicWeight(topics.get(j).getTopicName()));
				}			
			}
		}
		return list;
	}
	//2 - TF
	public double TF(ArrayList<TopicWeight> topicList){
		int max = 0;
		for (int i=1; i< topicList.size(); i++){
			if (topicList.get(i).getTopicWeight() > topicList.get(max).getTopicWeight()){
				max = i;
			}
		}
		
		if (topicList.size() == 0 || topicList.get(max).getTopicWeight() == 0) {
			return 0;
		}
		return topicList.size()/topicList.get(max).getTopicWeight();
	}
	//3 - IDF
	public ArrayList<TopicWeight> TFIDF (ArrayList<TopicWeight> topicList, ArrayList<Author> authorsList){
		ArrayList<TopicWeight> list = new ArrayList<TopicWeight>();
//		System.out.println("authorsList.size() : " +authorsList.size());
//		System.out.println("topicList.size() : " + topicList.size());
		for (int i=0; i< topicList.size(); i++){
			TopicWeight tmpTopic = new TopicWeight(topicList.get(i));
			String topicName = topicList.get(i).getTopicName();
			int autohrCount = 0;
			double IDF = 0;
			for (int j=0; j< authorsList.size();j++){
				//System.out.println(authorsList.get(j).getTopics().contains(topicName));
				if (authorsList.get(j) != _author && authorsList.get(j).getTopics().contains(tmpTopic)){
					//System.out.println(authorsList.get(j).getName());
					autohrCount++;
				}
			}
			if(autohrCount > 0)
			{ 
				IDF = java.lang.Math.log10((double)authorsList.size() / (double)autohrCount);			
				//System.out.println(tmpTopic + " - " + autohrCount + " - " + authorsList.size() + " - " + IDF);
			}

			list.add(new TopicWeight(topicName, _TF*IDF));
		}
		return list;
	}
	//4 - cosine
	public double cosineSimilarity(recommendAuthor authorB) {
	    double dotProduct = 0.0;
	    double normA = 0.0;
	    double normB = 0.0;
	    ArrayList <TopicWeight> topicListA;
	    ArrayList <TopicWeight> topicListB;
	    if (_authorTopicsVec.size() > authorB.getAuthorTopicsVec().size()){
	    	topicListA = _authorTopicsVec;
		    topicListB = authorB.getAuthorTopicsVec();
	    }
	    else{
	    	topicListA = authorB.getAuthorTopicsVec();
		    topicListB = _authorTopicsVec;
	    }
	    for (int i = 0; i < topicListA.size(); i++) {
	    	normA += Math.pow(topicListA.get(i).getTopicWeight() , 2);
	    }
	    for (int i = 0; i < topicListB.size(); i++) {
	    	int indexOfTopic = topicListA.indexOf(topicListB.get(i));
//	    	System.out.println("indexOfTopic " +topicListB.get(i)+": "+ indexOfTopic);
	    	if (indexOfTopic >= 0){
	    		dotProduct += topicListB.get(i).getTopicWeight() * topicListA.get(indexOfTopic).getTopicWeight();
//	    		normA += Math.pow(topicListA.get(indexOfTopic).getTopicWeight(), 2);
		        normB += Math.pow(topicListB.get(i).getTopicWeight() , 2);
	    	}
	    } 
	    if ((Math.sqrt(normA) * Math.sqrt(normB)) ==0){
	    	return 0;
	    }
	    _score = dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
	    return  _score;
	}
	/**---------------------------------------------------------------------------------------
	 * equals
	 * @return
	 * 			true if the title and the topic list is equals.
	 * ---------------------------------------------------------------------------------------
	 */
	@Override
	public boolean equals(Object obj) {
		if (_author.equals(((recommendAuthor)obj).getAuthor())){
			return true;
		}
		return false;
	}
	
	public String toString(){
		String toReturn = "";
		toReturn = _author + "score: " + _score+"\n"+ _authorTopicsVec+"\n";
		return toReturn;
	}
	public int compareTo(recommendAuthor obj) {
		if (_score > obj.getScore()){
			return -1;
		}else if(_score == obj.getScore()){
			return 0;
		}else{
			return 1;
		}
	}
	
}
