package one.sunny.judgeservice.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestCaseResults{
    private Integer cpu_time;
    private Integer real_time;
    private Integer memory;
    private Integer signal;
    private Integer exit_code;
    private Integer error;
    private Integer result;
    private String test_case;
    private String output_md5;
    private String output;
}
