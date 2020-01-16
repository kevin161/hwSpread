package com.leisurely.spread.model.enums;

public enum ScoreWay {
    BUSINESS(1,"交易");

    private int id;

    private String name;

    ScoreWay(int id, String name) {
        this.id = id;
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
