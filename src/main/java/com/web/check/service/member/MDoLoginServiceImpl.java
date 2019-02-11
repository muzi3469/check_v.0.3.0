package com.web.check.service.member;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.springframework.ui.Model;

import com.web.check.DAO.MemberDAO;
import com.web.check.constant.Constant;
import com.web.check.service.Service;

public class MDoLoginServiceImpl implements Service{

	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		String email = request.getParameter("email");
		String pwd = request.getParameter("pwd");
		
		MemberDAO dao = new MemberDAO();
		boolean loginResult = dao.doLogin(email, pwd);
		model.addAttribute("loginResult", loginResult);
		if(loginResult) {
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", email);
			Constant.loginUser = email;
			System.err.println("login session email : " + (String)session.getAttribute("loginUser"));
		}
	}

}
