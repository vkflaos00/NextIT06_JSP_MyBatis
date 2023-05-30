package kr.or.nextit.member.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.nextit.exception.BizNotEffectedException;
import kr.or.nextit.exception.DaoException;
import kr.or.nextit.member.service.IMemberService;
import kr.or.nextit.member.service.MemberServiceImpl;
import kr.or.nextit.member.vo.MemberVO;
import kr.or.nextit.servlet.NextITProcess;

public class MemberView implements NextITProcess {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("MemberView process");
		
		MemberVO memberVO =  (MemberVO)request.getSession().getAttribute("memberVO");
		if(memberVO == null){
			return "redirect:/login/login.do?msg=none";
		}
		
		String memId = request.getParameter("memId");
		IMemberService memberService = new MemberServiceImpl();
		try{
			MemberVO member = memberService.getMember(memId);
			member.setMemPass("");  
			request.setAttribute("member", member);
		}catch(BizNotEffectedException bne){
			request.setAttribute("bne", bne);
			bne.printStackTrace();
		}catch(Exception de){
			request.setAttribute("de", de);
			de.printStackTrace();
		}
		return "/WEB-INF/views/member/memberView.jsp";
	}

}
