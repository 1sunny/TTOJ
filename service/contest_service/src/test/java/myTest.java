import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class myTest{
    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void test(){
        Object asdfasdf = redisTemplate.opsForValue().get("asdfasdf");
        System.out.println(asdfasdf);
    }
}
