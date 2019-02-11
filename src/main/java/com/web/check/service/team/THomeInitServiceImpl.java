package com.web.check.service.team;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.web.check.DAO.TeamDAO;
import com.web.check.DTO.TeamDTO;
import com.web.check.service.Service;

public class THomeInitServiceImpl implements Service {

	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("loginUser");
		
		TeamDAO dao = new TeamDAO();
		HashMap<String, HashMap> teams = dao.initHome(email);
		
		if(teams != null) {
			model.addAttribute("teams", teams);
			System.out.println(teams);
		} else System.err.println("[Check] THomeInitService fail...");
		
	}

}
