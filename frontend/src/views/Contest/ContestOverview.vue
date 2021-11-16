<template>
<div>
  {{contest.description}}
</div>
</template>

<script>
import request from "../../utils/request";

export default {
  name: "ContestOverview",
  data(){
    return{
      contest: {}
    }
  },
  created() {
    let id = this.$route.params.id
    this.getContestById(id)
  },
  methods:{
    getContestById(id){
      request.get('/contest-api/contest/'+id).then(res=>{
        this.contest = res.data.contest
        console.log(this.contest);
      })
    }
  }
}
</script>

<style scoped>

</style>
