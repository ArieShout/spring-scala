package me.menxiao.config

import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import springfox.documentation.schema.configuration.ObjectMapperConfigured

@Component
class JacksonScalaModuleConfigurer extends ApplicationListener[ObjectMapperConfigured] {
  override def onApplicationEvent(event: ObjectMapperConfigured): Unit = {
    event.getObjectMapper.registerModule(DefaultScalaModule)
  }
}
