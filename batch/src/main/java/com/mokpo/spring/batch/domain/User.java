package com.mokpo.spring.batch.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
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

    @Temporal(TemporalType.TIMESTAMP)
    Date crt_dt;

    @Column(name="user_email")
    String userEmail;

    @Column(name="user_status")
    String userStatus;
}
