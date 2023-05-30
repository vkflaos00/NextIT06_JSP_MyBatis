package kr.or.nextit.login.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.nextit.servlet.NextITProcess;

public class Login implements NextITProcess{

	@Override
	public String process(HttpServletRequest request, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		
		String msg = request.getParameter("msg");
		System.out.println("msg : "+ msg);
		
		/*if(msg !=null && msg.equals("null")){
			out.print("<script>");
			out.print("alert('ID 또는 PASSWORD를 입력해주세요')");
			out.print("</script>");
		}else if(msg !=null && msg.equals("fail")){
			out.print("<script>");
			out.print("alert('로그인 실패하였습니다. ID 또는 PASSWORD를 확인해주세요.')");
			out.print("</script>");
		}else if(msg !=null && msg.equals("none")){
			out.print("<script>");
			out.print("alert('로그인 하셔야 이용 가능합니다.')");
			out.print("</script>");
		}*/
		request.setAttribute("msg", msg);

		Cookie[] cookies =  request.getCookies();
		if( cookies != null && cookies.length >0){
			for(int i=0; i< cookies.length ; i++){
				if(cookies[i].getName().equals("rememberMe")){
					System.out.println( cookies[i].getName() +" : "+ cookies[i].getValue());
					request.setAttribute("checkBox", "checked");
					request.setAttribute("memId", cookies[i].getValue());
				}
			}
		}
		
		return "/WEB-INF/views/login/login.jsp";
	}

 
}
