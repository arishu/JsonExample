package cn.org.aris.json.jackson;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonToJavaConverter {

	private static Logger logger = LoggerFactory.getLogger(JsonToJavaConverter.class);
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		JsonToJavaConverter converter = new JsonToJavaConverter();
		StringBuilder jsonBuilder = new StringBuilder();
		jsonBuilder.append("\n{\n");
		jsonBuilder.append("    \"name\"   : \"Hu\",\n");
		jsonBuilder.append("    \"surname\": \"Aris\",\n");
		jsonBuilder.append("    \"phone\"  : 123456 \n}");
		
		converter.fromJson(jsonBuilder.toString());
	}

	/**
	 * Convert JSON string to a JAVA POJO
	 * @param json	The string to be converted
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public Object fromJson(String json) throws JsonParseException, JsonMappingException, IOException
	{
		User garima = new ObjectMapper().readValue(json, User.class);
		logger.info("Java Object created from JSON string");
		logger.info("JOSN string: " + json);
		logger.info("Java Object: " + garima);
		return garima;
	}
	
	public static class User {
		private String name;
		private String surname;
		private long phone;
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getSurname() {
			return surname;
		}
		public void setSurname(String surname) {
			this.surname = surname;
		}
		public long getPhone() {
			return phone;
		}
		public void setPhone(long phone) {
			this.phone = phone;
		}
		
		@Override
		public String toString() {
			return "User [name=" + name + ", surname="+ surname + ", phone=" + phone + "]";
		}
	}
}
