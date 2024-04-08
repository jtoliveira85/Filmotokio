package com.tokioschool.filmotokio.controller.mvc;

import com.tokioschool.filmotokio.domain.User;
import com.tokioschool.filmotokio.dto.CreateUserDTO;
import com.tokioschool.filmotokio.dto.UserDTO;
import com.tokioschool.filmotokio.security.SecurityUtils;
import com.tokioschool.filmotokio.service.FileService;
import com.tokioschool.filmotokio.service.RoleService;
import com.tokioschool.filmotokio.service.UserService;
import com.tokioschool.filmotokio.service.impl.AuthService;
import com.tokioschool.filmotokio.util.FormVal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Service
public class UserRegisterController extends UserController {


    private final Logger logger = LoggerFactory.getLogger(UserRegisterController.class);

    private final RoleService roleService;
    private final FileService fileService;

    private final AuthService authService;


    @Autowired
    public UserRegisterController(UserService userService, RoleService roleService, FileService fileService, AuthService authService) {
        super(userService);
        // UserService From UserController Class
        this.roleService = roleService;
        this.fileService = fileService;
        this.authService = authService;
    }


    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        dataBinder.registerCustomEditor(Date.class, null,  new CustomDateEditor(dateFormat, true));
    }

    @GetMapping("/register")
    public ModelAndView register() {


        ModelAndView mv = new ModelAndView();
        mv.addObject("newUser", new CreateUserDTO());

        //List Of Roles
        mv.addObject("rolesList", roleService.findAll());

        mv.setViewName("user/registo");
        return mv;
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid @ModelAttribute("newUser") CreateUserDTO newUser, BindingResult result, RedirectAttributes redirectAttributes) {

        ModelAndView mv = new ModelAndView();


        logger.info("User Register Controler -> User Register");

        if (result.hasErrors()) {
            mv.addObject("rolesList", roleService.findAll());
            mv.setViewName("user/registo");
            return mv;
        }
        if (userService.existsByUsername(newUser.getUsername())) {
            result.rejectValue("username", null, FormVal.USERNAME_EXISTS.toString());
            logger.info("User Register Controler -> User Register -> Username already Exists - Username: " + newUser.getUsername());
            mv.addObject("rolesList", roleService.findAll());
            mv.setViewName("user/registo");
            return mv;
        }
        if (userService.existsByEmail(newUser.getEmail())) {
            result.rejectValue("email", null, FormVal.EMAIL_EXISTS);
            logger.info("User Register Controler -> User Register -> Email already Exists - Email: " + newUser.getEmail());
            mv.addObject("rolesList", roleService.findAll());
            mv.setViewName("user/registo");
            return mv;
        }


        boolean isSaved = userService.save(newUser);

        if (isSaved) {
            redirectAttributes.addFlashAttribute("message", "User Registado com sucesso");
        }


        logger.info("User Register Controler -> Saving User... " + newUser.getUsername());
        mv.setViewName("redirect:/");
        return mv;
    }

    @GetMapping("/myprofile")
    public String profile(UserDTO currUser, Model model, HttpServletRequest request) {

        model.addAttribute("currUser", userService.findByUsername(request.getRemoteUser()));

        return "user/profile";
    }

    @PostMapping("/update")
    public String editProfile(@Valid @ModelAttribute("currUser") UserDTO currUser, BindingResult result, @RequestParam("userImage") MultipartFile imageFile, RedirectAttributes redirectAttributes) {

        User oldUser = userService.findById(SecurityUtils.getLoggedUserId());

        if (result.hasErrors()) {
            System.out.println("ERRRO 1");
            System.out.println(result.toString());
            return "user/profile";
        }

        if (userService.existsByEmail(currUser.getEmail()) && !Objects.equals(currUser.getEmail(), oldUser.getEmail())) {
            result.rejectValue("email", null, FormVal.EMAIL_EXISTS);
            logger.info("User Register Controler -> User Register -> Email already Exists - Email: " + currUser.getEmail());
            System.out.println("ERRRO 2");
            return "user/profile";
        }

        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("CURRUSER CONTROLLER: " + currUser.getEmail() + " " + currUser.getUsername());

        if (!imageFile.isEmpty()) {
            fileService.uploadUserFile(imageFile, currUser);
        }

        boolean isUpdated = userService.update(currUser);


        if (isUpdated) {
            redirectAttributes.addFlashAttribute("message", "User Editado com sucesso");
        }

        return "redirect:/";
    }

}
