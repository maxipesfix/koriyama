package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import views._

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

import play.api.libs.iteratee.Enumerator


object Application extends Controller {

  /**
   * Describes the hello form.
   */
  val helloForm = Form(
    tuple(
      "recipe_name" -> nonEmptyText,
      "uu_string" -> nonEmptyText,
      "relation" -> nonEmptyText,
      "ru_string" -> nonEmptyText))

  val result = false

  // -- Actions

  /**
   * Home page
   */
  def index = Action { implicit request =>
    DB.withConnection { implicit c =>

      val result: Boolean = SQL("Select 1").execute()
      println("Datasource result: " + result)
      Ok(html.index(helloForm))
    }
  }

  /**
   * Handles the form submission.
   */
  def sayHello = Action { implicit request =>

    helloForm.bindFromRequest.fold(
      formWithErrors => BadRequest(html.index(formWithErrors)),
      {
        case (recipe_name, uu_string, relation, ru_string) =>
          Ok(html.hello(recipe_name, uu_string, relation, ru_string))
      })
  }

  /**
   *  to save the file
   */

  def downloadRecipe = Action {
    /*
    Ok.sendFile(
	content = new java.io.File("/tmp/fileToServe.pdf"),
    inline = true
  )
    */
    Ok.stream(
      Enumerator("kiki", "foo", "bar").andThen(Enumerator.eof))
  }

}
