package util;

import java.util.ArrayList;
import java.util.Comparator;

import bl.RecommenderAuthorsBL;

public class Author {
	private String _name;
	private ArrayList<Topic> _topics;
	
	public Author(String name){
		_name = name;
		_topics =  createTopicList();
	}
	
	private ArrayList<Topic> createTopicList (){
		RecommenderAuthorsBL bl = new RecommenderAuthorsBL();
		
		ArrayList<String> fullTopics = bl.getAuthorsSubjects(_name);
		ArrayList<Topic> list = new ArrayList<Topic>();
		boolean exist = false;
		for(int i=0; i<fullTopics.size(); i++){
			exist = false;
			for( int j=0; j<list.size(); j++){
				if(fullTopics.get(i).equals(list.get(j).getTopic())){
					list.get(j).addWeight(1);
					exist=true;
				}
			}
			if(!exist){
				list.add(new Topic(fullTopics.get(i)));
				
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
		String str = "Author: " + _name;
//		str+=".\nTopics:\n";
//		for(int i=0; i<_topics.size(); i++){
//			str+= _topics.get(i).toString() +"\n";
//		}
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

	@Override
	public boolean equals(Object obj) {
		if (_name.equals(((Author)obj).getName()) && _topics.equals(((Author)obj).getTopics())){
			return true;
		}
		return false;
	}

	public void setTopics(ArrayList<Topic> topics) {
		_topics = topics;
	}
}
