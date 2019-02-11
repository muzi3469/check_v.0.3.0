package com.web.check.service.list;

import java.util.Map;

import org.springframework.ui.Model;

import com.web.check.DAO.ListDAO;
import com.web.check.DTO.ListDTO;
import com.web.check.service.Service;

public class ListTitleModifyServiceImpl implements Service {

	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		Map<String, Object> map = model.asMap();
		ListDTO list = (ListDTO) map.get("list");
		
		ListDAO dao = new ListDAO();
		boolean result = dao.modifyListTitle(list); 
		
		model.addAttribute("result", result);
	}

}
