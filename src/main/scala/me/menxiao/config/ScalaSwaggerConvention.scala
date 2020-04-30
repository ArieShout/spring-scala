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
      newRule[Iterator[_], util.Iterator[_]],
      newRule[Iterable[_], java.lang.Iterable[_]],
      newRule[mutable.Buffer[_], util.List[_]],
      newRule[mutable.Set[_], util.Set[_]],
      newRule[Seq[_], util.List[_]],
      newRule[mutable.Seq[_], util.List[_]],
      newRule[Set[_], util.Set[_]],
      newRule[mutable.Map[_, _], util.Map[_, _]],
      newRule[collection.concurrent.Map[_, _], util.concurrent.ConcurrentMap[_, _]],
      newRule[Map[_, _], util.Map[_, _]]
    ).asJava
  }

  override def getOrder: Int = org.springframework.core.Ordered.HIGHEST_PRECEDENCE

  private def newRule[TOriginal: ClassTag : TypeTag, TAlternate: ClassTag]: AlternateTypeRule = {
    val original = classTag[TOriginal].runtimeClass
    val alternate = classTag[TAlternate].runtimeClass
    val arity = typeTag[TOriginal].tpe match {
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
