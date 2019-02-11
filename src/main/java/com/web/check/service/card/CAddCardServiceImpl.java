package com.web.check.service.card;

import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.Model;

import com.web.check.DAO.CardDAO;
import com.web.check.DTO.CardDTO;
import com.web.check.service.Service;

public class CAddCardServiceImpl implements Service {

	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		Map<String, Object> map = model.asMap();
		
		CardDTO card = (CardDTO)map.get("card");
		
		CardDAO dao = new CardDAO();
		HashMap<String, Integer> result = dao.insertCard(card);
		System.out.println("CAddCardServ result : " + result);
		model.addAttribute("result", result);
	}

}
