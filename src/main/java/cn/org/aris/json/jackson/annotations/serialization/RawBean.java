package cn.org.aris.json.jackson.annotations.serialization;

import com.fasterxml.jackson.annotation.JsonRawValue;

public class RawBean {
	
	public RawBean(String name, String json) {
		this.name = name;
		this.json = json;
	}
	
	public String name;
	
	@JsonRawValue
	public String json;
}
