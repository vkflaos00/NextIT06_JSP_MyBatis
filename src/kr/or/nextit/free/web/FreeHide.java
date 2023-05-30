package kr.or.nextit.free.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.nextit.exception.BizNotEffectedException;
import kr.or.nextit.exception.DaoException;
import kr.or.nextit.free.service.FreeBoardServiceImpl;
import kr.or.nextit.free.service.IFreeBoardService;
import kr.or.nextit.member.vo.MemberVO;
import kr.or.nextit.servlet.NextITProcess;

public class FreeHide implements NextITProcess{

	@Override
	public String process(HttpServletRequest request, HttpServletResponse resp) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("FreeHide process");
		
		MemberVO memberVO = (MemberVO) request.getSession().getAttribute("memberVO");
		if(memberVO == null){
			return "redirect:/login/login.do?msg=none";
		}
	
		String memId = request.getParameter("memId");
		String boNo = request.getParameter("boNo");

		IFreeBoardService freeBoardService = new FreeBoardServiceImpl();
		try{
			freeBoardService.hideBoard(memId, boNo);
		}catch(BizNotEffectedException bne){
			request.setAttribute("bne", bne);
			bne.printStackTrace();
		}catch(DaoException de){
			request.setAttribute("de", de);
			de.printStackTrace();
		}
		
		return "/WEB-INF/views/free/freeHide.jsp";
	}

}
