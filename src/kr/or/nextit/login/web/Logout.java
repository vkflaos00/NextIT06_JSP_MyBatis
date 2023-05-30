package kr.or.nextit.login.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.nextit.servlet.NextITProcess;

public class Logout implements NextITProcess{

	@Override
	public String process(HttpServletRequest request, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		
		System.out.println("Logout process");
		
		/*session.removeAttribute("memberVO");
		response.sendRedirect(request.getContextPath()+"/login/login.jsp");
		*/
		request.getSession().removeAttribute("memberVO");
		
		return "redirect:/login/login.do";
	}

}
