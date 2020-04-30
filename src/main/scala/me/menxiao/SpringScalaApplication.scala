package me.menxiao

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.builders.{PathSelectors, RequestHandlerSelectors}
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@SpringBootApplication
@EnableSwagger2
class SpringScalaApplication {
  @Bean def api(): Docket = {
    new Docket(DocumentationType.SWAGGER_2)
      .select()
      .apis(RequestHandlerSelectors.withClassAnnotation(classOf[RestController]))
      .paths(PathSelectors.any())
      .build()
      .genericModelSubstitutes(classOf[Option[_]])
  }
}

object SpringScalaApplication extends App {
  SpringApplication.run(classOf[SpringScalaApplication], args: _*)
}
