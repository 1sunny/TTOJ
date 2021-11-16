<!--
TODO
无线滚动
timeline: 时间线
-->
<template>
  <el-container>
    <el-main style="box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
                    margin-right: 20px">
      <div style="margin-top: 50px">
        <div style="">
          <el-table :data="problemData" stripe style="width: 100%"
                    @row-click="goToDetail">
            <template #default="scope">
              <!-- TODO 个人通过状态   -->
              <el-table-column prop="displayId" label="#" width="180" />
              <el-table-column prop="name" label="题目" width="180" />
              <el-table-column prop="level" label="难度" />
              <el-table-column prop="acRate" label="通过率" />
              <el-table-column prop="evaluate" label="评价" />
            </template>
          </el-table>
        </div>
      </div>
    </el-main>
    <el-aside style="width: 250px;
                    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)">
      <ProblemSetAside></ProblemSetAside>
    </el-aside>
  </el-container>
</template>

<script>
import request from "@/utils/request";
import ProblemSetAside from "./ProblemSetAside";

export default {
  name: "ProblemSet",
  components:{
    ProblemSetAside
  },
  data() {
    return {
      problemData: [
        {
          date: '2016-05-03',
          name: 'Tom',
          address: 'No. 189, Grove St, Los Angeles',
        },
      ],
    }
  },
  created() {
    this.getAllProblems()
  },
  methods: {
    goToDetail(problem){
      console.log(problem);
      console.log(111);
      this.$router.push('/problem/'+problem.id)
    },
    getAllProblems(){
      request.get('/problem-api/problem/list').then(res=>{
        console.log(res);
        // TODO alter table submissions_cnt
        for (let i = 0; i < res.data.problemSet.length; i++){
          res.data.problemSet[i].acRate = res.data.problemSet[i].acCnt + '/' + res.data.problemSet[i].submitCnt
          res.data.problemSet[i].evaluate = res.data.problemSet[i].up -  res.data.problemSet[i].down
        }
        this.problemData = res.data.problemSet
        console.log(this.problemData);
      })
    }
  }
}
</script>

<style scoped>

</style>
