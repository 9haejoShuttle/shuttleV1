package com.shuttle.payment;

import com.shuttle.domain.Payment;
import com.shuttle.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    /*TODO Test클래스와 함께 삭제 예정.
    *   유저가 가진 전체 payment를 조회하는 메서드
    *   이 작업은 User가 Payment를 불러오는 방식으로 사용할 것임.
    * */
    @Query("SELECT p, u" +
            " FROM Payment p join User u on p.user = u" +
            " WHERE p.user = :user")
    List<Payment> userPaymentHistory(@Param("user")User user);
}
