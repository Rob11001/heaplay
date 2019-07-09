package com.heaplay.control;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ServletFilter implements Filter {
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse resp = (HttpServletResponse) response;
		resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		request.setAttribute("error_title", "Pagina non trovata - 404");
		request.setAttribute("error", "La pagina \""+ ((HttpServletRequest)request).getRequestURL() + "\" non è stata trovata o non esiste");		
	}

	
}
