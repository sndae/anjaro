package de.anjaro.web.page;

import org.apache.wicket.markup.html.panel.Panel;

import de.anjaro.web.panel.BlogSidebarPanel;
import de.anjaro.web.panel.SettingsPanel;

public class SettingsPage extends AContentPage {

	@Override
	protected Panel getSidebarPanel(String pId) {
		return new BlogSidebarPanel(pId);
	}

	@Override
	protected Panel getContentPanel(String pId) {
		return new SettingsPanel(pId);
	}

}
