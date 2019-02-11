package com.web.check.service.member;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.web.check.DAO.MemberDAO;
import com.web.check.DTO.MemberDTO;
import com.web.check.service.Service;

public class MemberRegisterServiceImpl implements Service {

	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		Map<String, Object> map = model.asMap();
		HttpServletRequest request =(HttpServletRequest)map.get("request");
		
		MemberDTO member = new MemberDTO();
		
		String m_email = request.getParameter("m_email"); 	String m_pwd = request.getParameter("m_pwd");
		String m_nickname = request.getParameter("m_nickname"); String m_school = request.getParameter("m_school");
		String m_field = request.getParameter("m_field");
		
		member.setM_email(m_email); member.setM_pwd(m_pwd); member.setM_nickname(m_nickname);
		member.setM_school(m_school); member.setM_field(m_field);
		
		MemberDAO dao = new MemberDAO();
		boolean registerResult = dao.doRegister(member);
		
		model.addAttribute("registerResult", registerResult);
		
	}

}
