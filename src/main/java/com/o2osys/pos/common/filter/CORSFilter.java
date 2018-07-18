package com.o2osys.pos.common.filter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class CORSFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Value("${start.time}")
	String starttime;

	@Value("${end.time}")
	String endtime;

	@Autowired
	private MessageSource mMessageSource;

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		String nowtime = (new SimpleDateFormat("HHmmss").format(new Date()));
		int nowinttime = Integer.parseInt(nowtime);
		int startinttime = Integer.parseInt(starttime);
		int endinttime = Integer.parseInt(endtime);
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET ,PUT ,OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "x-auth-token,Content-Type,Accept");
		if (!request.getRequestURI().split("/")[1].equals("api-docs")) {
			if (nowinttime > startinttime && nowinttime < endinttime) {
				response.resetBuffer();
				response.setHeader("Content-Type", "text/html");
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				response.getOutputStream()
						.write(mMessageSource.getMessage("error.time", null, new Locale("ko")).getBytes("utf-8"));
				response.flushBuffer();

			} else {
				filterChain.doFilter(servletRequest, servletResponse);
			}
		} else {
			filterChain.doFilter(servletRequest, servletResponse);
		}

	}

	@Override
	public void destroy() {

	}
}