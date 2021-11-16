package one.sunny.contest.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author sunny
 * @since 2021-11-15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateContestVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String description;
    private Date startTime;
    private Date endTime;
    private String password;
    private Integer ruleType;
    private Boolean showRank;
    private Boolean visible;
}
