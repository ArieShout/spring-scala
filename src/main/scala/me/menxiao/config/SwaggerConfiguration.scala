package me.menxiao.config

import java.util

import com.fasterxml.classmate.TypeResolver
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.builders.{PathSelectors, RequestHandlerSelectors}
import springfox.documentation.schema.{AlternateTypeRule, AlternateTypeRuleConvention, AlternateTypeRules, WildcardType}
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

import scala.collection.mutable
import scala.jdk.CollectionConverters._
import scala.reflect._
import scala.reflect.runtime.universe._

@Configuration
@EnableSwagger2
class SwaggerConfiguration {
  @Bean def api(): Docket = {
    new Docket(DocumentationType.SWAGGER_2)
      .select()
      .apis(RequestHandlerSelectors.withClassAnnotation(classOf[RestController]))
      .paths(PathSelectors.any())
      .build()
      .genericModelSubstitutes(classOf[Option[_]])
  }

  @Bean def alternativeTypeRules(typeResolver: TypeResolver): AlternateTypeRuleConvention = {
    new AlternateTypeRuleConvention {
      override def rules(): util.List[AlternateTypeRule] =
        Seq(
          rule[Iterator[_], util.Iterator[_]],
          rule[Iterable[_], java.lang.Iterable[_]],
          rule[mutable.Buffer[_], util.List[_]],
          rule[mutable.Set[_], util.Set[_]],
          rule[Seq[_], util.List[_]],
          rule[mutable.Seq[_], util.List[_]],
          rule[Set[_], util.Set[_]],
          rule[mutable.Map[_, _], util.Map[_, _]],
          rule[collection.concurrent.Map[_, _], util.concurrent.ConcurrentMap[_, _]],
          rule[Map[_, _], util.Map[_, _]]
        ).asJava

      override def getOrder: Int = org.springframework.core.Ordered.HIGHEST_PRECEDENCE

      private def rule[TOriginal: ClassTag : WeakTypeTag, TAlternate: ClassTag]: AlternateTypeRule = {
        val original = classTag[TOriginal].runtimeClass
        val alternate = classTag[TAlternate].runtimeClass
        val arity = weakTypeOf[TOriginal] match {
          case ExistentialType(_, TypeRef(_, _, args)) => args.length
          case TypeRef(_, _, args) => args.length
        }
        val wildcards = 1.to(arity).map(_ => classOf[WildcardType]).toArray
        AlternateTypeRules.newRule(
          typeResolver.resolve(original, wildcards: _*),
          typeResolver.resolve(alternate, wildcards: _*)
        )
      }
    }
  }
}
