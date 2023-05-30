package kr.or.nextit.member.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import kr.or.nextit.exception.BizNotEffectedException;
import kr.or.nextit.exception.BizNotFoundException;
import kr.or.nextit.exception.BizPasswordNotMatchedException;
import kr.or.nextit.exception.DaoException;
import kr.or.nextit.member.service.IMemberService;
import kr.or.nextit.member.service.MemberServiceImpl;
import kr.or.nextit.member.vo.MemberVO;
import kr.or.nextit.servlet.NextITProcess;

public class MemberDelete implements NextITProcess{

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("MemberDelete process");

		MemberVO memberVO =  (MemberVO)request.getSession().getAttribute("memberVO");
		if(memberVO == null){
			return "redirect:/login/login.do?msg=none";
		}
		
		request.setCharacterEncoding("UTF-8");
		
		MemberVO member = new MemberVO();
		BeanUtils.populate(member, request.getParameterMap());
		
		IMemberService memberService = new MemberServiceImpl();
		try{
			memberService.removeMember(member);
			HttpSession session = request.getSession();
			session.removeAttribute("memberVO");
		}catch(BizNotFoundException bnf){
			request.setAttribute("bnf", bnf);
			bnf.printStackTrace();
		}catch(BizPasswordNotMatchedException bpn){
			request.setAttribute("bpn", bpn);
			bpn.printStackTrace();
		}catch(BizNotEffectedException bne){
			request.setAttribute("bne", bne);
			bne.printStackTrace();
		}catch(Exception de){
			request.setAttribute("de", de);
			de.printStackTrace();
		}

		return "/WEB-INF/views/member/memberDelete.jsp";
	}

}
