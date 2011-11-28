package util.map;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import util.StringUtil;

public class MapToObject {

	private static Map<String, String> paramMap = new HashMap<String, String>();
	static {
		paramMap.put("SERVER2.BOCLPARID", "34");
		paramMap.put("SERVER1.DATAVG1.NAME", "v1");
		paramMap.put("SERVER2.BOCLPARName", "detailserver2");
		paramMap.put("SERVER2.DATAVG1.PPSIZE", "23");
		paramMap.put("SERVER1.PMRDP.Net.IPAddress_1", "4.4.4.1");
		paramMap.put("SERVER1.DATAVG2.PPSIZE", "34");
		paramMap.put("SERVER2.PMRDP.Net.Script", "UAT.SH");
		paramMap.put("SERVER1.DATAVG2.SIZE", "12");
		paramMap.put("SERVER1.PMRDP.Net.IPAddress_2", "4.4.4.2");
		paramMap.put("SERVER1.ROOTVGSIZE", "26");
		paramMap.put("SERVER1.PMRDP.Net.Subnet_1", "4.4.4.0/24");
		paramMap.put("SERVER1.PMRDP.Net.Subnet_2", "4.4.4.0/24");
		paramMap.put("SERVER2.PMRDP.Net.Slot_1", "56");
		paramMap.put("SERVER1.BOCSlot1", "12");
		paramMap.put("SERVER1.DATAVG2.NAME", "v2");
		paramMap.put("SERVER2.ROOTVGSIZE", "27");
		paramMap.put("SERVER1.BOCSlot2", "23");
		paramMap.put("SERVER1.BOCHostName", "detailhost");
		paramMap.put("SERVER1.PMRDP.Net.NetMask_1", "255.255.255.0");
		paramMap.put("SERVER1.PMRDP.Net.NetMask_2", "255.255.255.0");
		paramMap.put("SERVER2.PMRDP.Net.Subnet_1", "4.4.4.0/24");
		paramMap.put("SERVER2.PMRDP.Net.VLANID_1", "1");
		paramMap.put("SERVER1.PMRDP.Net.VLANID_1", "1");
		paramMap.put("SERVER1.PMRDP.Net.VLANID_2", "1");
		paramMap.put("SERVER2.PMRDP.Net.NetMask_1", "255.255.255.0");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		String[] aaa = "a.b.c".split("\\.");
//		System.out.println(aaa[1]);
//		VODataVG vo = new VODataVG();
//		invokeMethod(vo, "NAME", "DDDSDSDS");
//		System.out.println(vo.getName());
		List<HashMap> serverList = genObjListFromMap(paramMap);
		
		for (int i = 0; i < serverList.size(); i++) {
			
			HashMap serverMap = (HashMap)serverList.get(i);
			printMapAttr(serverMap);
			List datavgList = (List)serverMap.get("DATAVGLIST");
			for (int j = 0; j < datavgList.size(); j++) {
				VODataVG voDatavg = (VODataVG)datavgList.get(j);
				System.out.println("DataVG id:" + voDatavg.getId()
						+ "\tName:" + voDatavg.getName()
						+ "\tSize:" + voDatavg.getSize()
						+ "\tPPSize:" + voDatavg.getPpsize());
			}
			List netCardList = (List)serverMap.get("NETCARDLIST");
			for (int j = 0; j < netCardList.size(); j++) {
				VONetCard voNetCard = (VONetCard)netCardList.get(j);
				System.out.println("NetCard id:" + voNetCard.getId()
						+ "\tIpaddress:" + voNetCard.getIpaddress()
						+ "\tNetmask:" + voNetCard.getNetmask()
						+ "\tSubnet:" + voNetCard.getSubnet()
						+ "\tVlanid:" + voNetCard.getVlanid()
						);
			}
		}

	}
	public static void printMapAttr(Map map){
		for(Object key : map.keySet()){
			Object value = map.get(key);
			if (value instanceof String){
				System.out.println(key + " : " + value);
			}
	    }
	}

	private static List<HashMap> genObjListFromMap(Map<String, String> srcMap) {
		
		List<HashMap> resultList = new ArrayList<HashMap>();
		HashMap<String,HashMap> tempServerListMap = new HashMap<String,HashMap>();
		
		for(String srcKey : srcMap.keySet()){
			String[] arrKey = srcKey.split("\\.");
			String scrValue = srcMap.get(srcKey);
			
			
			String serverId = arrKey[0].substring("SERVER".length());
			String serverAttrName = arrKey[1];
			
			HashMap serverMap = tempServerListMap.get(serverId);
			if (serverMap == null){
				serverMap = new HashMap();
				serverMap.put("MapID", serverId);
				serverMap.put("DATAVGLIST", new ArrayList<VODataVG>());
				serverMap.put("DataVGHashMap", new HashMap());
				serverMap.put("NETCARDLIST", new ArrayList());
				serverMap.put("NetCardHashMap", new HashMap());
				
				tempServerListMap.put(serverId, serverMap);
				resultList.add(serverMap);
			}
			
			switch (arrKey.length) {
			case 2:
				//如果属性长度为2，则认为是Server的属性
				serverMap.put(serverAttrName,scrValue);
				
				break;
			case 3:
				//如果属性长度是3，则认为是子对象（DataVG）及其属性
				String dataVGId = arrKey[1].substring("DATAVG".length());
				HashMap dataVGMap = (HashMap)serverMap.get("DataVGHashMap");
				List dataVGList = (List)serverMap.get("DATAVGLIST");
				VODataVG voDataVg = (VODataVG)dataVGMap.get(dataVGId);
				if (voDataVg == null){
					voDataVg = new VODataVG();
					voDataVg.setId(dataVGId);
					dataVGMap.put(dataVGId, voDataVg);
					dataVGList.add(voDataVg);
				}
				invokeMethod(voDataVg,arrKey[2], scrValue);
				break;
			case 4:
				
				//如果属性长度是4，则认为是子对象（Net）及其属性
				String netCardId = arrKey[3].substring(arrKey[3].length()-1);
				//土鳖的判断ID是否为数字
				try{
					Integer.parseInt(netCardId);
				}catch(Exception e){
					break;
				}
				
				HashMap netCardMap = (HashMap)serverMap.get("NetCardHashMap");
				List netCardList = (List)serverMap.get("NETCARDLIST");
				VONetCard voNetCard = (VONetCard)netCardMap.get(netCardId);
				if (voNetCard == null){
					voNetCard = new VONetCard();
					voNetCard.setId(netCardId);
					netCardMap.put(netCardId, voNetCard);
					netCardList.add(voNetCard);
				}
				String netField = arrKey[3].substring(0, arrKey[3].length()-2);
				invokeMethod(voNetCard,netField, scrValue);
				
			default:
				break;
			}
	    }
		return resultList;
	}

	private static void invokeMethod(Object objInstance,String fieldName, String fieldValue) {
		Method setMethod;
		try {
			String fistLetter = fieldName.substring(0, 1).toUpperCase();
			String otherLetter = fieldName.substring(1).toLowerCase();
			setMethod = objInstance.getClass().getMethod("set"+ fistLetter + otherLetter, new Class[]{fieldValue.getClass()});
			setMethod.invoke(objInstance,new Object[]{fieldValue});
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	

}

