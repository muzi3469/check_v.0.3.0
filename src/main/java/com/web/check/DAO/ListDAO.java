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
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

import com.web.check.DTO.CardDTO;
import com.web.check.DTO.ListDTO;
import com.web.check.constant.Constant;

public class ListDAO {

	private JdbcTemplate template;
	private String TABLE = "c_list";

	public ListDAO() {
		template = Constant.template;
	}

	public HashMap<String, Integer> insertList(final ListDTO listdto) {

		HashMap<String, Integer> result = new HashMap<String, Integer>();

		List<SqlParameter> parameters = Arrays.asList(
				new SqlParameter(Types.VARCHAR),
				new SqlParameter(Types.INTEGER),
				new SqlOutParameter("out_lid", Types.INTEGER),
				new SqlOutParameter("out_index", Types.INTEGER)
				);
		Map<String, Object> t = template.call(new CallableStatementCreator() {

			@Override
			public CallableStatement createCallableStatement(Connection con) throws SQLException {
				CallableStatement callableStatement = con.prepareCall("{call register_list(?,?,?,?)}");
				callableStatement.setString(1, listdto.getL_title());
				callableStatement.setInt(2, listdto.getB_bid());
				callableStatement.registerOutParameter(3, Types.INTEGER);
				callableStatement.registerOutParameter(4, Types.INTEGER);
				return callableStatement;
			}
		}, parameters);
		result.put("lid", (Integer)t.get("out_lid"));
		result.put("index", (Integer)t.get("out_index"));

		return result;
	}

	public HashMap<String, HashMap> initBoard(final int bid) {
		// TODO Auto-generated method stub
		/**
		 * l_list에서 해당 bid에 관한것만 찾고
		 * l_card에서 위 결과로 카드를 찾아야한다.
		 */
		HashMap<String, HashMap> result = new HashMap<String, HashMap>();
		HashMap<Integer, ListDTO> lists = new HashMap<Integer, ListDTO>();
		HashMap<Integer, CardDTO> cards = new HashMap<Integer, CardDTO>();
		int index=0;
		String SELECT_LIST_BYBID = "select * from c_list where b_bid=" + bid;
		String SeLECT_CARD_BY_LIST_BID = "select * " + 
				"from c_card c " + 
				"where c.l_lid in (" + 
				"    select l.l_lid " + 
				"    from c_list l " + 
				"    where l.b_bid=" + bid + 
				") order by c.c_index";
		try {
			ArrayList<ListDTO> listArr = 
					(ArrayList<ListDTO>) template.query(SELECT_LIST_BYBID, 
							new BeanPropertyRowMapper<ListDTO>(ListDTO.class));
			for(ListDTO l : listArr)
				lists.put(index++, l);
			index = 0;
			ArrayList<CardDTO> cardArr = 
					(ArrayList<CardDTO>) template.query(SeLECT_CARD_BY_LIST_BID, 
							new BeanPropertyRowMapper<CardDTO>(CardDTO.class));
			for(CardDTO c : cardArr)
				cards.put(index++, c);

			result.put("lists", lists);
			result.put("cards", cards);

		}catch(Exception e) {e.printStackTrace();}

		return result;
	}

	public boolean removeList(final ArrayList<ListDTO> listarr, final int bid) {
		// TODO Auto-generated method stub
		boolean removeResult = true;

		String UPDATE_LISTS_BATCH_SQL = "update " + TABLE + " set l_index=?, b_bid=? where l_lid=?";

		try {
			template.batchUpdate(UPDATE_LISTS_BATCH_SQL, new BatchPreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					// TODO Auto-generated method stub
					ListDTO list = listarr.get(i);
					if(list.getL_lid()!=-2) {
						ps.setInt(1, list.getL_index());
						ps.setInt(3, list.getL_lid());
						if(list.getL_index()!=-1) {
							ps.setInt(2, bid);
						}else {
							ps.setInt(2, -1);
						}
					}
				}

				@Override
				public int getBatchSize() {
					// TODO Auto-generated method stub
					return listarr.size();
				}
			});
		} catch(Exception e) {
			e.printStackTrace();
			removeResult=false;
		}

		return removeResult;
	}

	public boolean modifyListTitle(ListDTO list) {
		// TODO Auto-generated method stub
		boolean modifyResult = true;
		String UPDATE_LIST_TITLE_SQL = "update " + TABLE + " set l_title='" + list.getL_title() + "' where l_lid=" + list.getL_lid();

		try {
			template.update(UPDATE_LIST_TITLE_SQL);
		} catch(Exception e) {
			e.printStackTrace();
			modifyResult = false;
		}

		return modifyResult;
	}

}




