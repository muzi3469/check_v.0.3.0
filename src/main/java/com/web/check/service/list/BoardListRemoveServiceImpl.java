package com.web.check.service.list;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.web.check.DAO.ListDAO;
import com.web.check.DTO.ListDTO;
import com.web.check.service.Service;

public class BoardListRemoveServiceImpl implements Service {

	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		Map<String, Object> map = model.asMap();
		ArrayList<ListDTO> listarr = (ArrayList<ListDTO>) map.get("listarr");
		
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		HttpSession session = request.getSession();
		
		int bid = Integer.parseInt(session.getAttribute("boardid").toString());
		
		ListDAO dao = new ListDAO();
		boolean result = dao.removeList(listarr,bid);
		model.addAttribute("result",result);
	}

}
