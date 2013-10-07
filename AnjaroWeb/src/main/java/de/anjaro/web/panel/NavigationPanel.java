package de.anjaro.web.panel;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;

import de.anjaro.web.page.ControlPanelPage;
import de.anjaro.web.page.SettingsPage;
import de.anjaro.web.page.WelcomePage;

public class NavigationPanel extends Panel {

	private static final long serialVersionUID = 419799448215510110L;

	public NavigationPanel(final String id) {
		super(id);
		final List<NavigationItem> navigationList = new ArrayList<NavigationItem>();
		navigationList.add(new NavigationItem("home", WelcomePage.class));
		navigationList.add(new NavigationItem("controlPanel", ControlPanelPage.class));
		navigationList.add(new NavigationItem("settings", SettingsPage.class));
		final ListView<NavigationItem> listView = new ListView<NavigationItem>("listView", navigationList) {

			private static final long serialVersionUID = 4071897194654536853L;

			@Override
			protected void populateItem(final ListItem<NavigationItem> item) {
				item.add(AttributeModifier.replace("class", new Model<String>() {

					private static final long serialVersionUID = 8159401529835367216L;

					@Override
					public String getObject() {
						String result = "";
						if (getPage().getClass().equals(item.getModelObject().getPage())) {
							result = "current_page_item";
						}
						return result;
					}

				}));
				final BookmarkablePageLink<Page> page = new BookmarkablePageLink<Page>("link", item.getModelObject().getPage());
				page.add(new Label("linkName", new StringResourceModel(item.getModelObject().getName(), null, this)));
				item.add(page);
			}
		};

		this.add(listView);
	}
}
