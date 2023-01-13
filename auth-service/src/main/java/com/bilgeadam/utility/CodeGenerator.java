package com.bilgeadam.utility;

import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

public class CodeGenerator {


    public  static String genarateCode(){
        String code=UUID.randomUUID().toString();
        String [] data=code.split("-")  ;
        StringBuilder newCode=new StringBuilder();
        for (String string:data){
            newCode.append(string.charAt(0));
        }
       return newCode.toString();
    }


}
