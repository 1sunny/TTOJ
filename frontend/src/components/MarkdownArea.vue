<template>
<div style="background-color: #f2f3f4;
            border-radius: 5px;
            width: 95%;
            margin: 10px 0">

  <div style="padding: 5px;
              ">
    {{content}}
  </div>
</div>
</template>

<script>
export default {
  name: "MarkdownArea",
  props:{
    content:{
      type: String,
      default: ''
    }
  },
  data(){
    return{
    }
  }
}
</script>

<style scoped>

</style>
