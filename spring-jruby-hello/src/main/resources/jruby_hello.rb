puts $:

ENV['GEM_HOME']= "/home/ogriffin/.rvm/gems/jruby-1.5.2/:/home/ogriffin/.rvm/gems/jruby-1.5.2@global"
ENV['GEM_PATH']= "/home/ogriffin/.rvm/gems/jruby-1.5.2/:/home/ogriffin/.rvm/gems/jruby-1.5.2@global"
require "rubygems"

#Gem.clear_paths

  puts Gem.path
  
#require "nokogiri"
#require "open-uri"
require "chronic"
 
class JRubyHello

  def setName(name)
    @name = name
  end
  
  def getName()
    @name
  end
  
  def greet()
    puts $:
    puts Gem.path
#    doc = Nokogiri::HTML(open('http://www.google.com/search?q=tenderlove'))
#    doc.css('h3.r a.l').each do |link|
#    puts link.content
#    end
    puts Chronic.parse('tomorrow')
    puts "Whatchya! #{@name}"
  end
end

JRubyHello.new
