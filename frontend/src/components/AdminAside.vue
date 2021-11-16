<template>
  <div>
    <el-menu
        :default-active="activePath"
        class="el-menu-vertical-demo"
        router
        @open="handleOpen"
        @close="handleClose"
        style="width: 200px; min-height: calc(100vh - 50px)">
      <!--        default-openeds="[1]"-->

      <el-sub-menu index="1">
        <template #title>
          <i class="el-icon-location"></i>
          <span>题目管理</span>
        </template>
        <el-menu-item index="/admin">仪表盘</el-menu-item>
        <el-menu-item index="/admin/problems">题目列表</el-menu-item>
        <el-menu-item index="/admin/problem/create">创建题目</el-menu-item>
      </el-sub-menu>
      <el-menu-item index="/home">
        <i class="el-icon-menu"></i>
        <span>Navigator Two</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: "Aside",
  data(){
    return{
      activePath: this.$route.path
    }
  },
}
</script>

<style scoped>

</style>
