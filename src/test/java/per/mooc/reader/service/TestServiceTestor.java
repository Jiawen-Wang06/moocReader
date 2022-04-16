package per.mooc.reader.service;

import org.junit.Test;

import javax.annotation.Resource;

import static org.junit.Assert.*;

public class TestServiceTestor {
    @Resource
    private TestService testService;
    @Test
    public void batchImport() {
        testService.batchImport();
        System.out.println("Success!");
    }
}