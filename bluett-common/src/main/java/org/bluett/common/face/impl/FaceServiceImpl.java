package org.bluett.common.face.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bluett.common.face.FaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * name: MengHao Tian
 * date: 2023/5/10 15:16
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class FaceServiceImpl implements FaceService {
    private final RestTemplate restTemplate;

    @Override
    public Long faceRecognition(String url, byte[] bytes) {
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, bytes, Long.class);
        return responseEntity.getBody() == null ? -1L : responseEntity.getBody();
    }
}
