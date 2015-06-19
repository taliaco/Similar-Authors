package Authors;

import java.util.ArrayList;


import Authors.Author;
import Authors.TopicWeight;
import net.ricecode.similarity.JaroWinklerStrategy;
import net.ricecode.similarity.SimilarityStrategy;
import net.ricecode.similarity.StringSimilarityService;
import net.ricecode.similarity.StringSimilarityServiceImpl;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
 
@Entity
public class recommendAuthor implements Comparable<recommendAuthor>{// Serializable{
	private static final long serialVersionUID = 1L;
	// Persistent Fields:
    @Id @GeneratedValue
    Long id;
	private Author _author;
	private double _TF;
	private ArrayList<TopicWeight> _authorTopicsVec;
	private double _score;

	public recommendAuthor (Author a, double s){
		_author = a;
		_TF = 0;
		_authorTopicsVec = new ArrayList<TopicWeight>();
		_score = s;
	}
	public recommendAuthor (Author a, ArrayList<Author> authorsList){
		_author = a;
		_authorTopicsVec = createTopicVecWithCounter();
		_TF = TF(_authorTopicsVec);
		_authorTopicsVec = TFIDF(_authorTopicsVec, authorsList);
		_score = 0;
	}
	public recommendAuthor (recommendAuthor a){
		_author = new Author(a.getAuthor());
		_TF = a.getTF();
		_authorTopicsVec = new ArrayList<TopicWeight>(a.getAuthorTopicsVec());
		_score = a.getScore();
	}
	
	public Author getAuthor() {
		return _author;
	}

	public double getScore() {
		return _score;
	}
	public double getTF() {
		return _TF;
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
						break;
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
		for (int i=0; i< topicList.size(); i++){
			TopicWeight tmpTopic = new TopicWeight(topicList.get(i));
			String topicName = topicList.get(i).getTopicName();
			int autohrCount = 0;
			double IDF = 0;
			for (int j=0; j< authorsList.size();j++){
				if (authorsList.get(j) != _author && authorsList.get(j).getTopics().contains(tmpTopic)){
					autohrCount++;
				}
			}
			if(autohrCount > 0)
			{ 
				IDF = java.lang.Math.log10((double)authorsList.size() / (double)autohrCount);			
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
	    ArrayList <TopicWeight> topicListA  = _authorTopicsVec;
	    ArrayList <TopicWeight> topicListB = authorB.getAuthorTopicsVec();
	    
	    ArrayList <TopicWeight> all =  new ArrayList <TopicWeight>();
	    all.addAll(topicListA);
	    for (int i=0; i<topicListB.size(); i++ ){
	    	if (!all.contains(topicListB.get(i))){
	    		all.add(topicListB.get(i));
	    	}
	    }

	    for (int i = 0; i < topicListA.size(); i++) {
	    	normA += (double)Math.pow(topicListA.get(i).getTopicWeight() , 2);
	    }
	    for (int i = 0; i < topicListB.size(); i++) {
	    	normB += (double)Math.pow(topicListB.get(i).getTopicWeight() , 2);
	    }
	    for (int i = 0; i < all.size(); i++) {
	    	
	    	int indexOfTopicA = topicListA.indexOf(all.get(i));
	    	int indexOfTopicB = topicListB.indexOf(all.get(i));
	    	if (indexOfTopicA > -1 && indexOfTopicB > -1){
	    		dotProduct += topicListB.get(indexOfTopicB).getTopicWeight() * topicListA.get(indexOfTopicA).getTopicWeight();
	    	}
	    } 
	    if (((double)Math.sqrt(normA) * (double)Math.sqrt(normB)) ==0){
	    	return 0;
	    }
	    _score = (double)dotProduct / ((double)Math.sqrt(normA) * (double)Math.sqrt(normB));
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
		SimilarityStrategy strategy = new JaroWinklerStrategy();
		StringSimilarityService service = new StringSimilarityServiceImpl(strategy);
		double score = service.score(_author.getName(), ((recommendAuthor)obj).getAuthor().getName()); // Score is 0.90
		System.out.println(_author.getName()+ " - "+((recommendAuthor)obj).getAuthor().getName()+": "+ score);
//		if(score>0.8){
//			
//			return true;
//		}
		if (_author.equals(((recommendAuthor)obj).getAuthor())){
			return true;
		}
		return false;
	}
	
	public String toString(){
		String toReturn = "";
//		toReturn = _author + " score: " + _score+".";
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
