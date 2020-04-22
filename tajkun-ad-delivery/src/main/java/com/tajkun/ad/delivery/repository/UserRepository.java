package com.tajkun.ad.delivery.repository;

import com.tajkun.ad.delivery.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-22 18:00
 **/
public interface UserRepository extends JpaRepository<User, Long> {

    // 根据用户名查找用户记录
    User findByUsername(String username);
}