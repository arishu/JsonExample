package cn.org.aris.json.jackson.annotations.serialization;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.IsAnything.anything;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.hamcrest.core.IsSame.theInstance;
import static org.hamcrest.core.StringContains.containsString;

import org.hamcrest.core.Is;

import cn.org.aris.json.jackson.annotations.serialization.Event;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@SuppressWarnings("unused")
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
	
	/**
	 * Testing '@JsonSerialize'
	 * @throws ParseException
	 * @throws JsonProcessingException
	 */
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
	
	/**
	 * Testing '@JsonIgnoreProperties'
	 * @throws JsonProcessingException
	 */
	@Test
	public void givenFieldIsIgnoredByName_whenDtoIsSerialized_thenCorrect() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		MyDtoIgnoreProperties dtoObject = new MyDtoIgnoreProperties();
		
		String dtoAsString = mapper.writeValueAsString(dtoObject);
		
		System.out.println(dtoAsString);
		
		assertThat(dtoAsString, not(containsString("intValue")));
	}
	
	/**
	 * Testing '@JsonIgnore'
	 * @throws JsonProcessingException
	 */
	@Test
	public void givenFieldIsIgnoredDirectly_whenDtoIsSerialized_thenCorrect() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		MyDtoIgnoreSpecificProperties dtoObject = new MyDtoIgnoreSpecificProperties();
		
		String dtoAsString = mapper.writeValueAsString(dtoObject);
		
		System.out.println(dtoAsString);
		
		assertThat(dtoAsString, not(containsString("intValue")));
	}
	
	@Test
	public void givenFieldTypeIsIgnored_whenDtoIsSerialized_thenCorrect() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.addMixIn(String[].class, MyMixInForIgnoreType.class);
		MyDtoWithSpecialField dtoObject = new MyDtoWithSpecialField();
		//dtoObject.setBooleanValue(true);
		
		String dtoAsString = mapper.writeValueAsString(dtoObject);
		
		System.out.println(dtoAsString);
		
		assertThat(dtoAsString, not(containsString("stringArrayValue")));
		
		assertThat(dtoAsString, containsString("stringValue"));
		assertThat(dtoAsString, containsString("booleanValue"));
		assertThat(dtoAsString, containsString("intValue"));
	}
	
	/**
	 * Testing '@JsonFilter'
	 * @throws JsonProcessingException
	 */
	@Test
	public void givenTypeHasFilterThatIgnoresFieldByName_whenDtoIsSerialize() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		
		// add filter to the mapper
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
				.serializeAllExcept("intValue");
		FilterProvider filters = new SimpleFilterProvider()
				.addFilter("myFilter", filter);
		
		MyDtoWithFilter dtoObject = new MyDtoWithFilter();
		
		String dtoAsString = mapper.writer(filters).writeValueAsString(dtoObject);
		
		System.out.println(dtoAsString);
		
		assertThat(dtoAsString, not(containsString("intValue")));
		assertThat(dtoAsString, containsString("booleanValue"));
		assertThat(dtoAsString, containsString("stringValue"));
	}
	
	/**
	 * Testing '@JsonInclude'
	 * @throws JsonProcessingException
	 */
	@Test
	public void givenNullsIgnoredOnClass_whenWritingObjectWithNullField_thenIgnore() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		MyDtoIgnoreNullFields dtoObject = new MyDtoIgnoreNullFields();
		
		String dtoAsString = mapper.writeValueAsString(dtoObject);
		
		System.out.println(dtoAsString);
		
		assertThat(dtoAsString, containsString("intValue"));
		assertThat(dtoAsString, not(containsString("stringValue")));
	}
	
	/**
	 * Add Inclusion to mapper
	 * @throws JsonProcessingException
	 */
	@Test
	public void givenNullsIgnoredGlobally_whenWritingObjectWithNullField_thenIgnore() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		
		MyDto dtoObject = new MyDto();
		
		String dtoAsString = mapper.writeValueAsString(dtoObject);
		
		assertThat(dtoAsString, containsString("intValue"));
		assertThat(dtoAsString, containsString("booleanValue"));
		assertThat(dtoAsString, not(containsString("stringValue")));
	}
	
	/**
	 * Testing '@JsonProperty'
	 * @throws JsonProcessingException
	 */
	@Test
	public void givenNameOfFieldIsChanged_whenSerializing_thenCorrect() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		MyDtoWithCustomProperty dtoObject = new MyDtoWithCustomProperty();
		dtoObject.setStringValue("a");
		
		String dtoAsString = mapper.writeValueAsString(dtoObject);
		
		System.out.println(dtoAsString);
		
		assertThat(dtoAsString, not(containsString("stringValue")));
		assertThat(dtoAsString, containsString("strVal"));
	}
	
}
