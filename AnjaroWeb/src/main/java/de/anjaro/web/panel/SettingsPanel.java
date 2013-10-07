package de.anjaro.web.panel;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;

import de.anjaro.model.Settings;
import de.anjaro.web.AnjaroSession;

public class SettingsPanel extends Panel {

	public SettingsPanel(String id) {
		super(id);
		AnjaroSession session = (AnjaroSession) Session.get();
		Form<Settings> form = new Form<Settings>("form", new CompoundPropertyModel<Settings>(session.getSettings()));
		form.add(new RequiredTextField<String>("cameraHost"));
		
		Button submitButton = new Button("submit") {

			@Override
			public void onSubmit() {
				// TODO Auto-generated method stub
				super.onSubmit();
			}
			
		};
		
		form.add(submitButton);
		
		this.add(form);
	}
	
	

}
