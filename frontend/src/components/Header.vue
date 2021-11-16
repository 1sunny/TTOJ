<template>
<div style="margin: 0 auto;">
  <el-menu
      :default-active="activePath"
      class="el-menu-demo"
      mode="horizontal"
      router
      @select="handleSelect"
  >
    <el-menu-item index="/home">主页</el-menu-item>
    <el-menu-item index="/problemset">题库</el-menu-item>
    <el-menu-item index="/contest">比赛</el-menu-item>
    <el-menu-item index="/forum">论坛</el-menu-item>
    <el-menu-item index="/admin">Admin</el-menu-item>
    <template v-if="user !== null">
      <el-sub-menu>
          <template #title>{{ user.username }}</template>
          <el-menu-item index="2-1">个人</el-menu-item>
      </el-sub-menu>
    </template>
    <template v-else>
        <el-button @click="loginDialogVisible = true">登录</el-button>
        <el-button @click="registerDialogVisible = true">注册</el-button>
    </template>
  </el-menu>
  <el-dialog
      v-model="loginDialogVisible"
      title="登录"
      width="30%"
      :before-close="handleLoginClose"
  >
    <el-form ref="loginForm" :model="loginForm" label-width="120px">
      <el-form-item label="用户名">
        <el-input v-model="loginForm.username"></el-input>
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="loginForm.password"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onLoginSubmit">登录</el-button>
        <el-button @click="closeLoginForm">Cancel</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
  <el-dialog
      v-model="registerDialogVisible"
      title="注册"
      width="30%"
      :before-close="handleRegisterClose"
  >
    <el-form-item label="用户名">
      <el-input v-model="registerForm.username"></el-input>
    </el-form-item>
    <el-form-item label="密码">
      <el-input v-model="registerForm.password" show-password></el-input>
    </el-form-item>
<!--    TODO-->
<!--    <el-form-item label="确认密码">-->
<!--      <el-input v-model="registerForm.confirmPassword" show-password></el-input>-->
<!--    </el-form-item>-->
    <el-form-item>
      <el-button type="primary" @click="onRegisterSubmit">注册</el-button>
      <el-button @click="closeRegisterForm">取消</el-button>
    </el-form-item>
  </el-dialog>
</div>
</template>

<script>
import request from "@/utils/request";
import { defineComponent } from 'vue'
import { ElMessage } from 'element-plus'

export default {
  name: "Header",
  data(){
    return{
      user: null,
      loginDialogVisible: false,
      registerDialogVisible: false,
      loginForm: {},
      registerForm: {}
    }
  },
  methods:{
    /*
    type: success,warning,message,error
    TODO make this function global
     */
    sendMessage(message, type='success'){
      ElMessage({
        message,
        type
      })
    },
    onLoginSubmit(){
      request.post('/user-api/user/login', this.loginForm)
          .then(res=>{
            console.log(res);
            if (res.success){
              this.loginDialogVisible = false
              this.sendMessage(res.message)
              this.user = res.data.user
              // TODO localStorage
              sessionStorage.setItem("user", JSON.stringify(res.data.user))
              this.$router.push('/')
            }else{
              this.sendMessage(res.message, 'error')
            }
          })
    },
    onRegisterSubmit(){
      request.post('/user-api/user/register', this.registerForm)
          .then(res=>{
            console.log(res)
            if (res.success){
              this.registerDialogVisible = false
              this.sendMessage(res.message)
            }else{
              this.sendMessage(res.message, 'error')
            }
          })
    },
    closeLoginForm(){
      this.loginDialogVisible = false
      this.loginForm = {}
    },
    closeRegisterForm(){
      this.registerDialogVisible = false
      this.registerForm = {}
    },
    handleLoginClose(){

    },
    handleRegisterClose(){

    }
  }
}
</script>

<style scoped>

</style>
