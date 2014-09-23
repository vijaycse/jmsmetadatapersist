package com.fiverr.mq.metadata.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

 

 

@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown=true)
public class QueueConfigDetails  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("QueueSize")
	private Integer queueSize;
	@JsonProperty("MaxPageSize")
	private Integer maxPageSize;
	@JsonProperty("PrioritizedMessages")
	private Boolean prioritizedMessages;
	@JsonProperty("MemoryUsagePortion")
	private Integer memoryUsagePortion;
	@JsonProperty("EnqueueCount")
	private Integer enqueueCount;
	@JsonProperty("AverageEnqueueTime")
	private Integer averageEnqueueTime;
	@JsonProperty("Name")
	private String name;
	@JsonProperty("TotalBlockedTime")
	private Integer totalBlockedTime;
	
	public Integer getQueueSize() {
		return queueSize;
	}
	public void setQueueSize(Integer queueSize) {
		this.queueSize = queueSize;
	}
	public Integer getMaxPageSize() {
		return maxPageSize;
	}
	public void setMaxPageSize(Integer maxPageSize) {
		this.maxPageSize = maxPageSize;
	}
	public Boolean getPrioritizedMessages() {
		return prioritizedMessages;
	}
	public void setPrioritizedMessages(Boolean prioritizedMessages) {
		this.prioritizedMessages = prioritizedMessages;
	}
	public Integer getMemoryUsagePortion() {
		return memoryUsagePortion;
	}
	public void setMemoryUsagePortion(Integer memoryUsagePortion) {
		this.memoryUsagePortion = memoryUsagePortion;
	}
	public Integer getEnqueueCount() {
		return enqueueCount;
	}
	public void setEnqueueCount(Integer enqueueCount) {
		this.enqueueCount = enqueueCount;
	}
	public Integer getAverageEnqueueTime() {
		return averageEnqueueTime;
	}
	public void setAverageEnqueueTime(Integer averageEnqueueTime) {
		this.averageEnqueueTime = averageEnqueueTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getTotalBlockedTime() {
		return totalBlockedTime;
	}
	public void setTotalBlockedTime(Integer totalBlockedTime) {
		this.totalBlockedTime = totalBlockedTime;
	}
	@Override
	public String toString() {
		return "QueueConfigDetails [queueSize=" + queueSize + ", maxPageSize="
				+ maxPageSize + ", prioritizedMessages=" + prioritizedMessages
				+ ", memoryUsagePortion=" + memoryUsagePortion
				+ ", enqueueCount=" + enqueueCount + ", averageEnqueueTime="
				+ averageEnqueueTime + ", name=" + name + ", totalBlockedTime="
				+ totalBlockedTime + "]";
	}
	 
	

}
