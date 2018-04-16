package cn.org.aris.json.jackson.annotations.serialization;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TypeEnumWithValue {
	
	TYPE1(1, "Type A"), TYPE2(2, "Type 2");
	
	TypeEnumWithValue(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	
	@JsonValue
	public String getName() {
		return this.name;
	}
	
	@SuppressWarnings("unused")
	private Integer id;
	private String name;
}
