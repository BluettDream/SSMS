package org.bluett.common.exception.user;

/**
 * 验证码失效异常类
 *
 * @author bluett
 */
public class CaptchaExpireException extends UserException {
    private static final long serialVersionUID = 1L;

    public CaptchaExpireException() {
        super("user.jcaptcha.expire");
    }
}
