package com.ecommerce.backend.ecommerce_practice.service;

import com.ecommerce.backend.ecommerce_practice.api.modal.LoginBody;
import com.ecommerce.backend.ecommerce_practice.api.modal.RegistrationBody;
import com.ecommerce.backend.ecommerce_practice.exception.EmailFailureException;
import com.ecommerce.backend.ecommerce_practice.exception.UserAlreadyExistException;
import com.ecommerce.backend.ecommerce_practice.exception.UserNotVerified;
import com.ecommerce.backend.ecommerce_practice.model.DAO.LocalUserDAO;
import com.ecommerce.backend.ecommerce_practice.model.DAO.VerificationTokenDAO;
import com.ecommerce.backend.ecommerce_practice.model.DAO.VerifictionMailDAO;
import com.ecommerce.backend.ecommerce_practice.model.LocalUser;
import com.ecommerce.backend.ecommerce_practice.model.VerificationToken;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class UserService{
    private EncryptionService encryptionService;

    public UserService(EncryptionService encryptionService, EmailService emailService, LocalUserDAO localUserDAO, JWTService jwtService, JWTService jwtService1, VerifictionMailDAO verifictionMailDAO,
                       VerificationTokenDAO verificationTokenDAO) {
        this.encryptionService = encryptionService;
        this.localUserDAO = localUserDAO;
        this.jwtService = jwtService1;
        this.emailService = emailService;
        this.verifictionMailDAO = verifictionMailDAO;
        this.verificationTokenDAO = verificationTokenDAO;
    }

    private LocalUserDAO localUserDAO;
    private JWTService jwtService;
    private EmailService emailService;
    private VerifictionMailDAO verifictionMailDAO;
    private final VerificationTokenDAO verificationTokenDAO;

    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistException, EmailFailureException {
        if(localUserDAO.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent()||
                localUserDAO.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent()){
            throw new UserAlreadyExistException();
        }

        LocalUser user = new LocalUser();
        user.setEmail(registrationBody.getEmail());
        user.setFirstName(registrationBody.getFirstName());
        user.setLastName(registrationBody.getLastName());
        user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));
        user.setUsername(registrationBody.getUsername());
        LocalUser savedUser = localUserDAO.save(user);
        VerificationToken verificationToken = createVerificationToken(savedUser);
        emailService.sendVerificationEmail(verificationToken);
        verificationTokenDAO.save(verificationToken);
        return savedUser;
    }
    public VerificationToken createVerificationToken(LocalUser user){
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(jwtService.generateVerificationJWT(user));
        verificationToken.setLocalUser(user);
        verificationToken.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        user.getVerificationTokens().add(verificationToken);
        return verificationToken;

    }
    @Transactional
    public boolean verifyUser(String token){
        Optional<VerificationToken> opToken = verificationTokenDAO.findByToken(token);
        if(opToken.isPresent()){
            VerificationToken token1 = opToken.get();
            LocalUser user = token1.getLocalUser();
            if(!user.isEmailVerified()){
                user.setEmailVerified(true);
                localUserDAO.save(user);
                verificationTokenDAO.deleteByLocalUser(user);
                return true;
            }
        }
        return false;
    }

    public String loginUser(LoginBody loginBody) throws EmailFailureException, UserNotVerified {
        Optional<LocalUser> opuser = localUserDAO.findByUsernameIgnoreCase(loginBody.getUsername());
        if(opuser.isPresent()){
            LocalUser user = opuser.get();
            if(encryptionService.checkPassword(loginBody.getPassword(), user.getPassword()))
                if(user.isEmailVerified()){
                    return jwtService.generateJWT(user);
                }else{
                    List<VerificationToken> verificationTokens = user.getVerificationTokens();
                    Boolean resend=verificationTokens.size()==0||verificationTokens.get(0).
                            getCreatedTime().before(new Timestamp(System.currentTimeMillis()-(60*60*1000)));
                    if(resend){
                        VerificationToken verificationToken = createVerificationToken(user);
                        emailService.sendVerificationEmail(verificationToken);
                        verifictionMailDAO.save(verificationToken);
                    }
                    throw new UserNotVerified(resend);
                }

        }
        return null;
    }

}
