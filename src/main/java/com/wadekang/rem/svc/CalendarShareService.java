package com.wadekang.rem.svc;

import org.apache.coyote.BadRequestException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface CalendarShareService {

    String generateCodeForShare(Long calendarId) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException;

    Long validateCode(String code) throws BadRequestException;

}
