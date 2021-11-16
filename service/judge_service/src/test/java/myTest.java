import com.alibaba.fastjson.JSONObject;
import one.sunny.judgeservice.entity.JudgeBean;

public class myTest {
    @org.junit.Test
    public void test(){
        JudgeBean judgeBean = new JudgeBean("cpp", "src_code",100,100,"case_id",true);
        System.out.println(judgeBean);
        String json = JSONObject.toJSONString(judgeBean);
        System.out.println(json);
    }
}
