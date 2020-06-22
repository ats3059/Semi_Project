package web.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import web.dao.face.UserDao;
import web.dao.impl.UserDaoImpl;
import web.dto.User;
import web.service.face.UserService;

public class UserServiceImpl implements UserService {

	private UserDao userDao = new UserDaoImpl();
	
	@Override
	public User getLoginUser(HttpServletRequest req) {
		
		User user = new User();
		user.setUserId(req.getParameter("id"));
		user.setUserPw(req.getParameter("pw"));
		
		return user;
	}

	@Override
	public boolean login(User user) {
		
		int cnt = userDao.selectUserCntByIdPw(user);
		
		if(cnt>0) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public User info(User user) {
		
		return userDao.selectUserByUserid(user);
	}

	@Override
	public User doublecheckId(HttpServletRequest req) {
		
		User user = new User();
		user.setUserId( req.getParameter("id") );
		User result = userDao.selectUserByUserid(user);
		
		System.out.println("UserService : " + result);
		
		return result;
	}

	@Override
	public int joinUser(HttpServletRequest req) {

		User user = new User();

		//생년월일
		String paramsBirth = req.getParameter("year") + req.getParameter("month") + req.getParameter("date");
		//생년월일형변환(String -> utilDate -> sqlDate)
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date utilDate = new Date();
		try {
			utilDate = sdf.parse( paramsBirth );
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    java.sql.Date birth = new java.sql.Date(utilDate.getTime());
		
		
		//전화번호
		String paramsTel = req.getParameter("tel_01") + req.getParameter("tel_02") + req.getParameter("tel_03");
		int tel = Integer.parseInt(paramsTel);
		
		//계정권한
		int userAuth = 1;
		
		//방문횟수
		int cnt = 0;
		
		//회원등급
		int grade = 1;
		
		//관심프랜차이즈
		int franno = userDao.selectFranNoByFranName( req.getParameter("franName") );
		
		user.setUserId( req.getParameter("id") );
		user.setUserPw( req.getParameter("pw") );
		user.setUserName( req.getParameter("name") );
		user.setUserNick( req.getParameter("nick") );
		user.setUserBirth( birth );
		user.setUserGender( req.getParameter("gender") );
		user.setUserEmail( req.getParameter("email") );
		user.setUserTel( tel );
		user.setUserCnt( cnt );
		user.setUserAuth( userAuth );
		user.setUserGrade( grade );
		user.setUserReport( "N" );
		user.setFranNo( franno );
		
		
		int result = userDao.insert(user);
		
		return result;
	}
	
	@Override
	public int joinFran(HttpServletRequest req) {
		
		User user = new User();
		
		//생년월일
		String paramsBirth = req.getParameter("year") + req.getParameter("month") + req.getParameter("date");
		//생년월일형변환(String -> utilDate -> sqlDate)
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date utilDate = new Date();
		try {
			utilDate = sdf.parse( paramsBirth );
		} catch (ParseException e) {
			e.printStackTrace();
		}
		java.sql.Date birth = new java.sql.Date(utilDate.getTime());
		
		
		//전화번호
		String paramsTel = req.getParameter("tel_01") + req.getParameter("tel_02") + req.getParameter("tel_03");
		int tel = Integer.parseInt(paramsTel);
		
		//계정권한
		int userAuth = 2;
		
		//방문횟수
		int cnt = 0;
		
		//회원등급
		int grade = 1;
		
		//관심프랜차이즈
		int franno = userDao.selectFranNoByFranName( req.getParameter("franName") );
		
		user.setUserId( req.getParameter("id") );
		user.setUserPw( req.getParameter("pw") );
		user.setUserName( req.getParameter("name") );
		user.setUserNick( req.getParameter("nick") );
		user.setUserBirth( birth );
		user.setUserGender( req.getParameter("gender") );
		user.setUserEmail( req.getParameter("email") );
		user.setUserTel( tel );
		user.setUserCnt( cnt );
		user.setUserAuth( userAuth );
		user.setUserGrade( grade );
		user.setUserReport( "N" );
		user.setFranNo( franno );
		
		
		int result = userDao.insert(user);
		
		return result;
	}

	@Override
	public boolean keyVerify(HttpServletRequest req) {
		
		//key인증
		String param = req.getParameter("key");
		if( param.equals("franchisee") ) {
			return true;
		} else {
			return false;
		}
	}


}
