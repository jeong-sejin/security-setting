package com.todo.securitySetting.controller;

import com.todo.securitySetting.entity.User;
import com.todo.securitySetting.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Resource(name="userService")
    private UserService userService;

    @Autowired
    MessageSource messageSource;

    @GetMapping("/loginForm")
    public String loginForm(@RequestParam(required = false) String errorCode,HttpServletRequest request, Model model, Authentication authentication){
        String uri = request.getHeader("Referer"); //이전 경로
        String errMsg = null;

        //이미 인증된 사용자의 로그인 페이지 접근 방지
        if(authentication!=null) return "duplicateLoginAlert";
        //이전 경로가 로그인 관련 페이지가 아닐 경우에만 저장, 속성값이 null이 되면 오류가 발생하므로 이 경우도 처리한다.
        if(uri!=null && !(uri.contains("/login"))) request.getSession().setAttribute("prevPage", uri);

        if(errorCode != null){
            if(errorCode.equals("1")){
                errMsg = messageSource.getMessage("login.errorMessage.1",null,null);
            }else if(errorCode.equals("2")){
                errMsg = messageSource.getMessage("login.errorMessage.2",null,null);
            }else if(errorCode.equals("3")){
                errMsg = messageSource.getMessage("login.errorMessage.3",null,null);
            }else if(errorCode.equals("4")){
                errMsg = messageSource.getMessage("login.errorMessage.4",null,null);
            }else if(errorCode.equals("5")){
                errMsg = messageSource.getMessage("login.errorMessage.5",null,null);
            }

            model.addAttribute("errMsg",errMsg);
        }

        return "login";
    }

    @GetMapping("/registForm")
    public String registrationForm(Model model) {
        model.addAttribute("user", User.builder().build());
        return "regist";
    }

    @PostMapping("/registration")
    public String registration(Model model, @Validated User user, BindingResult bindingResult){
        try{
            User validUser = userService.getUserByUserid(user.getUserid());
            if(!user.getPassword().equals(user.getConfirmPassword())){
                bindingResult.rejectValue("confirmPassword","error.user","비밀번호와 비밀번호 확인이 맞지 않습니다.");
                return "regist";
            }

            if(validUser != null){
                bindingResult.rejectValue("userid","error.user","이미 존재하는 id입니다.");
                return "regist";
            }

            userService.createUser(user);
            model.addAttribute("RESULT_CODE", "1");
            model.addAttribute("Message", "회원가입에 성공하셨습니다. 회원가입한 id로 로그인해주세요.");


        }catch (Exception e){
            model.addAttribute("RESULT_CODE", "-1");
            model.addAttribute("Message", "회원가입에 실패하셨습니다. 관리자에게 문의하세요.");
        }

        return "regist";
    }
}
