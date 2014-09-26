package com.fiverr.jmx.rest.api;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.mule.DefaultMuleMessage;
import org.mule.api.MuleContext;
import org.mule.api.MuleMessage;
import org.mule.api.annotations.expressions.Lookup;
import org.mule.api.transport.PropertyScope;

public class ParseMessageDetails {


	@Lookup
	private MuleContext muleContext;

	public MuleMessage  processPayload(HashMap<String,LinkedHashMap<String,String>> payload ){
		System.out.println(" payload in request " + payload.toString());
		MuleMessage msg = new DefaultMuleMessage(payload,muleContext);
		for(Map.Entry iterateMapValues : payload.entrySet()){
			String payLoadKey = (String) iterateMapValues.getKey();
			if("request".equalsIgnoreCase(payLoadKey)){
				// set the destination type in flow var
				String destinationType="";
				LinkedHashMap<String,String> valueList =    (LinkedHashMap<String, String>) iterateMapValues.getValue();
				for(Map.Entry iterateDestination : valueList.entrySet()){
					if("mbean".equalsIgnoreCase((String)iterateDestination.getKey())){
						String[] queueKeyArrays =  ((String)iterateDestination.getValue()).split(",");
						if(null!=queueKeyArrays && queueKeyArrays.length > 3){
							String queueType = queueKeyArrays[2];
							destinationType = queueType.equalsIgnoreCase("destinationType=Queue") ? "Queue" : "Topic";
						}
						msg.setProperty("destinationTypeProp",destinationType ,PropertyScope.OUTBOUND);
						System.out.println(" payload in desti type " + destinationType);
					}
				}
			}
			if("value".equalsIgnoreCase(payLoadKey)){
				System.out.println(" payload in metadata details " + iterateMapValues.getValue().toString());
				msg.setPayload((LinkedHashMap<String, String>) iterateMapValues.getValue());
				return msg;
			}
		}
		return (MuleMessage) new LinkedHashMap<String, String>();
	}

}

