package kr.or.nextit.login.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.nextit.servlet.NextITProcess;

public class Login implements NextITProcess{

	@Override
	public String process(HttpServletRequest request, HttpServletResponse resp) {

		String msg = request.getParameter("msg");
		System.out.println("msg : "+ msg);
		
	
		request.setAttribute("msg", msg);

		Cookie[] cookies =  request.getCookies();
		if( cookies != null && cookies.length >0){
			for(int i=0; i< cookies.length ; i++){
				if(cookies[i].getName().equals("rememberMe")){
					System.out.println( cookies[i].getName() +" : "+ cookies[i].getValue());
					request.setAttribute("checkBox", "checked");
					request.setAttribute("memId", cookies[i].getValue());
				}
			}
		}
		
		return "/WEB-INF/views/login/login.jsp";
	}

 
}
