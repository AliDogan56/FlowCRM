package com.dogan.bilisim.restservice.auth.util;

import com.dogan.bilisim.restservice.auth.exception.InvalidConfigurationInternalException;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import java.io.File;
import java.io.FileReader;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class KeyUtil {
    public static RSAPublicKey readPublicKey(File file) {
        try {
            KeyFactory factory = KeyFactory.getInstance("RSA");

            try (FileReader keyReader = new FileReader(file);
                 PemReader pemReader = new PemReader(keyReader)) {

                PemObject pemObject = pemReader.readPemObject();
                byte[] content = pemObject.getContent();
                X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(content);
                return (RSAPublicKey) factory.generatePublic(pubKeySpec);
            }
        } catch (Throwable e) {
            throw new InvalidConfigurationInternalException(e);
        }
    }

    public static RSAPrivateKey readPrivateKey(File file) {
        try {
            KeyFactory factory = KeyFactory.getInstance("RSA");

            try (FileReader keyReader = new FileReader(file);
                 PemReader pemReader = new PemReader(keyReader)) {

                PemObject pemObject = pemReader.readPemObject();
                byte[] content = pemObject.getContent();
                PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(content);
                return (RSAPrivateKey) factory.generatePrivate(privKeySpec);
            }
        } catch (Throwable e) {
            throw new InvalidConfigurationInternalException(e);
        }
    }
}
