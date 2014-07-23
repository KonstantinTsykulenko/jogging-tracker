package com.tsykul.joggingtracker.service

import com.tsykul.joggingtracker.entity.User
import com.tsykul.joggingtracker.exception.UserExistsException
import com.tsykul.joggingtracker.model.Credentials
import com.tsykul.joggingtracker.repository.UserRepository
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

/**
 * @author KonstantinTsykulenko
 * @since 7/20/2014.
 */
class UserServiceTest extends Specification {
    def "Should save a non existing user"() {
        given:
            def userRepository = Mock(UserRepository)
            def passwordEncoder = Mock(PasswordEncoder)
            def email = "test@gmail.com"
            def password = "password"
            userRepository.findOne(email) >> null
            userRepository.save(_) >> new User(email, password)
            def userService = new UserServiceImpl(userRepository, passwordEncoder)
        when:
            def user = userService.saveUser(new Credentials(email, password))
        then:
            user.email == email
            user.password == password
    }

    def "Should not save an existing user"() {
        given:
            def userRepository = Mock(UserRepository)
            def passwordEncoder = Mock(PasswordEncoder)
            def email = "test@gmail.com"
            def password = "password"
            userRepository.findOne(email) >> new User(email, password)
            def userService = new UserServiceImpl(userRepository, passwordEncoder)
        when:
            userService.saveUser(new Credentials(email, password))
        then:
            thrown(UserExistsException)
    }

    def "Should load an existing user"() {
        given:
            def userRepository = Mock(UserRepository)
            def passwordEncoder = Mock(PasswordEncoder)
            def email = "test@gmail.com"
            def password = "password"
            userRepository.findOne(email) >> new User(email, password)
            def userService = new UserServiceImpl(userRepository, passwordEncoder)
        when:
            def user = userService.loadUserByUsername(email)
        then:
            user.email == email
            user.password == password
    }

    def "Should throw exception when loading non existing user"() {
        given:
            def userRepository = Mock(UserRepository)
            def passwordEncoder = Mock(PasswordEncoder)
            def email = "test@gmail.com"
            userRepository.findOne(email) >> null
            def userService = new UserServiceImpl(userRepository, passwordEncoder)
        when:
            userService.loadUserByUsername(email)
        then:
            thrown(UsernameNotFoundException)
    }
}
