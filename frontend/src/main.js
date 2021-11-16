import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import Codemirror from "codemirror-editor-vue3";
// plugin-style
import "codemirror-editor-vue3/dist/style.css";
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'

createApp(App).use(router).use(ElementPlus,{size: 'medium'})
    .use(Codemirror).use(mavonEditor)
    .mount('#app')
