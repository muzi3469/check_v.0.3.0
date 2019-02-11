package com.web.check.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

import com.web.check.DTO.CalendarDTO;
import com.web.check.constant.Constant;


public class CalendarDAO {
	private JdbcTemplate template;
	
	private String TABLE = " c_calendar ";
	
	public CalendarDAO() {
		// TODO Auto-generated constructor stub
		template = Constant.template;
	}
	public int publicprocess(final String title,final String start,final String end,final String description, final int cid,final int bid) {
		//String sql="update mvc_board set name=?,title=?,content=? where id=?;
		String sql = "update"+TABLE+"set caltitle=?,calstart=?,calend=?,b_bid=?,caldescription=? where calid=?";
		int result = template.update(sql,new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps)throws SQLException{
				ps.setString(1, title);
				ps.setString(2, start);
				ps.setString(3, end);
				ps.setInt(4, bid);
				ps.setString(5, description);
				ps.setInt(6, cid);
			}
		});
		return result;
	}
	public boolean saveDragDrop(final String title,final String start,final String end,final int cid,final String description,final int bid) {
		//table 생성할때 컬럼이름에 start 안됨
		int result = this.publicprocess(title, start, end,description,cid,bid);
		if(result!=0) {
			return true;
		}else {
			return false;
		}
	}
	public int saveSelectDrop(final String title,final String start,final String end,final int bid,final String description) {
		// TODO Auto-generated method stub
		int cid = -1;

		List<SqlParameter> parameters = Arrays.asList(
				new SqlParameter(Types.VARCHAR), new SqlParameter(Types.VARCHAR),
				new SqlParameter(Types.VARCHAR), new SqlParameter(Types.INTEGER),
				new SqlParameter(Types.VARCHAR), new SqlOutParameter("out_calid",Types.INTEGER));
		
		Map<String,Object> t = template.call(new CallableStatementCreator() {			
			@Override
			public CallableStatement createCallableStatement(Connection con) throws SQLException {
				CallableStatement callableStatement = con.prepareCall("{call register_calendar(?,?,?,?,?,?)}");
				callableStatement.setString(1, title);
				callableStatement.setString(2, start);
				callableStatement.setString(3, end);
				callableStatement.setInt(4, bid);
				callableStatement.setString(5, description);
				callableStatement.registerOutParameter(6, Types.INTEGER);
				return callableStatement;
			}
		}, parameters);
		cid = (Integer)t.get("out_calid");
		
		return cid;
	}
	public boolean saveResizeDrop(final String title,final String start,final String end,final int cid,final String description,final int bid) {
		int result = this.publicprocess(title, start, end,description,cid,bid);
		if(result!=0) {
			return true;
		}else {
			return false;
		}
	}
	public boolean saveModifyEvent(final String title,final String start,final String end,final int cid,final String description,final int bid) {
		int result = this.publicprocess(title, start, end,description,cid,bid);
		if(result!=0) {
			return true;
		}else {
			return false;
		}
	}
	public HashMap<String,HashMap> initCalendar(final int bid){
		HashMap<String, HashMap> result = new HashMap<String, HashMap>();
		HashMap<Integer, CalendarDTO> events = new HashMap<Integer, CalendarDTO>();
		int index=0;
		String sql = "select * from"+TABLE+"where b_bid=" + bid;
		try {
			ArrayList<CalendarDTO> listArr = 
					(ArrayList<CalendarDTO>) template.query(sql, 
					new BeanPropertyRowMapper<CalendarDTO>(CalendarDTO.class));
			for(CalendarDTO l : listArr)
				events.put(index++, l);
			index = 0;
			result.put("events", events);
			

		}catch(Exception e) {e.printStackTrace();}
		
		return result;
	}
	public int deleteEvent(final int cid) {
		String sql = "delete from"+TABLE+"where calid=?";
		int result = template.update(sql,new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				ps.setInt(1, cid);
			}
		
		});
		return result;
	}
	
	
	
	
	
	
}
