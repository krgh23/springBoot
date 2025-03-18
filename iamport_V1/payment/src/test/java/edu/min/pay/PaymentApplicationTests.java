package edu.min.pay;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.AccessToken;
import com.siot.IamportRestClient.response.IamportResponse;

@SpringBootTest
class PaymentApplicationTests {

  IamportClient client;
  
  @Before
  public void setup() {
    String test_api_key = "3215114738045161";
    String test_api_secret = "3sVpFiKt4rDninVG5hzgzlOgaRUtFQPXp323iU1MNFl6t7czePIukgbnUj5ZPL8KpGJ2tguFUXgVYFaQ";
    client = new IamportClient(test_api_key, test_api_secret);
  }
  
  @Test
  void test_get_token() {
    try {
      IamportResponse<AccessToken> authResponse = client.getAuth();
      assertNotNull(authResponse.getResponse());
      assertNotNull(authResponse.getResponse().getToken());
    } catch (IamportResponseException e) {
      System.out.println(e.getMessage());
      switch(e.getHttpStatusCode()) {
      case 401:
        System.out.println("401");
        break;
      case 500:
        System.out.println("500");
        break;
      }
    } catch (IOException e) {
      e.printStackTrace();  // 서버 연결 실패
    }
  }

}
