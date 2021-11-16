package one.sunny.judgeservice.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JudgeVo {
    private Integer mysqlProblemId;
    private String code;
    private String type;
    private Integer contestId;
    private String problemDisplayId;
}
