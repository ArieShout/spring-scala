package me.menxiao

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class SpringScalaApplication

object SpringScalaApplication extends App {
  SpringApplication.run(classOf[SpringScalaApplication], args: _*)
}
