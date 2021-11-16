package one.sunny;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import com.alibaba.fastjson.JSONObject;
import one.sunny.commonutils.MyFileUtil;
import one.sunny.problem.entity.TestCase;
import org.junit.Test;

import java.io.File;

public class MyTest {
    @Test
    public void test(){
        String location = "H:\\";
        String testCaseDir = "test";
        String target_dirname = location + testCaseDir;
        File dir = new File(target_dirname);
        String[] children = dir.list();

        int testCaseNumber = children.length / 2;
        String info_front = "{\"test_case_number\": "+ String.valueOf(testCaseNumber) +", \"spj\": false,\"test_cases\": {";
        String info_mid = "";
        String info_end = "}}";

        if (children == null) {
            System.out.println( "目录不存在或它不是一个目录");
        }
        else {
            for (int i=0; i < children.length / 2; i++) {
                TestCase testCase = new TestCase();
//                读取对应下标文件
                //默认UTF-8编码，可以在构造中传入第二个参数做为编码
                String input_filename = String.valueOf(i+1) + ".in";
                File inputFile = new File(target_dirname + "\\" + input_filename);

                testCase.setInput_name(input_filename);
                testCase.setInput_size((int) inputFile.length());

                String output_filename = String.valueOf(i+1) + ".out";
                File outputFile = new File(target_dirname + "\\" + output_filename);
                String content = MyFileUtil.FileToString(outputFile);

                testCase.setOutput_name(output_filename);
                testCase.setOutput_size((int) outputFile.length());
                testCase.setOutput_md5(SecureUtil.md5(content));
                testCase.setStripped_output_md5(MyFileUtil.md5FileByTrimLines(outputFile));

                info_mid += "\""+String.valueOf(i+1)+"\":" + JSONObject.toJSONString(testCase) + (i == children.length / 2 - 1 ? "":",");
            }
        }
        String info = info_front + info_mid + info_end;
// 写入info文件
        MyFileUtil.stringToFile(info, target_dirname + "\\info");
// 返回结果
    }
}
