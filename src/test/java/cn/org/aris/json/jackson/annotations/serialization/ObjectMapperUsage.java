package cn.org.aris.json.jackson.annotations.serialization;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUsage {
	private static String resPath = null;
	static {
		resPath = ObjectMapperUsage.class.getClassLoader().getResource("").getFile();
		System.out.println(resPath);
	}
	
	/**
	 * Writing a Java Object to a JSON file
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@Test
	public void javaObjectToJson_writetToFile_usingObjectMapper() throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Car car = new Car("yellow", "renault");
		mapper.writeValue(new File(resPath + "car.json"), car);
	}
	
	/**
	 * Convert JSON Object reading from a String to Java Object,
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@Test
	public void jsonObjectToJava_readFromString_usingObjectMapper() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";
		Car car = mapper.readValue(jsonString, Car.class);
		
		String carStr = car.toString();
		
		System.out.println(carStr);
	}
	
	/**
	 * Convert JSON Object reading from a file contains JSON to Java object
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@Test
	public void jsonObjectToJava_readFromFile_usingObjectMapper() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Car car = mapper.readValue(new File(resPath + "car.json"), Car.class);
		
		String carStr = car.toString();
		
		System.out.println(carStr);
	}
	
	/**
	 * Convert JSON Object reading from an URL to Java Object
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@Test
	public void jsonObjectToJava_readFromURL_usingObjectMapper() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Car car = mapper.readValue(new URL("ftp://hwc:hwc123456@localhost/promptboard/car.json"), Car.class);
		
		String carStr = car.toString();
		
		System.out.println(carStr);
	}
	
	/**
	 * Convert JSON Object reading from a JSON string to JSON Node
	 * @throws IOException
	 */
	@Test
	public void jsonObjectToJsonNode_readFromString_usingObjectMapper() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";
		JsonNode jsonNode = mapper.readTree(jsonString);
		String color = jsonNode.get("color").asText();
		
		System.out.println(color);
		
		assertThat(color, equalTo("Black"));
	}
	
	/**
	 * Convert JSON Array to Java List
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@Test
	public void jsonArrayStringToJavaList_usingObjectMapper() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String jsonArrayString = "[{ \"color\" : \"Black\", \"type\" : \"BMW\" }, { \"color\" : \"Red\", \"type\" : \"FIAT\" }]";
		List<Car> carList = mapper.readValue(jsonArrayString, new TypeReference<List<Car>>() {});
		
		System.out.println(carList.toString());
	}
	
	/**
	 * Convert jSON String to Java Map
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@Test
	public void jsonStringToJavaMap_usingObjectMapper() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";
		Map<String, Object> map = mapper
				.readValue(jsonString, new TypeReference<Map<String, Object>>(){});
		
		System.out.println(map);
	}
}
