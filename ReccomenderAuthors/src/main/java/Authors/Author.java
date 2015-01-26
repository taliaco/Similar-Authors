package Authors;

import java.util.ArrayList;
import java.util.Comparator;

import util.Main;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

import bl.RecommenderAuthorsBL;

public class Author {
	private String _name;
	private ArrayList<Book> _books;
	private ArrayList<Topic> _topicsV;

	public Author(String name){
		_name = name;
		_books = createBookList();
		_topicsV =  createTopicList();
		_topicsV.sort(new Comparator<Topic>(){
			public int compare(Topic t1, Topic t2) {
				if (t2.getWeight()-t1.getWeight()>0)
					return 1;
				return -1;
			}
		});
	}

	/**---------------------------------------------------------------------------------------
	 * createBookList
	 * @return: 
	 * 			list of book that written by this author
	 * 			each book contain title and list of topics
	 * ---------------------------------------------------------------------------------------
	 */
	private ArrayList<Book> createBookList (){
		RecommenderAuthorsBL bl = new RecommenderAuthorsBL();
		ResultSet fullBooks = bl.getBooksByAuthor(_name);
		Book tmpBook = null;
		String title, topic;
		ArrayList<Book> list = new ArrayList<Book>();
		while (fullBooks.hasNext()) {
			QuerySolution rs = fullBooks.nextSolution();
			title = rs.get("title").toString();
			topic = rs.get("subject").toString();
			int id = list.size();
			if( id==0){
				list.add(new Book(title,topic));
			}
			else{
				tmpBook = list.get(id-1);
				if(tmpBook.getName().equals(title)){
					list.get(id-1).addTopic(topic);
				}
				else
				{
					list.add(new Book(title,topic));
				}
			}
		}
		return list;
	}
	/**---------------------------------------------------------------------------------------
	 * createTopicList
	 * @return
	 * 			sort list of all topics with weights.
	 * ---------------------------------------------------------------------------------------
	 */
	private ArrayList<Topic> createTopicList (){
		ArrayList<Topic> list = new ArrayList<Topic>();
		boolean exist = false;
		for(int i=0; i<_books.size(); i++){
			ArrayList<String> topics = _books.get(i).getTopics();
			for(int j=0; j<topics.size(); j++){
				exist = false;
				for( int k=0; k<list.size(); k++){
					if(topics.get(j).equals(list.get(k).getTopic())){
						if (Main.WITH_WEIGHT){							
							list.get(k).addWeight(_books.get(i).getWeight());
						}
						else{
							list.get(k).addWeight(1);							
						}
						exist=true;
					}
				}
				if(!exist){
					if (Main.WITH_WEIGHT){	
						list.add(new Topic(topics.get(j),_books.get(i).getWeight()));
					}
					else{
						list.add(new Topic(topics.get(j),1));
					}

				}			
			}
		}
		return list;
	}


	@Override
	public String toString(){
		String str = "Author: " + _name +"\n";		
		//str+=".\nBooks:\n";

		//for(int i=0; i<_books.size(); i++){
		//	str+= _books.get(i) +"\n";
		//}

		//for(int i=0; i<_topicsV.size(); i++){
		//	str+= "\t"+_topicsV.get(i) +"\n";
		//}
		return  str;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public ArrayList<Topic> getTopics() {
		return _topicsV;
	}
	/**---------------------------------------------------------------------------------------
	 * equals
	 * @return
	 * 			true if the title and the topic list is equals.
	 * ---------------------------------------------------------------------------------------
	 */
	@Override
	public boolean equals(Object obj) {
		if (_name.equals(((Author)obj).getName()) && _topicsV.equals(((Author)obj).getTopics())){
			return true;
		}
		return false;
	}

	public void setTopics(ArrayList<Topic> topics) {
		_topicsV = topics;
	}
}
