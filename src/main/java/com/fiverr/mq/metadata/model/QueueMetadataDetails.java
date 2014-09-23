package com.fiverr.mq.metadata.model;
import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
 
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown=true)
public class QueueMetadataDetails implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("value")
	private QueueConfigDetails value;
	
	private int timestamp;
	private int status;
	
	
	public QueueConfigDetails getValue() {
		return value;
	}
	public void setValue(QueueConfigDetails value) {
		this.value = value;
	}
	public int getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "QueueMetadataDetails [value=" + value + ", timestamp="
				+ timestamp + ", status=" + status + "]";
	}
	
	


}
