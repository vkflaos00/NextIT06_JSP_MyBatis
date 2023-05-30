package kr.or.nextit.member.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.nextit.code.service.CommCodeServiceImpl;
import kr.or.nextit.code.service.ICommCodeService;
import kr.or.nextit.code.vo.CodeVO;
import kr.or.nextit.exception.BizNotEffectedException;
import kr.or.nextit.exception.DaoException;
import kr.or.nextit.member.service.IMemberService;
import kr.or.nextit.member.service.MemberServiceImpl;
import kr.or.nextit.member.vo.MemberVO;
import kr.or.nextit.servlet.NextITProcess;

public class MemberEdit implements NextITProcess{

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("MemberEdit process");
		
		MemberVO memberVO =  (MemberVO)request.getSession().getAttribute("memberVO");
		if(memberVO == null){
			return "redirect:/login/login.do?msg=none";
		}
		
		ICommCodeService codeService = new CommCodeServiceImpl();
		List<CodeVO> jobList = codeService.getCodeListByParent("JB00");
		
		request.setAttribute("jobList", jobList);
		
		List<CodeVO> hobbyList = codeService.getCodeListByParent("HB00");
		request.setAttribute("hobbyList", hobbyList);


		String memId = request.getParameter("memId");
		System.out.println("(memberEdit) memId :"+ memId );
		
		IMemberService memberService = new MemberServiceImpl();
		try{
			MemberVO member = memberService.getMember(memId);
			member.setMemPass("");
			request.setAttribute("member", member);
		}catch(BizNotEffectedException bne){
			request.setAttribute("bne", bne);
			bne.printStackTrace();
		}catch(DaoException de){
			request.setAttribute("de", de);
			de.printStackTrace();
		}
		
		return "/WEB-INF/views/member/memberEdit.jsp";
	}

}
