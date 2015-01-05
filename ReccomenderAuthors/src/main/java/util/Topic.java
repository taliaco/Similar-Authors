package util;


public class Topic {
	private String _topic;
	private int _weight;
	//private Map<String , Integer> topicsWeight;
	
	public Topic(String topic){
		_topic = topic;
		_weight = 1;
	}
	
	public int getWeight(){
		return _weight;
	}
	public String getTopic(){
		return _topic;
	}
	public void addWeight(int num){
		_weight = _weight + num;
	}
	@Override
	public String toString(){
		return "Topic: " + _topic +". Weight: " + _weight;
	}
	
	
}
