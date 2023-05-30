package kr.or.nextit.free.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import kr.or.nextit.exception.BizNotEffectedException;
import kr.or.nextit.exception.DaoException;
import kr.or.nextit.free.service.FreeBoardServiceImpl;
import kr.or.nextit.free.service.IFreeBoardService;
import kr.or.nextit.free.vo.FreeBoardSearchVO;
import kr.or.nextit.free.vo.FreeBoardVO;
import kr.or.nextit.member.vo.MemberVO;
import kr.or.nextit.servlet.NextITProcess;

public class FreeView implements NextITProcess{

	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse resp) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("FreeView process");
		
		MemberVO memberVO = (MemberVO) request.getSession().getAttribute("memberVO");
		if(memberVO == null){
			return "redirect:/login/login.do?msg=none";
		}
		
		FreeBoardSearchVO searchVO = new FreeBoardSearchVO();
		BeanUtils.populate(searchVO, request.getParameterMap());
		
		System.out.println("searchType: "+searchVO.getSearchType() + " , searchWord: "+searchVO.getSearchWord()+ " , searchCategory: "+searchVO.getSearchCategory()+ " , curPage: "+searchVO.getCurPage()+ " , rowSizePerPage: "+searchVO.getRowSizePerPage());
		String boNo = request.getParameter("boNo");
		System.out.println("boNo : " + boNo);
		IFreeBoardService freeBoardService = new FreeBoardServiceImpl();
		
		try{
			FreeBoardVO freeBoard = freeBoardService.getBoard(boNo);
			freeBoardService.increaseHit(boNo);
			
			request.setAttribute("freeBoard", freeBoard);
			request.setAttribute("searchVO", searchVO);
			
		}catch(BizNotEffectedException bne){
			request.setAttribute("bne", bne);
			bne.printStackTrace();
		}catch(DaoException de){
			request.setAttribute("de", de);
			de.printStackTrace();
		}
		
		return "/WEB-INF/views/free/freeView.jsp";
	}

}
