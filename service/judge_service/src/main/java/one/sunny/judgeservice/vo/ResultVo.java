package one.sunny.judgeservice.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultVo {
    private String err;
    private List<TestCaseResults> data;
}
