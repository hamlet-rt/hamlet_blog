package hamlet.blog.controller;

import hamlet.blog.entity.User;
import hamlet.blog.repository.UserRepository;
import hamlet.blog.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
@RequestMapping(path = "/registration")
public class RegistrationController {

    @GetMapping
    public String showRegistrationPage(){
        return "public/registration_page";
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @PostMapping
    public String proceedRegistration(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String login,
            @RequestParam String password
    ){
        User user = new User();
        user.setName(firstName);
        user.setSurname(lastName);
        user.setLogin(login);
        user.setPassword(password);
        user.setUserRole(userRoleRepository.getById(3L));
        user.setDate(LocalDateTime.now());
        userRepository.save(user);
        return "public/success_registration";
    }


}
