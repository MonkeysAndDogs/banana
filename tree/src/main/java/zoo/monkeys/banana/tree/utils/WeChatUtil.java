package zoo.monkeys.banana.tree.utils;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * @author Hofoo
 * @since 2017-10-03 15:08
 */
@Slf4j
public class WeChatUtil {

    public static boolean checkSignature(String token, String signature, String timestamp, String nonce) {

        if (isBlank(signature) || isBlank(timestamp) || isBlank(nonce) || isBlank(token)) {
            log.error("signature:{}, timestamp:{}, nonce:{}, token:{}", signature, timestamp, token);
            return false;
        }

        String[] array = new String[]{token, timestamp, nonce};

        Arrays.sort(array);
        log.info("array sorted:{}", array);

        String str = String.join("", array);
        log.info("str:{}", str);

        String strSHA1 = toSHA1(str);

        return signature.equals(strSHA1);
    }

    /**
     * SHA1加密
     *
     * @param decript 要加密的字符串
     * @return 加密的字符串
     * @author 穗苗子 http://www.jianshu.com/p/244216d1616b
     */
    public final static String toSHA1(String decript) {
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @param decript 要加密的字符串
     * @return 加密的字符串
     * MD5加密
     */
    public final static String toMD5(String decript) {
        char hexDigits[] = { // 用来将字节转换成 16 进制表示的字符
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
                'e', 'f'};
        try {
            byte[] strTemp = decript.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("toMD5");
            mdTemp.update(strTemp);
            byte tmp[] = mdTemp.digest(); // toMD5 的计算结果是一个 128 位的长整数，
            // 用字节表示就是 16 个字节
            char strs[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
            // 所以表示成 16 进制需要 32 个字符
            int k = 0; // 表示转换结果中对应的字符位置
            for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 toMD5 的每一个字节
                // 转换成 16 进制字符的转换
                byte byte0 = tmp[i]; // 取第 i 个字节
                strs[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
                // >>> 为逻辑右移，将符号位一起右移
                strs[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
            }
            return new String(strs).toUpperCase(); // 换后的结果转换为字符串
        } catch (Exception e) {
            return null;
        }
    }

}
