package Authors;

import java.util.ArrayList;

public class Book {
	private String _bookName;
	private ArrayList<TopicWeight> _bookTopicList;
	
	public Book(){
		_bookName = "";
		_bookTopicList = new ArrayList<TopicWeight>();
	}
	public Book( Book book){
		_bookName = book.getBookName();
		_bookTopicList = book.getBookTopics();
	}
	
	public Book(String name){
		_bookName = name;
		_bookTopicList = new ArrayList<TopicWeight>();
	}
	
	public Book(String name, ArrayList<TopicWeight> topics){
		_bookName = name;
		_bookTopicList = new ArrayList<TopicWeight>();
		for (int i=0; i< topics.size(); i++){
			_bookTopicList.add(new TopicWeight(topics.get(i)));
		}
	}
	
	public void addBookTopic(String topicName){
		if (!isTopicExist(topicName))
		{
			_bookTopicList.add(new TopicWeight(topicName));
		}
	}
	
	public void addBookTopic(TopicWeight topic){
		if (!isTopicExist(topic.getTopicName()))
		{
			_bookTopicList.add(new TopicWeight(topic));
		}
	}
	public void setBookTopic(ArrayList<TopicWeight> topics){
		_bookTopicList = new ArrayList<TopicWeight>();
		for (int i=0; i< topics.size(); i++){
			_bookTopicList.add(new TopicWeight(topics.get(i)));
		}
	}
	
	public String getBookName() {
		return _bookName;
	}
	
	public ArrayList<TopicWeight> getBookTopics() {
		ArrayList<TopicWeight> toReturn = new ArrayList<TopicWeight>();
		for (int i=0; i< _bookTopicList.size(); i++){
			toReturn.add(new TopicWeight(_bookTopicList.get(i)));
		}
		return toReturn;
	}
	
	/**---------------------------------------------------------------------------------------
	 * isTopicExist
	 * @return
	 * 			return if this is a new topic
	 * ---------------------------------------------------------------------------------------
	 */
	public boolean isTopicExist(String topic){
		boolean exist = false;
		for( int j=0; j<_bookTopicList.size(); j++){
			if(topic.equals(_bookTopicList.get(j))){
				exist=true;
			}
		}
		return exist;
	}
	
	/**---------------------------------------------------------------------------------------
	 * equals
	 * @return
	 * 			true if the name of the book is equals, ignore topics.
	 * ---------------------------------------------------------------------------------------
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj.getClass().equals(getClass()))
			return _bookName.equals(((Book)obj).getBookName());
		return super.equals(obj);
	}
	@Override
	public String toString(){
		String str = "\t" + _bookName+"\n";
//		str+=".\nTopics:\n";
//		for(int i=0; i<_topicsName.size(); i++){
//			str+= "\t\t\t" + _topicsName.get(i).toString() +"\n";
//		}
		return  str;
	}
}
