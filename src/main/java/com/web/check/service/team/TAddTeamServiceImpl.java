package com.web.check.service.team;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.web.check.DAO.TeamDAO;
import com.web.check.DTO.TeamDTO;
import com.web.check.service.Service;

public class TAddTeamServiceImpl implements Service {

	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("loginUser");
		
		TeamDTO team = (TeamDTO)map.get("team");
		System.err.println(email);
		
		TeamDAO dao = new TeamDAO();
		
		int tid = dao.createNewTeam(team,email);
		if(tid == -1)	System.err.println("fail.");
		else {
			System.err.println("[Check] TAddTeamServiceImpl tid : " + tid);
			model.addAttribute("tid", tid);
		}
	}

}
