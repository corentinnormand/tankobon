package io.github.alessandrojean.tankobon.infrastructure.security

import io.github.alessandrojean.tankobon.domain.persistence.TankobonUserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

@Component
class TankobonUserDetailsService(
  private val userRepository: TankobonUserRepository
) : UserDetailsService {

  override fun loadUserByUsername(username: String): UserDetails {
    return userRepository.findByEmailIgnoreCaseOrNull(username)?.let {
      TankobonPrincipal(it)
    } ?: throw UsernameNotFoundException(username)
  }
}
