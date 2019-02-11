package com.web.check.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

import com.web.check.DTO.BoardDTO;
import com.web.check.constant.Constant;

public class BoardDAO {
	
	private JdbcTemplate template;
	private String TABLE = "c_board";
	
	public BoardDAO() {
		// TODO Auto-generated constructor stub
		template = Constant.template;
	}

	public int makeBoard(final BoardDTO board) {
		// TODO Auto-generated method stub
		int bid = -1;
		
		List<SqlParameter> parameters = Arrays.asList(
				new SqlParameter(Types.VARCHAR),
				new SqlParameter(Types.INTEGER),
				new SqlParameter(Types.VARCHAR),
				new SqlOutParameter("out_bid", Types.INTEGER));
		Map<String, Object> t = template.call(new CallableStatementCreator() {
			
			@Override
			public CallableStatement createCallableStatement(Connection con) throws SQLException {
				CallableStatement callableStatement = con.prepareCall("{call register_board(?,?,?,?)}");
				callableStatement.setString(1, board.getB_title());
				callableStatement.setInt(2, board.getT_tid());
				callableStatement.setString(3, board.getB_admin());
				callableStatement.registerOutParameter(4, Types.INTEGER);
				return callableStatement;
			}
		}, parameters);
		bid = (Integer) t.get("out_bid");
		return bid;
	}

}












