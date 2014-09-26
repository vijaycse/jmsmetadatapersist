package com.fiverr.jmx.rest.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.annotations.expressions.Lookup;
import org.mule.api.client.LocalMuleClient;

public class MuleClientJMXAttrDetails {

	@Lookup
	private MuleContext muleContext;
	private LocalMuleClient client;
	
	/**
	 * 
	 * @param payload
	 * @return String
	 */
	// TODO: clean up the method code and modularize this and create a sub flow to do this instead of this two java comp
	public List<String> testMuleClientQueueAttrDetails(Map<String, LinkedHashMap<String, String>> payload){
		return getJolokiaQueueDetails(payload);
	}

	/***
	 * 
	 * @param payload
	 * @return
	 */
	private List<String> getJolokiaQueueDetails(Map<String, LinkedHashMap<String, String>> payload) {
		client =   muleContext.getClient();
		MuleMessage result = null;
		String jsonPayload="";
		//List<QueueConfigDetails> queueDetailList  = new ArrayList<QueueConfigDetails>();
		List<String> queueDetailList  = new ArrayList<String>();
		for(Map.Entry<String, LinkedHashMap<String, String>> entrySet :  payload.entrySet()){
			LinkedHashMap<String,String> queueTypeDetails = (LinkedHashMap) entrySet.getValue();
			String queueName = queueTypeDetails.get("destinationName");
			String queueType = 	queueTypeDetails.get("destinationType");
			//TODO: clean it to read it from final string.
			jsonPayload = "[{\"type\":\"READ\",\"mbean\":\"org.apache.activemq:type=Broker,brokerName=localhost,destinationType="+queueType+",destinationName="+queueName+"\",\"ignoreErrors\":true}]";
			try {
				result = client.send("http://localhost:8161/api/jolokia?maxDepth=7&maxCollectionSize=500&ignoreErrors=true&canonicalNaming=false", 
						jsonPayload, setHTTPHeaders());
				System.out.println(" payload " +result.getPayloadAsString() );
				//queueDetailList.add(getJsontoQueueConfigDetails(result.getPayloadAsString()));
				String tempJson = result.getPayloadAsString();
				String jsonObject = tempJson.substring(1, tempJson.length()-1);
				queueDetailList.add((jsonObject));
			} catch (MuleException e) {
				System.out.println("mule exception in queue details fetch " + e.getMessage());
			} catch (Exception e) {
				System.out.println("exception in queue details fetch  " + e.getMessage());
			}
		}
		//return getStringofQueueConfigDetails(queueDetailList);
		return (queueDetailList);
	}
	
	/***
	 *  setting http headers
	 * @return
	 */
	private final Map<String, Object> setHTTPHeaders(){
		Map<String, Object> props = new HashMap<String,Object>();
		props.put("Content-type", "application/json");
		props.put("Accept", "application/json");
		props.put("http.method", "POST");
		props.put("Authorization", "Basic YWRtaW46YWRtaW4=");
		return props;
	}
	
}
