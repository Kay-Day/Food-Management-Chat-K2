package Helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyStore;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class PasswordEncryption {

    private static final String ALGORITHM = "AES";
    private static final byte[] keyValue = "1234567891234567".getBytes();
    private static String PASSWORD = "secretpassword";
    private static String KEYPATH = "./key.key";

    private static Key generateKey() throws Exception {
        Key key = new SecretKeySpec(keyValue, ALGORITHM);
        return key;
    }

    public static void storeKey(Key key) throws Exception {
        File file = new File(KEYPATH);
        KeyStore keyStore = KeyStore.getInstance("JCEKS");
        if (!file.exists()) keyStore.load(null, null);
        keyStore.setKeyEntry("keyAlias", key, PASSWORD.toCharArray(), null);
        OutputStream outputStream = new FileOutputStream(file);
        keyStore.store(outputStream, PASSWORD.toCharArray());
    }

    public static SecretKey loadKey() throws Exception  {
        KeyStore keyStore = KeyStore.getInstance("JCEKS");
        InputStream inputStream = new FileInputStream(KEYPATH);
        keyStore.load(inputStream, PASSWORD.toCharArray());
        SecretKey key = (SecretKey) keyStore.getKey("keyAlias", PASSWORD.toCharArray());
        return key;
    }

    public static String encrypt(String valueToEnc) throws Exception {
        File keyFile = new File(KEYPATH);
        if (!keyFile.exists()) {
            Key key = generateKey();
            storeKey(key);
        }
        Key newKey = loadKey();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, newKey);
        byte[] encValue = cipher.doFinal(valueToEnc.getBytes());
        byte[] encryptedByteValue = Base64.getEncoder().encode(encValue);
        return new String(encryptedByteValue);
    }

    public static String decrypt(String encryptedValue) throws Exception {
        Key newKey = loadKey();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, newKey);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedValue.getBytes());
        byte[] enctVal = cipher.doFinal(decodedBytes);
        return new String(enctVal);
    }
}
