package one.sunny.problem.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.MD5;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import one.sunny.commonutils.MyFileUtil;
import one.sunny.commonutils.R;
import one.sunny.problem.entity.Problem;
import one.sunny.problem.entity.TestCase;
import one.sunny.problem.service.IProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sunny
 * @since 2021-11-01
 */
@Slf4j
@Api("problem_service")
@RestController
@RequestMapping("/problem")
@CrossOrigin
public class ProblemController {
    @Autowired
    IProblemService problemService;

    @ApiOperation("获得所有题目")
    @GetMapping("/list")
    public R getAllProblems(){
        System.out.println("getAllProblems");
        List<Problem> problemSet = problemService.getAllProblem();
        System.out.println(problemSet);
        return R.ok().data("problemSet", problemSet);
    }
    @ApiOperation("通过id获得单个题目")
    // TODO
    @GetMapping("{id}")
    public R getProblemById(@PathVariable("id") Integer id){
        Problem problem = problemService.getById(id);
        return R.ok().data("problem", problem);
    }
    // TODO 改为linux环境
    @ApiOperation("保存题目")
    @PostMapping("/save")
    public R saveProblem(@RequestParam("file") MultipartFile testcase,
                         @RequestParam("form") String form) throws IOException {
        Problem problem = JSON.parseObject(form, Problem.class);
        System.out.println(problem);
        if (problem == null || testcase == null){
            return R.error();
        }
// 保存文件部分
        String testCaseDir = IdUtil.simpleUUID();
        problem.setTestCaseDir(testCaseDir);
        System.out.println(testcase.getOriginalFilename());
        String filename = testcase.getOriginalFilename();
        String extension = filename.substring(filename.lastIndexOf("."));
        boolean save = problemService.save(problem);
        String location = "W:\\my\\docker\\qdoj\\tests\\test_case\\";
        String saveLocation = location + testCaseDir + extension;
        FileOutputStream fos = new FileOutputStream(saveLocation,false);
        fos.write(testcase.getBytes());
        fos.close();
// 解压 删除文件
// TODO 不同扩展用不同命令
        String shell = "unzip " + saveLocation + " -d " + location + testCaseDir + " && rm " + saveLocation;
        log.info(shell);
//        String result = CmdUtil.execCmd(shell);
// TODO 题目名作为文件夹名
// 制作info文件
// 读目录
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
//  读取对应下标文件
// 默认UTF-8编码，可以在构造中传入第二个参数做为编码
                String input_filename = String.valueOf(i+1) + ".in";
                File inputFile = new File(target_dirname + "\\" + input_filename);

                testCase.setInput_name(input_filename);
                testCase.setInput_size((int) inputFile.length());

                String output_filename = String.valueOf(i+1) + ".out";
                File outputFile = new File(target_dirname + "\\" + output_filename);
                String content = MyFileUtil.FileToString(outputFile);

                testCase.setOutput_name(output_filename);
                testCase.setOutput_size((int) outputFile.length());
                testCase.setOutput_md5(MD5.create().digestHex16(content));
                testCase.setStripped_output_md5(MyFileUtil.md5FileByTrimLines(outputFile));

                info_mid += "\""+String.valueOf(i+1)+"\":" + JSONObject.toJSONString(testCase) + (i == children.length / 2 - 1 ? "":",");
            }
        }
        String info = info_front + info_mid + info_end;
// 写入info文件
        MyFileUtil.stringToFile(info, target_dirname + "\\info");
// 返回结果
        if (save){
            return R.ok().message("创建成功");
        }else{
            return R.error().message("创建失败");
        }
    }
}
