package com.example.snashuitraverl.demos.once;

import com.alibaba.excel.EasyExcel;

import java.util.List;

/**
 * 导入 Excel
 *
 * @author shen_.li
 * shen_.li
 */
public class ImportExcel {

    /**
     * 读取数据
     */
    public static void main(String[] args) {
        // todo 记得改为自己的测试文件
        String fileName = "C:\\Users\\32551\\Desktop\\项目集锦\\HYzn\\src\\main\\resources\\工作簿1.xlsx";
//        readByListener(fileName);
        synchronousRead(fileName);
    }

    /**
     * 监听器读取
     *
     * @param fileName
     */
    public static void readByListener(String fileName) {
        EasyExcel.read(fileName, UserNames.class, new TableListener()).sheet().doRead();
    }

    // [加入我们](https://shen_.li.icu) 从 0 到 1 项目实战，经验拉满！10+ 原创项目手把手教程、7 日项目提升训练营、1000+ 项目经验笔记、60+ 编程经验分享直播

    /**
     * 同步读
     *
     * @param fileName
     */
    public static void synchronousRead(String fileName) {
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 同步读取会自动finish
        List<UserNames> totalDataList =
                EasyExcel.read(fileName).head(UserNames.class).sheet().doReadSync();
        for (UserNames userNames : totalDataList) {
            System.out.println(userNames);
        }
    }

}
