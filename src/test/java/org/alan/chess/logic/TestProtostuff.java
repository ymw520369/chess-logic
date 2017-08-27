package org.alan.chess.logic;

import com.google.protobuf.InvalidProtocolBufferException;
import org.alan.chess.logic.login.LoginMessageHandler;

import java.util.Arrays;


/**
 * author Alan
 * eamil mingweiyang@foxmail.com
 * date 2017/8/23
 */
public class TestProtostuff {

    final static byte[] d1 = {10, 32, 100, 57, 51, 56, 49, 99, 55, 53, 99, 97, 97, 51, 50, 100, 97, 52, 55, 56, 48, 99, 52, 57, 100, 98, 56, 48, 57, 50, 48, 57, 52, 49, 16, -111, 78, 24, 1};
    final static byte[] d2 = {10, 32, 100, 57, 51, 56, 49, 99, 55, 53, 99, 97, 97, 51, 50, 100, 97, 52, 55, 56, 48, 99, 52, 57, 100, 98, 56, 48, 57, 50, 48, 57, 52, 49, 16, -111, 78, 24, 1};
    final static byte[] d3 = {10, 32, 100, 57, 51, 56, 49, 99, 55, 53, 99, 97, 97, 51, 50, 100, 97, 52, 55, 56, 48, 99, 52, 57, 100, 98, 56, 48, 57, 50, 48, 57, 52, 49, 16, -111, 78, 24, 1};

    public static void main(String[] args) throws InvalidProtocolBufferException {
//        byte[] source = {11, 10, 32, 100, 57, 51, 56, 49, 99, 55, 53, 99, 97, 97, 51, 50, 100, 97, 52, 55, 56, 48, 99, 52, 57, 100, 98, 56, 48, 57, 50, 48, 57, 52, 49, 16, -111, 78, 24, 1, 12, 18, 4, 97, 108, 97, 110};
//        byte[] cssour = {10, 39, 10, 32, 100, 57, 51, 56, 49, 99, 55, 53, 99, 97, 97, 51, 50, 100, 97, 52, 55, 56, 48, 99, 52, 57, 100, 98, 56, 48, 57, 50, 48, 57, 52, 49, 16, -111, 78, 24, 1, 18, 4, 97, 108, 97, 110};
        LoginMessageHandler.ReqCreateRole reqCreateRole = new LoginMessageHandler.ReqCreateRole();
        reqCreateRole.vertifyUserInfo = new LoginMessageHandler.VertifyUserInfo();
        reqCreateRole.vertifyUserInfo.token = "d9381c75caa32da4780c49db80920941";
        reqCreateRole.vertifyUserInfo.userId = 10001;
        reqCreateRole.vertifyUserInfo.zoneId = 1;
        reqCreateRole.name = "alan";
//        byte[] data = ProtocstuffUtils.bean2Byte(reqCreateRole, reqCreateRole.getClass());
//        System.out.println(Arrays.toString(data));
//
//        data = MyProtostuffUtil.serialize(reqCreateRole,reqCreateRole.getClass());
//        System.out.println(Arrays.toString(data));
//        System.out.println(data.length);
//        ReqCreateRole reqCreateRole1 = MyProtostuffUtil.deserialize(data, ReqCreateRole.class);
//        System.out.println(JSON.toJSON(reqCreateRole1));

//        byte[] cssour = {10, 39, 10, 32, 100, 57, 51, 56, 49, 99, 55, 53, 99, 97, 97, 51, 50, 100, 97, 52, 55, 56, 48, 99, 52, 57, 100, 98, 56, 48, 57, 50, 48, 57, 52, 49, 16, -111, 78, 24, 1, 18, 4, 97, 108, 97, 110};
//
//        Proto.ReqCreateRole r = Proto.ReqCreateRole.parseFrom(cssour);
//        System.out.println(r);

//        Proto.ReqCreateRole.Builder builder = Proto.ReqCreateRole.newBuilder().setName("alan").
//                setVertifyUserInfo(Proto.VertifyUserInfo.newBuilder().setToken("d9381c75caa32da4780c49db80920941").setUserId(10001).setZoneId(1));
//        //builder.build().toByteArray();
//        System.out.println(Arrays.toString(builder.build().toByteArray()));

    }
}
