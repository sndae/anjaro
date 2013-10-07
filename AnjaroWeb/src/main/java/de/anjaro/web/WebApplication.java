package de.anjaro.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import de.anjaro.web.page.WelcomePage;

public class WebApplication extends org.apache.wicket.protocol.http.WebApplication implements ApplicationContextAware {

	private static final Logger LOG = Logger.getLogger(WebApplication.class);

	public static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

	private ApplicationContext ctx;

	@Override
	public Class<? extends Page> getHomePage() {
		return WelcomePage.class;
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext)
			throws BeansException {
		this.ctx = applicationContext;

	}

	public ApplicationContext getApplicationContext() {
		return this.ctx;
	}

	@Override
	protected void init() {
		super.init();
		LOG.debug("init");
		this.getMarkupSettings().setDefaultMarkupEncoding("UTF-8");
		this.getComponentInstantiationListeners().add(new SpringComponentInjector(this, this.ctx));
	}

	@Override
	public Session newSession(Request request, Response response) {
		return new AnjaroSession(request);
	}
	
	



}
