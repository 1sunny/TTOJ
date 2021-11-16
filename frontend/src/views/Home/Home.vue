<template>
  <el-container>
    <el-main style="box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
                    margin-right: 20px">
      <div>
        1. banner 图片 <br>
        2. 比赛帖子 <br>
        3. 使用教程 <br>
        4. 各种介绍 <br>
      </div>
    </el-main>
    <el-aside width="250px">
      <HomeAside style="box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)"></HomeAside>
    </el-aside>
  </el-container>

</template>

<script>
import HomeAside from "@/views/Home/HomeAside";
export default {
  name: "Home",
  components:{
    HomeAside
  }
}
</script>

<style scoped>

</style>
