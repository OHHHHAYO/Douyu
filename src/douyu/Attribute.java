/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package douyu;

/**
 *
 * @author yansunkang
 */
public class Attribute {

    private int error;
    private String room_num;
    private String cate_name;
    private String status;
    private int online;
    private String time;

    public int getError() {
        return error;
    }

    public String getRoomNum() {
        return room_num;
    }

    public String getCateName() {
        return cate_name;
    }

    public boolean getStatus() {
        if (status == "1") {
            return true;
        }

        return false;
    }

    public int getOnline() {
        return online;
    }

    public String getTime() {

        return time;
    }

    public void setError(String error) {
        this.error = Integer.parseInt(error);
    }

    public void setRoomNum(String room_num) {
        this.room_num = room_num;
    }

    public void setCateName(String cate_name) {
        this.cate_name = cate_name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setOnline(String online) {
        this.online = Integer.parseInt(online);
    }

    public void setTime(String time) {
        this.time = time;
    }
}
