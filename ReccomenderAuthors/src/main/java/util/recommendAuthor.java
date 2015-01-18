package util;

public class recommendAuthor {
	private Author _author;
	private double _score;
	
	public recommendAuthor (Author a, double s){
		_author = a;
		_score = s;
	}

	public Author getAuthor() {
		return _author;
	}

	public double getScore() {
		return _score;
	}
	public String toString(){
		return _author+" score: "+_score;
	}
}
