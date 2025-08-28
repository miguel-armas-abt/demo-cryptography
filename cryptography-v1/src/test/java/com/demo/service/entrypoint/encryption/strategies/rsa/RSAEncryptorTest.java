package com.demo.service.encryption.strategies.rsa;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.demo.service.entrypoint.encryption.strategies.rsa.CipherGenerator;
import com.demo.service.entrypoint.encryption.strategies.rsa.RSAEncryptor;
import com.demo.service.entrypoint.encryption.strategies.rsa.keys.Base64KeyReader;
import lombok.extern.slf4j.Slf4j;
import java.security.PrivateKey;
import java.security.PublicKey;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@Slf4j
public class RSAEncryptorTest {

    private static RSAEncryptor rsaEncryptor;
    private static Base64KeyReader keyReader;

    @BeforeAll
    static void setup() {
        CipherGenerator cipherGenerator = new CipherGenerator();
        rsaEncryptor = new RSAEncryptor(cipherGenerator);
        keyReader = new Base64KeyReader();
    }

    public static final String BASE64_RSA_PRIVATE_KEY = "LS0tLS1CRUdJTiBSU0EgUFJJVkFURSBLRVktLS0tLQ0KTUlJQ1dnSUJBQUtCZ0dhNkZLVEw5cS9jZXJhbkQ1MFgxS0E1VmhFYWtGYTZ2c011VU9XRVFqS1BrRjRkSklhTQ0KRE9RMnZKZnB3ejhpZGtpeDNrSndoVndOcGROdS9EdjN5d0xOZGorNStFeW9LZTI4a3Z4QnhUdmtESXV3R0JrUA0KU05vbWtrcGozM0RUV3dwZGFBaUFha0hIbGhCWll3TGhtUmtJbytOS1ArOWVza09Cb1lYYXoxcHRBZ01CQUFFQw0KZ1lBK0l5eTlpa3FMa2RnQWlBa2Z3dnh3ZG40akZtaGNyZFVhQnZnVC9PNTEydlUrbUc0eG5LRm5vZHh1SDQ1aw0Kc0JuUVd4YlFKWFBsZkxQanN1eUw4WlV3TmJlM25GYkxsbXlBV21xQlBDK3NWUFE0SkZiZ3M5bDZ4ekFzNjRBWQ0KaHc5RWZQQUpwalZIeCszSUNNZVNWa0hjOVRpNkQ3TlhldkNDMXRWSXZWc2ZRUUpCQUxKbEhFOGFmRFJOQ2tPZg0KNDVGcWgyVEFpdmZPa0s3M3pSeE9Pb0FPNG4raDZ4cHBuVE83S25KT2c2YTZaRkhPRktFaXRac1VrRWtidUhuKw0KQnpkVG1rVUNRUUNUYWppM0E2R3d0U2U1dHp3alFpek5UYk5hRXdXdHUwUEZwdTRrdjJxeXlJSHNMMG0zcUdhag0KMWNBakYycEJML3ZGQ3BlVGtwa2dETHd3MmYwRGVSWUpBa0JaM3dZNzg2UE8xV0R1WUlaTW85WkJzNTVPNlFtSw0KbTl3QS9vZ0tLM1YveHhxaDI4TU9aS1Fsb2RoQkRuV3JvSjVUN3lCK1dBVzRUdmJJdm1qMFBuSGhBa0JjamtqbA0KdjhYNWFRUjBPblBydjFUWjRudEZoc1A5L0tIa3E1YkNROXorTzU0M2FNbGtJcnV2Q2pvRXptTE1ZeEEwK3gySQ0KYm5idUJKRDBLeW9NMEMzaEFrQXArVUJySnBtVTZpQkRNTTZuSmQrOXNia1JQS2RIZEI4K2F4b3FnWDJOQkJYVw0KdFFUbnBBYUZ4Zi9BeVRSRm1jMzAyMGdMeDgwVEQ4TmtCSFUrSHI5Sw0KLS0tLS1FTkQgUlNBIFBSSVZBVEUgS0VZLS0tLS0=";
    public static final String BASE64_RSA_PUBLIC_KEY = "LS0tLS1CRUdJTiBQVUJMSUMgS0VZLS0tLS0NCk1JR2VNQTBHQ1NxR1NJYjNEUUVCQVFVQUE0R01BRENCaUFLQmdHYTZGS1RMOXEvY2VyYW5ENTBYMUtBNVZoRWENCmtGYTZ2c011VU9XRVFqS1BrRjRkSklhTURPUTJ2SmZwd3o4aWRraXgza0p3aFZ3TnBkTnUvRHYzeXdMTmRqKzUNCitFeW9LZTI4a3Z4QnhUdmtESXV3R0JrUFNOb21ra3BqMzNEVFd3cGRhQWlBYWtISGxoQlpZd0xobVJrSW8rTksNClArOWVza09Cb1lYYXoxcHRBZ01CQUFFPQ0KLS0tLS1FTkQgUFVCTElDIEtFWS0tLS0t";

    @ParameterizedTest
    @CsvSource(value = {
        "dummyMessage",
    })
    @DisplayName("Given message, when encrypt, then return cipher message")
    void givenMessage_WhenEncrypt_ThenReturnCipherMessage(String message) {
        //Arrange
        PrivateKey privateKey = keyReader.getPrivateKey(BASE64_RSA_PRIVATE_KEY);
        PublicKey publicKey = keyReader.getPublicKey(BASE64_RSA_PUBLIC_KEY);

        //Act
        String cipherMessage = rsaEncryptor.encrypt(publicKey, message);
        log.info("cipher message: {}", cipherMessage);

        String actualMessage = rsaEncryptor.decrypt(privateKey, cipherMessage);

        //Assert
        assertEquals(message, actualMessage);
    }


}
