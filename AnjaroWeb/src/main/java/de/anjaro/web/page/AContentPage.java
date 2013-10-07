package de.anjaro.web.page;

import org.apache.wicket.markup.html.panel.Panel;

public abstract class AContentPage extends MainPage {

	private static final long serialVersionUID = -1376922289293186115L;

	public AContentPage() {
		super();
		this.add(this.getContentPanel("contentPanel"));
		this.add(this.getSidebarPanel("sidebarPanel"));
	}

	protected abstract Panel getSidebarPanel(String pId);

	protected abstract Panel getContentPanel(String pId);


}
