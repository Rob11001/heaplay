package com.heaplay.control;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


public class AuthenticationFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//		HttpServletRequest httpRequest = (HttpServletRequest) request;
//		HttpSession session = httpRequest.getSession();
//		UserBean userBean = (UserBean) session.getAttribute("user");
//		//Probabilmente non si fa così
//		if(userBean == null) {
//			RequestDispatcher rd = httpRequest.getRequestDispatcher("/login.jsp");
//			rd.forward(httpRequest, response);
//		}
//		else
//			chain.doFilter(request, response);
		chain.doFilter(request,response);
	}
}
