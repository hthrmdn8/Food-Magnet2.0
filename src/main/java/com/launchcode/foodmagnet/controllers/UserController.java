package com.launchcode.foodmagnet.controllers;

@Controller
public class UserController {
    @Autowired
    private UserDetailsService userDetailsService;

    private UserService userService;
    public UserController(UserService userService) {

        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(Model model, UserDto userDto) {
        model.addAttribute("user", userDto);
        return "login";
    }


    @GetMapping("/register")
    public String register(Model model, UserDto userDto) {

        model.addAttribute("user", userDto);
        return "register";
    }

    @PostMapping("/register")
    public String registerSave(@ModelAttribute("user") @Valid UserDto userDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "register";
        }

        User user = userService.findByUsername(userDto.getUsername());
        if (user != null) {
            model.addAttribute("userexist", user);
            return "register";

        }
        userService.save(userDto);
        return "redirect:/register?success";
    }

}