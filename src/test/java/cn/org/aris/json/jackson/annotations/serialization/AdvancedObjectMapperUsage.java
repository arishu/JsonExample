package cn.org.aris.json.jackson.annotations.serialization;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AdvancedObjectMapperUsage {
	private static String resPath = null;
	
	static {
		resPath = AdvancedObjectMapperUsage.class.getClassLoader().getResource("").getFile();
	}
	
	@Test
	public void configSerializationOrdeserializationFeture_usingObjectMapper() throws JsonParseException, JsonMappingException, IOException {
		String jsonString = "{ \"color\" : \"Black\", \"type\" : \"Fiat\", \"year\" : \"1970\" }";
		ObjectMapper mapper = new ObjectMapper();
		
		Car car = mapper.readValue(jsonString, Car.class);
		
		System.out.println(car);
	}
	
}
