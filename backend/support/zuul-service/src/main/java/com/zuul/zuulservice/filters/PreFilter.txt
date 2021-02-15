package com.zuul.zuulservice.filters;

import javax.servlet.http.HttpServletResponse;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PreFilter extends ZuulFilter {
    private static Logger log = LoggerFactory.getLogger(PreFilter.class);

    @Override
    public int filterOrder() {
        // TODO Auto-generated method stub
        return 1;
    }

    @Override
    public String filterType() {
        // TODO Auto-generated method stub
        return "pre";
    }

    @Override
    public Object run() throws ZuulException {
        // TODO Auto-generated method stub
        HttpServletResponse response = RequestContext.getCurrentContext().getResponse();

        log.info("PreFilter" + String.format("response Status is %d", response.getStatus()));
        return null;
    }

    @Override
    public boolean shouldFilter() {
        // TODO Auto-generated method stub
        return false;
    }
    
    
}
