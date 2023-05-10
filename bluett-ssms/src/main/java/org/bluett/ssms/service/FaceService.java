package org.bluett.ssms.service;

/**
 * @author bluett
 * @date 2023/05/10
 **/
public interface FaceService {
    /**
     * 人脸识别
     * @param url 人脸识别接口地址
     * @param bytes 人脸照片
     * @return 用户id
     */
    Long faceRecognition(String url, byte[] bytes);
}
