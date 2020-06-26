package web.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import web.dao.face.MenuDao;
import web.dbutil.JDBCTemplate;
import web.dto.Fran;
import web.dto.Menu;
import web.util.Paging;

public class MenuDaoImpl implements MenuDao{
	
	private Connection conn = null; //DB연결 객체
	private PreparedStatement ps = null; //SQL수행 객체
	private ResultSet rs = null; //SQL조회 결과 객체

	@Override
	public int selectCntAll(int franno) {
		
		System.out.println("여기도?" + franno);
		
		conn = JDBCTemplate.getConnection();

		String sql = "";
		sql += "SELECT count(*) FROM menu";
		sql += " WHERE FRAN_NO = ?";
		
		//결과저장할 변수
		int totalCount = 0;
		
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, franno);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {//조회 결과 처리
				totalCount = rs.getInt(1);
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		
		return totalCount;
	}

	@Override
	public List<Menu> selectCntAll(Paging paging , int franno) {
		
		conn = JDBCTemplate.getConnection();
		

		
		
		String sql = "";
		sql +="SELECT * FROM (";
		sql +="		select rownum rnum , B.* FROM(";
		sql +="			SELECT"; 
		sql +="	            menu_no , menu_name , menu_info";
		sql +="			            , menu_cost , menu_date , menu_stat , menu_blind";
		sql +="	        FROM menu";
		sql +="        WHERE FRAN_NO = ?";
		sql +="        ORDER BY menu_no DESC";
		sql +="	        ) B";
		sql +="	    ) Menu";
		sql +="	 WHERE rnum BETWEEN ? AND ?";
		
		
		List<Menu> MenuList = new ArrayList<>();
		
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, franno);
			ps.setInt(2, paging.getStartNo());
			ps.setInt(3, paging.getEndNo());
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Menu menu = new Menu();
				
				menu.setMenuNo(rs.getInt("MENU_NO"));
				menu.setMenuName(rs.getString("MENU_NAME"));
				menu.setMenuInfo(rs.getString("MENU_INFO"));
				menu.setMenuCost(rs.getInt("MENU_COST"));
				menu.setMenuDate(rs.getDate("MENU_DATE"));
				menu.setMenuStat(rs.getString("MENU_STAT"));
				menu.setMenuBlind(rs.getString("MENU_BLIND"));
//				menu.setFranNo(rs.getInt("FRAN_NO"));
				
				MenuList.add(menu);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		return MenuList;
	}

	@Override
	public Menu selectMenuByMenuno(Menu menuno) {

		//DB연결 객체
		conn = JDBCTemplate.getConnection();
		
		//SQL 작성
		String sql = "";
		sql += "SELECT * FROM menu";
		sql += " WHERE menu_no = ?";
		
		Menu viewMenu = null;
		
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, menuno.getMenuNo());
			
			rs = ps.executeQuery();

			while(rs.next()) {
				viewMenu = new Menu();
				
				viewMenu.setMenuNo(rs.getInt("MENU_NO"));
				viewMenu.setMenuName(rs.getString("MENU_NAME"));
				viewMenu.setMenuInfo(rs.getString("MENU_INFO"));
				viewMenu.setMenuCost(rs.getInt("MENU_COST"));
				viewMenu.setMenuDate(rs.getDate("MENU_DATE"));
				viewMenu.setMenuStat(rs.getString("MENU_STAT"));
				viewMenu.setMenuBlind(rs.getString("MENU_BLIND"));
				viewMenu.setFranNo(rs.getInt("FRAN_NO"));
				
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			//DB객체 닫기
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return viewMenu;
	}

	@Override
	public int insertMenu(Menu menu , int franno) {
		
		conn = JDBCTemplate.getConnection();
		
		String sql = "";
		sql += "INSERT INTO menu(MENU_NAME, MENU_INFO, MENU_COST, MENU_DATE, FRAN_NO ,menu_stat, MENU_BLIND)";
		sql += " VALUES(?,?,?,?,?,'N','N')";
		
		String sql2 ="select MENU_SEQ.currval from dual";

		int res = 0;
		
		try {
			ps = conn.prepareStatement(sql);
			
			//여기
			ps.setString(1, menu.getMenuName());
			ps.setString(2, menu.getMenuInfo());
			ps.setInt(3, menu.getMenuCost());
			ps.setDate(4, new java.sql.Date(menu.getMenuDate().getTime()));
			ps.setInt(5, franno);
			ps.executeUpdate();
			
			ps = conn.prepareStatement(sql2);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				res = rs.getInt("currval");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(ps);
		}
		return res;
		
	}

	@Override
	public List<Menu> selectAllMenu() {
		
		conn = JDBCTemplate.getConnection();
		
		String sql = "";
	    sql += " SELECT * FROM menu";
	    sql += "  where menu_stat='Y' and menu_blind='N'";
	    sql += " ORDER BY MENU_NO DESC";
		
	    List<Menu> MenuList = new ArrayList<>();
	    
	    try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				Menu menu = new Menu();
				
				menu.setMenuNo(rs.getInt("MENU_NO"));
				menu.setMenuName(rs.getString("MENU_NAME"));
				menu.setMenuInfo(rs.getString("MENU_INFO"));
				menu.setMenuCost(rs.getInt("MENU_COST"));
				menu.setMenuDate(rs.getDate("MENU_DATE"));
				menu.setMenuStat(rs.getString("MENU_STAT"));
				menu.setMenuBlind(rs.getString("MENU_BLIND"));
				menu.setFranNo(rs.getInt("FRAN_NO"));
				
				MenuList.add(menu);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(ps);
		}
		
		return MenuList;
	}

	@Override
	public Menu selectMenuByMenuNo(int foodByMenuNo) {
		
		conn = JDBCTemplate.getConnection();
		
		String sql = "";
	    sql += " SELECT * FROM menu";
	    sql += " WHERE MENU_NO = ?";
	    
	    Menu menu = null;
	    
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, foodByMenuNo);
			rs = ps.executeQuery();

			while(rs.next()) {
				menu = new Menu();
				
				menu.setMenuNo(rs.getInt("MENU_NO"));
				menu.setMenuName(rs.getString("MENU_NAME"));
				menu.setMenuInfo(rs.getString("MENU_INFO"));
				menu.setMenuCost(rs.getInt("MENU_COST"));
				menu.setMenuDate(rs.getDate("MENU_DATE"));
				menu.setMenuStat(rs.getString("MENU_STAT"));
				menu.setMenuBlind(rs.getString("MENU_BLIND"));
				menu.setFranNo(rs.getInt("FRAN_NO"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		return menu;
	}

	


	
	
	
	
	
}
