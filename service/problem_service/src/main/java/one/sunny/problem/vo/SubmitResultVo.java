package one.sunny.problem.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmitResultVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long judgeId;
    private String username;
    private String problemDisplayId;
    private String judgeResult;
    private Integer timeUse;
    private Integer memoryUse;
    private Integer codeLength;
    private String language;
    private Date submitTime;
}
