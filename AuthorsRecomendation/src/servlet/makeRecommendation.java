package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
//		System.out.println(name);
		String str="";
		if(name !=null)
		{
			Author a = new Author(name);
			recommendAuthor a1 = new recommendAuthor(a, Main.authors);
			int index = Main.topAuthors.indexOf(a1);
			if (index < 0)
			{
				response.getWriter().write("0");
				return;
			}
			a1 = Main.topAuthors.get(index);
//			System.out.println(a1);
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

}
