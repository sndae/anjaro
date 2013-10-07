package de.anjaro.web.page;

import org.apache.wicket.markup.html.WebPage;

import de.anjaro.web.panel.NavigationPanel;

public class MainPage extends WebPage {

	private static final long serialVersionUID = 4509294908036997116L;

	public MainPage() {
		super();
		this.add(new NavigationPanel("navigationBar"));
	}

}
