package com.yueqiu.framework.web.service;

import com.yueqiu.common.constant.CacheConstants;
import com.yueqiu.common.constant.Constants;
import com.yueqiu.common.constant.UserConstants;
import com.yueqiu.common.core.domain.model.LoginUser;
import com.yueqiu.common.core.redis.RedisCache;
import com.yueqiu.common.utils.AddressUtils;
import com.yueqiu.common.utils.Id.IdUtils;
import com.yueqiu.common.utils.IpUtils;
import com.yueqiu.common.utils.ServletUtils;
import com.yueqiu.common.utils.StringUtils;
import com.yueqiu.common.utils.date.DateUtils;
import eu.bitwalker.useragentutils.UserAgent;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class TokenService {
    @Value("${token.header}")
    private String header;

    // 令牌秘钥
    @Value("${token.secret}")
    private String secret;
    @Autowired
    private RedisCache redisCache;
    @Value("${token.expireTime}")
    private int expireTime;
    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

    /**
     * 拿到当前登录用户信息
     *
     * @param request
     * @return
     */
    public LoginUser getLoginUser(HttpServletRequest request) {
        //通过token拿到syslogininfor
        String token = getToken(request);
        //解析token
        if (StringUtils.isNotEmpty(token)) {
            try {
                Claims claims = parseToken(token);
                String uuid = (String) claims.get(Constants.LOGIN_USER_KEY);
                String tokenKey = getTokenKey(uuid);
                LoginUser loginUser = redisCache.getCacheObject(tokenKey);
                return loginUser;
            } catch (Exception e) {

            }
        }
        return null;
    }

    private String getTokenKey(String uuid) {
        return CacheConstants.LOGIN_TOKEN_KEY + uuid;
    }

    private Claims parseToken(String token) {

        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(header);
        if (!StringUtils.isEmpty(token) && StringUtils.startsWith(token, Constants.TOKEN_PREFIX)) {
            return token.replace(Constants.TOKEN_PREFIX,"");
        }
        return null;
    }

    public void delLogininfo(String token) {
        if (StringUtils.isNotEmpty(token)) {
            String userKey = getTokenKey(token);
            redisCache.deleteObject(userKey);
        }
    }

    public void verifyToken(LoginUser loginUser) {
        long expireTime = loginUser.getExpireTime();
        long nowTime = System.currentTimeMillis();
        if(nowTime-expireTime<= MILLIS_MINUTE_TEN){
            refreshToken(loginUser);
        }
    }



    /**
     * token刷新
     * @param loginUser
     */
    private void refreshToken(LoginUser loginUser) {
        loginUser.setExpireTime(System.currentTimeMillis()+expireTime*MILLIS_MINUTE);
        loginUser.setLoginTime(System.currentTimeMillis());
        String tokenKey = getTokenKey(loginUser.getToken());
        redisCache.setCacheObject(tokenKey,loginUser,expireTime, TimeUnit.MINUTES);
    }

    /**
     * 创建token
     * @param loginUser
     * @return
     */
    public String createToken(LoginUser loginUser) {
        String token = IdUtils.fastUUID();
        Map<String,Object> map = new HashMap<>();
        map.put(Constants.LOGIN_USER_KEY,token);
        loginUser.setToken(token);
        refreshToken(loginUser);
        setUserAgent(loginUser);
        return createToken(map);
    }

    /**
     * 设置用户代理信息
     * @param loginUser
     */
    private void setUserAgent(LoginUser loginUser) {
        UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        loginUser.setOs(String.valueOf(userAgent.getOperatingSystem()));
        loginUser.setIpaddr(IpUtils.getIpAddr());
        loginUser.setBrowser(String.valueOf(userAgent.getBrowser()));
        loginUser.setLoginLocation(AddressUtils.getRealAddressByIP(IpUtils.getIpAddr()));
    }

    private String createToken(Map<String, Object> map) {
        String token = Jwts.builder()
                .setClaims(map)
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
        return token;
    }

    /**
     * 刷新当前登录用户信息
     * @param loginUser
     */
    public void setLoginUser(LoginUser loginUser) {
       if(StringUtils.isNotNull(loginUser)&&StringUtils.isNotEmpty(loginUser.getToken())){
           refreshToken(loginUser);
       }
    }

    public String getUserName(String key) {
        if (StringUtils.isNotEmpty(key)) {
            try {
                Claims claims = parseToken(key);
                String uuid = (String) claims.get(Constants.LOGIN_USER_KEY);
                String tokenKey = getTokenKey(uuid);
                LoginUser loginUser = redisCache.getCacheObject(tokenKey);
                refreshToken(loginUser);
                return loginUser.getUsername();
            } catch (Exception e) {

            }
        }
        return null;
    }
}
