package com.shuttle.payment;

import com.shuttle.domain.Payment;
import com.shuttle.domain.User;
import com.shuttle.user.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RequiredArgsConstructor
@RestController
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentRepository paymentRepository;

    @Value("${imp_key}")
    private String imp_key;
    @Value("${imp_secret}")
    private String imp_secret;

    @PostMapping("/payments/complete")
    public ResponseEntity paymentComplete(@CurrentUser User user, @RequestBody PaymentCompleteResultDto paymentResult) {
        paymentService.save(user, paymentResult);
        return ResponseEntity.ok().build();
    }

    /*
    *  reference : https://docs.iamport.kr/implementation/cancel?lang=ko
    * */
    @PostMapping("/payments/cancel")
    public ResponseEntity paymentCancel(@RequestBody CancelPayRequestDto cancelPayRequestDto) {
        //TODO 어떻게 이걸 보기 좋게 수정할 것인가...
        Payment targetPaymented = paymentRepository.findById(cancelPayRequestDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 결제 내역입니다."));

        //이미 취소된 내역이라면 BadRequest
        if (targetPaymented.isCancel()) { return ResponseEntity.badRequest().build(); }

        //Access Token 받아오기
        String accessToken = getAccessToken();
        //취소 요청을 보낼 URL
        final String CANCEL_PAYMENT_URL = "https://api.iamport.kr/payments/cancel";
        //RestTemplate 생성
        RestTemplate restTemplate = new RestTemplate();
        //HttpHeader 설정
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", accessToken);
        httpHeaders.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        //body에 넣을 데이터 설정
        Map<String, String> httpBody = new HashMap<>();
        httpBody.put("imp_uid", targetPaymented.getImpUid());
        httpBody.put("reason", cancelPayRequestDto.getReason());

        //설정한 헤더와 바디를 넣어서 요청 보낼 HTTP를 생성한다.
        HttpEntity<Map<String, String>> httpEntity = new HttpEntity(httpBody, httpHeaders);

        //아임포트로 취소 요청 보내기
        ResponseEntity cancelResponse = restTemplate.postForEntity(CANCEL_PAYMENT_URL, httpEntity, Object.class);

        if (cancelResponse.getStatusCode().is2xxSuccessful())
            paymentService.cancelPayment(targetPaymented);

        return cancelResponse;
    }

    private String getAccessToken() {
        RestTemplate restTemplate = new RestTemplate();
        String requestUrl = "https://api.iamport.kr/users/getToken";

        Map<String, String> iamportKey = new HashMap();
        iamportKey.put("imp_key", imp_key);
        iamportKey.put("imp_secret", imp_secret);

        ResponseEntity<Object> responseData = restTemplate.postForEntity(requestUrl, iamportKey, Object.class);

        LinkedHashMap responseBody = (LinkedHashMap) responseData.getBody();

        LinkedHashMap responseBodyProps = (LinkedHashMap) responseBody.get("response");


        String accessToken = (String) responseBodyProps.get("access_token");

        return accessToken;
    }
}
