package cn.org.aris.json.jackson.annotations.serialization;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("user")
public class UserWithRoot {
	public int id;
	public String name;
	
	public UserWithRoot(int id, String name) {
		this.id = id;
		this.name = name;
	}
}
