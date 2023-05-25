<template>
 <div style="height:50px; line-height:50px;border-bottom:1px solid #ccc;display: flex;">
  <img style="padding-left: 30px;"  src="../imge/logo1.jpg" alt="">
    <div style="width:200px;padding-left: 30px;font-weight:blod ;font-size: large; color: #008c8c;color: #fff;text-shadow:  0px 0px 10px black;"  v-if="this.role!='2'">后台管理 </div>

    <div style="width:200px;padding-left: 30px;font-weight:blod ;font-size: large;color: #fff;text-shadow:  0px 0px 10px black;" v-if="this.role==='2'">欢迎来到宠物商城</div>
    <div style="width:200px;padding-left: 10px;font-weight:blod ;font-size: large;color: #fff;text-shadow:  0px 0px 10px black;" >岁月静好，只因有喵~</div>
    <div style="flex:1"> </div>
    <div style="width:100px;margin-top: 1%;">
      <el-dropdown>
    <span class="el-dropdown-link">
     {{$store.getters.getUser.name}}
      <!-- <i class="el-icon-arrow-down el-icon--right"></i> -->
      <el-icon class="el-icon--right" >
        <ArrowDown />
      </el-icon>
    </span>
    <template #dropdown>
      <el-dropdown-menu>
        <el-dropdown-item @click="$router.push('/contact')" > 企业信息</el-dropdown-item>
        <el-dropdown-item @click="$router.push('/login')">退出系统</el-dropdown-item>
        
      </el-dropdown-menu>
    </template>
  </el-dropdown>
    </div>
  
</div>
</template>

  <script >
import { ArrowDown } from '@element-plus/icons-vue'

  export default{
    components: {
          ArrowDown
        },
        data(){
      return{
        role:this.$store.getters.getUser.role,
      }
  },
  name:"header",

  }
</script>
<!-- <style scoped>
.example-showcase .el-dropdown-link {
  cursor: pointer;
  color: var(--el-color-primary);
  display: flex;
  align-items: center;
}
</style> -->