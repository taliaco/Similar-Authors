package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ricecode.similarity.JaroWinklerStrategy;
import net.ricecode.similarity.SimilarityStrategy;
import net.ricecode.similarity.StringSimilarityService;
import net.ricecode.similarity.StringSimilarityServiceImpl;
import util.Main;
import Authors.Author;
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		Object nameObj = request.getParameter("name");
		String name = nameObj.toString();
		String str="";
		if(name !=null)
		{
			int index = getIndexMostSimilar(name);
			if (index < 0)
			{
				response.getWriter().write("0");
				return;
			}
			recommendAuthor a1 = Main.topAuthors.get(index);
			str+= a1.getAuthor().getName()+"* *";
			for(int i = 0; i < Main.topAuthors.size(); i++){
				Main.topAuthors.get(i).cosineSimilarity(a1);
			}
			Main.topAuthors.sort(null);
			int i=0, j=0;
			while (j<5){
				if (!Main.topAuthors.get(i).getAuthor().equals(a1.getAuthor())){
					str += Main.topAuthors.get(i).getAuthor().getName()+". Score: " + Main.topAuthors.get(i).getScore() +"*";
					j++;
				}
				i++;
			}

			response.getWriter().write(str);

		}
		else
			response.getWriter().write("0");
	}
	
	public int getIndexMostSimilar(String name){
		SimilarityStrategy strategy = new JaroWinklerStrategy();
		StringSimilarityService service = new StringSimilarityServiceImpl(strategy);
		double score = 0; 
		int index=-1;
		double maxScore=0;
		for(int i=0; i<Main.topAuthors.size(); i++){
			score = service.score(name, Main.topAuthors.get(i).getAuthor().getName().replaceAll("[0-9]",""));
			if (score>maxScore){
				maxScore = score;
				index =i;
			}
		}
		System.out.println(maxScore +" " + Main.topAuthors.get(index).getAuthor().getName());
		if (maxScore > 0.7)
			return index;
		return -1;
		
	}
}
