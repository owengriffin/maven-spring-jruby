require "rubygems"
require "chronic"
 
class JRubyHello
  def greet()
    tomorrow = Chronic.parse('tomorrow')
    puts "I'll say hello #{tomorrow}"
  end
end

JRubyHello.new
