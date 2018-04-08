package com.flowergarden.servlet;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public abstract class AbstractServlet extends HttpServlet {

    protected AutowireCapableBeanFactory ctx;

    @Override
    public void init() throws ServletException {
        super.init();
        WebApplicationContext context = WebApplicationContextUtils
                .getWebApplicationContext(getServletContext());
        ctx = context.getAutowireCapableBeanFactory();
        ctx.autowireBean(this);

    }
}
