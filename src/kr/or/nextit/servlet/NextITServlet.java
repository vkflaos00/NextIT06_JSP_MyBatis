package kr.or.nextit.servlet;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import kr.or.nextit.login.web.Login;


public class NextITServlet extends HttpServlet{

	
	private Map<String, NextITProcess> npMap = new HashMap<String, NextITProcess>();
	
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		//super.init();
		
		String uriLocation = "/WEB-INF/classes/config/uri_mapping.properties";
		String fullPath = getServletContext().getRealPath(uriLocation);
		//System.out.println("fullPath: "+ fullPath);
		
		FileReader fr = null;
		Properties prop = null;
		try {
			fr = new FileReader(fullPath);
			prop = new Properties();
			prop.load(fr);
		}catch (Exception e) {
			e.printStackTrace();
		}
	 
		//System.out.println("path size : "+  prop.keySet().size());
		//System.out.println("all path: "+  prop.entrySet().toString());
		/*
		path size : 5
		all path: 
			[
				/login/loginCheck.do=kr.or.nextit.login.web.LoginCheck
				, /login/logout.do=kr.or.nextit.login.web.Logout
				, /home/home.do=kr.or.nextit.home.web.Home
				, /login/login.do=kr.or.nextit.login.web.Login
				, /login/join.do=kr.or.nextit.login.web.Join]
		*/
		
		
		Iterator<Object> keyIterator = prop.keySet().iterator();
		while (keyIterator.hasNext()) {
			String uri  = (String) keyIterator.next();
			//System.out.println("uri: "+ uri);
			
			String targetClassLocation = prop.getProperty(uri);
			//System.out.println("targetClassLocation: "+ targetClassLocation);
			
			try {
				Class<?> targetClass = Class.forName(targetClassLocation);
				
				 NextITProcess np  = (NextITProcess) targetClass.newInstance();
				 
				 npMap.put(uri, np);
				 System.out.println("uri : "+ uri + " , "
						 + np.getClass().getName() +" 을 Map에 담았습니다.");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.service(req, resp);
		
		System.out.println("NextITServlet service");
		
		
		//String uri = req.getRequestURI();
		//uri = uri.substring(req.getContextPath().length());
		//System.out.println("uri : "+ uri );
		
		/*if(uri.equals("/nextitLogin.do")) {
			System.out.println("로그인 요청이 정상적으로 들어왔습니다.");
			//로그인 페이지로 이동
			Login login = new Login();
			String viewPage = login.loginPage();
			RequestDispatcher는 클라이언트로부터 최초에 들어온 요청을 JSP/Servlet 내에서
			 	원하는 자원으로 요청을 넘기는(보내는) 역활을 수행하여 특정 자원에 처리를 요청하고
			 	결과를 얻어오는 기능을 수행하는 클래스입니다.
			RequestDispatcher rd = req.getRequestDispatcher(viewPage);
			rd.forward(req, resp);
		}*/
	
		String uri = req.getRequestURI();
		uri = uri.substring(req.getContextPath().length());
		System.out.println("client uri : "+ uri );
		
		NextITProcess np = npMap.get(uri);
		if(np == null) {
			resp.sendError(404);
		}else {
			String viewPage;
			try {
				viewPage = np.process(req, resp);
				System.out.println("viewPage:" + viewPage);
				
				//redirect:/home/home.do
				if(viewPage.startsWith("redirect:")) {
					System.out.println("redirect uri "+req.getContextPath() 
						+ viewPage.substring("redirect:".length()));	
					resp.sendRedirect(req.getContextPath()
							+ viewPage.substring("redirect:".length()));
				}else {
					RequestDispatcher rd = req.getRequestDispatcher(viewPage);
					rd.forward(req, resp);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
	}	
	
}
