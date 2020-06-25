package web.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import web.dao.face.StarDao;
import web.dbutil.JDBCTemplate;
import web.dto.Star;
import web.util.Paging;

public class StarDaoImpl implements StarDao {


	@Override
	public int selectCntStarByUserNo(int userno) {

		Connection conn = JDBCTemplate.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		int cnt = 0;
		
		String sql = "SELECT COUNT(*) FROM STAR WHERE USERS_NO = ?";
		
		
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userno);
			
			rs = ps.executeQuery();
			
			while( rs.next() ) {
				cnt = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
			JDBCTemplate.close(rs);
		}
		return cnt;
	}

	@Override
	public void updateStarByUserNoMenuNo(double updatescore, int userno, int menuNo) {
		
		Connection conn = JDBCTemplate.getConnection();
		PreparedStatement ps = null;
		
		int result = -1;
		
		String sql = "UPDATE STAR SET STAR_SCORE = ?";
		sql += "	WHERE USERS_NO = ? AND MENU_NO = ?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setDouble(1, updatescore);
			ps.setInt(2, userno);
			ps.setInt(3, menuNo);
			
			result = ps.executeUpdate();
			
			if(result>0) {
				JDBCTemplate.commit(conn);
				System.out.println("별점 수정 성공");
			}else {
				JDBCTemplate.rollback(conn);
				System.out.println("별점 수정 실패");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}
	}



}
