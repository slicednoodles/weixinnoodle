package com.noodle.common.baidu.pojo;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.noodle.common.utils.JaxbUtil;

@XmlAccessorType(XmlAccessType.FIELD)  
@XmlRootElement 
@XmlType(name = "placesearchresponse", propOrder = { "status", "message", "results"})  
public class Placesearchresponse {
	@XmlElement(name = "status")
	private String status;
	@XmlElement(name = "message")
	private String message;
	@XmlElementWrapper(name = "results")
    @XmlElement(name = "result")
	private List<PlaceSearchResults> results;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<PlaceSearchResults> getResults() {
		return results;
	}
	public void setResults(List<PlaceSearchResults> results) {
		this.results = results;
	}
	
	public static void main(String[] args) {
		String str = "<placesearchresponse><status>0</status><message>ok</message><results><result><name>有客酒店</name><location><lat>30.662843</lat><lng>104.083625</lng></location><address>锦江区春熙路北段新街后巷子10号</address><telephone>18081899757</telephone><uid>d6df91c13a8f7a73f6b9169c</uid></result><result><name>四川宾馆(西楼)</name><location><lat>30.66654</lat><lng>104.083291</lng></location><address>成都市锦江区暑袜北二街89号</address><telephone>(028)86755555</telephone><uid>2cc3c8e8aab55dcf4d2fdf76</uid></result><result><name>城市客栈成都宽窄巷子店</name><location><lat>30.667433</lat><lng>104.0568</lng></location><address>金河路75号</address><telephone>(028)66679000,13699492481</telephone><uid>91c13f9306632af921f01729</uid></result><result><name>成都弗斯达酒店文武店</name><location><lat>30.6778</lat><lng>104.07573</lng></location><address>成都市青羊区文武路152号（市公安局旁）</address><telephone>(028)86615333</telephone><uid>6952db28341f64d726d2bb0c</uid></result><result><name>成都花城假日酒店公寓</name><location><lat>30.655986</lat><lng>104.08915</lng></location><address>锦江区下东大街216号喜年广场内B栋27楼12号</address><telephone>(028)86586015</telephone><uid>0523a141fbf5c504b33c81bb</uid></result><result><name>新良大酒店(上东大街店)</name><location><lat>30.659737</lat><lng>104.080216</lng></location><address>成都市锦江区东大街上东大街段246号（西南书城旁）</address><telephone>(028)86739999,4006639517</telephone><uid>504d6113fcb775aa46572638</uid></result><result><name>四川四季康成酒店</name><location><lat>30.660335</lat><lng>104.0965</lng></location><address>成都市望平滨河路6号</address><telephone>(028)84376838</telephone><uid>a41927c2c1bf4f172efaa740</uid></result><result><name>瑞熙酒店</name><location><lat>30.622762</lat><lng>104.084036</lng></location><address>四川省成都市武侯区科华中路2号</address><telephone>(028)85219888</telephone><uid>e85e2527316a984a95f08c44</uid></result><result><name>如家七斗星商旅酒店(火车站店)</name><location><lat>30.700882</lat><lng>104.073206</lng></location><address>二环路北二段85号</address><telephone>(028)66777770</telephone><uid>55fa2c0b4cbae4341ce99979</uid></result><result><name>锦江之星（成都东风大桥店）</name><location><lat>30.658129</lat><lng>104.096809</lng></location><address>四川省成都市东风路二段15号</address><telephone>4008209999</telephone><uid>8ab0dfc4f8df4f0fd8eced53</uid></result></results></placesearchresponse>";
		Placesearchresponse res = JaxbUtil.converyToJavaBean(str, Placesearchresponse.class);
		System.out.println(res);
	}
}
