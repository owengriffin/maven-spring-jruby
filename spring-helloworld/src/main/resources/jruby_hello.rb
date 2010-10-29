class JRubyHello

  def setName(name)
    @name = name
  end
  
  def getName()
    @name
  end
  
  def greet()
    puts "Whatchya! #{@name}"
  end
end

JRubyHello.new
