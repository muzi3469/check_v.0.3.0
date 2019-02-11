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

import com.web.check.DTO.MemberDTO;
import com.web.check.constant.Constant;

public class MemberDAO {

	private JdbcTemplate template;
	private String TABLE = "c_member";

	public MemberDAO() {
		// TODO Auto-generated constructor stub
		template = Constant.template;
	}

	public boolean doLogin(String email, String pwd) {
		String SELECT_BYEMAIL  = "select * from " + TABLE + " where m_email='" + email + "'";
		boolean loginResult = false;
		System.out.println("dao doLogin email : " + email);
		try {
			MemberDTO dto = template.queryForObject(SELECT_BYEMAIL, new BeanPropertyRowMapper<MemberDTO>(MemberDTO.class));
			if(dto.getM_email() != null)
				if(dto.getM_pwd().equals(pwd))
					loginResult = true;
		}catch(Exception e) {
			e.printStackTrace(); 
		}

		return loginResult;
	}

	public boolean doRegister(final MemberDTO member) {
		boolean registerResult = false;

		List<SqlParameter> parameters = Arrays.asList(
				new SqlParameter(Types.VARCHAR),new SqlParameter(Types.VARCHAR),
				new SqlParameter(Types.VARCHAR),new SqlParameter(Types.VARCHAR),
				new SqlParameter(Types.VARCHAR),
				new SqlOutParameter("out_register", Types.INTEGER));
		Map<String, Object> t = template.call(new CallableStatementCreator() {

			@Override
			public CallableStatement createCallableStatement(Connection con) throws SQLException {
				CallableStatement callableStatement = con.prepareCall("{call register_member(?,?,?,?,?,?)}");
				callableStatement.setString(1, member.getM_email());
				callableStatement.setString(2, member.getM_pwd());
				callableStatement.setString(3, member.getM_nickname());
				callableStatement.setString(4, member.getM_school());
				callableStatement.setString(5, member.getM_field());
				callableStatement.registerOutParameter(6, Types.INTEGER);
				return callableStatement;
			}
		}, parameters);
		if((Integer)t.get("out_register")!=0) registerResult = true;

		return registerResult;
	}

	public HashMap<Integer, MemberDTO> getMemberList(String loginUser) {
		// TODO Auto-generated method stub
		HashMap<Integer, MemberDTO> members = new HashMap<Integer, MemberDTO>();
		String GET_MEMBER_LIST_EXCEPT_ME = "select * from "+ TABLE + " where m_email!='" + loginUser + "'";
		
		try {
			ArrayList<MemberDTO> memberArr = (ArrayList<MemberDTO>) template.query(GET_MEMBER_LIST_EXCEPT_ME, 
						new BeanPropertyRowMapper<MemberDTO>(MemberDTO.class));
			int index=0;
			for(MemberDTO m : memberArr)
				members.put(index++, m);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return members;
	}

}
