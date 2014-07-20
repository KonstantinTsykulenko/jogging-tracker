package com.tsykul.joggingtracker.service

import com.tsykul.joggingtracker.entity.User
import com.tsykul.joggingtracker.exception.UserExistsException
import com.tsykul.joggingtracker.model.Credentials
import com.tsykul.joggingtracker.repository.UserRepository
import spock.lang.Specification

/**
 * @author KonstantinTsykulenko
 * @since 7/20/2014.
 */
class UserServiceTest extends Specification {
    def "Should save a non existing user"() {
        given:
            def userRepository = Mock(UserRepository)
            def email = "test@gmail.com"
            def password = "password"
            userRepository.findOne(email) >> null
            userRepository.save(_) >> new User(email, password)
            def userService = new UserServiceImpl(userRepository)
        when:
            def user = userService.saveUser(new Credentials(email, password))
        then:
            user.email == email
            user.password == password
    }

    def "Should not save an existing user"() {
        given:
            def userRepository = Mock(UserRepository)
            def email = "test@gmail.com"
            def password = "password"
            userRepository.findOne(email) >> new User(email, password)
            def userService = new UserServiceImpl(userRepository)
        when:
            userService.saveUser(new Credentials(email, password))
        then:
            thrown(UserExistsException)
    }
}
