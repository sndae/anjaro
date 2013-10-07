package de.anjaro.model;

import java.io.Serializable;

public class Settings implements Serializable {

	private String cameraHost = "192.168.2.4";
	private String cameraPort = "8080";
	public String getCameraHost() {
		return cameraHost;
	}
	public void setCameraHost(String cameraHost) {
		this.cameraHost = cameraHost;
	}
	public String getCameraPort() {
		return cameraPort;
	}
	public void setCameraPort(String cameraPort) {
		this.cameraPort = cameraPort;
	}

	
	
	
	
	
}
