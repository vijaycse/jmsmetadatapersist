package com.fiverr.jmx.rest.api;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class JMSMQDetailsList {

	public Map<String, LinkedHashMap<String, String>>  processPayload(HashMap<String,List<LinkedHashMap<String,String>>> payload){
		Map<String,LinkedHashMap<String,String>> dataPersist = new HashMap<String,LinkedHashMap<String,String>>();
		for(Map.Entry entryset : payload.entrySet()){
			String key = (String) entryset.getKey();
			if("value".equalsIgnoreCase(key))
			{
				LinkedHashMap<String,String> valueList =    (LinkedHashMap<String, String>) entryset.getValue();
				//System.out.println(" key " + key + " value " + valueList.toString());
				// TODO: need to revisit return data structure as queuename is already part of the value map.not needed as key
				for( Entry entrySet1 : valueList.entrySet()){
					String keyLinkedHashMap =  (String) entrySet1.getKey();
					String[] queueKeyArrays =  keyLinkedHashMap.split(",");
					if(null!=queueKeyArrays && queueKeyArrays.length > 3){
						String queueType = queueKeyArrays[2];
						if(queueType.equalsIgnoreCase("destinationType=Queue") ||  queueType.equalsIgnoreCase("destinationType=Topic")){ 
							String queueName = queueKeyArrays[3];
							LinkedHashMap<String, String> queueTypeDetails  = new LinkedHashMap<String,String>();
							for(int i=0; i  < queueKeyArrays.length ; i++){	
								String[] tmpAttr = queueKeyArrays[i].split("="); // add additional validation
								queueTypeDetails.put(tmpAttr[0], tmpAttr[1]);
								dataPersist.put(queueName, queueTypeDetails);
							}
						}
					}

				}

			}
		}
		System.out.println( " data persist size " + dataPersist.size());
		return dataPersist;
	}

}
