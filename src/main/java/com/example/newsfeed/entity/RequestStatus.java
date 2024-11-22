package com.example.newsfeed.entity;

public enum RequestStatus {
    PENDING(0),
    ACCEPTED(1),
    REJECTED(2);

    private final int value;

    RequestStatus(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }

    public static RequestStatus fromValue(int value){
        for(RequestStatus status : RequestStatus.values()){
            if(status.getValue() == value){
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown enum value : " + value);
    }
}
