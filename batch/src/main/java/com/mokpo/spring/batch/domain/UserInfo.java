package com.mokpo.spring.batch.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="User_info")
@Entity
public class UserInfo {

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

    public UserInfo setInactive() {
        this.status = UserStatus.INACTIVE;
        return this;
    }
}
