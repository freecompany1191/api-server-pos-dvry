package com.o2osys.pos;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.util.Base64Utils;

/**
   @FileName  : EncryptionMD5Key.java
   @Description : MD5 형식 인증 키 생성모듈
   @author      : KMS
   @since       : 2018. 5. 16.
   @version     : 1.0

   @개정이력

   수정일          수정자         수정내용
   -----------     ---------      -------------------------------
   2018. 5. 16.     KMS            최초생성

 */
public class EncryptionMD5Key {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        //      "MANNA_TEST"; //인성테스트키
        //      "MANNA_REAL"; //인성리얼키
        //      "MANNA_TEST_BAROGO"; //BAROGO 테스트키
        //      "MANNA_REAL_BAROGO"; //BAROGO 리얼키
        //      "MANNA_TEST_POS주문연동_SKY포스";
        //      "MANNA_TEST_POS주문연동_유니타스";
        //      "MANNA_TEST_POS주문연동_SPC";
        //      "MANNA_REAL_POS주문연동_SKY포스";
        //      "MANNA_REAL_POS주문연동_유니타스";
        //      "MANNA_TEST_POS주문연동_Easy",
        //      "MANNA_REAL_POS주문연동_Easy"
        //      "MANNA_TEST_POS주문연동_SolBi",
        //      "MANNA_REAL_POS주문연동_SolBi"
    	
        //      "MANNA_TEST_POS주문연동_SolBi",
        //      "MANNA_REAL_POS주문연동_SolBi"

        String[] contents = {
                "MANNA_TEST_POS주문연동_OkPos",
                "MANNA_REAL_POS주문연동_OkPos"
        };
        String hashCode;

        for(String content : contents){

            byte[] buffers = encryptMd5(content);

            hashCode = Base64Utils.encodeToString(buffers);

            System.out.println(content+" KEY : "+ hashCode);
        }

    }

    /**
     * 암호화 모듈
     * @Method Name : encryptMd5
     * @param input
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static byte[] encryptMd5(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("MD5"); //SHA-1, SHA-256
        byte[] result = mDigest.digest(input.getBytes());

        /*
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            stringBuffer.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
        System.out.println(stringBuffer.toString());
         */

        return result;
    }

}

