package com.web.check.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.check.DTO.BoardDTO;
import com.web.check.DTO.BoardUserDTO;
import com.web.check.DTO.CardDTO;
import com.web.check.DTO.ListDTO;
import com.web.check.constant.Constant;
import com.web.check.service.Service;
import com.web.check.service.board.BoardInitServiceImpl;
import com.web.check.service.card.CAddCardServiceImpl;
import com.web.check.service.card.CardInfoUpdateServiceImpl;
import com.web.check.service.card.CardListRemoveServiceImpl;
import com.web.check.service.list.BoardListRemoveServiceImpl;
import com.web.check.service.list.LAddListServiceImpl;
import com.web.check.service.list.ListTitleModifyServiceImpl;


@Controller
public class BoardController {
	
	private Service service;
	private GenericXmlApplicationContext context;

	public BoardController() {
		System.err.println("BoardController 생성자 실행, Controller 실행 되었음.");
		System.out.println("[Check] ip 주소 확인할 것. localhost인지 ip인지");
		String configLocation = "classpath:servlet_context.xml";
		context = new GenericXmlApplicationContext(configLocation);
		Constant.template = context.getBean("template", JdbcTemplate.class);
		Constant.bid = null; Constant.loginUser=null;
	}

	@RequestMapping(value = "/board_main")
	public String boardMainGet(Model model, HttpServletRequest request) {
		System.err.println("board_main_get");

		return "check/board";
	}
	@RequestMapping(value = "/board_main",method=RequestMethod.POST)
	public String boardMainPost(Model model, HttpServletRequest request) {
		System.err.println("board_main_post");

		int boardid = Integer.parseInt(request.getParameter("bid"));
		//		String boardtitle = request.getParameter("btitle");
		
		System.out.println("[Check] boardMainPost boardid : " + boardid);
		
		HttpSession session = request.getSession();

		session.setAttribute("boardid", boardid);
		
		Constant.bid =  boardid;
		
		return "check/board";
	}

	@RequestMapping(value = "/makelist", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Integer> makelist(Model model,HttpServletRequest request, @RequestBody ListDTO list) {
		System.err.println("ListDTO title : " + list.getL_title());

		model.addAttribute("request", request);
		model.addAttribute("listdto", list);

		service = new LAddListServiceImpl();
		service.execute(model);

		Map<String, Object> map = model.asMap();
		HashMap<String, Integer> addListResult = (HashMap<String, Integer>)map.get("insertResult");

		return addListResult;
	}

	@RequestMapping(value = "/makecard", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Integer> makecard(Model model,HttpServletRequest request, @RequestBody CardDTO card) {
		System.err.println("card lid : " + card.getL_lid());
		Object obj = card.getL_lid();
		if(obj instanceof Integer)
			System.out.println("card lid type : integer");

		model.addAttribute("request", request);
		model.addAttribute("card", card);

		service = new CAddCardServiceImpl();
		service.execute(model);

		Map<String, Object> map = model.asMap();
		HashMap<String, Integer> result = (HashMap<String, Integer>)map.get("result");

		System.out.println("makecard result : " + result);

		return result;
	}

	@RequestMapping(value="/initBoard")
	@ResponseBody
	public HashMap<String, HashMap> initBoard(Model model, HttpServletRequest request) {
		System.err.println("[Check] initBoard");

		model.addAttribute("request", request);

		service = new BoardInitServiceImpl();
		service.execute(model);

		Map<String, Object> map = model.asMap();
		HashMap<String, HashMap> result = (HashMap<String, HashMap>) map.get("result");

		return result;
	}
	
	@RequestMapping(value = "/updateListCard", method = RequestMethod.POST)
	@ResponseBody
	public boolean updateListCard(Model model,HttpServletRequest request, 
									@RequestBody ArrayList<CardDTO> cardarr) {
		System.out.println("[Check] updateListCard cardArr : "+cardarr);
		
		ObjectMapper mapper = new ObjectMapper();
		ArrayList<CardDTO> mapperCardarr = mapper.convertValue(cardarr, new TypeReference<ArrayList<CardDTO>>(){});
		
		model.addAttribute("request", request);
		model.addAttribute("cardarr", mapperCardarr);
		
		service = new CardInfoUpdateServiceImpl();
		service.execute(model);
		
		Map<String, Object> map = model.asMap();
		boolean result = (Boolean)map.get("result");

		return result;
	}
	
	
	@RequestMapping(value = "/removeList", method = RequestMethod.POST)
	@ResponseBody
	public boolean removeList(Model model,HttpServletRequest request, @RequestBody ArrayList<ListDTO> listarr) {
		System.out.println("[Check] removeList listarr : "+listarr);
		
		ObjectMapper mapper = new ObjectMapper();
		ArrayList<CardDTO> mapperListarr = mapper.convertValue(listarr, new TypeReference<ArrayList<ListDTO>>(){});
		
		model.addAttribute("request", request);
		model.addAttribute("listarr", mapperListarr);
		
		service = new BoardListRemoveServiceImpl();
		service.execute(model);

		Map<String, Object> map = model.asMap();
		boolean result = (Boolean)map.get("result");

		return result;
	}
	
	@RequestMapping(value = "/removeCard")
	@ResponseBody
	public boolean removeCard(Model model,HttpServletRequest request, 
								@RequestBody ArrayList<CardDTO> cardarr) {
		System.out.println("[Check] updateBoardList listarr : "+cardarr);
		
		ObjectMapper mapper = new ObjectMapper();
		ArrayList<CardDTO> mapperCardtarr = mapper.convertValue(cardarr, new TypeReference<ArrayList<CardDTO>>(){});
		
		model.addAttribute("request", request);
		model.addAttribute("cardarr", mapperCardtarr);
		
		service = new CardListRemoveServiceImpl();
		service.execute(model);
		
		Map<String, Object> map = model.asMap();
		boolean result = (Boolean)map.get("result");

		return result;
	}
	
	@RequestMapping(value = "/modifyListTitle", method=RequestMethod.POST)
	@ResponseBody
	public boolean modifyListTitle(Model model,HttpServletRequest request, 	@RequestBody ListDTO list) {
		System.out.println("[Check] modifyListTitle list : "+list);
		
		model.addAttribute("request", request);
		model.addAttribute("list", list);
		
		service = new ListTitleModifyServiceImpl();
		service.execute(model);
		
		Map<String, Object> map = model.asMap();
		boolean result = (Boolean)map.get("result");
		
		return result;
	}
}














