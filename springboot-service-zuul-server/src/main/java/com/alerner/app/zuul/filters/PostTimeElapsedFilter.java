package com.alerner.app.zuul.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;


@Component
public class PostTimeElapsedFilter extends ZuulFilter
{

	private static Logger log = LoggerFactory.getLogger(PostTimeElapsedFilter.class);
	
	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		
		log.info("Enter to Post Filter");
		Long initTime = (Long)request.getAttribute("initTime");
		Long finalTime = System.currentTimeMillis(); 
		Long elapsedTime = finalTime -initTime;
		
		log.info(String.format("Time elapsed in seconds %s", elapsedTime.doubleValue()/1000.00));
		log.info(String.format("Time elapsed in miliseconds %s", elapsedTime));
		
		return null;
	}

	@Override
	public String filterType() {
	
		return "post";
	}

	@Override
	public int filterOrder() {
		return 1;
	}
	
}
