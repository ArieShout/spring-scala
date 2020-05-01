package me.menxiao

import java.util.Base64

import me.menxiao.model.User
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.{HttpEntity, HttpHeaders, MediaType}
import org.springframework.test.context.junit4.SpringRunner

import scala.jdk.CollectionConverters._

@RunWith(classOf[SpringRunner])
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class UserTests {
  @Autowired
  var template: TestRestTemplate = _

  @Test def testPostCreateUser(): Unit = {
    val headers = new HttpHeaders
    headers.add("Authorization", "Basic " + new String(Base64.getEncoder.encode(("root" + ":" + "root").getBytes)))
    headers.setContentType(MediaType.APPLICATION_JSON)
    headers.setAccept(List(MediaType.APPLICATION_JSON).asJava)
    val user = new User
    user.setId(101)
    user.setUsername("Test")
    user.setPassword("Test")
    user.setEnabled(true)
    val entity = new HttpEntity(user, headers)
    val result = template.postForObject("/api/users", entity, classOf[String])
    println(result)
  }
}
