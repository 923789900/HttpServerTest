package com.king.httpservertest.logUtils.Http;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class Request {

    public String Path;

    public String Version;

    public byte[] Content;

    public Protocol ProtocolType;

    public RequestMethod Method;

    public HashMap<String, String> Headers = new HashMap<>();

    /**
     * 解析request object
     * @param outputStream
     * @return
     */
    public static Request Parsing(ByteArrayOutputStream outputStream) {

        Request req = new Request();
        int Size = outputStream.size();
        byte[] buf = outputStream.toByteArray();
        int index = getIndex(buf, "\n\n".getBytes());
        //header data
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int Length = ++index;
        out.write(buf,0,Length);
        String HeaderStr = out.toString();
        baseParsing(HeaderStr,req);

        //date
        if(outputStream.size() > Length)
        {
            out.reset();
            Length = Length+2;
            out.write(buf,Length,buf.length-Length);
            req.Content =out.toByteArray();
        }

        return req;
    }

    /**
     * 解析request object
     * @param HeaderStr
     * @param req
     */
    private static void baseParsing(String HeaderStr, Request req)
    {
        String[] heads =  HeaderStr.split("\n");

        for (int i=0;i<heads.length;i++)
        {
            if (i == 0)
            {
                parsingMethodAndProtocol(heads[0],req);
            }else
            {
                String[] singleHead = heads[i].split(":",2);
                if (singleHead.length == 2 )
                {
                    req.Headers.put(singleHead[0],singleHead[1]);
                }

            }


        }


    }

    /**
     * 解析request 方法和协议类型
     * @param str
     * @param req
     */
    private static void parsingMethodAndProtocol(String str, Request req)
    {
        String[] method = str.split(" ");
        switch (method[0])
        {
            case "GET":
                req.Method = RequestMethod.GET;
                break;
            case "POST":
                req.Method = RequestMethod.POST;
                break;
            case "PUT":
                req.Method = RequestMethod.PUT;
                break;
            default:
                req.Method = RequestMethod.GET;
                break;
        }

        req.Path = method[1];

        String[] portocol = method[2].split("/");
        switch (portocol[0])
        {
            case "HTTP":
                req.ProtocolType = Protocol.HTTP;
                break;
            case "HTTPS":
                req.ProtocolType = Protocol.HTTPS;
                break;
            default:
                req.ProtocolType = Protocol.HTTP;
                break;
        }

        req.Version = portocol[1];
    }

    /**
     * 获取指定byte的索引位置
     * @param mBaseData
     * @param src
     * @return
     */
    public static int getIndex(byte[] mBaseData, byte[] src) {
        int i = 0;
        while (i < mBaseData.length) {
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
        builder.append(String.format("[Path=%s],",Path));
        builder.append(String.format("[Version=%s],",Version));
        builder.append(String.format("[ProtocolType=%s],",ProtocolType.toString()));
        builder.append(String.format("[Method=%s],",Method.toString()));
        builder.append(String.format("[Headers=%s],",Headers.toString()));
        return builder.toString();
    }

}

