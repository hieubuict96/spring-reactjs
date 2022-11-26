package com.springbootapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.springbootapp.payload.request.user.PhoneNumberDto;
import com.springbootapp.payload.request.user.SendCodeDto;
import com.springbootapp.payload.request.user.SigninDto;
import com.springbootapp.payload.request.user.SigninWithFacebookDto;
import com.springbootapp.payload.request.user.SigninWithGoogleDto;
import com.springbootapp.payload.request.user.SignupDto;
import com.springbootapp.payload.response.user.UserInfoResponse;
import com.springbootapp.payload.response.user.SuccessResponse;
import com.springbootapp.exception.AppException;
import com.springbootapp.exception.BadRequestException;
import com.springbootapp.model.Category;
import com.springbootapp.model.Code;
import com.springbootapp.model.RequestAuthenticateCode;
import com.springbootapp.model.Shop;
import com.springbootapp.model.User;
import com.springbootapp.repository.CategoryRepository;
import com.springbootapp.repository.CodeRepository;
import com.springbootapp.repository.RequestAuthenticateCodeRepository;
import com.springbootapp.repository.ShopRepository;
import com.springbootapp.repository.UserRepository;
import com.springbootapp.security.jwt.TokenProvider;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;

@RestController
@RequestMapping("/api/user")
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ShopRepository shopRepository;

  @Autowired
  private CodeRepository codeRepository;

  @Autowired
  private RequestAuthenticateCodeRepository requestAuthenticateCodeRepository;

  @Autowired
  private TokenProvider tokenProvider;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private CategoryRepository categoryRepository;

  @Value("${twilio.TWILIO_ACCOUNT_SID}")
  private String twilioAccountSid;

  @Value("${twilio.TWILIO_AUTH_TOKEN}")
  private String twilioAuthToken;

  @Value("${twilio.NUMBERPHONE_FROM}")
  private String numberPhoneFrom;

  // void sendCode() {

  // }

  @PostMapping("/signup/send-phone-number")
  public ResponseEntity<SuccessResponse> sendPhoneNumber(@RequestBody @Valid PhoneNumberDto phoneNumberObj,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      throw new BadRequestException("phoneNumber");
    }

    String phoneNumber = phoneNumberObj.getPhoneNumber();
    List<User> user = userRepository.findByPhoneNumber(phoneNumber);
    if (user.size() > 0) {
      throw new BadRequestException("phoneNumberAlreadyUse");
    }

    try {
      Random random = new Random();
      int intCode = random.nextInt(999999);
      String code = String.format("%06d", intCode);

      long timeSendCode = System.currentTimeMillis();

      List<Code> listCode = codeRepository.findByPhoneNumber(phoneNumber);
      if (listCode.size() > 0) {
        Code objCode = listCode.get(0);
        objCode.setCode(code);
        objCode.setTimeSendCode(timeSendCode);
        codeRepository.save(objCode);
      } else {
        Code dtoCode = new Code(phoneNumber, code, timeSendCode);
        codeRepository.save(dtoCode);
      }

      // Twilio.init("AC1f992454125cf5454177e5fe08e50b58",
      // "8c727770c6d6b3e3cd4fba6088b471eb");
      // Message.creator(
      // new PhoneNumber("+84962535248"), new PhoneNumber("+19362516568"), "Mã xác
      // minh của bạn là " + code)
      // .create();

      return ResponseEntity.ok(new SuccessResponse());
    } catch (Exception error) {
      System.out.println(error);
      throw new AppException("serverError");
    }
  }

  @PostMapping("/signup/send-code")
  public ResponseEntity<SuccessResponse> sendCode(@RequestBody SendCodeDto body) {
    String phoneNumber = body.getPhoneNumber();
    String code = body.getCode();

    List<Code> listCode = codeRepository.findByPhoneNumber(phoneNumber);

    if (listCode.size() <= 0) {
      throw new AppException("serverError");
    }

    Long timeVerifyCode = System.currentTimeMillis();

    if (timeVerifyCode - listCode.get(0).getTimeSendCode() < 300000) {
      if (code.equals(listCode.get(0).getCode())) {
        Random random = new Random();
        int intCode = random.nextInt(999999);
        String strCode = String.format("%06d", intCode);

        List<RequestAuthenticateCode> list = requestAuthenticateCodeRepository.findByPhoneNumber(phoneNumber);

        if (list.size() <= 0) {

          // StrCode codeModel = new RequestAuthenticateCode(phoneNumber, strCode);
          // strCodeRepository.save(codeModel);

          RequestAuthenticateCode a = new RequestAuthenticateCode(phoneNumber, null, strCode);

          requestAuthenticateCodeRepository.save(a);
        } else {
          list.get(0).setCode(strCode);
          requestAuthenticateCodeRepository.save(list.get(0));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse());
      }

      throw new BadRequestException("codeIncorrect");
    }

    throw new BadRequestException("timeoutVerifyCode");
  }

  @PostMapping("/signup/resend-code")
  public ResponseEntity<SuccessResponse> resendCode(@RequestBody @Valid PhoneNumberDto phoneNumberObj,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      throw new BadRequestException("phoneNumber");
    }

    try {
      Random random = new Random();
      int intCode = random.nextInt(999999);
      String code = String.format("%06d", intCode);

      long timeSendCode = System.currentTimeMillis();

      String phoneNumber = phoneNumberObj.getPhoneNumber();

      List<Code> listCode = codeRepository.findByPhoneNumber(phoneNumber);
      if (listCode.size() > 0) {
        Code objCode = listCode.get(0);
        objCode.setCode(code);
        objCode.setTimeSendCode(timeSendCode);
        codeRepository.save(objCode);
      } else {
        Code dtoCode = new Code(phoneNumber, code, timeSendCode);
        codeRepository.save(dtoCode);
      }

      // Twilio.init("AC1f992454125cf5454177e5fe08e50b58",
      // "8c727770c6d6b3e3cd4fba6088b471eb");
      // Message.creator(
      // new PhoneNumber("+84962535248"), new PhoneNumber("+19362516568"), "Mã xác
      // minh của bạn là " + code)
      // .create();

      return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse());
    } catch (Exception error) {
      throw new AppException("serverError");
    }
  }

  @PostMapping("/signup")
  public ResponseEntity<Map<String, Object>> signup(@RequestBody @Valid SignupDto body, BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      throw new BadRequestException(bindingResult.getFieldError().getField());
    }

    String firstName = body.getFirstName();
    String lastName = body.getLastName();
    String password = body.getPassword();
    String phoneNumber = body.getPhoneNumber();
    String code = body.getRequestAuthenticateCode();

    try {
      List<RequestAuthenticateCode> list = requestAuthenticateCodeRepository.findByPhoneNumber(phoneNumber);

      if (list.size() <= 0) {
        throw new AppException("serverError");
      }

      if (!code.equals(list.get(0).getCode())) {
        throw new BadRequestException("codeIncorrect");
      }

      BCryptPasswordEncoder b = new BCryptPasswordEncoder();
      String hashPassword = b.encode(password);

      User user = new User(firstName, lastName, hashPassword, phoneNumber);
      User newUser = userRepository.save(user);

      String token = tokenProvider.generateToken(newUser);

      Map<String, Object> response = new HashMap<>();
      Map<String, Object> userResponse = new HashMap<>();

      userResponse.put("_id", newUser.getId());
      userResponse.put("firstName", newUser.getFirstName());
      userResponse.put("lastName", newUser.getLastName());
      userResponse.put("phoneNumber", newUser.getPhoneNumber());
      response.put("user", userResponse);
      response.put("accessToken", token);

      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    } catch (AppException e) {
      throw new AppException(e.getMessage());
    } catch (BadRequestException e) {
      throw new BadRequestException(e.getMessage());
    }
  }

  @PostMapping("/signin")
  public Object signin(@RequestBody SigninDto signinDto) {
    String phoneNumberOrEmail = signinDto.getUser();
    String password = signinDto.getPassword();
    String hashPassword;
    Optional<User> user;

    if (new EmailValidator().isValid(phoneNumberOrEmail, null)) {
      user = userRepository.findOneByEmail(phoneNumberOrEmail);
      if (user.isEmpty()) {
        throw new BadRequestException("signinFail");
      }

      hashPassword = user.get().getHashPassword();
    } else {
      String phoneNumber = "+84" + phoneNumberOrEmail.substring(1);
      user = userRepository.findOneByPhoneNumber(phoneNumber);
      if (user.isEmpty()) {
        throw new BadRequestException("signinFail");
      }

      hashPassword = user.get().getHashPassword();
    }

    boolean isPassword = passwordEncoder.matches(password, hashPassword);
    if (!isPassword) {
      throw new BadRequestException("signinFail");
    }

    UserInfoResponse response = new UserInfoResponse(user.get(), tokenProvider);
    return response;
  }

  @PostMapping("/signin-with-google")
  public UserInfoResponse signinWithGoogle(@RequestBody SigninWithGoogleDto body) {
    String email = body.getData().getEmail();
    String familyName = body.getData().getFamilyName();
    String givenName = body.getData().getGivenName();
    String imageUrl = body.getData().getImageUrl();

    Optional<User> user = userRepository.findOneByEmail(email);

    UserInfoResponse response;

    if (!user.isEmpty()) {
      response = new UserInfoResponse(user.get(), tokenProvider);
    } else {
      User newUser = new User(familyName, givenName, email, null, null, null, imageUrl, null, null);

      User userSaved = userRepository.save(newUser);
      response = new UserInfoResponse(userSaved, tokenProvider);
    }

    return response;
  }

  @PostMapping("/signin-with-facebook")
  public UserInfoResponse signinWithFacebook(@RequestBody SigninWithFacebookDto body) {
    String name = body.getData().getName();
    String userId = body.getData().getUserID();
    String img = body.getData().getPicture().getData().getUrl();
  
    Optional<User> user = userRepository.findOneByUserIdFacebook(userId);

    UserInfoResponse response;

    if (!user.isEmpty()) {
      response = new UserInfoResponse(user.get(), tokenProvider);
    } else {
      User userData = new User("", name, null, null, null, null, img, userId, null);
      User userSaved = userRepository.save(userData);

      response = new UserInfoResponse(userSaved, tokenProvider);

    }

    return response;
  }

  @GetMapping("/get-data")
  public Object getData(HttpServletRequest request) {
    String accessToken = request.getHeader("authorization").substring(7);
    System.out.println(accessToken);

    Object id = tokenProvider.getAuthentication(accessToken);
    return id;
  }
}
