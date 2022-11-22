package com.example.newestlinen.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.newestlinen.intercepter.MyAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

@Slf4j
public class JWTUtils {
    public static final String ALGORITHMS_HMAC = "HMAC";
    public static final String ALGORITHMS_RSA = "RSA";
    private static final String PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCZh/hWaOAeXL9nHO6njl3coTjt0CelY7rbOpaG7ZovP//xPOsgJDq9MAsPho1e9Mmn/lVS1hj1CkPRx4n5QIqeGuzBF7CF6NcEy68PtYNWRM0SKv62ZLY7QGT+8f/dWrsZLI/GzV2nQrbpl+lFKqQKMzgdFYjLNyn5AxWmI/L5MLFGqk7S3PCNef9ucj2+T4Nlgb7UP0aQb49hHeEUliD9aAkP7Q0BzTQlWXcHGDDRLDdmhhal8h8Z1qjtxpL+8+HuzfdioyuGHOFwucqy2J25VzkTARAGTVmsIq+Bq+EkjOEVHz/JLNOz7cUxqZMtrcKPxwPwVtHJlh5ntqpb2LbbAgMBAAECggEAZBcdkg+7FHA4o9tbxsZscJp2h6stPYhf1+5BFeOuoeEiVT32tzSHSrEYdN2uKlqBYNDRBTjoek9f2VIxo9osxFgfQt7TyOj7zHeRQDeAUOx3mMGI3pWXq9cWQJK50LAGy6mebeeSh6lZn73WaK4c1T2A9o0Eah8jcOBO68oB6+uLjZxc1QUOUTVqZjv2UYfKlGOhRRehJiGxSNY/oGX44T/VjkUfNkp4Rho4SCZ4BldJhqDqwSEA5fu/Z/OSEQTna6p5W8KKdNOpbS9slIGu0ee8H4pj9Dr0wUI9Mq9kZFJumZp2yyEkcFmV4N5r3uKQToZGjH/N3f6p6EfoIw+P8QKBgQDdH40xLpiu60VN/OXX8YHmRpv81C78qvu4tXzBzgliXOzjX8pfS4DBByLEkV7bBUGC6hxdR86a7JUV7cl1Nb9GgbYJ9z9pEfSDaDettf1FtHxgG850jNCiOa8NzG3oqOGC5K05Ni/NowWzXi9hyMw6/f87Ztr3TAEFrg2+J8rI2QKBgQCxvzRNKpj/GuBfbgXogJrNdJ1zYiZu1MvU7fxHXObRXKfOKkGlt3BhEThwPHY1NthBzaQqeATQaqgFEuSdoS+3Fj+zbes4mRcpzz7VJwBpxnDdP4QG7APft3u8YMKRoqpHI3d+azQB0iXvBd9j4ROaGgG0qd+XRpGFIqZA+cMM0wKBgQCtxbDg2LyWoj/5tOgv1xR9kjnd7CXs3LYrhzy+1ZtcWKi1Wz8fuqc5itEQ5ylDOBMuvaqCx/6ek5PqAs/SM+XYk/EqTcrgWILujgVLl3ytvdQMd228/DitdGWUk3ZAZA+tHObg0iupEBdXyYm07+dH6PoehYtI0sru2sTkDkN3qQKBgF1Qij6wzF2xWHYBJuznB14xiK+YqaCp6tDAmB9G8I6dXCNqH8jGQyNSjRSvSUeqoWH6eeMp2y0nsUnon0j55Dj0X0Dtxf2SsYxrlwsETL07RumAfOm9YlNC2p0NF1ip00dBoWwnD8ic8PlJVfvzn/zZBmg4fn/GtoL0q9+UwAetAoGAPEIMIx1bpzt7xv6fkuV3LVR04Ll/62IJkeGyD7ligeuXqVyPvZGYjWZNA5akIOmqH5aRC6GVp2Bdoq6Lxk0k1pHuWCqfwhwC1q/Wt9SzPphxalCoGDGdrFU4VMrONUN2aefhVNTEeqk+naiI94D6xQkX8R/T/bIQqc+1pLLjM/w=";
    private static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmYf4VmjgHly/Zxzup45d3KE47dAnpWO62zqWhu2aLz//8TzrICQ6vTALD4aNXvTJp/5VUtYY9QpD0ceJ+UCKnhrswRewhejXBMuvD7WDVkTNEir+tmS2O0Bk/vH/3Vq7GSyPxs1dp0K26ZfpRSqkCjM4HRWIyzcp+QMVpiPy+TCxRqpO0tzwjXn/bnI9vk+DZYG+1D9GkG+PYR3hFJYg/WgJD+0NAc00JVl3Bxgw0Sw3ZoYWpfIfGdao7caS/vPh7s33YqMrhhzhcLnKstiduVc5EwEQBk1ZrCKvgavhJIzhFR8/ySzTs+3FMamTLa3Cj8cD8FbRyZYeZ7aqW9i22wIDAQAB";
    private static final String HMAC_KEY = "AGBHRJJUBJJK";

    private JWTUtils(){

    }

    public static String getCurrentUserJWT() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        try {
            MyAuthentication authentication = (MyAuthentication)securityContext.getAuthentication();
            if(authentication!=null){
                return  authentication.getName();
            }
        }catch (Exception e){
            return  null;
        }
        return null;
    }



    public static String createJWT(String algorithms, String id, UserJwt qrJwt, Date expiredDate) {
        try {
            if ("HMAC".equalsIgnoreCase(algorithms)) {
                return createHMACJWT(id, qrJwt.toClaim(), expiredDate);
            }
            // default RSA
            return createRSAJWT(id, qrJwt.toClaim(), expiredDate);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    private static String createRSAJWT(String id, String claim, Date expiredDate){
        try {
            RSA rsa = new RSA();
            RSAPublicKey publicKey = (RSAPublicKey) rsa.stringToPublicKey(PUBLIC_KEY);
            RSAPrivateKey privateKey = (RSAPrivateKey) rsa.stringToPrivate(PRIVATE_KEY);
            Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);
            Date d = new Date();
            return JWT.create().withJWTId(id).withKeyId(id).withIssuedAt(d).withNotBefore(d)
                    .withExpiresAt(expiredDate).withClaim("data",claim).sign(algorithm);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    private static String createHMACJWT(String id, String claim, Date expiredDate) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(HMAC_KEY);
            Date d = new Date();

            com.auth0.jwt.JWTCreator.Builder builder  = JWT.create();
            builder.withKeyId(id).withIssuedAt(d).withNotBefore(d)
                    .withExpiresAt(expiredDate).withClaim("data",claim);
            return builder.sign(algorithm);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }


    public static DecodedJWT isLogin(String algorithms, String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        DecodedJWT decodedJWT = verifierJWT(algorithms, token);
        if (decodedJWT == null) {
            return null;
        }
        if (decodedJWT.getExpiresAt().before(new Date())) {
            log.info("decodedJWT.getExpiresAt().before(new Date())");
            return null;
        }
        return decodedJWT;
    }
    public static DecodedJWT verifierJWT(String algorithms, String token) {
        try {
            if ("HMAC".equalsIgnoreCase(algorithms)) {
                Algorithm algorithm = Algorithm.HMAC512(HMAC_KEY);
                JWTVerifier verifier = JWT.require(algorithm)
                        .acceptLeeway(1) //1 sec for nbf and iat
                        .acceptExpiresAt(5) //5 secs for exp
                        .build();
                return verifier.verify(token);
            }
            //default RSA
            RSA rsa = new RSA();
            RSAPublicKey publicKey = (RSAPublicKey) rsa.stringToPublicKey(PUBLIC_KEY);
            RSAPrivateKey privateKey = (RSAPrivateKey) rsa.stringToPrivate(PRIVATE_KEY);
            Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .acceptLeeway(1) //1 sec for nbf and iat
                    .acceptExpiresAt(5) //5 secs for exp
                    .build();
            return verifier.verify(token);

        } catch (Exception e) {
            log.error("verifierJWT>>"+e.getMessage(), e);
        }
        return null;
    }

    public static UserJwt getSessionFromToken(DecodedJWT decodedJWT){
        if(decodedJWT == null){
            return null;
        }
        return UserJwt.decode(decodedJWT.getClaim("data").asString());
    }





}
