package com.web.check.service.team;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.ui.Model;

import com.web.check.DAO.TeamDAO;
import com.web.check.DTO.TeamMemberDTO;
import com.web.check.service.Service;

public class InviteTeamMemberServiceImpl implements Service {

	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		Map<String, Object> map = model.asMap();
		ArrayList<TeamMemberDTO> tmArr = (ArrayList<TeamMemberDTO>) map.get("teammember");
		
		TeamDAO dao = new TeamDAO();
		boolean result = dao.insertTeamMember(tmArr);
		
		model.addAttribute("result", result);
	}

}
