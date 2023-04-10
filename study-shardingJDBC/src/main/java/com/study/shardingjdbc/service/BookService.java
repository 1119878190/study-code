package com.study.shardingjdbc.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.shardingjdbc.mapper.BookDao;
import com.study.shardingjdbc.model.entity.Book;
import org.springframework.stereotype.Service;

/**
 * @author lx
 * @date 2023/04/03
 */
@Service
public class BookService extends ServiceImpl<BookDao, Book> {


}
