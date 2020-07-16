package com.atguigu.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class DemoData {
    // 设置表头
    // index = 0 表示第一列
    @ExcelProperty(value = "学生编号",index = 0)
    private Integer sno;
    // index = 1 第二列
    @ExcelProperty(value = "学生姓名",index = 1)
    private String sname;
}
