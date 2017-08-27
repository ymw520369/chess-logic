package org.alan.chess.logic.constant;

/**
 * author Alan
 * eamil mingweiyang@foxmail.com
 * date 2017/8/20
 */
public interface MessageConst {

    interface Login {
        int TYPE = 1000;
        int REQ_VERTIFY = 1;
        int RESP_CREATE_ROLE = 2;
        int REQ_CREATE_ROLE = 3;
        int RESP_ENTER_GAME = 4;
    }

    interface Tips {
        int TYPE = 1002;
        int TIPS_RESP_RESULT = 2;
    }

    interface Room {
        int TYPE = 1100;
        int REQ_CREATE_ROOM = 1;
        int RESP_CREATE_ROOM = 2;
        int REQ_BEGIN_MATCH = 3;
        int RESP_BEGIN_MATCH = 4;
        int REQ_CANEL_MATCH = 5;
        int RESP_CANEL_MATCH = 6;
        int REQ_QUICK_MATCH = 7;
    }

}
