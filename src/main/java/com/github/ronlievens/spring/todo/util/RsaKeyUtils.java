package com.github.ronlievens.spring.todo.util;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.val;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import static org.apache.commons.lang3.StringUtils.isNotBlank;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RsaKeyUtils {

    private static final String ALGORITHM = "RSA";

    private static PrivateKey getPrivateKey(final byte[] key) throws NoSuchAlgorithmException, InvalidKeySpecException {
        val spec = new PKCS8EncodedKeySpec(key);
        val kf = KeyFactory.getInstance(ALGORITHM);
        return kf.generatePrivate(spec);
    }

    private static PublicKey getPublicKey(final byte[] key) throws NoSuchAlgorithmException, InvalidKeySpecException {
        val spec = new X509EncodedKeySpec(key);
        val kf = KeyFactory.getInstance(ALGORITHM);
        return kf.generatePublic(spec);
    }

    public static Algorithm getAlgorithm(final String publicKey,
                                         final String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        RSAPublicKey rsaPublicKey = null;
        if (isNotBlank(publicKey)) {
            val bytes = Base64.getDecoder().decode(publicKey.trim());
            rsaPublicKey = (RSAPublicKey) getPublicKey(bytes);
        }

        RSAPrivateKey rsaPrivateKey = null;
        if (isNotBlank(privateKey)) {
            val bytes = Base64.getDecoder().decode(privateKey.trim());
            rsaPrivateKey = (RSAPrivateKey) getPrivateKey(bytes);
        }

        return Algorithm.RSA256(rsaPublicKey, rsaPrivateKey);
    }
}
