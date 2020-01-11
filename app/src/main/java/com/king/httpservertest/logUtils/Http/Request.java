package com.king.httpservertest.logUtils.Http;

import java.io.ByteArrayOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class Request extends HttpClient {

    public String Path;

    public String Version;

    public byte[] Content;

    public Protocol ProtocolType;

    public RequestMethod Method;

    public HashMap<String, String> Headers = new HashMap<>();

    private byte[] Buffer;

    private int BufferLength;

    private Request() {
        super();
    }

    public Request(Socket Client) {
        super(Client);
        Parsing();
    }

    public Request(Socket Client, byte[] Buffer, int Length) {
        super(Client);
        this.Buffer = Buffer;
        this.BufferLength = Length;
        Parsing();
    }

    /**
     * 解析request object
     *
     * @return
     */
    private void Parsing() {

        int Size = 0;
        byte[] buf = null;
        if (Buffer != null && BufferLength > 0) {
            Size = this.BufferLength;
            buf = this.Buffer;
        } else {
            ByteArrayOutputStream outputStream = super.getReciveData();
            Size = outputStream.size();
            buf = outputStream.toByteArray();
        }

        int index = getIndex(buf, "\r\n\r\n".getBytes());
        if (index != -1) {
            //POST
            //header data
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int Length = ++index;
            out.write(buf, 0, Length);
            String HeaderStr = out.toString();
            boolean state = baseParsing(HeaderStr);
            if (!state) {
                Response res = new Response();
                res.setCode(ResponseCode.Bad_Request);
                res.setReturnContent("Bad_Request".getBytes());
                super.SendMessage(res);
            }

            //data
            if (Size > Length) {
                out.reset();
                Length += "\r\n".length();
                out.write(buf, Length, buf.length - Length);
                Content = out.toByteArray();
            }

        } else {
            //GET
            //header data
            String HeaderStr = new String(buf);
            boolean state = baseParsing(HeaderStr);
            if (!state) {
                Response res = new Response();
                res.setCode(ResponseCode.Bad_Request);
                res.setReturnContent("Bad_Request".getBytes());
                super.SendMessage(res);
            }
        }

    }

    /**
     * 解析request object
     *
     * @param HeaderStr
     */
    private boolean baseParsing(String HeaderStr) {
        String[] heads = HeaderStr.split("\r\n");

        for (int i = 0; i < heads.length; i++) {
            if (i == 0) {
                boolean State = parsingMethodAndProtocol(heads[0]);
                if (!State) {
                    return false;
                }
            } else {
                String[] singleHead = heads[i].split(":", 2);
                if (singleHead.length == 2) {
                    Headers.put(singleHead[0], singleHead[1]);
                }

            }


        }

        return true;
    }

    /**
     * 解析request 方法和协议类型
     *
     * @param str
     */
    private boolean parsingMethodAndProtocol(String str) {
        String[] method = str.split(" ");
        if (method.length < 3) {
            return false;
        }
        switch (method[0]) {
            case "GET":
                Method = RequestMethod.GET;
                break;
            case "POST":
                Method = RequestMethod.POST;
                break;
            case "PUT":
                Method = RequestMethod.PUT;
                break;
            default:
                Method = RequestMethod.GET;
                break;
        }

        Path = method[1];
        if (Path.contains("?")) {
            String[] Param = Path.split("\\?");
            Content = Param[1].getBytes();
        } else {
            Content = new byte[0];
        }


        String[] portocol = method[2].split("/");
        switch (portocol[0]) {
            case "HTTP":
                ProtocolType = Protocol.HTTP;
                break;
            case "HTTPS":
                ProtocolType = Protocol.HTTPS;
                break;
            default:
                ProtocolType = Protocol.HTTP;
                break;
        }

        Version = portocol[1];
        return true;
    }

    /**
     * 获取指定byte的索引位置
     *
     * @param mBaseData
     * @param src
     * @return
     */
    public static int getIndex(byte[] mBaseData, byte[] src) {
        int i;
        for (i = 0; i < mBaseData.length; i++) {
            if (mBaseData[i] == src[0]) {
                int index = i;
                for (byte by : src) {
                    if (mBaseData[index++] != by) {
                        index = -1;
                        break;
                    }
                }
                if (index != -1) {
                    return i;
                }

            } else {
                i++;
            }

        }
        return -1;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("[Path=%s],", Path == null ? "" : Path));
        builder.append(String.format("[Version=%s],", Version == null ? "" : Version));
        builder.append(String.format("[ProtocolType=%s],",ProtocolType == null ? "": ProtocolType.toString()));
        builder.append(String.format("[Method=%s],", Method == null ? "" : Method.toString()));
        builder.append(String.format("[Headers=%s],", Headers == null ? "" : Headers.toString()));
        return builder.toString();
    }

}

