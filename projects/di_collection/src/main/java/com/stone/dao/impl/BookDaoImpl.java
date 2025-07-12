package com.stone.dao.impl;

import com.stone.dao.BookDao;

import java.util.*;

public class BookDaoImpl implements BookDao {

    private int[] arrName;

    private List<String> listName;

    private Set<String> setName;

    private Map<String, String> mapName;

    private Properties propertiesName;

    public void setArrName(int[] arrName) {
        this.arrName = arrName;
    }

    public void setListName(List<String> listName) {
        this.listName = listName;
    }

    public void setSetName(Set<String> setName) {
        this.setName = setName;
    }

    public void setMapName(Map<String, String> mapName) {
        this.mapName = mapName;
    }

    public void setPropertiesName(Properties propertiesName) {
        this.propertiesName = propertiesName;
    }

    @Override
    public void save() {
        System.out.println("book dao save...");

        System.out.println("遍历 数组：" + Arrays.toString(arrName));
        System.out.println("遍历 List：" + listName);
        System.out.println("遍历 Set：" + setName);
        System.out.println("遍历 Map：" + mapName);
        System.out.println("遍历 Properties：" + propertiesName);
    }
}
