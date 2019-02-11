package com.web.check.service.card;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.ui.Model;

import com.web.check.DAO.CardDAO;
import com.web.check.DTO.CardDTO;
import com.web.check.service.Service;

public class CardInfoUpdateServiceImpl implements Service {

	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		Map<String, Object> map = model.asMap();
		
		ArrayList<CardDTO> cardArr = (ArrayList<CardDTO>) map.get("cardarr");
		CardDAO dao = new CardDAO();
		boolean result;
		if(dao.updateCardInfo(cardArr) == 1)
			result=true;
		else
			result=false;
		model.addAttribute("result", result);
		System.out.println("[Check] CardInfoUpdateServiceImpl result : "+ result);
	}

}
