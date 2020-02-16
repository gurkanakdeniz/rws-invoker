package com.rws.invoker.model;

public class RestWebServiceEndpoint {

    private String url;
    private String interfaceName;
    private String interfaceMethod;
    private RestWebServiceMethod method;
    private int connectionTimeout;
    private int readTimeout;

    public RestWebServiceEndpoint(String url, String interfaceName, String interfaceMethod) {
        super();
        init(url, interfaceName, interfaceMethod, RestWebServiceMethod.GET, -1, -1);
    }

    public RestWebServiceEndpoint(String url, String interfaceName, String interfaceMethod,
            RestWebServiceMethod method) {
        super();
        init(url, interfaceName, interfaceMethod, method, -1, -1);
    }

    public RestWebServiceEndpoint(String url, String interfaceName, String interfaceMethod, RestWebServiceMethod method,
            int connectionTimeout, int readTimeout) {
        super();
        init(url, interfaceName, interfaceMethod, method, connectionTimeout, readTimeout);
    }

    private void init(String url, String interfaceName, String interfaceMethod, RestWebServiceMethod method,
            int connectionTimeout, int readTimeout) {
        this.url = url;
        this.interfaceName = interfaceName;
        this.interfaceMethod = interfaceMethod;
        this.method = method;
        this.connectionTimeout = connectionTimeout;
        this.readTimeout = readTimeout;
    }

    public String getUrl() {
        return url;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public String getInterfaceMethod() {
        return interfaceMethod;
    }

    public RestWebServiceMethod getMethod() {
        return method;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public String getEndpointKey() {
        return getEndpointKey(this.interfaceName, this.interfaceMethod, this.method);
    }

    public static String getEndpointKey(String interfaceName, String interfaceMethod) {
        return getEndpointKey(interfaceName, interfaceMethod, RestWebServiceMethod.GET);
    }

    public static String getEndpointKey(String interfaceName, String interfaceMethod, RestWebServiceMethod method) {
        return interfaceName + "." + interfaceMethod + "." + method.getMethod();
    }

}
