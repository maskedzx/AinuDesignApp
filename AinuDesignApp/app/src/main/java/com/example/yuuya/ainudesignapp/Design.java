package com.example.yuuya.ainudesignapp;


import java.util.ArrayList;
import java.util.List;

public class Design  {

    private String value;
    private long createdTime;

    public Design( String value, long createdTime) {
        this.value = value;
        this.createdTime = createdTime;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * �e�X�g�\���p�Ƀ_�~�[�̃��X�g�A�C�e�����쐬.
     */
    public static List<Design> addDummyItem() {
        List<Design> items = new ArrayList<>();
        return items;
    }
}
