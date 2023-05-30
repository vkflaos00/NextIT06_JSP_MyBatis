package kr.or.nextit.member.web;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.nextit.exception.BizNotEffectedException;
import kr.or.nextit.exception.DaoException;
import kr.or.nextit.member.service.IMemberService;
import kr.or.nextit.member.service.MemberServiceImpl;
import kr.or.nextit.member.vo.MemberVO;
import kr.or.nextit.servlet.NextITProcess;

public class MemberRoleUpdate implements NextITProcess{

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("MemberRoleUpdate process");
		
		MemberVO memberVO =  (MemberVO)request.getSession().getAttribute("memberVO");
		if(memberVO == null){
			return "redirect:/login/login.do?msg=none";
		}
		 
		request.setCharacterEncoding("utf-8");
		String memId = request.getParameter("memId");
		String[] roles = request.getParameterValues("userRole");/*userRole 배열로 전달 되어짐   */
		System.out.println( Arrays.toString(roles));
		IMemberService memberService = new MemberServiceImpl();
	 	try{
			memberService.updateUserRole(memId, roles);
		}catch(BizNotEffectedException bne){
			request.setAttribute("bne", bne);
			bne.printStackTrace();
		}catch(Exception de){
			request.setAttribute("de", de);
			de.printStackTrace();
		}
		return "/WEB-INF/views/member/memberRoleUpdate.jsp";
	}

}
