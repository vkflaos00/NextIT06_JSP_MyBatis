package kr.or.nextit.free.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import kr.or.nextit.exception.BizNotEffectedException;
import kr.or.nextit.exception.BizNotFoundException;
import kr.or.nextit.exception.BizPasswordNotMatchedException;
import kr.or.nextit.exception.DaoException;
import kr.or.nextit.free.service.FreeBoardServiceImpl;
import kr.or.nextit.free.service.IFreeBoardService;
import kr.or.nextit.free.vo.FreeBoardVO;
import kr.or.nextit.member.vo.MemberVO;
import kr.or.nextit.servlet.NextITProcess;

public class FreeDelete implements NextITProcess{


	@Override
	public String process(HttpServletRequest request, HttpServletResponse resp) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("FreeDelete process");
		
		MemberVO memberVO = (MemberVO) request.getSession().getAttribute("memberVO");
		if(memberVO == null){
			return "redirect:/login/login.do?msg=none";
		}
		
		FreeBoardVO freeBoard =new FreeBoardVO();
		BeanUtils.populate(freeBoard, request.getParameterMap());
		
		IFreeBoardService freeBoardService = new FreeBoardServiceImpl();
		try{
			freeBoardService.deleteBoard(freeBoard);
		}catch(BizNotEffectedException | BizNotFoundException bne){
			request.setAttribute("bne", bne);
			bne.printStackTrace();
		}catch(BizPasswordNotMatchedException bpn){
			request.setAttribute("bpn", bpn);
			bpn.printStackTrace();
		}catch(DaoException de){
			request.setAttribute("de", de);
			de.printStackTrace();
		}
		
		
		return "/WEB-INF/views/free/freeDelete.jsp";
	}

}
