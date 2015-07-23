package Authors;

public class AuthorRDF {
	private String _name;
	private String _dcCreator;
	public double _score;
	
	
	public AuthorRDF(){
		_name = "";
		_dcCreator = "";
		_score = 0;
	}
	public AuthorRDF(String name, String dcCreator){
		_name = name;
		_dcCreator = dcCreator;
		_score = 0;
	}
	public AuthorRDF(String name, String dcCreator, double score){
		_name = name;
		_dcCreator = dcCreator;
		_score = score;
	}
	public String getName(){
		return _name;
	}
	public String getDcCreator(){
		return _dcCreator;
	}
	public String toString(){
		return _dcCreator+ " --- " + _name + " --- " + _score;
	}
}
