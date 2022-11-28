package com.example.newestlinen.controller;

import com.example.newestlinen.constant.LandingISConstant;
import com.example.newestlinen.dto.ApiMessageDto;
import com.example.newestlinen.dto.ErrorCode;
import com.example.newestlinen.dto.ResponseListObj;
import com.example.newestlinen.dto.account.AccountAdminDto;
import com.example.newestlinen.dto.account.AccountDto;
import com.example.newestlinen.dto.account.ForgetPasswordDto;
import com.example.newestlinen.dto.account.LoginDto;
import com.example.newestlinen.exception.RequestException;
import com.example.newestlinen.form.account.*;
import com.example.newestlinen.intercepter.MyAuthentication;
import com.example.newestlinen.jwt.JWTUtils;
import com.example.newestlinen.jwt.UserJwt;
import com.example.newestlinen.mapper.AccountMapper;
import com.example.newestlinen.service.LandingIsApiService;
import com.example.newestlinen.service.UploadService;
import com.example.newestlinen.storage.criteria.AccountCriteria;
import com.example.newestlinen.storage.model.Account;
import com.example.newestlinen.storage.model.Group;
import com.example.newestlinen.utils.projection.repository.AccountRepository;
import com.example.newestlinen.utils.projection.repository.GroupRepository;
import com.example.newestlinen.utils.AESUtils;
import com.example.newestlinen.utils.ConvertUtils;
import com.example.newestlinen.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping("/v1/account")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class AccountController extends ABasicController {
    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    LandingIsApiService landingIsApiService;

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    UploadService uploadService;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListObj<AccountAdminDto>> getList(AccountCriteria accountCriteria, Pageable pageable) {
        if (!isAdmin()) {
            throw new RequestException(ErrorCode.GENERAL_ERROR_UNAUTHORIZED, "Not allow get list");
        }

        System.out.println(accountCriteria.getSpecification());

        ApiMessageDto<ResponseListObj<AccountAdminDto>> apiMessageDto = new ApiMessageDto<>();
        Page<Account> accountPage = accountRepository.findAll(accountCriteria.getSpecification(), pageable);
        ResponseListObj<AccountAdminDto> responseListObj = new ResponseListObj<>();
        responseListObj.setData(accountMapper.fromEntityListToDtoList(accountPage.getContent()));
        responseListObj.setPage(pageable.getPageNumber());
        responseListObj.setTotalPage(accountPage.getTotalPages());
        responseListObj.setTotalElements(accountPage.getTotalElements());

        apiMessageDto.setData(responseListObj);
        apiMessageDto.setMessage("List account success");
        return apiMessageDto;
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<LoginDto> login(@Valid @RequestBody LoginForm loginForm, BindingResult bindingResult) {
        ApiMessageDto<LoginDto> apiMessageDto = new ApiMessageDto<>();
        Account account = accountRepository.findAccountByUsername(loginForm.getUsername());
        if (account == null || !passwordEncoder.matches(loginForm.getPassword(), account.getPassword()) || !Objects.equals(account.getStatus(), LandingISConstant.STATUS_ACTIVE)) {
            throw new RequestException(ErrorCode.GENERAL_ERROR_LOGIN_FAILED, "Login fail, check your username or password");
        }

        //Tao xong tra ve cai gi?
        LocalDate parsedDate = LocalDate.now();
        parsedDate = parsedDate.plusDays(7);

        UserJwt qrJwt = new UserJwt();
        qrJwt.setAccountId(account.getId());
        qrJwt.setKind(account.getKind().toString());
        String appendStringRole = getAppendStringRole(account);

        qrJwt.setUsername(account.getUsername());
        qrJwt.setPemission(landingIsApiService.convertGroupToUri(account.getGroup().getPermissions()) + appendStringRole);
        qrJwt.setUserKind(account.getKind());
        qrJwt.setIsSuperAdmin(account.getIsSuperAdmin());

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(new MyAuthentication(qrJwt));

        log.info("jwt user ne: {}", qrJwt);
        String token = JWTUtils.createJWT(JWTUtils.ALGORITHMS_HMAC, "authenticationToken.getId().toString()", qrJwt, DateUtils.convertToDateViaInstant(parsedDate));
        LoginDto loginDto = new LoginDto();
        loginDto.setFullName(account.getFullName());
        loginDto.setId(account.getId());
        loginDto.setToken(token);
        loginDto.setAvatarPath(account.getAvatarPath());
        loginDto.setUsername(account.getUsername());
        loginDto.setKind(account.getKind());

        apiMessageDto.setData(loginDto);
        apiMessageDto.setMessage("Login account success");
        account.setLastLogin(new Date());
        //update lastLogin
        accountRepository.save(account);
        return apiMessageDto;
    }

    private String getAppendStringRole(Account account) {
//        String appendStringRole = "";
////        if (Objects.equals(account.getKind(), LandingISConstant.USER_KIND_ADMIN)) {
//            appendStringRole =;
//        } else {
//            throw new RequestException(ErrorCode.GENERAL_ERROR_UNAUTHORIZED);
//        }
        return "/account/profile,/account/update_profile,/account/logout";
    }

    @PostMapping(value = "/create_admin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> createAdmin(@Valid @RequestBody CreateAccountAdminForm createAccountAdminForm, BindingResult bindingResult) throws IOException {
        if (!isAdmin()) {
            throw new RequestException(ErrorCode.GENERAL_ERROR_UNAUTHORIZED, "Not allow create.");
        }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();

        Long accountCheck = accountRepository
                .countAccountByUsername(createAccountAdminForm.getUsername());
        if (accountCheck > 0) {
            throw new RequestException(ErrorCode.GENERAL_ERROR_NOT_FOUND, "Username is existed");
        }

        Integer groupKind = LandingISConstant.GROUP_KIND_EMPLOYEE;
        if (createAccountAdminForm.getKind().equals(LandingISConstant.USER_KIND_ADMIN)) {
            groupKind = LandingISConstant.GROUP_KIND_SUPER_ADMIN;
        }
        Group group = groupRepository.findFirstByKind(groupKind);
        if (group == null) {
            throw new RequestException(ErrorCode.GENERAL_ERROR_NOT_FOUND, "Group does not exist!");
        }

        Account account = accountMapper.fromCreateAccountAdminFormToAdmin(createAccountAdminForm);
        account.setGroup(group);
        account.setPassword(passwordEncoder.encode(createAccountAdminForm.getPassword()));
        account.setKind(createAccountAdminForm.getKind());
        account.setAvatarPath(uploadService.uploadImg(createAccountAdminForm.getAvatarPath()));

        accountRepository.save(account);
        apiMessageDto.setMessage("Create account admin success");
        return apiMessageDto;

    }

    @PutMapping(value = "/update_admin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> updateAdmin(@Valid @RequestBody UpdateAccountAdminForm updateAccountAdminForm, BindingResult bindingResult) throws IOException {
        if (!isAdmin()) {
            throw new RequestException(ErrorCode.GENERAL_ERROR_UNAUTHORIZED, "Not allowed to update");
        }

        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Account account = accountRepository.findById(updateAccountAdminForm.getId()).orElse(null);
        if (account == null) {
            throw new RequestException(ErrorCode.GENERAL_ERROR_NOT_FOUND, "Not found");
        }

        accountMapper.mappingFormUpdateAdminToEntity(updateAccountAdminForm, account);
        if (StringUtils.isNoneBlank(updateAccountAdminForm.getPassword())) {
            account.setPassword(passwordEncoder.encode(updateAccountAdminForm.getPassword()));
        }
        account.setFullName(updateAccountAdminForm.getFullName());
        if (StringUtils.isNoneBlank(updateAccountAdminForm.getAvatarPath())) {
            account.setAvatarPath(uploadService.uploadImg(updateAccountAdminForm.getAvatarPath()));
        }

        accountRepository.save(account);

        apiMessageDto.setMessage("Update account admin success");
        return apiMessageDto;

    }

    @GetMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<AccountDto> profile() {
        long id = getCurrentUserId();
        Account account = accountRepository.findById(id).orElse(null);
        if (account == null) {
            throw new RequestException(ErrorCode.GENERAL_ERROR_NOT_FOUND, "Not found account");
        }
        ApiMessageDto<AccountDto> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(accountMapper.fromEntityToAccountDto(account));
        apiMessageDto.setMessage("Get Account success");
        return apiMessageDto;

    }

    @PutMapping(value = "/update_profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> updateProfileAdmin(@Valid @RequestBody UpdateProfileAdminForm updateProfileAdminForm, BindingResult bindingResult) throws IOException {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        long id = getCurrentUserId();
        Account account = accountRepository.findById(id).orElse(null);
        if (account == null) {
            throw new RequestException(ErrorCode.GENERAL_ERROR_NOT_FOUND, "Not found");
        }

        if (StringUtils.isNoneBlank(updateProfileAdminForm.getPassword())) {
            if (!passwordEncoder.matches(updateProfileAdminForm.getOldPassword(), account.getPassword())) {
                throw new RequestException(ErrorCode.GENERAL_ERROR_NOT_MATCH, "Old password not match");
            }
            account.setPassword(passwordEncoder.encode(updateProfileAdminForm.getPassword()));
        }
        if (StringUtils.isNoneBlank(updateProfileAdminForm.getAvatar())) {
            account.setAvatarPath(uploadService.uploadImg(updateProfileAdminForm.getAvatar()));
        }
        accountMapper.mappingFormUpdateProfileToEntity(updateProfileAdminForm, account);
        accountRepository.save(account);

        apiMessageDto.setMessage("Update admin account success");
        return apiMessageDto;

    }

    @PostMapping("/update_profile_user")
    public ApiMessageDto<AccountDto> updateAccountUser(@Valid @RequestBody UpdateProfileUserForm updateProfileUserForm) throws IOException {
        Account account1 = accountRepository.findByUsernameOrEmailOrPhoneLike(updateProfileUserForm.getUsername(),
                updateProfileUserForm.getEmail(), updateProfileUserForm.getPhone());

        if (account1 != null && account1.getId() != getCurrentUserId()) {
            throw new RequestException("Username or Email or PhoneNumber taken");
        }

        Account account = accountRepository.findById(getCurrentUserId()).get();

        if (account == null) {
            throw new RequestException(ErrorCode.GENERAL_ERROR_NOT_FOUND, "Not found");
        }

        ApiMessageDto<AccountDto> apiMessageDto = new ApiMessageDto<>();

        if (StringUtils.isNoneBlank(updateProfileUserForm.getPassword())) {
            if (!passwordEncoder.matches(updateProfileUserForm.getOldPassword(), account.getPassword())) {
                throw new RequestException(ErrorCode.GENERAL_ERROR_NOT_MATCH, "Old password not match");
            }
            account.setPassword(passwordEncoder.encode(updateProfileUserForm.getPassword()));
        }
        if (StringUtils.isNoneBlank(updateProfileUserForm.getAvatar())) {
            uploadService.deleteImg(account.getAvatarPath());
            account.setAvatarPath(uploadService.uploadImg(updateProfileUserForm.getAvatar()));
        }

        if (StringUtils.isNoneBlank(updateProfileUserForm.getFullName())) {
            account.setFullName(updateProfileUserForm.getFullName());
        }

        if (StringUtils.isNoneBlank(updateProfileUserForm.getEmail())) {
            account.setEmail(updateProfileUserForm.getEmail());
        }

        if (StringUtils.isNoneBlank(updateProfileUserForm.getPhone())) {
            account.setPhone(updateProfileUserForm.getPhone());
        }

        AccountDto a = accountMapper.fromUpdateProfileFormUserToObject(updateProfileUserForm);
        accountRepository.save(account);

        apiMessageDto.setData(a);
        apiMessageDto.setMessage("Update user account success");
        return apiMessageDto;
    }

    @Transactional
    @GetMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> logout() {
        SecurityContextHolder.clearContext();
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setMessage("Logout success");
        return apiMessageDto;
    }


    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<AccountAdminDto> get(@PathVariable("id") Long id) {
        if (!isAdmin()) {
            throw new RequestException(ErrorCode.GENERAL_ERROR_UNAUTHORIZED, "Not allowed to get.");
        }
        Account account = accountRepository.findById(id).orElse(null);
        ApiMessageDto<AccountAdminDto> apiMessageDto = new ApiMessageDto<>();
        if (account == null) {
            throw new RequestException(ErrorCode.GENERAL_ERROR_NOT_FOUND, "Not found account");
        }
        apiMessageDto.setData(accountMapper.fromEntityToAccountAdminDto(account));
        apiMessageDto.setMessage("Get shop profile success");
        return apiMessageDto;

    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> delete(@PathVariable("id") Long id) {
        if (!isAdmin()) {
            throw new RequestException(ErrorCode.GENERAL_ERROR_UNAUTHORIZED, "Not allow delete");
        }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Account account = accountRepository.findById(id).orElse(null);
        if (account == null) {
            throw new RequestException(ErrorCode.GENERAL_ERROR_NOT_FOUND, "Account not found");
        }
        landingIsApiService.deleteFile(account.getAvatarPath());
        accountRepository.deleteById(id);
        apiMessageDto.setMessage("Delete Account success");
        return apiMessageDto;
    }

    @PostMapping(value = "/request_forget_password", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ForgetPasswordDto> requestForgetPassword(@Valid @RequestBody RequestForgetPasswordForm forgetForm, BindingResult bindingResult) {
        ApiMessageDto<ForgetPasswordDto> apiMessageDto = new ApiMessageDto<>();
        Account account = accountRepository.findAccountByEmail(forgetForm.getEmail());
        if (account == null) {
            throw new RequestException(ErrorCode.GENERAL_ERROR_NOT_FOUND, "Account not found.");
        }

        String otp = landingIsApiService.getOTPForgetPassword();
        account.setAttemptCode(0);
        account.setResetPwdCode(otp);
        account.setResetPwdTime(new Date());
        accountRepository.save(account);

        //send email
        landingIsApiService.sendEmail(account.getEmail(), "OTP: " + otp, "Reset password", false);

        ForgetPasswordDto forgetPasswordDto = new ForgetPasswordDto();
        String hash = AESUtils.encrypt(account.getId() + ";" + otp, true);
        forgetPasswordDto.setIdHash(hash);

        apiMessageDto.setResult(true);
        apiMessageDto.setData(forgetPasswordDto);
        apiMessageDto.setMessage("Request forget password success, please check email.");
        return apiMessageDto;
    }

    @PostMapping(value = "/forget_password", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<Long> forgetPassword(@Valid @RequestBody ForgetPasswordForm forgetForm, BindingResult bindingResult) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();

        String[] hash = AESUtils.decrypt(forgetForm.getIdHash(), true).split(";", 2);
        Long id = ConvertUtils.convertStringToLong(hash[0]);
        if (Objects.equals(id, 0)) {
            throw new RequestException(ErrorCode.GENERAL_ERROR_WRONG_HASH, "Wrong hash");
        }

        Account account = accountRepository.findById(id).orElse(null);
        if (account == null) {
            throw new RequestException(ErrorCode.GENERAL_ERROR_NOT_FOUND, "account not found.");
        }

        if (account.getAttemptCode() >= LandingISConstant.MAX_ATTEMPT_FORGET_PWD) {
            throw new RequestException(ErrorCode.GENERAL_ERROR_LOCKED, "Account locked");
        }

        if (!account.getResetPwdCode().equals(forgetForm.getOtp()) ||
                (new Date().getTime() - account.getResetPwdTime().getTime() >= LandingISConstant.MAX_TIME_FORGET_PWD)) {
            //tang so lan
            account.setAttemptCode(account.getAttemptCode() + 1);
            accountRepository.save(account);

            throw new RequestException(ErrorCode.GENERAL_ERROR_INVALID, "Code invalid");
        }

        account.setResetPwdTime(null);
        account.setResetPwdCode(null);
        account.setAttemptCode(null);
        account.setPassword(passwordEncoder.encode(forgetForm.getNewPassword()));
        accountRepository.save(account);

        apiMessageDto.setResult(true);
        apiMessageDto.setMessage("Change password success.");
        return apiMessageDto;
    }

    @PostMapping(value = "/verify_account", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> verify(@RequestBody @Valid VerifyForm verifyForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();

        Account account = accountRepository.findById(verifyForm.getId()).orElse(null);
        if (account == null) {
            throw new RequestException(ErrorCode.GENERAL_ERROR_NOT_FOUND, "Account is not found");
        }

        if (!account.getVerifyCode().equals(verifyForm.getOtp()) ||
                (new Date().getTime() - account.getVerifyTime().getTime() >= LandingISConstant.MAX_TIME_VERIFY_ACCOUNT)) {

            throw new RequestException(ErrorCode.GENERAL_ERROR_NOT_MATCH, "Otp not match");
        }

        account.setVerifyTime(null);
        account.setVerifyCode(null);
        account.setStatus(LandingISConstant.STATUS_ACTIVE);
        accountRepository.save(account);

        apiMessageDto.setResult(true);
        apiMessageDto.setMessage("Verify account success.");

        return apiMessageDto;
    }

    @PostMapping("/register")
    public ApiMessageDto<String> Register(@RequestBody @Valid CreateAccountUserForm createAccountUserForm) throws IOException {
        Account account = accountRepository.findByUsernameOrEmailOrPhoneLike(createAccountUserForm.getUsername(),
                createAccountUserForm.getEmail(), createAccountUserForm.getPhone());

        if (account != null) {
            throw new RequestException("Username or Email or PhoneNumber taken");
        }

        account = new Account();
        account.setUsername(createAccountUserForm.getUsername());
        account.setEmail(createAccountUserForm.getEmail());
        account.setPassword(passwordEncoder.encode(createAccountUserForm.getPassword()));
        account.setFullName(createAccountUserForm.getFullName());
        account.setPhone(createAccountUserForm.getPhone());

        if (createAccountUserForm.getAvatar() != null) {
            String avatar = uploadService.uploadImg(createAccountUserForm.getAvatar());
            account.setAvatarPath(avatar);
        }

        Group group = groupRepository.findFirstByKind(LandingISConstant.GROUP_KIND_CUSTOMER);

        account.setGroup(group);

        account.setKind(LandingISConstant.USER_KIND_CUSTOMER);

        try {
            accountRepository.save(account);
        } catch (Exception e) {
            throw new RequestException("invalid form");
        }

        return new ApiMessageDto<String>("Account Created", HttpStatus.OK);
    }
}
