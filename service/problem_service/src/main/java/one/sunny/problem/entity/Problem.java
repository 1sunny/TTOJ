package one.sunny.problem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author sunny
 * @since 2021-11-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_problem")
@ApiModel(value="Problem对象", description="")
public class Problem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String displayId;
    private Boolean visible;
    private String name;
    private String description;
    private String input;
    private String output;
    private String hint;
    private String level;
    private Integer timeLimit;
    private Integer memoryLimit;
    private String tag;
    private String languages;
    private Integer up;
    private Integer down;
    private Integer submitCnt;
    private Integer acCnt;
    private String author;
    private String source;
    private String ioMode;
    private Date createTime;
    private Integer isDeleted;
    private Integer solutionCnt;
    private Integer commentCnt;
    private String testCaseDir;
    private String sampleCase;
}
