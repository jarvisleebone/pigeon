package org.pigeon.model;

import java.io.Serializable;

public class PigeonRequest implements Serializable {

    private static final long serialVersionUID = -6362419231214892659L;
    private String interfaceName;
    private Object[] parameters;
    private String returnType;
    private boolean sync;
    private String methodSign;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public boolean isSync() {
        return sync;
    }

    public void setSync(boolean sync) {
        this.sync = sync;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getMethodSign() {
        return methodSign;
    }

    public void setMethodSign(String methodSign) {
        this.methodSign = methodSign;
    }

    @Override
    public String toString() {
        return interfaceName + ":" + methodSign;
    }
}
