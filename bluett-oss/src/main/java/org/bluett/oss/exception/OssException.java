package org.bluett.oss.exception;

/**
 * OSS异常类
 *
 * @author Bluett Dream
 */
public class OssException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public OssException(String msg) {
        super(msg);
    }

}
