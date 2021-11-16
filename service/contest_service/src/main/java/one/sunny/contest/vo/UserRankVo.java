package one.sunny.contest.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import one.sunny.user.entity.User;

import java.util.Date;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRankVo implements Comparable<UserRankVo>{
    private User user;
    private Integer acCnt;
    private Long penalty;
    private Map<String, Boolean> isFirstAcUser;
    private Map<String, Date> firstAcTime;
    private Map<String, Integer> waTimes;

    @Override
    public int compareTo(UserRankVo userRankVo){
        if (this.getAcCnt() > userRankVo.getAcCnt()){
            return -1;
        }else if(this.getAcCnt() < userRankVo.getAcCnt()){
            return 1;
        }else{
            if (this.getPenalty() < userRankVo.getPenalty()){
                return -1;
            }else{
                return 1;
            }
        }
    }
}
