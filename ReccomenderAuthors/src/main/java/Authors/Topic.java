package Authors;


public class Topic {
	private String _topic;
	private int _weight;
	
	public Topic(String topic){
		_topic = topic;
		_weight = 1;
	}
	public Topic(String topic,int weight){
		_topic = topic;
		_weight = weight;
	}
	public Topic(Topic topic){
		_topic = topic.getTopic();
		_weight = topic.getWeight();
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
	/**---------------------------------------------------------------------------------------
	 * equals
	 * @return
	 * 			true if the name of the topics is equals, ignore weight.
	 * ---------------------------------------------------------------------------------------
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj.getClass().equals(getClass()))
			return _topic.equals(((Topic)obj).getTopic());
		return super.equals(obj);
	}
	
	
}
