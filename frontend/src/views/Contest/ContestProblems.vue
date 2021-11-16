<template>
<div>
  <h1>ContestProblems</h1>
  <el-table :data="problems" stripe style="width: 100%"
            @row-click="toContestProblemDetail"
  >
    <el-table-column prop="displayId" label="#" width="180" />
    <el-table-column prop="name" label="题目" width="180" />
    <el-table-column prop="submitCnt" label="提交总数" width="180" />
    <el-table-column prop="acRate" label="通过率" />
  </el-table>
</div>
</template>

<script>
import request from "../../utils/request";
export default {
  name: "ContestProblems",
  data(){
    return{
      problems: [],
      contestId: 0
    }
  },
  created() {
    let id = this.$route.params.id
    this.getProblemsByContestId(id)
  },
  methods:{
    toContestProblemDetail(row, column, event){
      // console.log(row, column, event);
      let problemDisplayId = row.displayId
      let contestId = this.$route.params.id
      this.$router.push('/contest/'+contestId+'/problems/'+problemDisplayId)

    //  TODO 比赛题目和题库分开
    },
    getProblemsByContestId(id){
      request.get('/contest-api/contest/'+id+'/problems').then(res=>{
        this.problems = res.data.problems
        console.log(res.data.problems);
        for (let i in this.problems){
          let problem = this.problems[i]
          problem.acRate = (problem.submitCnt == 0 ? 0 : (problem.acCnt / problem.submitCnt).toFixed(2))+'%'
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
