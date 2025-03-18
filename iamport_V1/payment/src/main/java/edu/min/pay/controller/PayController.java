package edu.min.pay.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.AccessToken;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PayController {
  
  // PostOne 개발자센터
  // https://developers.portone.io/opi/ko/integration/start/v1/auth?v=v1
  
  private IamportClient client;
  
  public PayController() {
    this.client = this.getClient();
    this.getToken();
  }
  
  private IamportClient getClient() {    
    String test_api_key = "321511****";  //----- REST API Key
    String test_api_secret = "3sVpFiKt4r****";  //----- REST API Secret (환경 변수 처리해야 합니다.)
    return new IamportClient(test_api_key, test_api_secret);
  }
  
  private void getToken() {
    try {
      IamportResponse<AccessToken> authResponse = client.getAuth();
      log.info("-------------------- getToken() --------------------");
      log.info("-------------------- " + authResponse.getResponse().getToken() + " --------------------");
    } catch (IamportResponseException e) {      
      log.info("-------------------- pay-IamportResponseException --------------------");
      log.info(e.getMessage());
      switch(e.getHttpStatusCode()) {
      case 401:
        log.info("-------------------- 401 --------------------");
        break;
      case 500:
        log.info("-------------------- 500 --------------------");
        break;
      }
    } catch (IOException e) {
      e.printStackTrace();  // 서버 연결 실패
    }
  }
  
  // 결제
  @PostMapping("/payment/pay")
  public ResponseEntity<Void> pay(@RequestBody Map<String, Object> map) {
    log.info("-------------------- pay() --------------------");
    log.info("-------------------- " + map + " --------------------");
    //---------- 필요한 정보를 DB에 저장하는 로직 필요 ----------//
    return ResponseEntity.status(200).build();
  }
  
  // 결제 취소
  @PostMapping("/payment/cancel")
  public ResponseEntity<Void> cancel(@RequestBody Map<String, Object> map) {
    log.info("-------------------- cancel() --------------------");
    log.info("-------------------- " + map + " --------------------");
    CancelData cancelData = new CancelData((String) map.get("merchant_uid"), false); //---------- merchant_uid를 통한 전액 취소
    cancelData.setEscrowConfirmed(true); //---------- 에스크로 구매확정 후 취소인 경우 true 설정
    try {
      IamportResponse<Payment> paymentResponse = client.cancelPaymentByImpUid(cancelData);
      if (paymentResponse.getResponse() == null) {
        log.info("-------------------- 이미 취소된 거래입니다. --------------------");
      } else {        
        log.info("-------------------- 취소 완료 메시지 --------------------");
        log.info("-------------------- " + paymentResponse.getMessage() + " --------------------");
        //---------- 필요한 정보를 DB에 반영하는 로직 필요 ----------//
      }
    } catch (IamportResponseException e) {
      log.info("-------------------- cancel-IamportResponseException --------------------");
      log.info(e.getMessage());
      switch(e.getHttpStatusCode()) {
      case 401:
        log.info("-------------------- 401 --------------------");
        break;
      case 500:
        log.info("-------------------- 500 --------------------");
        break;
      }
    } catch (IOException e) {
      e.printStackTrace();  // 서버 연결 실패
    }
    return ResponseEntity.status(200).build();
  }
  
}
