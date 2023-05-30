package kr.or.nextit.member.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.nextit.common.vo.RoleInfoVO;
import kr.or.nextit.exception.BizNotEffectedException;
import kr.or.nextit.exception.DaoException;
import kr.or.nextit.member.service.IMemberService;
import kr.or.nextit.member.service.MemberServiceImpl;
import kr.or.nextit.member.vo.MemberVO;
import kr.or.nextit.servlet.NextITProcess;

public class MemberRole implements NextITProcess{

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("MemberRole process");
		
		MemberVO memberVO =  (MemberVO)request.getSession().getAttribute("memberVO");
		if(memberVO == null){
			return "redirect:/login/login.do?msg=none";
		}
		
		request.setCharacterEncoding("UTF-8");
		String memId = request.getParameter("memId");
		System.out.println("memId : "+ memId);

		IMemberService memberService = new MemberServiceImpl();
		try{
		 	MemberVO member	= memberService.getMemberRole(memId);
		 	request.setAttribute("member", member);
		 	List<RoleInfoVO> roleInfoList = memberService.getRoleInfo();
		 	request.setAttribute("roleInfoList", roleInfoList);
		}catch(BizNotEffectedException bne){
			bne.printStackTrace();
			request.setAttribute("bne", bne);
		}catch(Exception de){
			de.printStackTrace();
			request.setAttribute("de", de);
		}
		return "/WEB-INF/views/member/memberRole.jsp";
	}

}
