<template>
<div class="page" style="margin-top: -20px;
                         ">
  <div class="wrapper">
    <div v-resize="options" class="box1" @resize="handleResizeX">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="题目描述" name="description"/>
        <el-tab-pane label="评论" name="comments"/>
        <el-tab-pane label="题解" name="solutions"/>
        <el-tab-pane label="提交记录" name="submitRecord"/>
      </el-tabs>
      <router-view/>
    </div>
<!--  TODO  -->
    <div ref="resize" class="box2">
      <Editor v-on:codeChanged="codeChanged"></Editor>
      <button @click="submit">提交</button>
      <input type="text" v-model="result">
    </div>
  </div>
</div>

</template>
<!--TODO  keep alive  代码丢失-->
<script>
import request from "@/utils/request";
import Editor from "@/components/Editor";
import { ref, defineComponent } from 'vue';
import { ResizeEvent, ResizeDirective } from '@howdyjs/resize';

export default {
  name: "ProblemDetail",
  components:{
    Editor,
  },
  directives: {
    resize: ResizeDirective
  },
  setup() {
    const resize = ref();
    return {
      resize,
      options: {
        // use default
      },
      handleResizeX (e/*: ResizeEvent*/) {
        console.log('resizeEvent', e);
        let el = e.target/* as HTMLElement*/;
        if (e.resizeWidthPercent) {
          el.style.width = `${e.resizeWidthPercent}%`;
          resize.value.style.width = `${100 - e.resizeWidthPercent}%`;
        }
      }
    };
  },
  data(){
    return{
      problem: {},
      code: '', // 用户代码
      result: '', // 测评结果
      type: 'cpp', // 语言类型
      err: '', // 编译错误
      activeName: 'description' // tabbar
    }
  },
  created() {
    console.log(this.$route.params);
    var id = this.$route.params.id
    request.get('/problem-api/problem/'+id).then(res=>{
      this.problem = res.data.problem
      console.log(this.problem );
    })
  },
  methods: {
    // TODO bug --> 直接url进入, tabbar错误
    handleClick(tab, event) {
      let name = tab.props.name
      console.log(name);
      console.log('/problem/' + this.problem.id + '/' + (name == 'description' ? '':name));
      this.activeName = name
      this.$router.push('/problem/'+this.problem.id+'/'+ (name == 'description' ? '':name))
      console.log(tab.props, event)
    },
    codeChanged(code) {
      this.code = code
      console.log(code);
    },
    submit() {
      console.log(this.code);
      request.post('/judge-api/judge/problemSet', {
        id: this.problem.id,
        code: this.code,
        type: this.type
      }).then(res => {
        console.log(res);
        this.result = res.data.judgeResult
      })
    }
  }
}
</script>

<style scoped>
.page {
  overflow-x: auto;
  height: 100%;
}
.wrapper {
  width: 100%;
  height: 100%;
  display: flex;
  box-shadow: 0 1px 5px #ccc;
  position: relative;
}
.box1 {
  width: 50%;
  height: 100%;
}
.box2 {
  width: 50%;
  height: 100%;
  background: #ffe0e0;
}
.text {
  font-size: 12px;
  color: #889;
  padding: 5px;
}
</style>
