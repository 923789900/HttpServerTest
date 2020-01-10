package com.king.httpservertest.logUtils.Http;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

/**
 * HTTP Response
 */
public class Response {
    /**
     * Response Protocol
     */
    private Protocol protocol = null;
    /**
     * Response Content
     */
    private byte[] ReturnContent = null;
    /**
     * Response Protocol Version
     */
    private HttpVersion Version = null;
    /**
     * Response Status Code
     */
    private ResponseCode Code = null;
    /**
     * Response Headers
     */
    private HashMap<String, String> headers = null;
    /**
     * Response Cookies
     */
    private HashMap<String, String> ReturnCookis = null;

    public Response() {
        protocol = Protocol.HTTP;
        ReturnContent = new byte[0];
        Version = HttpVersion.VERSION_1_1;
        Code = ResponseCode.OK;
        headers = new HashMap<>();
        headers.put("Server", "King v1.0");
        ReturnCookis = new HashMap<>();
    }

    public Response setProtocol(Protocol protocol) {
        this.protocol = protocol;
        return this;
    }

    public Response setReturnContent(byte[] returnContent)  {
        ReturnContent = returnContent;
        return this;
    }

    public Response setVersion(HttpVersion version) {
        Version = version;
        return this;
    }

    public Response setCode(ResponseCode code) {
        Code = code;
        return this;
    }

    public Response addHeader(String key, String Value) {
        headers.put(key, Value);
        return this;
    }

    public Response addCookie(String key, String Value) {
        ReturnCookis.put(key, Value);
        return this;
    }

    /**
     * headers to Byte Array
     * @return
     */
    private byte[] HeadersToByteArray() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        for (String key : headers.keySet()) {
            String value = headers.get(key);
            byte[] singleHead = String.format("%s: %s\r\n",key,value).getBytes();
            out.write(singleHead,0,singleHead.length);
        }
        return out.toByteArray();
    }

    /**
     * Cookies to Byte Array
     * @return
     */
    private byte[] CookiesToByteArray()
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        for (String key : ReturnCookis.keySet()) {
            String value = headers.get(key);
            byte[] singleHead = String.format("Set-Cookie: %s=%s\r\n",key,value).getBytes();
            out.write(singleHead,0,singleHead.length);
        }
        return out.toByteArray();

    }

    /**
     * build Response Context to ByteArrayOutputStream
     *
     * @return
     */
    public ByteArrayOutputStream Build()  {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        //HTTP/1.1 200
        byte[] ResponseTop = String.format("%s/%s %s %s\r\n", protocol.toString(), Version.getValue(), Code.getValue(), Code.toString().replace("_", " ")).getBytes();
        out.write(ResponseTop, 0, ResponseTop.length);

        //HttpHeaders
        byte[] headers = HeadersToByteArray();
        out.write(headers, 0, headers.length);

        //Cookies
        byte[] Cookies = CookiesToByteArray();
        out.write(Cookies, 0, Cookies.length);

        //Split Content
        byte[] Split = "\r\n\r\n".getBytes();
        out.write(Split, 0, Split.length);

        //Content
        if (ReturnContent == null)
        {
            ReturnContent = new byte[0];
        }
        out.write(ReturnContent, 0, ReturnContent.length);

        return out;
    }
}
