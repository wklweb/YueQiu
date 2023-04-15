package com.yueqiu.framework.web.service;

import com.alibaba.fastjson2.util.UUIDUtils;
import com.yueqiu.common.constant.CacheConstants;
import com.yueqiu.common.constant.UserConstants;
import com.yueqiu.common.core.redis.RedisCache;
import com.yueqiu.common.domain.model.MailInfo;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Service("mail")
public class EmailService {

    public static final Logger log = LoggerFactory.getLogger(EmailService.class);
    /**
     * 发送者
     */
    @Value("${spring.mail.username}")
    private String username;
    /**
     * 邮箱授权码
     */
    @Value("${spring.mail.password}")
    private String password;
    /**
     * 主机协议
     */
    @Value("${spring.mail.host}")
    private String host;
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;


    /**
     * 发送文本邮件
     */
    public boolean sendEmail(String title, String receiverEmail) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(username);
            simpleMailMessage.setTo(receiverEmail);
            simpleMailMessage.setSubject(title);
            simpleMailMessage.setText("这是一封信");
            javaMailSender.send(simpleMailMessage);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 发送html文本邮件
     */
    @Async("emailExecutor")
    public boolean sendHtmlMail(String title, String receiverEmail, String htmlText) {
        try {
            Thread.sleep(200);
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(username);
            helper.setTo(receiverEmail);
            helper.setSubject(title);
            helper.setText(htmlText, true);
            javaMailSender.send(mimeMessage);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * 发送模板文本邮件
     */
    @Async("emailExecutor")
    public Future<Boolean> sendTemplateMail(MailInfo mailInfo) {
        try {
            Thread.sleep(3000);
            log.info(Thread.currentThread() + "执行发送邮箱");
            Template template = freeMarkerConfigurer.getConfiguration().getTemplate("message.ftl");
            Map<String, Object> map = new HashMap<>(3);
            //生成加密重置密码关键字
            String key = DigestUtils.md5DigestAsHex(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));
            System.out.println(key);
            map.put("sendTime", mailInfo.getTime());
            map.put("email", mailInfo.getTo());
            map.put("resetKey", key);

            redisCache.setCacheObject(CacheConstants.RESET_PASSWORD_KEY + key, mailInfo.getTo(), 24,
                    TimeUnit.HOURS);

            String htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
            sendHtmlMail(mailInfo.getTitle(), mailInfo.getTo(), htmlText);
            return new AsyncResult<>(true);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new AsyncResult<>(false);
        }
    }


}
