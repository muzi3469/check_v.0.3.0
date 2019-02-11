package com.web.check.app;

import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.web.check.constant.Constant;
import com.web.check.service.MailService;
import com.web.check.service.Service;
import com.web.check.service.member.MDoLoginServiceImpl;
import com.web.check.service.member.MailServiceImpl;
import com.web.check.service.member.MemberRegisterServiceImpl;

@Controller
public class MemberController {

	private Service service;
	private MailService mailService;
	private GenericXmlApplicationContext context;
	
	public MemberController() {
		// TODO Auto-generated constructor stub
		String configLocation = "classpath:servlet_context.xml";
		context = new GenericXmlApplicationContext(configLocation);
	}

	@RequestMapping(value="login_view")
	public String login_view(Model model,HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(session.getAttribute("loginUser")==null)
			return "check/login";
		else
			return "redirect:home";
	}

	@RequestMapping(value="doLogin", method=RequestMethod.POST)
	public String doLogin(Model model, HttpServletRequest request) {

		model.addAttribute("request", request);

		service = new MDoLoginServiceImpl();
		service.execute(model);

		Map<String, Object> map = model.asMap();
		boolean loginResult = (Boolean)map.get("loginResult");
		map.remove("loginResult");
		
		if(loginResult)
			return "redirect:home";
		else
			return "redirect:login_view";
	}


	@RequestMapping(value="homeLogout")
	public String homeLogout(Model model, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		System.err.println("logout session email : " + (String)session.getAttribute("loginUser"));
		if(session.getAttribute("loginUser")!=null) {
			session.removeAttribute("loginUser");
			Constant.loginUser = null;
		}

		return "redirect:login_view";
	}
	
	@RequestMapping(value="register_view")
	public String register_view(Model model,HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(session.getAttribute("loginUser")==null)
			return "check/register_view";
		else
			return "redirect:home";
	}
	
	@RequestMapping(value="register", method=RequestMethod.POST)
	public String register(Model model, HttpServletRequest request) {
		
		model.addAttribute("request", request);
		
		service = new MemberRegisterServiceImpl();
		service.execute(model);
		
		Map<String, Object> map = model.asMap();
		Boolean result = (Boolean)map.get("registerResult");
		
		if(result)
			return "redirect:login_view";
		else
			return "redirect:register_view";
	}
	
	@RequestMapping(value = "/sendMail/auth2", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String sendMailAuth2(HttpSession session, @RequestBody String email) {
    	
    
    	System.out.println("email:"+email);
    	
    	mailService = context.getBean("mailService",MailServiceImpl.class);
    	
        int ran = new Random().nextInt(100000) + 10000; // 10000 ~ 99999
        String joinCode = String.valueOf(ran);
        session.setAttribute("joinCode", joinCode);
 
        String subject = "[Check] 회원가입 인증 코드 발급 안내 입니다.";
        StringBuilder sb = new StringBuilder();
        sb.append("귀하의 인증 코드는 " + joinCode + " 입니다.");
        mailService.send(subject, sb.toString(), "checkproject0714@gmail.com", email, null);
        return joinCode;
        
    }
}







