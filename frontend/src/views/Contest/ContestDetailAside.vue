<template>
  <el-col :span="24" style="width: 100%">
    <el-menu
        default-active="2"
        class="el-menu-vertical-demo"
        @open="handleOpen"
        @close="handleClose"
        router
    >
      <el-menu-item :index="'/contest/'+id">
        <el-icon><icon-menu /></el-icon>
        <span>Overview</span>
      </el-menu-item>
      <el-menu-item :index="'/contest/'+id+'/problems'">
        <el-icon><document /></el-icon>
        <span>Problems</span>
      </el-menu-item>
      <el-menu-item :index="'/contest/'+id+'/submissions'">
        <el-icon><setting /></el-icon>
        <span>Submissions</span>
      </el-menu-item>
      <el-menu-item :index="'/contest/'+id+'/rankings'">
        <el-icon><setting /></el-icon>
        <span>Rankings</span>
      </el-menu-item>
      <el-menu-item :index="'/contest/'+id+'/announcements'">
        <el-icon><setting /></el-icon>
        <span>Announcements</span>
      </el-menu-item>
    </el-menu>
  </el-col>
</template>
<!--
TODO
aside组件化,全局使用
-->
<script>
export default {
  name: "ContestDetailAside",
  props:{
    id:{
      type:Number,
      default: 0
    }
  }
}
</script>

<style scoped>

</style>
