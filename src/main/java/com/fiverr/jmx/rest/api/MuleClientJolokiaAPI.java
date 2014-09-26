package com.fiverr.jmx.rest.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.annotations.expressions.Lookup;
import org.mule.api.client.LocalMuleClient;

import com.fiverr.mq.metadata.model.QueueConfigDetails;
import com.fiverr.mq.metadata.model.QueueMetadataDetails;



/**
 * move some of the methods to helper class and clean the code and refactor to use sub flow
 * @author vijay
 *
 */




public class MuleClientJolokiaAPI{


	@Lookup
	private MuleContext muleContext;
	private LocalMuleClient client;

	/***
	 * 
	 * @param payload
	 * @return
	 */
	public String testMuleClientGet(String payload){
		return getJolokiaQueueList();
	}

	/**
	 * 
	 * @param payload
	 * @return String
	 */
	// TODO: clean up the method code and modularize this and create a sub flow to do this instead of this two java comp
	//public List<String> testMuleClientQueueAttrDetails(Map<String, LinkedHashMap<String, String>> payload){
	//	return getJolokiaQueueDetails(payload);
	//}


	/***
	 * 
	 * @param queueConfigDetailsList
	 * @return
	 */
	private String getStringofQueueConfigDetails(List<QueueConfigDetails> queueConfigDetailsList){
		// TODO: later change it to buffer if needed
		StringBuilder sb = new StringBuilder();
		for(QueueConfigDetails queueConfig : queueConfigDetailsList){
			sb.append(queueConfig.toString());
			sb.append("\r\n");
		}
		return sb.toString();
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
				queueDetailList.add((result.getPayloadAsString()));
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
	 * 
	 * @param payloadAsString
	 * @return
	 */
	private QueueConfigDetails getJsontoQueueConfigDetails(
			String payloadAsString) {
		//TODO: [ represent list. so later changed it to list[obj]
		String jsonObject = payloadAsString.substring(1, payloadAsString.length()-1); // removing [ and  ] char
		try {
			ObjectMapper obMapper = new ObjectMapper();
			QueueMetadataDetails queueMetadata = obMapper.readValue(jsonObject, QueueMetadataDetails.class);
			return queueMetadata.getValue();
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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

	/***
	 * caling jolokia API to get all queue and topics  list
	 * @return
	 */
	private String  getJolokiaQueueList(){
		client =   muleContext.getClient();
		MuleMessage result = null;
		final String LIST_PAYLOAD = "{\"type\":\"list\",\"path\":\"org.apache.activemq\"}";
		try {
			result = client.send("http://localhost:8161/api/jolokia?maxDepth=7&maxCollectionSize=500&ignoreErrors=true&canonicalNaming=false", 
					LIST_PAYLOAD, setHTTPHeaders());
			//System.out.println(" payload " +result.getPayloadAsString() );
			return result.getPayloadAsString();
		} catch (MuleException e) {
			System.out.println("Mule exception occured in queue list fetch  " + e.getMessage());
		}
		catch (Exception e) {
			System.out.println("exception occured in queue list fetch  " + e.getMessage());
		}
		return  "";
	}



}
