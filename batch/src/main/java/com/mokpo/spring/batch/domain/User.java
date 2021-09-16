package com.mokpo.spring.batch.domain;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="User_info")
@Entity
public class User {

    @Id
    String UserId;

    @Column(name="user_pwd")
    String userPwd;

    @Column(name="user_name")
    String userName;

    @Column(name="crt_dt")
    LocalDateTime crtDt;

    @Column(name="user_email")
    String userEmail;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    UserStatus status;


    @Column
    private LocalDateTime updatedDate;

    public User setInactive() {
        this.status = UserStatus.INACTIVE;
        return this;
    }
}
