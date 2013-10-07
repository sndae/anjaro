package de.anjaro.web.panel;

import java.io.Serializable;

import org.apache.wicket.Page;

public class NavigationItem implements Serializable {

	private String name;

	private Class<? extends Page> page;

	public NavigationItem(final String name, final Class<? extends Page> page) {
		super();
		this.name = name;
		this.page = page;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Class<? extends Page> getPage() {
		return this.page;
	}

	public void setPage(final Class<? extends Page> page) {
		this.page = page;
	}



}
