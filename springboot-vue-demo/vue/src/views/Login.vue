<template>
  <!-- <img src="../imge/注册页面背景图.jpg" alt=""> -->
  <div id="login" style="width:100%;height:100vh; overflow: hidden;">
    <div style="width:400px; margin:150px auto; ">
      <div style="color:#cccccc;font-size:30px;text-align: center;margin-bottom: 30px;">欢迎登录
      <div style="font-size:small;margin-left: 70%;" @click="toReg">没有账号？点击注册</div>
      </div>
      
      <el-form  ref="form" :model="form" :rules="rules" >
        <el-form-item prop="username" style="margin-bottom: 30px;" id="input">
        <el-input   :prefix-icon="User" v-model="form.username" style="height: 40px;" placeholder="请输入账号/用户名"/>
      </el-form-item>
      <el-form-item prop="password" style="margin-bottom: 30px;"  id="input">
        <el-input :prefix-icon="Lock" v-model="form.password" show-password style="height: 40px;" placeholder="请输入密码"/>
      </el-form-item>
      <el-form-item >
        <el-button style="width:100%;height: 40px;" type="primary" @click="login" >登录</el-button>
      </el-form-item>
      
      </el-form>
      
    </div>
  </div>

 </template>
 
 <script>
 import {Avatar,User,Lock} from '@element-plus/icons-vue'
 import request from '@/utils/request'
 export default{
  name:"login",
  setup() {
    return{
             Avatar,User,Lock
          }
        },
  name:"login",
  data(){
    return {
      form:{},
      rules:{
         username:[ { required: true, message: '请输入用户名', trigger: 'blur' },
                    { min: 1, max: 16, message: '长度必须为1-16', trigger: 'blur' },
                  ],
         password: [ { required: true, message: '请输入密码', trigger: 'blur' },
                   ],                           
                            


      }
    }
  },
  methods:{
  login(){
    //  this.$refs['form'].validate((valid)=>{
    //    if(!vaild){
    //     return
    //  }else{
     
        
  
     request.post("/user/login",this.form).then(res=>{
      if(res.code==='0')
        {
          this.$message({
          type:"success",
          message:"登录成功"
        })

        request.get("/user",{
        params:{
          pageNum:this.currentPage,
          pageSize:this.pageSize,
          search:this.form.username,
          role:'role'
          
      }
       
      }).then(res=>{
        // console.log(res)
        if(res.code==='0')
        {
        console.log(res)
        window.sessionStorage.setItem('isLogin','true')
        this.$store.dispatch('asyncUpdateUser',{name:res.data.records[0].username,role:res.data.records[0].role})
        // sessionStorage.setItem("user",this.form)  // 缓存用户信息
        console.log(this.$store.getters.getUser.name)
        console.log(this.$store.getters.getUser.role)
        this.$router.push("/shop")//页面跳转到主页
      //   }
      // })
    }
     })
        // window.sessionStorage.setItem('isLogin','true')
        // this.$store.dispatch('asyncUpdateUser',{name:this.form.username})
        // sessionStorage.setItem("user",this.form)  // 缓存用户信息
        // this.$router.push("/home")//页面跳转到主页
        }else{
          this.$message({
          type:'error',
          message:res.msg
        })
        
        }
    })
    
   },
   toReg(){
    this.$router.push("/register")
  }
  }
  
}
 </script>
 
 <style scoped>
#login {
  background: url("../imge/注册页面背景图.jpg") no-repeat;
  background-position: center;
  height: 100%;
  width: 100%;
  background-size: cover;
  position: fixed;
}
#input{
  background-color: transparent;
  opacity: 0.65;
}
 </style>