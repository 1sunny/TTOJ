<template>
  <el-container>
    <el-main style="box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
                    margin-right: 20px">
      <router-view/>
    </el-main>
    <el-aside width="250px">
      <ContestDetailAside :id="id" style="box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)"></ContestDetailAside>
    </el-aside>
  </el-container>
</template>

<script>
import ContestDetailAside from "./ContestDetailAside";
export default {
  name: "ContestDetail",
  components:{
    ContestDetailAside,
  },
  data(){
    return{
      id: 0
    }
  },
  created() {
    this.id = this.$route.params.id
  },
}
</script>

<style scoped>

</style>
