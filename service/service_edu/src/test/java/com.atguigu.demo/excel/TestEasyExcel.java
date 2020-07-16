package com.atguigu.demo.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {
    public static void main(String[] args) {
        String filename = "D:\\wirte.xlsx";
        
        // 写入Excel
        // writeExcel(filename);
        
        // 读取Excel
         EasyExcel.read(filename,DemoData.class,new ExcelListener()).sheet().doRead();
    }

    private static void writeExcel(String filename) {
        
        // 路径 实体类
        EasyExcel.write(filename, DemoData.class).sheet("学生").doWrite(getData());
    }

    private static List<DemoData> getData() {
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("lucy" + i);
            list.add(data);
        }
        return list;
    }
}
