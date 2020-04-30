package me.menxiao.controller

import javax.sql.DataSource
import me.menxiao.model.User
import me.menxiao.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpHeaders, HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation._

@RestController
@RequestMapping(path = Array("/api"))
class UserController(
                      @Autowired val userService: UserService,
                      @Autowired val dataSource: DataSource
                    ) {

  @GetMapping(path = Array("/users"))
  def getAllUsers: Seq[User] = {
    userService.listUsers()
  }

  @GetMapping(path = Array("/users/{id}"))
  def getUser(@PathVariable id: Long): Option[User] = {
    userService.getUser(id)
  }

  @PostMapping(path = Array("/users"))
  def createUser(@RequestBody users: User): ResponseEntity[Long] = {
    val id = userService.createUser(users)
    new ResponseEntity(id, new HttpHeaders, HttpStatus.CREATED)
  }
}
