package cn.srblog.faceverification.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;


@JsonInclude(value= JsonInclude.Include.NON_NULL)
public class Result<T> implements Serializable {

    private int status;
    private String msg;
    private T data;

    @JsonIgnore
    public boolean isSuccess(){
        return this.status == 0;
    }

    public static <T> Result<T> createBySuccess(){
        return new Result<T>(0);
    }

    public static <T> Result<T> createBySuccess(String msg){
        return new Result<T>(0,msg);
    }

    public static <T> Result<T> createBySuccess(T data){
        return new Result<T>(0,data);
    }

    public static <T> Result<T> createBySuccess(String msg,T data){
        return new Result<T>(0,msg,data);
    }

    public static <T> Result<T> createByError(){
        return new Result<T>(-1);
    }

    public static <T> Result<T> createByErrorMessage(String errorMsg){
        return new Result<T>(-1,errorMsg);
    }

    public static <T> Result<T> createByErrorMessage(int errorCode,String errorMsg){
        return new Result<T>(errorCode,errorMsg);
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public Result(int status ) {
        this.status = status;
    }


    public Result(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public Result(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public Result(int status, T data) {
        this.status = status;
        this.data = data;
    }


}
