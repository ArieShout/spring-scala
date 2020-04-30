package me.menxiao.config

import java.util

import com.fasterxml.classmate.TypeResolver
import org.springframework.stereotype.Component
import springfox.documentation.schema.{AlternateTypeRule, AlternateTypeRuleConvention, AlternateTypeRules, WildcardType}

import scala.collection.mutable
import scala.jdk.CollectionConverters._
import scala.reflect._
import scala.reflect.runtime.universe._

@Component
class ScalaSwaggerConvention(typeResolver: TypeResolver) extends AlternateTypeRuleConvention {

  override def rules(): util.List[AlternateTypeRule] = {
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
  }

  override def getOrder: Int = org.springframework.core.Ordered.HIGHEST_PRECEDENCE

  private def rule[TOriginal: ClassTag : TypeTag, TAlternate: ClassTag]: AlternateTypeRule = {
    val original = classTag[TOriginal].runtimeClass
    val alternate = classTag[TAlternate].runtimeClass
    val arity = typeOf[TOriginal] match {
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
