package com.tajkun.ad.delivery.pojo;

import com.tajkun.ad.delivery.constant.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-22 09:52
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ad_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic //默认为Basic
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "user_status", nullable = false)
    private Integer userStatus;

    @Column(name = "create_time", nullable = false)
    private Date createTime;

    @Column(name = "update_time", unique = false)
    private Date updateTime;


    public User(String username, String token) {
        this.username = username;
        this.token = token;
        this.userStatus = CommonStatus.VALID.getStatusCode();
        this.createTime = new Date();
    }

}