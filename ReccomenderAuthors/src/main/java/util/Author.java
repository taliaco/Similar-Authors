package util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import bl.ReccomenderAuthorsBL;

public class Author {
	private String _name;
	private ArrayList<Topic> _topics;
	private ReccomenderAuthorsBL bl = new ReccomenderAuthorsBL();
	public Author(String name){
		ArrayList<String> tmpTopic = bl.getAuthorsSubjects(name);
		_name = name;
		_topics =  createTopicList(tmpTopic);
	}
	
	private ArrayList<Topic> createTopicList (ArrayList<String> tmpTopic){
		ArrayList<Topic> list = new ArrayList<Topic>();
		boolean exist = false;
		for(int i=0; i<tmpTopic.size(); i++){
			exist = false;
			for( int j=0; j<list.size(); j++){
				if(tmpTopic.get(i).equals(list.get(j).getTopic())){
					list.get(j).addWeight(1);
					exist=true;
				}
			}
			if(!exist){
				list.add(new Topic(tmpTopic.get(i)));
				
			}
		}
		list.sort( new Comparator<Topic>(){
			public int compare(Topic t1, Topic t2) {
				return t2.getWeight()-t1.getWeight();
			}
		});
		return list;
	}
	@Override
	public String toString(){
		String str = "Author: " + _name +".\nTopics:\n";
		for(int i=0; i<_topics.size(); i++){
			str+= _topics.get(i).toString() +"\n";
		}
		return  str;
	}
	
	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public ArrayList<Topic> getTopics() {
		return _topics;
	}

	public void setTopics(ArrayList<Topic> topics) {
		_topics = topics;
	}
}
