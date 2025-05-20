package clovar.howkiki.domain.pay.service;

import clovar.howkiki.global.exception.CustomException;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static clovar.howkiki.global.exception.ErrorCode.*;

// 전체 흐름
/*
1. 클라이언트로부터 imp_uid, merchant_uid를 받음
2. PortOne 서버에 API 키/시크릿으로 access_token 요청
3. 받은 토큰을 이용해 imp_uid로 실제 결제 내역 조회
4. DB의 주문 금액과 포트원 결제 금액 비교
5. 결제 상태에 따라 로직 처리 (ready, paid, 등)
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PortOneService {

    private final IamportClient iamportClient;

    // 결제 검증
    public Payment getPaymentData(String impUid) {
        try {
            IamportResponse<com.siot.IamportRestClient.response.Payment> response = iamportClient.paymentByImpUid(impUid);
            return response.getResponse(); // 실제 Payment 객체 반환
        } catch (IamportResponseException | IOException e) {
            log.error("결제 정보 조회 실패: {}", e.getMessage());
            throw new CustomException(FAILED_TO_GET_PAYMENT_INFO, "/payments/verification");
        }

    }
}

