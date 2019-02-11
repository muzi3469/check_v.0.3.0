package com.web.check.service.board;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.web.check.DAO.ListDAO;
import com.web.check.service.Service;

public class BoardInitServiceImpl implements Service {

	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		HttpSession session = request.getSession();
		
		System.out.println("[Check] BoardInitServImpl session : " + session.toString());
		
		String boardid = session.getAttribute("boardid").toString();
		System.out.println("BoardInitServImpl boardid : " + boardid);
		int bid = Integer.parseInt(boardid);
		
		ListDAO dao = new ListDAO();
		HashMap<String, HashMap> result = dao.initBoard(bid);
		
		model.addAttribute("result", result);
	}

}
