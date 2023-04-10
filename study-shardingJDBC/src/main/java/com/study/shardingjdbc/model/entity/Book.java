package com.study.shardingjdbc.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long id;

    private Date createDate;

    private Date updateDate;

    private String bookName;

    private String author;

    private String description;

    private BigDecimal price;

    private Integer stock;


}