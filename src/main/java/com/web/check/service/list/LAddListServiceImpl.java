package com.web.check.service.list;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.web.check.DAO.ListDAO;
import com.web.check.DTO.ListDTO;
import com.web.check.service.Service;

public class LAddListServiceImpl implements Service{

	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		Map<String, Object> map = model.asMap();
		
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		HttpSession session = request.getSession();
		
		int bid = (Integer) session.getAttribute("boardid");
		
		ListDTO listdto = (ListDTO)map.get("listdto");
		listdto.setB_bid(bid);
		ListDAO dao = new ListDAO();
		HashMap<String, Integer> insertResult = dao.insertList(listdto);
		model.addAttribute("insertResult", insertResult);
	}

}
