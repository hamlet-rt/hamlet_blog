package hamlet.blog.controller;

import hamlet.blog.entity.User;
import hamlet.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(path = "/profile")
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "/show")
    public String showProfilePage(Model model, Authentication authentication){
        String login = authentication.getName();
        User user = userRepository.findByLogin(login).orElse(null);
        model.addAttribute("user", user);
        return "public/show_profile_page";
    }
}
