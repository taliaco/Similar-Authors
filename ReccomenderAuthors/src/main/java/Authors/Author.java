package Authors;

import java.util.ArrayList;
import java.util.Comparator;

import util.Main;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

import bl.RecommenderAuthorsBL;

public class Author {
	private String _name;
	private ArrayList<Book> _authorBooks;
	private ArrayList<TopicWeight> _authorTopicsVec;

	public Author(String name){
		_name = name;
		_authorBooks = createBookList();
		//		_authorTopicsVec = createTopicList();
		//		_authorTopicsVec.sort(new Comparator<TopicWeight>(){
		//			public int compare(TopicWeight t1, TopicWeight t2) {
		//				if (t2.getTopicWeight()-t1.getTopicWeight()>0)
		//					return 1;
		//				return -1;
		//			}
		//		});
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
		ResultSet allBooksByAuthorResult = bl.getBooksByAuthor(_name);
		Book tmpBook;
		String title, topic;
		ArrayList<Book> list = new ArrayList<Book>();
		while (allBooksByAuthorResult.hasNext()) {
			QuerySolution rs = allBooksByAuthorResult.nextSolution();
			title = rs.get("title").toString();
			topic = rs.get("subject").toString();
			tmpBook = new Book(title);
			if(list.contains(tmpBook)){
				list.get(list.indexOf(tmpBook)).addBookTopic(topic);
			}
			else{
				tmpBook.addBookTopic(topic);
				list.add(tmpBook);
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
	private ArrayList<TopicWeight> createTopicList (){
		ArrayList<TopicWeight> list = new ArrayList<TopicWeight>();
		boolean exist = false;
		for(int i=0; i<_authorBooks.size(); i++){
			ArrayList<TopicWeight> topics = _authorBooks.get(i).getBookTopics();
			for(int j=0; j<topics.size(); j++){
				exist = false;
				for( int k=0; k<list.size(); k++){
					if(topics.get(j).equals(list.get(k).getTopicName())){
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

	public ArrayList<TopicWeight> getTopics() {

		return createTopicList();
	}
	/**---------------------------------------------------------------------------------------
	 * equals
	 * @return
	 * 			true if the title and the topic list is equals.
	 * ---------------------------------------------------------------------------------------
	 */
	@Override
	public boolean equals(Object obj) {
		if (_name.equals(((Author)obj).getName())){
			return true;
		}
		if (_name.equals(((Author)obj).getName()) && _authorTopicsVec.equals(((Author)obj).getTopics())){
			return true;
		}
		return false;
	}

	public void setTopics(ArrayList<TopicWeight> topics) {
		_authorTopicsVec = topics;
	}
	public ArrayList<Book> getBookList(){
		ArrayList<Book> toReturn = new ArrayList<Book>();
		for (int i=0; i< _authorBooks.size(); i++){
			toReturn.add(new Book(_authorBooks.get(i)));
		}
		return toReturn;
	}
}
