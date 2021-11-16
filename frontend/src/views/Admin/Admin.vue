<template>
<div style="display: flex">
  <AdminAside/>
  <router-view/>
</div>
</template>

<script>
import AdminAside from "../../components/AdminAside";
export default {
  name: "Admin",
  components:{
    AdminAside
  }
}
</script>

<style scoped>

</style>
