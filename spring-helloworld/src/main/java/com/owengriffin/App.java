package com.owengriffin;

import org.springframework.core.io.ClassPathResource;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;

/**
 * Hello world!
 * 
 */
public class App {
	public static void main(String[] args) {
		BeanFactory factory = new XmlBeanFactory(new ClassPathResource(
				"application-context.xml"));

		SayHello hello = (SayHello) factory.getBean("hello");
		hello.greet();
		
		Greeter greeter = (Greeter) factory.getBean("jrubyhello");
		greeter.greet();
	}
}
