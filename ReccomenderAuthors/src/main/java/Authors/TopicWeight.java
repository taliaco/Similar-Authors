package Authors;


public class TopicWeight {
	private String _topicName;
	private double _topicWeight;
	
	public TopicWeight(String topic){
		_topicName = topic;
		_topicWeight = 1;
	}
	public TopicWeight(String topic,double weight){
		_topicName = topic;
		_topicWeight = weight;
	}
	public TopicWeight(TopicWeight topic){
		_topicName = topic.getTopicName();
		_topicWeight = topic.getTopicWeight();
	}
	
	public double getTopicWeight(){
		return _topicWeight;
	}
	public String getTopicName(){
		return _topicName;
	}
	public void addWeight(int num){
		_topicWeight = _topicWeight + num;
	}
	public void addWeight(){
		_topicWeight = _topicWeight + 1;
	}
	@Override
	public String toString(){
		return "Topic: " + _topicName +". Weight: " + _topicWeight;
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
			return _topicName.equals(((TopicWeight)obj).getTopicName());
		return super.equals(obj);
	}
	
	
}
