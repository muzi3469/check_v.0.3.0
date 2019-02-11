package com.web.check.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
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
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

import com.web.check.DTO.ChatDTO;
import com.web.check.constant.Constant;

public class ChatDAO {
	
	private JdbcTemplate template;
	
	public ChatDAO() {
		// TODO Auto-generated constructor stub
		template = Constant.template;
	}

	public boolean insertChat(final ChatDTO chat) {
		// TODO Auto-generated method stub
		
		boolean insertResult = false;
		
		List<SqlParameter> parameters = Arrays.asList(
				new SqlParameter(Types.VARCHAR),new SqlParameter(Types.VARCHAR),new SqlParameter(Types.INTEGER),
				new SqlOutParameter("out_nickname", Types.VARCHAR),	new SqlOutParameter("out_chatid", Types.INTEGER),
				new SqlOutParameter("out_chatindex", Types.INTEGER)
				);
		Map<String, Object> t = template.call(new CallableStatementCreator() {
			@Override
			public CallableStatement createCallableStatement(Connection con) throws SQLException {
				CallableStatement statement = con.prepareCall("{call register_chat(?,?,?,?,?,?)}");
				statement.setString(1, chat.getC_message()); statement.setString(2, chat.getM_email());
				statement.setInt(3, chat.getB_bid()); statement.registerOutParameter(4, Types.VARCHAR);
				statement.registerOutParameter(5, Types.INTEGER); statement.registerOutParameter(6, Types.INTEGER);
				return statement;
			}
		}, parameters);
		chat.setM_nickname((String)t.get("out_nickname"));
		chat.setC_chatid((Integer)t.get("out_chatid"));
		chat.setC_chatindex((Integer)t.get("out_chatindex"));
		if(chat.getM_nickname()!=null)
			insertResult = true;
		
		return insertResult;
	}

	public HashMap<Integer, ChatDTO> initChat(final ChatDTO chat) {
		// TODO Auto-generated method stub
		HashMap<Integer, ChatDTO> result = new HashMap<Integer, ChatDTO>();
		String SELECT_CHAT_BID_EMAIL = "select ch.c_chatid, ch.c_message, ch.m_email, m.m_nickname, ch.b_bid, ch.c_chatindex " + 
				"from c_chat ch, c_member m " + 
				"where ch.b_bid =  " + chat.getB_bid() + 
				"and ch.m_email=m.m_email";
		int index=0;
		try {
			ArrayList<ChatDTO> chats = (ArrayList<ChatDTO>) template.query(SELECT_CHAT_BID_EMAIL, 
					new BeanPropertyRowMapper<ChatDTO>(ChatDTO.class));
			for(ChatDTO c : chats) {
				if(c.getM_email().equals(chat.getM_email())) c.setM_email("me");
				result.put(index++, c);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

}






