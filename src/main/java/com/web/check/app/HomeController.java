package com.web.check.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.check.DTO.BoardDTO;
import com.web.check.DTO.CardDTO;
import com.web.check.DTO.ListDTO;
import com.web.check.DTO.MemberDTO;
import com.web.check.DTO.TeamDTO;
import com.web.check.DTO.TeamMemberDTO;
import com.web.check.constant.Constant;
import com.web.check.service.Service;
import com.web.check.service.board.BAddBoardServiceImpl;
import com.web.check.service.member.MemberListGetServiceImpl;
import com.web.check.service.team.InviteTeamMemberServiceImpl;
import com.web.check.service.team.TAddTeamServiceImpl;
import com.web.check.service.team.THomeInitServiceImpl;

@Controller
public class HomeController {
	
	private Service service;
	
	@RequestMapping(value = "/home")
	public String home(Model model, HttpServletRequest request) {
		System.err.println("home url connect start.");
		
		HttpSession session = request.getSession();
		if(session.getAttribute("boardid")!=null) {
			session.removeAttribute("boardid");
			Constant.bid = null;
		}
		
		return "check/home";
	}
	
	@RequestMapping(value="/createTeamAjax",method=RequestMethod.POST)
	@ResponseBody
	public String createTeamAjax(Model model,HttpServletRequest request, @RequestBody TeamDTO team) {
		model.addAttribute("request", request);
		model.addAttribute("team", team);
		service = new TAddTeamServiceImpl();
		service.execute(model);
		
		Map<String, Object> map = model.asMap();
		int tid = (Integer)map.get("tid");
		
		return tid+"";
	}
	
	@RequestMapping(value="/initHome")
	@ResponseBody
	public HashMap<String, HashMap> initHome(Model model,HttpServletRequest request) {
		model.addAttribute("request", request);
		
		service = new THomeInitServiceImpl();
		service.execute(model);
		
		Map<String, Object> map = model.asMap();
		HashMap<String, HashMap> teams = (HashMap<String, HashMap>)map.get("teams");
		
		return teams;
	}
	
	
	@RequestMapping(value="/makeboard",method=RequestMethod.POST)
	@ResponseBody
	public String makeboard(Model model,HttpServletRequest request, @RequestBody BoardDTO board) {
		model.addAttribute("request", request);
		model.addAttribute("board", board);
		
		System.err.println("[Check] makeboard ctl / title : " + board.getB_title());
		
		service = new BAddBoardServiceImpl();
		service.execute(model);
		
		Map<String, Object> map = model.asMap();
		int bid = (Integer)map.get("bid");
		
		return bid+"";
	}
	
	@RequestMapping("/invitehome")
	public String home() {
		System.out.println("invite 실행");
		return "/check/invitehome";
	}


	@RequestMapping(value="/memberlist" , produces = "application/json")
	@ResponseBody
	public HashMap<Integer, MemberDTO> Memberlist(Model model ,HttpServletRequest request ) {
		System.out.println("[Check] memberlist 실행");
		model.addAttribute("request", request);
		
		service = new MemberListGetServiceImpl();
		service.execute(model);
		
		Map<String, Object> map = model.asMap();
		HashMap<Integer, MemberDTO> memberarr = (HashMap<Integer, MemberDTO>)map.get("list");

		System.out.println(memberarr);

		return memberarr;

	}
	
	
	@RequestMapping(value="/inviteTeamMember",method=RequestMethod.POST)
	@ResponseBody
	public boolean inviteTeamMember(Model model,HttpServletRequest request, @RequestBody ArrayList<TeamMemberDTO> tmArr) {
		
		ObjectMapper mapper = new ObjectMapper();
		ArrayList<TeamMemberDTO> mapperTmarr = mapper.convertValue(tmArr, new TypeReference<ArrayList<TeamMemberDTO>>(){});
		
		model.addAttribute("request", request);
		model.addAttribute("teammember", mapperTmarr);
		
		service = new InviteTeamMemberServiceImpl();
		service.execute(model);
		
		Map<String, Object> map = model.asMap();
		boolean result = (Boolean)map.get("result");
		
		return result;
	}
	
}



