package com.mokpo.spring.batch.repository;

import com.mokpo.spring.batch.domain.UserInfo;
import com.mokpo.spring.batch.domain.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, String> {
    //List<User> findByUpdatedDateBeforeAndStatusEquals(LocalDateTime localDateTime, UserStatus userStatus);


    @Query(value = "SELECT * FROM USER_INFO WHERE UPDATED_DATE < :date AND STATUS = :status", nativeQuery = true)
    List<UserInfo> test(@Param("date") LocalDateTime localDateTime, @Param("status") UserStatus userStatus);
}
