package util;

import dal.DataBase_API;
public class ReccomenderAuthors {
	public static void main(String[] args) {
		DataBase_API db = new DataBase_API("dba", "dba", "localhost", "1111", "http://hujilinkeddata/");
		String sql = "SELECT DISTINCT ?book ?creator "
				+ "WHERE { "
				+ "?s <http://purl.org/dc/terms/type> 'book'. "
				+ "?s <http://purl.org/dc/terms/creator> ?e. "
				+ "?s <http://purl.org/dc/terms/subject> ?book. "
				+ "?e <http://www.w3.org/2000/01/rdf-schema#label> ?creator "
				+ "}";
		db.GetTripples(sql);

	}
}
