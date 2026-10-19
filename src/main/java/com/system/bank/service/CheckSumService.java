package com.system.bank.service;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

@Service
public class CheckSumService {
    private static final String ALGORITHM = "SHA-256";

    public String genCheccksum(String data){
        try{
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
            byte[] hashBytes = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hashBytes);
        }catch(NoSuchAlgorithmException e) {
            throw new IllegalStateException("Không tìm thaadsy thuật toán"+ALGORITHM,e);
        }
    }

    public boolean verifyChecksum(String data, String expected){
        String actual = genCheccksum(data);
        return actual.equals(expected);
    }
}
