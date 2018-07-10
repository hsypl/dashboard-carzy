package com.crazy.code.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

import java.util.Locale;

/**
 * 对MessageSource接口简单封装，并可暴露给JSP中通过 JSP EL 调用。
 * Created by wanghongwei on 3/30/16.
 */
public class I18nMessage {

    private static final Logger log = LoggerFactory.getLogger(I18nMessage.class);

    private Locale defaultLocale = Locale.SIMPLIFIED_CHINESE;

    private MessageSource messageSource;

    public String get(String code) {
        return messageSource.getMessage(code, null, "", defaultLocale);
    }

    /**
     * 根据 code 从 MessageSource 中过获取message字符串
     * @param code String 在MessageSource资源文件中定义的属性名
     * @return String message字符串
     */
    public String get(String code, Locale locale) {
        log.debug("locale=" + locale);
        return messageSource.getMessage(
                code, null, "", locale != null ? locale : defaultLocale);
    }

    public String get(String code, String defaultMessage, Locale locale) {
        return messageSource.getMessage(
                code, null, defaultMessage, locale != null ? locale : defaultLocale);
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public void setDefaultLocale(Locale defaultLocale) {
        this.defaultLocale = defaultLocale;
    }
}
