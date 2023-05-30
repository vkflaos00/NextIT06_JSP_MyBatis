package kr.or.nextit.free.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.nextit.code.service.CommCodeServiceImpl;
import kr.or.nextit.code.service.ICommCodeService;
import kr.or.nextit.code.vo.CodeVO;
import kr.or.nextit.exception.BizNotEffectedException;
import kr.or.nextit.exception.DaoException;
import kr.or.nextit.free.service.FreeBoardServiceImpl;
import kr.or.nextit.free.service.IFreeBoardService;
import kr.or.nextit.free.vo.FreeBoardVO;
import kr.or.nextit.member.vo.MemberVO;
import kr.or.nextit.servlet.NextITProcess;

public class FreeEdit implements NextITProcess{

	@Override
	public String process(HttpServletRequest request, HttpServletResponse resp) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("FreeEdit process");
		
		MemberVO memberVO = (MemberVO) request.getSession().getAttribute("memberVO");
		if(memberVO == null){
			return "redirect:/login/login.do?msg=none";
		}
		
		ICommCodeService codeService = new CommCodeServiceImpl();
		List<CodeVO> categoryList = codeService.getCodeListByParent("BC00");
		
		request.setAttribute("categoryList", categoryList);
		
		String boNo = request.getParameter("boNo");
		System.out.println("boNo : " + boNo);
		IFreeBoardService freeBoardService = new FreeBoardServiceImpl();
		
		try{
			FreeBoardVO freeBoard = freeBoardService.getBoard(boNo);
			System.out.println("freeBoard: "+ freeBoard.toString());
			request.setAttribute("freeBoard", freeBoard);
		}catch(BizNotEffectedException bne){
			request.setAttribute("bne", bne);
			bne.printStackTrace();
		}catch(DaoException de){
			request.setAttribute("de", de);
			de.printStackTrace();
		}
		
		
		return "/WEB-INF/views/free/freeEdit.jsp";
	}

}
