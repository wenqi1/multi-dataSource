package com.learn.multidatasource.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ErpUser {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;
    private String erpName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getErpName() {
        return erpName;
    }

    public void setErpName(String erpName) {
        this.erpName = erpName;
    }
}
