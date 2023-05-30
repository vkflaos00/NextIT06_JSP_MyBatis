package kr.or.nextit.login.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.nextit.code.service.CommCodeServiceImpl;
import kr.or.nextit.code.service.ICommCodeService;
import kr.or.nextit.code.vo.CodeVO;
import kr.or.nextit.servlet.NextITProcess;

public class Join implements NextITProcess {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		
		System.out.println("Join process");
		
		ICommCodeService codeService = new CommCodeServiceImpl();
		List<CodeVO> jobList = codeService.getCodeListByParent("JB00");
		request.setAttribute("jobList", jobList);

		List<CodeVO> hobbyList = codeService.getCodeListByParent("HB00");
		request.setAttribute("hobbyList", hobbyList);

		return "/WEB-INF/views/login/join.jsp";
	}

}
