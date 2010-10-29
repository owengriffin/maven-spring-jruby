package com.owengriffin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import org.springframework.beans.factory.annotation.Qualifier;
import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml"})
public class ContextTest {
	
	@Resource(name="jrubyGreeter")
	private Greeter jrubyGreeter;
	
	@Resource(name="javaGreeter")
	private Greeter javaGreeter;
	
	@Test
	public void hasJRubyBean() {
		assertThat(jrubyGreeter, notNullValue());
		assertThat(javaGreeter, notNullValue());
		
	    jrubyGreeter.greet();
	    javaGreeter.greet();
	}
}
