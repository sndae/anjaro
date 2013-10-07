package de.anjaro.web.panel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.ContextRelativeResource;

import de.anjaro.control.StaticAnjaroController;

public class MotorControlPanel extends Panel {

	private static final long serialVersionUID = -3874756302864549083L;

	public MotorControlPanel(final String id) {
		super(id);
		final Image powerButtonImageOn = new  Image("powerButtonImage", new ContextRelativeResource("powerOn.png"));
		final Image powerButtonImageOff = new  Image("powerButtonImage", new ContextRelativeResource("powerOff.png"));

		powerButtonImageOn.setOutputMarkupId(true);
		powerButtonImageOff.setOutputMarkupId(true);


		final Image arrowUpOff = new  Image("arrowUpImage", new ContextRelativeResource("arrowUpOff.png"));
		final Image arrowRightOff = new  Image("arrowRightImage", new ContextRelativeResource("arrowRightOff.png"));
		final Image arrowDownOff = new  Image("arrowDownImage", new ContextRelativeResource("arrowDownOff.png"));
		final Image arrowLeftOff = new  Image("arrowLeftImage", new ContextRelativeResource("arrowLeftOff.png"));
		final Image arrowUpOn = new  Image("arrowUpImage", new ContextRelativeResource("arrowUpOn.png"));
		final Image arrowRightOn = new  Image("arrowRightImage", new ContextRelativeResource("arrowRightOn.png"));
		final Image arrowDownOn = new  Image("arrowDownImage", new ContextRelativeResource("arrowDownOn.png"));
		final Image arrowLeftOn = new  Image("arrowLeftImage", new ContextRelativeResource("arrowLeftOn.png"));

		final Image stopOn = new Image("stopImage", new ContextRelativeResource("stopOn.png"));
		final Image stopOff = new Image("stopImage", new ContextRelativeResource("stopOff.png"));

		final AjaxLink<String> stopLink = this.addLink("stopLink", StaticAnjaroController.isStop() && StaticAnjaroController.isControllerActive(), stopOn, stopOff);

		this.addLink("arrowUpLink", StaticAnjaroController.isForward(), arrowUpOn, arrowUpOff);
		this.addLink("arrowRightLink", StaticAnjaroController.isRight(), arrowRightOn, arrowRightOff);
		this.addLink("arrowLeftLink", StaticAnjaroController.isLeft(), arrowLeftOn, arrowLeftOff);
		this.addLink("arrowDownLink", StaticAnjaroController.isBackward(), arrowDownOn, arrowDownOff);

		final AjaxLink<String> powerButtonLink = new AjaxLink<String>("powerButtonLink") {
			private static final long serialVersionUID = 4009772161526542988L;

			@Override
			public void onClick(final AjaxRequestTarget target) {
				if (StaticAnjaroController.isControllerActive()) {
					StaticAnjaroController.shutDown();
					this.remove("powerButtonImage");
					this.add(powerButtonImageOff);
					MotorControlPanel.this.handleArrows(this, target);
					target.add(this);
				} else {
					try {
						StaticAnjaroController.initController();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					this.remove("powerButtonImage");
					this.add(powerButtonImageOn);
					MotorControlPanel.this.handleArrows(stopLink, target);
					target.add(this);
				}
			}
		};

		powerButtonLink.setOutputMarkupId(true);
		if (StaticAnjaroController.isControllerActive()) {
			powerButtonLink.add(powerButtonImageOn);
		} else {
			powerButtonLink.add(powerButtonImageOff);
		}
		this.add(powerButtonLink);


	}

	private AjaxLink<String> addLink(final String pId, final boolean pOn, final Image pImageOn, final Image pImageOff) {
		final AjaxLink<String> link = new AjaxLink<String>(pId) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(final AjaxRequestTarget target) {
				if (StaticAnjaroController.isControllerActive()) {
					MotorControlPanel.this.handleArrows(this, target);
					if ("arrowUpLink".equals(this.getId())) {
						StaticAnjaroController.forward();
					} else if ("arrowDownLink".equals(this.getId())) {
						StaticAnjaroController.backward();
					} else if ("arrowRightLink".equals(this.getId())) {
						StaticAnjaroController.right();
					} else if ("arrowLeftLink".equals(this.getId())) {
						StaticAnjaroController.left();
					} else if ("stopLink".equals(this.getId())) {
						StaticAnjaroController.stop();
					}

				}
			}
		};
		link.setOutputMarkupId(true);
		if (pOn) {
			link.add(pImageOn);
		} else {
			link.add(pImageOff);
		}
		this.registerLink(link,pImageOn, pImageOff);

		this.add(link);
		return link;
	}

	private void handleArrows(final AjaxLink<String> pLink, final AjaxRequestTarget target) {
		for (final LinkHandler linkHandler : this.arrowList) {
			final AjaxLink<String> link = linkHandler.getLink();
			if (link.equals(pLink)) {
				link.remove(linkHandler.getImageOff());
				link.add(linkHandler.getImageOn());
			} else {
				link.remove(linkHandler.getImageOn());
				link.add(linkHandler.getImageOff());
			}
			target.add(link);
		}
	}

	private final List<LinkHandler> arrowList = new ArrayList<LinkHandler>();

	private class LinkHandler implements Serializable {

		private static final long serialVersionUID = -2315822626328151837L;
		private final AjaxLink<String> link;
		private final Image imageOn;
		private final Image imageOff;
		public LinkHandler(final AjaxLink<String> pLink, final Image pImageOn, final Image pImageOff) {
			super();
			this.link = pLink;
			this.imageOn = pImageOn;
			this.imageOff = pImageOff;
		}
		public AjaxLink<String> getLink() {
			return this.link;
		}
		public Image getImageOn() {
			return this.imageOn;
		}
		public Image getImageOff() {
			return this.imageOff;
		}
	}

	private void registerLink(final AjaxLink<String> pLink, final Image pImageOn, final Image pImageOff) {
		this.arrowList.add(new LinkHandler(pLink, pImageOn, pImageOff));
	}

}