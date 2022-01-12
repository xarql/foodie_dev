package com.imooc.utils;

import java.util.UUID;

public class IdGenerate  {

        /**
         * 生成UUID, 中间无-分割.
         */
        public static String uuid() {
            return UUID.randomUUID().toString().replaceAll("-", "");
        }


}
