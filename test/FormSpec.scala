package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

class FormSpec extends Specification {
  
  import controllers.Application.helloForm
  
  "HelloWorld form" should {
    
    "require all fields" in {
      val form = helloForm.bind(Map.empty[String,String])
      
      form.hasErrors must beTrue
      form.errors.size must equalTo(2)
      
      form("recipe_name").hasErrors must beTrue
      form("uu_string").hasErrors must beTrue
      form("ru_string").hasErrors must beTrue
      form("relation").hasErrors must beTrue
      
      form.value must beNone
    }
    
    "require name" in {
      val form = helloForm.bind(Map("uu_string" -> "hello robot", "relation" -> "equal", 
          "ru_string" -> "hey, there!"))
      
      form.hasErrors must beTrue
      form.errors.size must equalTo(2)
      
      form("recipe_name").hasErrors must beTrue
      form("uu_string").hasErrors must beFalse
      form("relation").hasErrors must beFalse
      form("ru_string").hasErrors must beFalse
      
      form.data must havePair("uu_string" -> "hello robot")
      form.data must havePair("relation" -> "equal")
      form.data must havePair("ru_string" -> "hey, there!")
      
      form("uu_string").value must beSome.which(_ == "hello robot")
      form("relation").value must beSome.which(_ == "equal")
      form("ru_string").value must beSome.which(_ == "hey, there!")
      form("recipe_name").value must beNone
      
      form.value must beNone
    }
    
    /*
    "validate repeat as numeric" in {
      val form = helloForm.bind(Map("name" -> "Bob", "repeat" -> "xx", "color" -> "red"))
      
      form.hasErrors must beTrue
      form.errors.size must equalTo(1)
      
      form("name").hasErrors must beFalse
      form("repeat").hasErrors must beTrue
      form("color").hasErrors must beFalse
      
      form.data must havePair("color" -> "red")
      form.data must havePair("repeat" -> "xx")
      form.data must havePair("name" -> "Bob")
      
      form("repeat").value must beSome.which(_ == "xx")
      form("color").value must beSome.which(_ == "red")
      form("name").value must beSome.which(_ == "Bob")
      
      form.value must beNone
    }
    
    "be filled" in {
      val form = helloForm.bind(Map("name" -> "Bob", "repeat" -> "10", "color" -> "red"))
      
      form.hasErrors must beFalse
      
      form.data must havePair("color" -> "red")
      form.data must havePair("repeat" -> "10")
      form.data must havePair("name" -> "Bob")
      
      form("repeat").value must beSome.which(_ == "10")
      form("color").value must beSome.which(_ == "red")
      form("name").value must beSome.which(_ == "Bob")
      
      form.value must beSome.which { _ match {
        case ("Bob", 10, Some("red")) => true
        case _ => false
      }}
    }
    * 
    */
    
  }
  
}