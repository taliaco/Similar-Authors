package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bl.RecommenderAuthorsBL;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

import net.ricecode.similarity.JaroWinklerStrategy;
import net.ricecode.similarity.SimilarityStrategy;
import net.ricecode.similarity.StringSimilarityService;
import net.ricecode.similarity.StringSimilarityServiceImpl;
import util.Main;
import Authors.Author;
import Authors.AuthorRDF;
import Authors.TopicWeight;
import Authors.recommendAuthor;
@WebServlet("/makeRecommendation")
public class makeRecommendation extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public makeRecommendation() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("testGET");

	}

	//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	//		response.setContentType("text/html; charset=UTF-8");
	//		Object nameObj = request.getParameter("name");
	//		String name = nameObj.toString();
	//		String str="";
	//		if(name !=null)
	//		{
	//			int index = getIndexMostSimilar(name);
	//			if (index < 0)
	//			{
	//				response.getWriter().write("0");
	//				return;
	//			}
	//			recommendAuthor a1 = Main.topAuthors.get(index);
	//			str+= a1.getAuthor().getName()+"* *";
	//			for(int i = 0; i < Main.topAuthors.size(); i++){
	//				Main.topAuthors.get(i).cosineSimilarity(a1);
	//			}
	//			Main.topAuthors.sort(null);
	//			int i=0, j=0;
	//			while (j<5){
	//				if (!Main.topAuthors.get(i).getAuthor().equals(a1.getAuthor())){
	//					str += Main.topAuthors.get(i).getAuthor().getName()+". Score: " + Main.topAuthors.get(i).getScore() +"*";
	//					j++;
	//				}
	//				i++;
	//			}
	//
	//			response.getWriter().write(str);
	//
	//		}
	//		else
	//			response.getWriter().write("0");
	//	}



	//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	//		response.setContentType("text/html; charset=UTF-8");
	//		response.setHeader("Cache-control", "no-cache, no-store");
	//		response.setHeader("Pragma", "no-cache");
	//		response.setHeader("Expires", "-1");
	//		response.setHeader("Access-Control-Allow-Origin", "*");
	//		response.setHeader("Access-Control-Allow-Methods", "POST");
	//		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
	//		response.setHeader("Access-Control-Max-Age", "86400");
	//
	//		Object nameObj = request.getParameter("name");
	//		PrintWriter out = response.getWriter();
	//
	//		Gson gson = new Gson(); 
	//		JsonObject myObj = new JsonObject();
	//
	//		String name = nameObj.toString();
	//		JsonElement authorObj;
	//		if(name !=null)
	//		{
	//			recommendAuthor[] arr = new recommendAuthor[6];
	//			int index = getIndexMostSimilar(name);
	//			if (index < 0)
	//			{
	//				myObj.addProperty("success", false);
	//			}		
	//			else 
	//			{
	//				myObj.addProperty("success", true);
	//				recommendAuthor a1 = Main.topAuthors.get(index);
	//				arr[0]=new recommendAuthor(a1);
	//				for(int i = 0; i < Main.topAuthors.size(); i++){
	//					Main.topAuthors.get(i).cosineSimilarity(a1);
	//				}
	//				Main.topAuthors.sort(null);
	//				int i=0, j=1;
	//				while (j<arr.length){
	//					if (!Main.topAuthors.get(i).getAuthor().equals(a1.getAuthor())){
	//						arr[j]=new recommendAuthor(Main.topAuthors.get(i));
	//						j++;
	//					}
	//					i++;
	//				}
	//				authorObj = gson.toJsonTree(arr);
	//				myObj.add("authorsInfo", authorObj);
	//			}
	//		}
	//		else
	//		{
	//			myObj.addProperty("success", false);
	//		}
	//
	//		out.println(myObj.toString());
	//
	//		out.close();
	//	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RecommenderAuthorsBL bl = new RecommenderAuthorsBL();

		response.setContentType("text/html; charset=UTF-8");
		response.setHeader("Cache-control", "no-cache, no-store");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "-1");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Max-Age", "86400");

		long start = System.currentTimeMillis();
		Object nameObj = request.getParameter("name");
		PrintWriter out = response.getWriter();

		Gson gson = new Gson(); 
		JsonObject myObj = new JsonObject();

		String name = nameObj.toString();
		JsonElement authorObj;
		if(name !=null)
		{
			recommendAuthor[] arr = new recommendAuthor[6];




			//search for the author that requested
			AuthorRDF SelecteddcCreator = getIndexMostSimilar(name);
			if (SelecteddcCreator == null)
			{
				myObj.addProperty("success", false);
			}		
			else 
			{
				myObj.addProperty("success", true);

				//get topic vec of this author
//				ArrayList<AuthorRDF> aaa = new ArrayList<AuthorRDF>();
				ArrayList<TopicWeight> selectedTopicVec = bl.getTFIDFTopic(SelecteddcCreator.getDcCreator());
//				ArrayList<recommendAuthor> a = new ArrayList<recommendAuthor>();
				recommendAuthor a1 = new recommendAuthor(SelecteddcCreator.getName(),  SelecteddcCreator.getDcCreator(),  1,  0,  selectedTopicVec);
				arr[0]= new recommendAuthor(a1,true);
//				ResultSet allAuthorsSet = bl.getAllAuthorsResultSet();
				//loop all authors get top 5 from cusine function
//				while (allAuthorsSet.hasNext()) {
//					QuerySolution rs = allAuthorsSet.nextSolution();
//					String dcCreator =rs.get("dcCreator").toString();
//					if(!SelecteddcCreator.equals(dcCreator)){
//						ArrayList<TopicWeight> topicVec = bl.getTFIDFTopic(dcCreator);
//						double score = (double)cosineSimilarity(selectedTopicVec, topicVec);
////						double tf = Double.parseDouble(rs.get("tf").toString());
//						a.add(new recommendAuthor( rs.get("name").toString(),  dcCreator,  score,  0,  topicVec));
//						System.out.println(rs.get("name").toString());
//					}
//				}
				for(int i=0; i<Main.a.size(); i++){
					Main.a.get(i).setScore((double)cosineSimilarity(selectedTopicVec, Main.a.get(i).getAuthorTopicsVec()));
				}
				System.err.println("Total Search time: " + (System.currentTimeMillis() - start)/1000 + " seconds");
				Main.a.sort(null);
				int i=0, j=1;
				while (j<arr.length){
					if (!Main.a.get(i).getDcCreator().equals(a1.getDcCreator())){
						arr[j]=new recommendAuthor(Main.a.get(i),true);
						j++;
					}
					i++;
				}
				authorObj = gson.toJsonTree(arr);
				myObj.add("authorsInfo", authorObj);
			}
		}
		else
		{
			myObj.addProperty("success", false);
		}

		out.println(myObj.toString());

		out.close();
	}

	private double cosineSimilarity(ArrayList<TopicWeight> selectedTopicVec, ArrayList<TopicWeight> topicVec) {
		double dotProduct = 0.0;
		double normA = 0.0;
		double normB = 0.0;
		ArrayList <TopicWeight> topicListA  = selectedTopicVec;
		ArrayList <TopicWeight> topicListB = topicVec;

		ArrayList <TopicWeight> all =  new ArrayList <TopicWeight>();
		all.addAll(topicListA);
		for (int i=0; i<topicListB.size(); i++ ){
			if (!all.contains(topicListB.get(i))){
				all.add(topicListB.get(i));
			}
		}

		for (int i = 0; i < topicListA.size(); i++) {
			normA += (double)Math.pow((double)topicListA.get(i).getTopicWeight() , 2);
		}
		normA = (double)Math.sqrt(normA);
		for (int i = 0; i < topicListB.size(); i++) {
			normB += (double)Math.pow((double)topicListB.get(i).getTopicWeight() , 2);
		}
		normB = (double)Math.sqrt(normB);
		for (int i = 0; i < all.size(); i++) {

			int indexOfTopicA = topicListA.indexOf(all.get(i));
			int indexOfTopicB = topicListB.indexOf(all.get(i));
			if (indexOfTopicA > -1 && indexOfTopicB > -1){
				dotProduct += ((double)topicListB.get(indexOfTopicB).getTopicWeight() * (double)topicListA.get(indexOfTopicA).getTopicWeight());
			}
		} 
		if (normA == 0 || normB == 0){
			return 0;
		}
		double _score = (double)dotProduct / ((double)normA * (double)normB);
		return  _score;
	}

	public AuthorRDF getIndexMostSimilar(String name){
		AuthorRDF toReturn = new AuthorRDF();
		RecommenderAuthorsBL bl = new RecommenderAuthorsBL();
		SimilarityStrategy strategy = new JaroWinklerStrategy();
		StringSimilarityService service = new StringSimilarityServiceImpl(strategy);
		double score = 0; 
		String dcCreator="";
		String authorName = "";
		double maxScore=0;
		for (int i=0; i< Main.a.size(); i++){
			score = service.score(name, Main.a.get(i).getName().replaceAll("[0-9]",""));

			if (score>maxScore){
				maxScore = score;
				dcCreator =Main.a.get(i).getDcCreator();
				authorName = Main.a.get(i).getName();
				toReturn = new AuthorRDF(authorName, dcCreator, maxScore);
			}
		}
//		ResultSet allAuthorsSet = bl.getAllAuthorsResultSet();
//		while (allAuthorsSet.hasNext()) {
//			QuerySolution rs = allAuthorsSet.nextSolution();
//			//			System.out.println(rs.get("dcCreator").toString());
//			score = service.score(name, rs.get("name").toString().replaceAll("[0-9]",""));
//
//			if (score>maxScore){
//				maxScore = score;
//				dcCreator =rs.get("dcCreator").toString();
//				authorName = rs.get("name").toString();
//				toReturn = new AuthorRDF(authorName, dcCreator, maxScore);
//			}
//		}

		System.out.println(toReturn);
		if (maxScore > 0.8)
			return toReturn;
		return null;

	}
}
