package ru.otus.spring.homework05.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.homework05.dao.LibraryDao;
import ru.otus.spring.homework05.service.LibraryService;

@Service
public class LibraryServiceImpl implements LibraryService {
    private final LibraryDao dao;

    public LibraryServiceImpl(LibraryDao dao) {
        this.dao = dao;
    }

}
