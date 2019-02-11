package com.web.check.service.member;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.web.check.DAO.MemberDAO;
import com.web.check.service.Service;

public class MemberListGetServiceImpl implements Service {

	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		HttpSession session = request.getSession();
		
		String loginUser = (String)session.getAttribute("loginUser");
		System.err.println("[Check] MemberListGetServiceImpl loginUser email : " + loginUser);
		
		MemberDAO dao = new MemberDAO();
		model.addAttribute("list", dao.getMemberList(loginUser));

	}

}
