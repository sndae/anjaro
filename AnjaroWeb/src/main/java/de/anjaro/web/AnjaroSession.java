package de.anjaro.web;

import org.apache.wicket.Session;
import org.apache.wicket.core.request.ClientInfo;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

import de.anjaro.model.Settings;

public class AnjaroSession extends WebSession {
	
	private Settings settings = new Settings();

	public AnjaroSession(Request request) {
		super(request);
	}
	
	public static final AnjaroSession get() {
		return (AnjaroSession) Session.get();
	}

	public Settings getSettings() {
		return settings;
	}



	public void setSettings(Settings settings) {
		this.settings = settings;
	}



}
