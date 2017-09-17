package org.pigeon.model;

import java.io.Serializable;

public class PigeonResponse implements Serializable {

    private static final long serialVersionUID = -6885462109090641531L;

    private String methodSign;
    private Object result;

    public String getMethodSign() {
        return methodSign;
    }

    public void setMethodSign(String methodSign) {
        this.methodSign = methodSign;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
