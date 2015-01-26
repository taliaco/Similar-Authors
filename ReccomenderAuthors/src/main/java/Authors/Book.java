package Authors;

import java.util.ArrayList;

public class Book {
	private String _name;
	private ArrayList<String> _topicList;
	
	public Book(String name){
		_name = name;
		_topicList = new ArrayList<String>();
	}
	public Book(String name,String topic){
		_name = name;
		_topicList = new ArrayList<String>();
		_topicList.add(topic);
	}
	public Book(String name, ArrayList<String> topics){
		_name = name;
		_topicList = topics;
	}
	
	public String getName() {
		return _name;
	}
	public int getWeight() {
		return _topicList.size();
	}
	public ArrayList<String> getTopics() {
		return _topicList;
	}
	
	/**---------------------------------------------------------------------------------------
	 * addTopic
	 * @return
	 * 			if this is a new topic then add to the topic list
	 * ---------------------------------------------------------------------------------------
	 */
	public void addTopic(String topic){
		boolean exist = false;
		for( int j=0; j<_topicList.size(); j++){
			if(topic.equals(_topicList.get(j))){
				exist=true;
			}
		}
		if(!exist){
			_topicList.add(topic);
		}
	}
	
	
	@Override
	public String toString(){
		String str = "\t" + _name+"\n";
//		str+=".\nTopics:\n";
//		for(int i=0; i<_topicsName.size(); i++){
//			str+= "\t\t\t" + _topicsName.get(i).toString() +"\n";
//		}
		return  str;
	}
}
