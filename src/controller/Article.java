package controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.BoardDao;
import db.BoardDto;

public class Article extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Article() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		URLEncoder.encode("UTF-8");
		
		int id = Integer.parseInt(request.getParameter("id"));
		boolean newVisit = true;
		
		Cookie[] cookies = request.getCookies();
		if(request.getCookies()!=null) {
			for(int i=0;i<cookies.length;i++) {
				if(cookies[i].getName().equals("board_id")) {
					if(cookies[i].getValue().equals(id+"")) {
						newVisit = false;
					}
				}
			}
		}
		
		if(newVisit) {
			BoardDao.getInstance().hitUp(id);
		}
//		String hitCheck = request.getParameter("hitCheck");
//		
//		if(hitCheck.equals("yes")) {
//			BoardDao.getInstance().hitUp(id);
//		}
		
		BoardDao boardDao = BoardDao.getInstance();
		BoardDto boardDto = boardDao.getDto(id);
		
		
		
		Cookie cok = new Cookie("board_id",id+"");
		response.addCookie(cok);
		request.setAttribute("boardDto", boardDto);
		RequestDispatcher rd = request.getRequestDispatcher("/article.jsp");
		rd.forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
