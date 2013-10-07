package de.anjaro.web.page;

import org.apache.wicket.markup.html.panel.Panel;

import de.anjaro.web.panel.MotorControlPanel;
import de.anjaro.web.panel.VideoControlPanel;

public class ControlPanelPage extends AContentPage {

	private static final long serialVersionUID = -1L;

	public ControlPanelPage() {
		super();
	}

	@Override
	protected Panel getSidebarPanel(final String pId) {
		return new MotorControlPanel(pId);
	}

	@Override
	protected Panel getContentPanel(final String pId) {
		return new VideoControlPanel(pId);
	}



}
