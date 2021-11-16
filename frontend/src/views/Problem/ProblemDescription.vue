<template>
<div style="display: flex;
            flex-direction: column">
  <div id="name">
    {{problem.displayId}}. {{problem.name}}
  </div>
  <div id="info" style="display: flex;
                        ">
    <div>
      <span style="">难度</span><span id="level">{{problem.level}}</span>
    </div>

    <div>
      <span style=""></span>
      <span id="up">{{problem.up}}</span>
      <span style=""></span>
      <span id="down">{{problem.down}}</span>
    </div>
  </div>
  <el-divider></el-divider>
  <div id="description">
    {{problem.description}}
  </div>
  <div id="input">
    {{problem.input}}
  </div>
  <div id="output">
    {{problem.output}}
  </div>
  <div class="sample" v-for="(item,i) in problem.sampleCase">
    示例 {{i+1}}:
    <MarkdownArea :content="item.input"></MarkdownArea>
    <MarkdownArea :content="item.output"></MarkdownArea>
  </div>
  <div id="hint">
    {{problem.hint}}
  </div>
</div>
</template>

<script>
import request from "@/utils/request";
import MarkdownArea from "@/components/MarkdownArea";

export default {
  name: "ProblemDescription",
  components:{
    MarkdownArea
  },
  props:{
    id:{
      type: Number,
      default: 0
    }
  },
  data(){
    return{
      problem: {}
    }
  },
  created() {
    // console.log(this.$route.params);
    var id = this.$route.params.id
    console.log(id);
    // TODO  getProblemById
    request.get('/problem-api/problem/'+id).then(res=>{
      console.log(res);
      this.problem = res.data.problem
      this.problem.sampleCase = JSON.parse(this.problem.sampleCase)
    })

  },
  methods:{

  }
}
</script>

<style scoped>

</style>
