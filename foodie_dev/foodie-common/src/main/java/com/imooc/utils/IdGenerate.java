package com.imooc.utils;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import org.apache.catalina.SessionIdGenerator;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.UUID;

public class IdGenerate  {

        /**
         * 生成UUID, 中间无-分割.
         */
        public static String uuid() {
            return UUID.randomUUID().toString().replaceAll("-", "");
        }


}
