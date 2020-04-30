package me.menxiao.repository

import me.menxiao.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
trait UserRepository extends CrudRepository[User, Long] {
  def findUserByUsername(username: String): User
}
