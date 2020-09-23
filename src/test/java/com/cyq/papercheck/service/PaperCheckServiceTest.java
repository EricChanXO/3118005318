package com.cyq.papercheck.service;

import com.cyq.papercheck.exception.CommonException;
import com.cyq.papercheck.service.impl.PaperCheckServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class PaperCheckServiceTest {

    @Test
    public void check() {
        PaperCheckService paperCheckService = new PaperCheckServiceImpl();
        String path1 = "src/main/resources/orig_null.txt";
        String path2 = "src/main/resources/orig.txt";
        String path3 = "src/main/resources/orig_0.8_add.txt";
        String path4 = "src/main/resources/orig_0.8_del.txt";
        String path5 = "src/main/resources/orig_0.8_dis_10.txt";
        String path6 = "src/main/resources/orig_0.8_dis_15.txt";

        Assertions.assertThrows(CommonException.class,
                () -> paperCheckService.check(path1, path2));
        try {
            Assertions.assertNotEquals(paperCheckService.check(path2, path3), 1.0);
            Assertions.assertNotEquals(paperCheckService.check(path2, path3), 0.0);
            Assertions.assertNotEquals(paperCheckService.check(path2, path4), 1.0);
            Assertions.assertNotEquals(paperCheckService.check(path3, path4), 1.0);
            Assertions.assertNotEquals(paperCheckService.check(path5, path6), 1.0);
        } catch (IOException e) {
            Assertions.assertEquals(e.getClass(), IOException.class);
        }
    }
}