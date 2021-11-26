package pl.turistica.service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    public String getEmailFromAuthorizationHeader(String authorizationHeader){
        String token = authorizationHeader.split(" ")[1];
        return new String(Base64.decodeBase64(token)).split(":")[0];
    }
}
