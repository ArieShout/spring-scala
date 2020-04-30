package me.menxiao.service

import me.menxiao.model.User
import me.menxiao.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import scala.jdk.CollectionConverters._
import scala.jdk.OptionConverters._

@Service
class UserService(@Autowired private val userRepository: UserRepository) {
  def listUsers(): Seq[User] = userRepository.findAll.asScala.toSeq

  def getUser(id: Long): Option[User] = userRepository.findById(id).toScala

  def createUser(users: User): Long = {
    userRepository.save(users)
    users.id
  }
}
