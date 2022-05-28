package ru.nsd.models;

import org.springframework.util.StringUtils;

public enum Role{
    USER, UNKNOWN;

    public static Role fromValue(String value){
        if(!StringUtils.hasText(value)){
            return null;
        }

        for(Role role:Role.values()){
            if(value.equals(role.name())){
                return role;
            }
        }

        throw new IllegalArgumentException("Неизвестная роль: " + value);
    }

}