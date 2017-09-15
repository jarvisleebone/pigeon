package org.pigeon.model;

import java.io.Serializable;

public class PigeonResponse implements Serializable {

    private static final long serialVersionUID = -6885462109090641531L;

    private String interfaceName;
    private String methodName;
    private Object result;

    /**
     * Getter method for property <tt>interfaceName</tt>.
     *
     * @return property value of interfaceName
     */
    public String getInterfaceName() {
        return interfaceName;
    }

    /**
     * Setter method for property <tt>interfaceName</tt>.
     *
     * @param interfaceName value to be assigned to property interfaceName
     */
    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    /**
     * Getter method for property <tt>methodName</tt>.
     *
     * @return property value of methodName
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * Setter method for property <tt>methodName</tt>.
     *
     * @param methodName value to be assigned to property methodName
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    /**
     * Getter method for property <tt>result</tt>.
     *
     * @return property value of result
     */
    public Object getResult() {
        return result;
    }

    /**
     * Setter method for property <tt>result</tt>.
     *
     * @param result value to be assigned to property result
     */
    public void setResult(Object result) {
        this.result = result;
    }
}
