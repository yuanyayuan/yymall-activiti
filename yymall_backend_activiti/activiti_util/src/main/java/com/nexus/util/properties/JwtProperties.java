package com.nexus.util.properties;


public class JwtProperties {

    private static final JwtProperties jwtProperties = new JwtProperties();

    private JwtProperties(){}

    public static JwtProperties getJwtProperties(){
        return jwtProperties;
    }

    public static final String JWT_PREFIX = "jwt";

    /**
     *
     * JWT存储的请求头
     *
    **/
    private String header = "Authorization";

    /**
     *
     * JWT加解密使用的密钥
     *
    **/
    private String secret = "defaultSecret";

    /**
     *
     * 默认是七天,JWT的超期限时间(60*60*24*7)
     *
    **/
    private Long expiration = 604800L;

    private String head = "Bearer ";

    private String authPath = "login";

    private String md5Key = "randomKey";

    public static String getJwtPrefix() {
        return JWT_PREFIX;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    public String getAuthPath() {
        return authPath;
    }

    public void setAuthPath(String authPath) {
        this.authPath = authPath;
    }

    public String getMd5Key() {
        return md5Key;
    }

    public void setMd5Key(String md5Key) {
        this.md5Key = md5Key;
    }
}