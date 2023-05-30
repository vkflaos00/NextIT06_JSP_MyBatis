package kr.or.nextit.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface NextITProcess {

	public String process(HttpServletRequest request , HttpServletResponse response) throws Exception;	
}
