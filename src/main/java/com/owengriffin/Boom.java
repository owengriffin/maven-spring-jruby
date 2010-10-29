package com.owengriffin;

import org.jruby.Ruby;
import org.jruby.embed.ScriptingContainer;
import org.jruby.internal.runtime.GlobalVariables;
import org.springframework.beans.factory.annotation.Autowired;

public class Boom {
	
	@Autowired
	private Ruby scriptingContainer;
	
	public Ruby getScriptingContainer() {
		return this.scriptingContainer;
	}
	
public Boom() {
	
	String home = "/home/ogriffin/.rvm/rubies/jruby-1.5.2/";
	System.setProperty("jruby.home", home);
	System.setProperty("jruby.gem.home", "/home/ogriffin/.rvm/gems/jruby-1.5.2/gems");
	
//	System.setProperty("jruby.gem.home", "/home/ogriffin/.rvm/gems/jruby-1.5.2");
//	System.setProperty("jruby.gemhome", "/home/ogriffin/.rvm/gems/jruby-1.5.2");
//	System.out.println(System.getenv("GEM_HOME"));
	//Ruby.getGlobalRuntime().setJRubyHome(home);
	//Ruby.getCurrentInstance().setJRubyHome("/home/ogriffin/.rvm/rubies/jruby-1.5.2/lib/");
}
}
