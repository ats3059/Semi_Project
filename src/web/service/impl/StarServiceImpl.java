package web.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import web.dao.face.StarDao;
import web.dao.impl.StarDaoImpl;
import web.service.face.StarService;

public class StarServiceImpl implements StarService{
	
	private StarDao starDao = new StarDaoImpl();
	

	@Override
	public int countStarByUserNo(HttpServletRequest req) {
	
		HttpSession session = req.getSession();
		String param = String.valueOf(session.getAttribute("userno"));
		int userno = Integer.parseInt(param);
		
		int cnt = starDao.selectCntStarByUserNo(userno);
		
		return cnt;
	}


	@Override
	public void updateStar(HttpServletRequest req) {
		
		//메뉴번호 전달받기
		String param = req.getParameter("menuNo");
		int menuNo = Integer.parseInt(param);
		
		//회원번호 꺼내기(세션)
		HttpSession session = req.getSession();
		String p = String.valueOf(session.getAttribute("userno"));
		int userno = Integer.parseInt(p);
		
		//수정된 별점 전달받기
		String par = req.getParameter("updatescore");
		int updatescore = Integer.parseInt(par);
		
		starDao.updateStarByUserNoMenuNo(updatescore, userno, menuNo);
		
	}



}
