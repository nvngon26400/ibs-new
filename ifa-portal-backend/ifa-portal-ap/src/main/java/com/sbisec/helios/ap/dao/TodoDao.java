package com.sbisec.helios.ap.dao;


import org.apache.ibatis.annotations.Mapper;

import com.sbisec.helios.ap.model.Todo;

@Mapper
public interface TodoDao {
	void insert(Todo todo);
    Todo select(int id);
}

