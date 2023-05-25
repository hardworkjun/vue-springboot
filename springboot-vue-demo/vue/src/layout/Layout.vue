<template>
    <div >
      <!-- 头部 -->
      <Header/>
      <div style="display: flex; ">
         <!-- 侧边栏 -->
         <Aside/>
         <!-- 主体 -->
         <router-view style="flex:1" />
      </div>
    </div>
    
</template>
<style>

</style>
    
<script>
    import Header from "@/components/header";
    import Aside from "@/components/Aside";
    export default {
    name:"Layout",
    components:{
      Header,
      Aside,
    }
    }
  </script>