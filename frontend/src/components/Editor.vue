<template>
  <Codemirror
      v-model:value="code"
      :options="cmOptions"
      border
      placeholder="test placeholder"
      :height="200"
      @change="codeChanged"
  />
</template>

<script>
import Codemirror from "codemirror-editor-vue3";
// plugin-style
import "codemirror-editor-vue3/dist/style.css";
// language
import "codemirror/mode/clike/clike"
import "codemirror/mode/javascript/javascript.js";
// theme
import "codemirror/theme/dracula.css"
import "codemirror/theme/idea.css"

import { ref } from "vue"
export default {
  name: 'Editor',
  components: { Codemirror },
  setup() {
    const code = ref("// write your code here")

    return {
      code,
      cmOptions:{
        mode: 'text/x-c++src', // Language mode
        theme: 'idea', // Theme
        lineNumbers: true, // Show line number
        smartIndent: true, // Smart indent
        indentUnit: 2, // The smart indent unit is 2 spaces in length
        foldGutter: true, // Code folding
        styleActiveLine: true, // Display the style of the selected row
      }
    };
  },
  methods:{
    codeChanged(){
      this.$emit('codeChanged', this.code)
    }
  }
};
</script>
<!--
增加字段: tag,languages,visible
-->
