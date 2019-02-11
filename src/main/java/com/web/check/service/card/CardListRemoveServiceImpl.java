package com.web.check.service.card;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.ui.Model;

import com.web.check.DAO.CardDAO;
import com.web.check.DTO.CardDTO;
import com.web.check.service.Service;

public class CardListRemoveServiceImpl implements Service {

	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		Map<String, Object> map = model.asMap();
		ArrayList<CardDTO> cardarr = (ArrayList<CardDTO>) map.get("cardarr");
		
		System.out.println("[Check] CardListRemoveServiceImpl cardarr : " + cardarr);
		
		CardDAO dao = new CardDAO();
		boolean result = dao.removeCard(cardarr);
		
		model.addAttribute("result", result);
	}

}
