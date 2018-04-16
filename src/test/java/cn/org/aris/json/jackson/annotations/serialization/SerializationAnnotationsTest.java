package cn.org.aris.json.jackson.annotations.serialization;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

import cn.org.aris.json.jackson.annotations.serialization.Event;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.hamcrest.core.Is;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class SerializationAnnotationsTest {
	
	/**
	 * Testing '@JsonAnyGetter'
	 * @throws JsonProcessingException
	 */
	@Test
	public void whenSerializingUsingJsonAnyGetter_thenCorrect() throws JsonProcessingException {
		ExtendableBean bean = new ExtendableBean("My bean");
		bean.add("attr1", "val1");
		bean.add("attr2", "val2");
		
		String result = new ObjectMapper().writeValueAsString(bean);
		
		System.out.println(result);
		
		assertThat(result, containsString("attr1"));
		assertThat(result, containsString("val1"));
	}
	
	/**
	 * Testing '@JsonGetter'
	 * @throws JsonProcessingException
	 */
	@Test
	public void whenSerializatingUsingJsonGetter_thenCorrect() throws JsonProcessingException {
		MyBean bean = new MyBean(1, "My Bean");
		
		String result = new ObjectMapper().writeValueAsString(bean);
		
		System.out.println(result);
		
		assertThat(result, containsString("My Bean"));
		assertThat(result, containsString("1"));
	}
	
	/**
	 * Testing '@JavaPropertyOrder'
	 * @throws JsonProcessingException
	 */
	@Test
	public void whenSerializatingUsingJsonPropertyOrder_thenCorrect() throws JsonProcessingException {
		MyBean bean = new MyBean(1, "My Bean");
		
		String result = new ObjectMapper().writeValueAsString(bean);
		
		System.out.println(result);
		
		assertThat(result, containsString("My Bean"));
		assertThat(result, containsString("1"));
	}
	
	/**
	 * Testing '@JsonRawValue'
	 * @throws JsonProcessingException
	 */
	@Test
	public void whenSerializingUsingJsonRawValue_thenCorrect() throws JsonProcessingException {
		RawBean rawBean = new RawBean("My bean", "{\"attr\":false}");
		
		String result = new ObjectMapper().writeValueAsString(rawBean);
		
		System.out.println(result);
		
		assertThat(result, containsString("My bean"));
		assertThat(result, containsString("{\"attr\":false}"));
	}
	
	/**
	 * Testing '@JsonValue'
	 * @throws JsonProcessingException
	 */
	@Test
	public void whenSerializingUsingJsonValue_thenCorrect() throws JsonProcessingException {
		String enumAsString = new ObjectMapper().writeValueAsString(TypeEnumWithValue.TYPE1);
		
		System.out.println(enumAsString);
		
		assertThat(enumAsString, Is.is("\"Type A\""));
	}
	
	/**
	 * Testing '@JsonRootName'
	 * @throws JsonProcessingException 
	 */
	@Test
	public void whenSerializingUsingJsonRootName_thenCorrect() throws JsonProcessingException {
		UserWithRoot user = new UserWithRoot(1,  "ArisHu");
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
		
		String result = mapper.writeValueAsString(user);
		
		System.out.println(result);
		
		assertThat(result, containsString("ArisHu"));
		assertThat(result, containsString("user"));
	}
	
	
	@Test
	public void whenSerializingUsingJsonSerialize_thenCorrect() throws ParseException, JsonProcessingException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String toParse = "2017-04-16 16:00:00";
		Date date = sdf.parse(toParse);
		Event event = new Event("party", date);
		
		String result = new ObjectMapper().writeValueAsString(event);
		
		System.out.println(result);
		
		assertThat(result, containsString(toParse));
		
	}
}
