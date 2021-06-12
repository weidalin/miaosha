package com.imooc.miaosha.exception;

import com.imooc.miaosha.result.CodeMsg;

public class GlobalException extends RuntimeException{

    private static final long serialVersionUID = 7103829434715939102L;

    public CodeMsg getCm() {
        return cm;
    }

    private CodeMsg cm;

    public GlobalException(CodeMsg cm){
        super(cm.toString());
        this.cm = cm;
    }
}
