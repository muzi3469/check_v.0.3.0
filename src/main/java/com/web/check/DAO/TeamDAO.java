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

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

import com.web.check.DTO.BoardDTO;
import com.web.check.DTO.TeamDTO;
import com.web.check.DTO.TeamMemberDTO;
import com.web.check.constant.Constant;

public class TeamDAO {
	
	private JdbcTemplate template;
	private String TABLE = "c_team";

	public TeamDAO() {
		// TODO Auto-generated constructor stub
		template = Constant.template;
	}

	public int createNewTeam(final TeamDTO team, final String email) {
		// TODO Auto-generated method stub
		int tid;
		if(email == null)
			return -1;
		List<SqlParameter> parameters = Arrays.asList(
				new SqlParameter(Types.VARCHAR),
				new SqlParameter(Types.VARCHAR),
				new SqlParameter(Types.VARCHAR),
				new SqlOutParameter("out_tid", Types.INTEGER));
		Map<String, Object> t = template.call(new CallableStatementCreator() {
			@Override
			public CallableStatement createCallableStatement(Connection con) throws SQLException {
				CallableStatement callableStatement = con.prepareCall("{call register_team(?,?,?,?)}");
				callableStatement.setString(1, team.getT_title());
				callableStatement.setString(2, team.getT_type());
				callableStatement.setString(3, email);
				callableStatement.registerOutParameter(4, Types.INTEGER);
				return callableStatement;
			}
		}, parameters);
		
		System.out.println(t.toString());
		
		tid = (Integer) t.get("out_tid");
		return tid;
	}

	public HashMap<String, HashMap> initHome(final String email) {
		// TODO Auto-generated method stub
		HashMap<String, HashMap> result = new HashMap<String, HashMap>();
		HashMap<Integer, TeamDTO> teams = new HashMap<Integer, TeamDTO>();
		HashMap<Integer, BoardDTO> boards = new HashMap<Integer, BoardDTO>();
		int index = 0;
		String SELECT_TEAM_BYEMAIL = "select t.t_tid, t.t_title, t.t_type " + 
				"from c_team t " + 
				"where t.t_tid in ( " + 
				"select tm.t_tid " + 
				"from c_team_member tm " + 
				"where tm.m_email='" + email +"')";
		String SELECT_BOARD_BY_TEAM_EMAIL = "select b.b_bid, b.b_title, b.t_tid, b.b_admin " + 
				"from c_board b " + 
				"where b.t_tid in (select t.t_tid " + 
				"from c_team t " + 
				"where t.t_tid in ( " + 
				"select tm.t_tid " + 
				"from c_team_member tm " + 
				"where tm.m_email='"+email+"')) "
			  +	" order by b.t_tid";
		try {
			ArrayList<TeamDTO> teamArr = (ArrayList<TeamDTO>) template.query(SELECT_TEAM_BYEMAIL, 
						new BeanPropertyRowMapper<TeamDTO>(TeamDTO.class));
			for(TeamDTO t : teamArr) {
				teams.put(index++, t);
			}
			index = 0;
			ArrayList<BoardDTO> boardArr = (ArrayList<BoardDTO>) template.query(SELECT_BOARD_BY_TEAM_EMAIL, 
					new BeanPropertyRowMapper<BoardDTO>(BoardDTO.class));
			
			System.out.println("boardarr : " + boardArr);
			System.out.println("teamArr : " + teamArr);
			for(BoardDTO b : boardArr) {
				boards.put(index++, b);
			}
			
			result.put("teams", teams);
			result.put("boards", boards);
		}catch(Exception e) {e.printStackTrace();}
		
		return result;
	}

	public boolean insertTeamMember(final ArrayList<TeamMemberDTO> tmArr) {
		// TODO Auto-generated method stub
		boolean insertResult = true;
		String INSERT_TEAM_MEMBER_BATCH_SQL = 
				"{call register_team_member(?,?)}";
		
		try {
			template.batchUpdate(INSERT_TEAM_MEMBER_BATCH_SQL, new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					// TODO Auto-generated method stub
					TeamMemberDTO tm = tmArr.get(i);
					ps.setInt(1, tm.getT_tid());
					ps.setString(2, tm.getM_email());
				}
				
				@Override
				public int getBatchSize() {
					// TODO Auto-generated method stub
					return tmArr.size();
				}
			});
		}catch(Exception e) {
			e.printStackTrace();
			insertResult = false;
		}
		
		return insertResult;
	}

}
