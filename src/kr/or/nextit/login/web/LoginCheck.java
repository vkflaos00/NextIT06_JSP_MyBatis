package kr.or.nextit.login.web;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import kr.or.nextit.member.service.IMemberService;
import kr.or.nextit.member.service.MemberServiceImpl;
import kr.or.nextit.member.vo.MemberVO;
import kr.or.nextit.servlet.NextITProcess;

public class LoginCheck implements NextITProcess{

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		MemberVO member = new MemberVO();
		BeanUtils.populate(member, request.getParameterMap());
		
		IMemberService memberService = new MemberServiceImpl();
		try{
			boolean loginCheck = memberService.loginCheck(member, request, response);
			
			if(loginCheck) {
				return "redirect:/home/home.do";
			}else {
				return "redirect:/login/login.do?msg=fail";
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return "redirect:/login/login.do?msg=error";
		}
		//return null;
	}

	 

}
