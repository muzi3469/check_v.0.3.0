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
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.UpdatableSqlQuery;

import com.web.check.DTO.CardDTO;
import com.web.check.constant.Constant;

public class CardDAO {

	private JdbcTemplate template;
	private String TABLE = "c_card";

	public CardDAO() {
		// TODO Auto-generated constructor stub
		template = Constant.template;
	}

	public HashMap<String, Integer> insertCard(final CardDTO card) {
		// TODO Auto-generated method stub
		HashMap<String, Integer> result = new HashMap<String, Integer>();

		List<SqlParameter> parameters = Arrays.asList(
				new SqlParameter(Types.VARCHAR),
				new SqlParameter(Types.VARCHAR),
				new SqlParameter(Types.INTEGER),
				new SqlOutParameter("out_cid", Types.INTEGER),
				new SqlOutParameter("out_index", Types.INTEGER)
				);
		Map<String, Object> t = template.call(new CallableStatementCreator() {
			@Override
			public CallableStatement createCallableStatement(Connection con) throws SQLException {
				CallableStatement callableStatement = con.prepareCall("{call register_card(?,?,?,?,?)}");
				callableStatement.setString(1, card.getC_title());
				callableStatement.setString(2, "hello");
				callableStatement.setInt(3, card.getL_lid());
				callableStatement.registerOutParameter(4, Types.INTEGER);
				callableStatement.registerOutParameter(5, Types.INTEGER);
				return callableStatement;
			}
		}, parameters);
		result.put("cid", (Integer)t.get("out_cid"));
		result.put("index", (Integer)t.get("out_index"));

		return result;
	}

	public int updateCardInfo(final ArrayList<CardDTO> cardArr) {
		// TODO Auto-generated method stub
		String UPDATE_CARES_BATCH_SQL = "update " + TABLE + " set l_lid=?, c_index=? where c_cid=?";
//		for(CardDTO card:cardArr) {
//			UPDATE_CARDS_SQL += "update " + TABLE + " set l_lid=" + card.getL_lid() +
//					", c_index="+card.getC_index() +" where c_cid="+ card.getC_cid()+";";
//		}
		try {
			template.batchUpdate(UPDATE_CARES_BATCH_SQL, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					CardDTO card = cardArr.get(i);
					ps.setInt(1, card.getL_lid());
					ps.setInt(2, card.getC_index());
					ps.setInt(3, card.getC_cid());
				}
				
				@Override
				public int getBatchSize() {
					return cardArr.size();
				}
			});
		} catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 1;
	}

	public boolean removeCard(final ArrayList<CardDTO> cardarr) {
		// TODO Auto-generated method stub
		boolean removeResult = true;
		
		String UPDATE_CARDS_BATCH_SQL = "update " + TABLE + " set c_index=?, l_lid=? where c_cid=?";
		
		try {
			template.batchUpdate(UPDATE_CARDS_BATCH_SQL, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					// TODO Auto-generated method stub
					CardDTO card = cardarr.get(i);
					ps.setInt(1, card.getC_index());
					ps.setInt(3, card.getC_cid());
					if(card.getC_index()!=-1) {
						ps.setInt(2, card.getL_lid());
					}else {
						ps.setInt(2, -1);
					}
				}
				
				@Override
				public int getBatchSize() {
					// TODO Auto-generated method stub
					return cardarr.size();
				}
			});
		}catch(Exception e) {
			e.printStackTrace();
			removeResult = false;
		}
		
		return removeResult;
	}

}
