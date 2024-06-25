package com.example.snashuitraverl.demos.once;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class UserNames {
    @ExcelProperty("userName")
    private String userName;
    @ExcelProperty("userId")
    private String userId;

}