package de.anjaro.web.page;

import org.apache.wicket.markup.html.panel.Panel;

import de.anjaro.web.panel.BlogPanel;
import de.anjaro.web.panel.BlogSidebarPanel;


public class WelcomePage extends AContentPage {

	private static final long serialVersionUID = -6853818328240826146L;

	public WelcomePage() {
		super();
	}

	@Override
	protected Panel getSidebarPanel(final String pId) {
		return new BlogSidebarPanel(pId);
	}


	@Override
	protected Panel getContentPanel(final String pId) {
		return new BlogPanel(pId);
	}





}
