<template>
  <div
      style="width: 100%;
           margin: 10px"
  >
    <el-form ref="form" :model="form"
             label-width="120px"
             label-position="top"
             inline
    >
      <el-col :span="8">
        <el-form-item label="题目ID">
          <el-input v-model="form.displayId"></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="16">
        <el-form-item label="标题">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="题目描述">
          <mavon-editor v-model="form.description"/>
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="输入格式">
          <mavon-editor v-model="form.input"/>
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="输出格式">
          <mavon-editor v-model="form.output"/>
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="题目提示">
          <mavon-editor v-model="form.hint"/>
        </el-form-item>
      </el-col>
      <!--  sample -->
      <el-col :span="24">
        <el-form-item label="测试用例">
          <el-table :data="tableData" style="width: 100%">
            <el-table-column type="expand">
              <template #default="props">
                <el-input
                    v-model="props.row.input"
                    :rows="2"
                    type="textarea"
                    placeholder=""
                    style="width: 30%;"
                />
                <el-input
                    v-model="props.row.output"
                    :rows="2"
                    type="textarea"
                    placeholder=""
                    style="width: 30%;"
                />
              </template>
            </el-table-column>
            <el-table-column label="好的测试用例..." prop="date" />
            <el-table-column fixed="right" label="Operations" width="120">
              <template #default="scope">
                <el-button
                    type="text"
                    size="small"
                    @click.prevent="deleteRow(scope.$index, tableData)"
                >
                  Remove
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-form-item>
      </el-col>

      <!--
      <el-col :span="">
            <el-form-item label="">
            </el-form-item>
      </el-col>
      -->
      <el-col :span="6">
        <el-form-item label="时间限制">
          <el-input-number v-model="form.timeLimit" :min="1" :max="10000" @change="handleChange"/>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="内存限制">
          <el-input-number v-model="form.memoryLimit" :min="1" :max="536870912" @change="handleChange"/>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="难度">
          <el-select v-model="form.level" placeholder="真实">
            <el-option
                v-for="item in levelOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <!--     label 不会就是 form 的 name  -->
        <el-form-item label="可见">
          <!--     TODO 修改字段   -->
          <el-switch v-model="form.visible"/>
        </el-form-item>
      </el-col>
<!--   action   -->
      <el-col :span="8">
        <el-form-item label="测试用例">
          <el-upload
              class="upload-demo"
              :action="uploadUrl"
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              :before-remove="beforeRemove"
              multiple
              :limit="1"
              :on-exceed="handleExceed"
              :file-list="fileList"
              method="post"
              :auto-upload="false"
              :on-change="uploadChange"
          >
            <el-button size="small" type="primary">Click to upload</el-button>
            <template #tip>
              <div class="el-upload__tip">
                jpg/png files with a size less than 500kb
              </div>
            </template>
          </el-upload>
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="可选语言">
          <div>
            <el-checkbox v-model="C" label="C"></el-checkbox>
            <el-checkbox v-model="Cpp" label="C++"></el-checkbox>
            <el-checkbox v-model="Java" label="Java"></el-checkbox>
            <el-checkbox v-model="Python2" label="Python2"></el-checkbox>
            <el-checkbox v-model="Python3" label="Python3"></el-checkbox>
          </div>
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="题目标签">
          <el-tag
              v-for="tag in dynamicTags"
              :key="tag"
              closable
              :disable-transitions="false"
              @close="handleClose(tag)"
          >
            {{ tag }}
          </el-tag>
          <el-input
              v-if="inputVisible"
              ref="saveTagInput"
              v-model="inputValue"
              class="input-new-tag"
              size="mini"
              @keyup.enter="handleInputConfirm"
              @blur="handleInputConfirm"
          >
          </el-input>
          <el-button v-else class="button-new-tag" size="small" @click="showInput"
          >+ New Tag</el-button
          >
        </el-form-item>
      </el-col>

      <el-col :span="6">
        <el-form-item label="作者">
          <el-input v-model="form.author"></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="18">
        <el-form-item label="来源">
          <el-input v-model="form.source"></el-input>
        </el-form-item>
      </el-col>
      <el-button type="primary" @click="save">保存</el-button>
    </el-form>
  </div>
</template>

<script>
import request from "@/utils/request";
import {ref} from "vue";

export default {
  name: "ProblemCreate",
  components: {},
  setup() {
    const C = ref(true)
    const Cpp = ref(true)
    const Java = ref(true)
    const Python2 = ref(true)
    const Python3 = ref(true)
    return {
      C,
      Cpp,
      Java,
      Python2,
      Python3,
    }
  },
  data() {
    return {
      form: {
      },
      // 难度选择
      levelOptions: [
        {
          value: '语法',
          label: '语法',
        },
        {
          value: '简单',
          label: '简单',
        },
        {
          value: '中等',
          label: '中等',
        },
        {
          value: '困难',
          label: '困难',
        },
        {
          value: '地狱',
          label: '地狱',
        },
      ],
      // 题目标签
      dynamicTags: ['Tag 1', 'Tag 2', 'Tag 3'],
      // tag
      inputVisible: false,
      // tag
      inputValue: '',
      // 样例
      tableData: [
        {
          input: '1 1',
          output: '2'
        },
        {
          input: '1 3',
          output: '4'
        },
      ],
      languages: {},
      uploadUrl: 'http://localhost:8001/problem/save',
      file: {}
    }
  },
  methods:{
    uploadChange(file,fileList) {
      this.file = fileList[0].raw;
      // 多个?
    },
    // 保存题面
    save(){
      // tableData -> 样例
      this.form.sampleCase = JSON.stringify(this.tableData)
      // dynamicTags -> 标签
      this.form.tag = JSON.stringify(this.dynamicTags)
      this.languages.C = this.C
      this.languages.Cpp = this.Cpp
      this.languages.Java = this.Java
      this.languages.Python2 = this.Python2
      this.languages.Python3 = this.Python3
      this.form.languages = JSON.stringify(this.languages)

      const formData = new FormData()
      console.log(JSON.stringify(this.form));
      formData.append("form", JSON.stringify(this.form))
      formData.append("file", this.file)
      console.log(formData.get("form"));
      console.log(formData.get("file"));

      let config = {
        headers: {
          "Content-Type": "multipart/form-data"
        }
      }
      request.post('/problem-api/problem/save', formData, config)
          .then(res => {
            console.log(res);
          });
      console.log(this.form)
    },
    // 测试用例删除
    deleteRow(index, rows) {
      rows.splice(index, 1)
    },
    // tag
    handleClose(tag) {
      this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1)
    },
    // tag
    showInput() {
      this.inputVisible = true
      this.$nextTick((_) => {
        this.$refs.saveTagInput.$refs.input.focus()
      })
    },
    // tag
    handleInputConfirm() {
      const inputValue = this.inputValue
      if (inputValue) {
        this.dynamicTags.push(inputValue)
      }
      this.inputVisible = false
      this.inputValue = ''
    },
  }
}
</script>

<style scoped>
.el-tag + .el-tag {
  margin-left: 10px;
}
.button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}
.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}
</style>
