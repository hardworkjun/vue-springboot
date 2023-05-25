<template>
  <div  id="login" style="width:100%;height:100vh;background-color:#008c8c;overflow: hidden;">
    <div style="width:400px; margin:150px auto ">
      <div style="color:#cccccc;font-size:30px;text-align: center;margin-bottom: 30px;">欢迎注册
        <div style="font-size:small;margin-left: 70%;" @click="toLon">已有账号？点击登录</div>
      </div>
      <el-form  ref="form" :model="form" :rules="rules" id="input">
        <el-form-item prop="username" >
          <el-input   :prefix-icon="User" v-model="form.username" placeholder="请输入账号/用户名" style="height: 40px;" />
        </el-form-item>
        <el-form-item prop="nickName" >
          <el-input   :prefix-icon="User" v-model="form.nickName" placeholder="请输公司名称" style="height: 40px;" />
        </el-form-item>
        <el-form-item prop="password" >
          <el-input :prefix-icon="Lock" v-model="form.password" placeholder="请输入密码" show-password style="height: 40px;"/>
        </el-form-item>
        <el-form-item prop="confirm" >
          <el-input :prefix-icon="Lock" v-model="form.confirm"  placeholder="请确认密码" show-password style="height: 40px;"/>
        </el-form-item>
        <el-form-item >
          <el-button style="width:100%; height: 40px;" type="primary" @click="register" >注册</el-button>
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
         confirm : [ { required: true, message: '请确认密码', trigger: 'blur' },
                   ],                        
         nickName:   [ { required: true, message: '请输入公司名称', trigger: 'blur' },
                   ],                


      }
    }
  },
  methods:{
    register(){
      this.$refs['form'].validate((valid)=>{
       if(!vaild){
        return
     }
     this.form.role="2"
     })
     if(this.form.username==''||this.form.password==''||this.form.nickName==null){
      this.$message({
       type:"error",
       message:"请完善用户信息"
      })
      return
    }
    if(this.form.password!==this.form.confirm)
    {
      this.$message({
       type:"error",
       message:"两次密码输入不一致"
      })
      return
    }
     request.post("/user/register",this.form).then(res=>{
      if(res.code==='0')
        {
          this.$message({
          type:"success",
          message:"注册成功"
        })
        window.sessionStorage.setItem('isLogin','true')
        this.$store.dispatch('asyncUpdateUser',{name:this.form.username,role:this.form.role})
        this.$router.push("/shop")//页面跳转到主页
        }else{
          this.$message({
          type:'error',
          message:res.msg
        })
        }
    })
   },
   toLon(){
    this.$router.push("/login")
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