package com.example.snashuitraverl.demos.once;

import com.alibaba.excel.EasyExcel;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 导入到数据库
 *
 * @author shen_.li
 * shen_.li
 */
public class ImportXingQiuUser {

    public static void main(String[] args) {
        // todo 记得改为自己的测试文件
        String fileName = "C:\\Users\\32551\\Desktop\\项目集锦\\HYzn\\src\\main\\resources\\工作簿1.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 同步读取会自动finish
        List<UserNames> userInfoList =
                EasyExcel.read(fileName).head(UserNames.class).sheet().doReadSync();
        System.out.println("总数 = " + userInfoList.size());
        Map<String, List<UserNames>> listMap =
                userInfoList.stream()
                        .filter(userInfo -> StringUtils.isNotEmpty(userInfo.getUserId()))
                        .collect(Collectors.groupingBy(UserNames::getUserName));
        for (Map.Entry<String, List<UserNames>> stringListEntry : listMap.entrySet()) {
            if (stringListEntry.getValue().size() > 1) {
                System.out.println("username = " + stringListEntry.getKey());
                System.out.println("1");
            }
        }
        System.out.println("不重复昵称数 = " + listMap.keySet().size());
    }
}
