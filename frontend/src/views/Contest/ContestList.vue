<template>
<div>
  <el-container>
    <el-main style="box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
                    margin-right: 20px">
      最近比赛:<br><br>
      <button v-for="(item,index) in contestList" @click="toContestDetail(item.id)">{{item.name}}<br></button>
      <br>已结束:<br><br>
    </el-main>
    <el-aside width="250px">
      <ContestAside style="box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)"></ContestAside>
    </el-aside>
  </el-container>
</div>
</template>

<!--
TODO
未开始和结束的分开
-->
<script>
import request from "../../utils/request";
import ContestAside from "./ContestAside";

export default {
  name: "ContestList",
  components:{
    ContestAside
  },
  created(){
    this.getContestList()
  },
  data(){
    return{
      contestList: []
    }
  },
  methods:{
    toContestDetail(id){
      this.$router.push('/contest/'+id)
    },
    getContestList(){
      request.get('/contest-api/contest').then(res=>{
        this.contestList = res.data.contests
        console.log(this.contestList);
      })
    }
  }
}
</script>

<style scoped>

</style>
