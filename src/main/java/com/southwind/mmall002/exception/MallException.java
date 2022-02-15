package com.southwind.mmall002.exception;

import com.southwind.mmall002.enums.ResultEnum;

/**
 * @Author: Lucas *
 * @Date: 2021/1/24 12:25 *
 */
public class MallException extends RuntimeException {
        public MallException(String error){super(error);}
        public MallException(ResultEnum resultEnum){super(resultEnum.getMsg());}
}
