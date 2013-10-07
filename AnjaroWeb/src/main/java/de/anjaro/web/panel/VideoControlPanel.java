package de.anjaro.web.panel;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;

import de.anjaro.model.Settings;
import de.anjaro.web.AnjaroSession;

public class VideoControlPanel extends Panel {

	private static final long serialVersionUID = 1766728047938455517L;

	public VideoControlPanel(final String id) {
		super(id);
		
		WebMarkupContainer iframe = new WebMarkupContainer("iframe");
		Settings settings = AnjaroSession.get().getSettings();
		StringBuilder builder = new StringBuilder("http://");
		builder.append(settings.getCameraHost());
		builder.append(":");
		builder.append(settings.getCameraPort());
		iframe.add(AttributeModifier.replace("src", builder.toString()));
		this.add(iframe);
	}



}
