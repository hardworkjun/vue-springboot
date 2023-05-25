<template>
  <div>
    <el-menu
    id="img"
    style="width:200px; min-height: calc(100vh - 50px);"
        default-active="shop"
        default-opens="1"
        router
        class="el-menu-vertical-demo"
        @open="handleOpen"
        @close="handleClose"
      >
       <el-sub-menu index="1" v-if="this.role==='1'">
          <template #title>
            <el-icon><Setting /></el-icon>
            <span>信息管理</span>
          </template>
  
            <el-menu-item index="home">
              <template #title>
                <el-icon><User/></el-icon>
                <span>网站用户管理</span>
              </template>
            </el-menu-item>
            <el-menu-item index="contacts">
              <template #title>
                <el-icon><Memo /></el-icon>
                <span>企业信息管理</span>
              </template>
            </el-menu-item>
          
        </el-sub-menu>
       
          <!-- <el-sub-menu index="1-4">
            
            <template #title>item four</template>
            <el-menu-item index="1-4-1">item one</el-menu-item>
          </el-sub-menu> -->
         
        <el-menu-item index="shop">
          <el-icon><Menu /></el-icon>
          <span>猫猫狗狗</span>
        </el-menu-item>

        <el-sub-menu index="" v-if="this.role==='2'">
          <template #title>
            <el-icon><HelpFilled /></el-icon>
            <span>订单查看</span>
          </template>
  
            <el-menu-item index="shopping">
              <template #title>
                <el-icon><Memo /></el-icon>
                <span>购物车</span>
              </template>
            </el-menu-item>
            <el-menu-item index="orders">
              <template #title>
                <el-icon><Memo /></el-icon>
                <span>未收货</span>
              </template>
            </el-menu-item>
            <el-menu-item index="getorder">
              <template #title>
                <el-icon><Memo /></el-icon>
                <span>购买记录</span>
              </template>
            </el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="4" v-if="this.role!='2'">
          <template #title>
            <el-icon><HelpFilled /></el-icon>
            <span>订单管理</span>
          </template>
            <el-menu-item index="notget">
              <template #title>
                <el-icon><Memo /></el-icon>
                <span>未发货</span>
              </template>
            </el-menu-item>
            <el-menu-item index="get">
              <template #title>
                <el-icon><Memo /></el-icon>
                <span>已发货</span>
              </template>
            </el-menu-item>
        </el-sub-menu>
        <el-menu-item index="contact">
              <template #title>
                <el-icon><Memo /></el-icon>
                <span>企业信息</span>
              </template>
        </el-menu-item>
      <!-- <img src="../imge/logo3.jpg" alt="" style="width:190px"> -->
      
       
  <div>
    
    <img src="../imge/logo2.jpg" alt="" style="width:190px; ">
  </div>
     
      </el-menu>
 
  </div>
</template>

<script >
 import {Location, Setting, Menu ,Document,User,HelpFilled,Memo} from '@element-plus/icons-vue'

export default {
  components: {
            Location, Setting, Menu ,Document,User,HelpFilled,Memo
        },
  name: 'Aside',
  data(){
      return{
        role:this.$store.getters.getUser.role,
      }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
