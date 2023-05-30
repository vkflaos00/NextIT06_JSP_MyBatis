package kr.or.nextit.free.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.nextit.code.service.CommCodeServiceImpl;
import kr.or.nextit.code.service.ICommCodeService;
import kr.or.nextit.code.vo.CodeVO;
import kr.or.nextit.member.vo.MemberVO;
import kr.or.nextit.servlet.NextITProcess;

public class FreeForm implements NextITProcess{

	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse resp) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("FreeForm process");
		
		MemberVO memberVO = (MemberVO) request.getSession().getAttribute("memberVO");
		if(memberVO == null){
			return "redirect:/login/login.do?msg=none";
		}
		
		ICommCodeService codeService = new CommCodeServiceImpl();
		List<CodeVO> categoryList = codeService.getCodeListByParent("BC00");

		request.setAttribute("categoryList", categoryList);

		return "/WEB-INF/views/free/freeForm.jsp";
	}

}
