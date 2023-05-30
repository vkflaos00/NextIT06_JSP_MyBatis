package kr.or.nextit.member.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import kr.or.nextit.exception.BizDuplicateKeyException;
import kr.or.nextit.exception.BizNotEffectedException;
import kr.or.nextit.exception.DaoException;
import kr.or.nextit.member.service.IMemberService;
import kr.or.nextit.member.service.MemberServiceImpl;
import kr.or.nextit.member.vo.MemberVO;
import kr.or.nextit.servlet.NextITProcess;

public class MemberRegister implements NextITProcess {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("MemberRegister process");
		
		request.setCharacterEncoding("utf-8");
		
		MemberVO member = new MemberVO();
		BeanUtils.populate(member, request.getParameterMap());
		System.out.println("member.toString(): "+ member.toString());

		IMemberService memberService = new MemberServiceImpl();
		try{
			memberService.registerMember(member);
		}catch(BizDuplicateKeyException bde){
			bde.printStackTrace();
			request.setAttribute("bde", bde);
		}catch(BizNotEffectedException bne){
			bne.printStackTrace();
			request.setAttribute("bne", bne);
		}catch(Exception de){
			de.printStackTrace();
			request.setAttribute("de", de);
		}
		
		return "/WEB-INF/views/member/memberRegister.jsp";
	}

}
