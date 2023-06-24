package com.examen.truper.truper.model.security;

import lombok.Getter;
import lombok.Setter;

import java.security.Key;

public class SingletonKey {
    private static SingletonKey singletonKey;
    @Getter
    @Setter
    private Key signingKey;

    private SingletonKey(){ }

    public static SingletonKey getInstance(){
        if(null == singletonKey){
            singletonKey = new SingletonKey();
        }

        return singletonKey;
    }
}
