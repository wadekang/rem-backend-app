package com.wadekang.rem.svc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.map.PassiveExpiringMap;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalendarShareServiceImpl implements CalendarShareService {

    private final Map<String, Long> calendarCodeMap = new PassiveExpiringMap<>(1000 * 60 * 60 * 24);

    @Override
    public String generateCodeForShare(Long calendarId) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        KeyGenerator aes = KeyGenerator.getInstance("AES");
        aes.init(256);
        SecretKey secretKey = aes.generateKey();

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        String before = "{ calendarId: " + calendarId + " }";

        byte[] bytes = cipher.doFinal(before.getBytes(StandardCharsets.UTF_8));

        String code = Base64.getEncoder().encodeToString(bytes);

        log.info("calendarId: {}, sharedCode: {}", calendarId, code);

        calendarCodeMap.put(code, calendarId);

        return code;
    }

    @Override
    public Long validateCode(String code) throws BadRequestException {
        if (calendarCodeMap.containsKey(code)) {
            Long calendarId = calendarCodeMap.get(code);

            log.info("calendarId: {}, sharedCode: {}", calendarId, code);

            calendarCodeMap.remove(code);

            return calendarId;
        }
        else throw new BadRequestException("Invalid Code");
    }
}
