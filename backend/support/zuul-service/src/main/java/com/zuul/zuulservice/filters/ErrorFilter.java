package com.zuul.zuulservice.filters;

import javax.servlet.http.HttpServletResponse;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class ErrorFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(PostFilter.class);

    @Override
    public int filterOrder() {
       
        return 1;
    }

    @Override
    public String filterType() {
        
        return "error";
    }

    @Override
    public Object run() throws ZuulException {
        
        HttpServletResponse response = RequestContext.getCurrentContext().getResponse();

        log.info("ErrorFilter" + String.format("response Status is %d", response.getStatus()));

        return null;
    }

    @Override
    public boolean shouldFilter() {
        // TODO Auto-generated method stub
        return true;
    }
    
    
}
