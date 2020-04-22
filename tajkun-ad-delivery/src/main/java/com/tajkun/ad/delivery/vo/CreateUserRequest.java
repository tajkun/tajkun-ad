package com.tajkun.ad.delivery.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/**
 * @program: tajkun-ad
 * @description: vo对象方便序列化和反序列化
 * @author: Jiakun
 * @create: 2020-04-22 20:41
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {

    private String username;

    public boolean validate() {
        return StringUtils.isEmpty(username);
    }


}