<template>
  <el-table :data="rankList"
            stripe
            style="width: 100%"
            :cell-class-name="addClass"
  >
    <el-table-column type="index" :index="indexMethod" label="排名" />
    <el-table-column prop="user.username" label="参赛者"/>
    <el-table-column prop="acCnt" label="通过"  />
    <el-table-column prop="penalty" label="罚时" />
    <el-table-column v-for="item in problemDisplayIds" :prop="'firstAcTime.'+item" :label="item" />
  </el-table>
</template>

<script>
axios.defaults.withCredentials = true
import request from "../../utils/request";
export default {
  name: "ContestRankings",
  data(){
    return{
      contestId: 0,
      rankList: [],
      problemDisplayIds: []
    }
  },
  created(){
    this.contestId = this.$route.params.id
    this.getRankList(this.contestId)
  },
  methods:{
    addClass(row, column, rowIndex, columnIndex){
      console.log(row);
      console.log(row.row.isFirstAcUser[row.column.label]);
      if (row.row.isFirstAcUser[row.column.label]){
        console.log("in");
        return 'firstBloodCell'
      }else if(row.row.firstAcTime[row.column.label] != null){
        return 'acceptedCell'
      }
    },
    indexMethod(index) {
      return index+1
    },
    getRankList(id){
      request.get('/contest-api/contest/rankings/'+id).then(res=>{
        this.rankList = res.data.rankList
        this.problemDisplayIds = res.data.problemDisplayIds
        console.log(this.rankList);
        console.log(this.problemDisplayIds);
      })
    }
  }
}
</script>

<style>
.firstBloodCell{
  background: #bfe6de;
}
.acceptedCell{
  background: #eff9f7;
}
</style>
