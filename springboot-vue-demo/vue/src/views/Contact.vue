<template>
<div  style="padding-top:50px; height: 90vh; overflow:scroll;" id="bg1">
    <div style="width: 80%;" >
    <span  style="margin-bottom:20px;position: relative;left:25%;bottom:3%">绑定登录账号 </span>
    <span style="margin-bottom:20px;position: relative;left:25%;bottom:3%; margin-left: 40px;"> </span>
    <span style="margin-bottom:20px;position: relative;left:25%;bottom:3%;margin-left: 50px;">{{$store.getters.getUser.name}}</span>
    <el-form :model="form1" label-width="30%" style="opacity: 0.7;">
        <el-form-item label="企业名称"  style="margin-top:20px">
          <el-input v-model="form1.name"  />
        </el-form-item>
        <el-form-item label="客户简称" style="margin-top:40px">
          <el-input v-model="form1.nickName" />
        </el-form-item>
        <el-form-item label="企业法人" style="margin-top:40px">
          <el-input v-model="form1.person" />
        </el-form-item>
        <el-form-item label="企业地址" style="margin-top:40px">
          <el-input v-model="form1.address" />
        </el-form-item>
        <el-form-item label="邮政编码" style="margin-top:40px">
          <el-input v-model="form1.post" />
        </el-form-item>
        <el-form-item label="开户银行" style="margin-top:40px">
          <el-input v-model="form1.bank" />
        </el-form-item>
        <el-form-item label="账号" style="margin-top:40px">
          <el-input v-model="form1.account" />
        </el-form-item>
        <!-- 绑定登录账号：{{userid}} -->
       
          <el-button type="primary" style="position: relative;left:75%;top: 40px;" @click="submit">提交</el-button>
          <el-button type="success" style="position: relative;left:45%;top: 40px;" @click="clear">清空</el-button>
   
        
    </el-form>

  <div  style="position: relative;left:25%;top: 140px; width: 70%;height: 300px;">
    <span style="position: relative;top: -40px; font-weight: 600;font-size: large;">添加联系人信息</span>
    <el-button type="primary" @click="add" style="position: relative;top: -38px;left: 20px;">新增</el-button>
    <el-button type="primary" @click="search" style="position: relative;top: -38px;left: 20px;">查询</el-button>
    <el-table :data="tableData" border  style="width: 100%">
    <el-table-column prop="id" label="ID"  sortable/>
    <!-- 后台数据库是下划线，前台自动变成驼峰命名法 -->
    <el-table-column prop="name2" label="联系人姓名"  />
    <el-table-column prop="phone1" label="联系人电话（座机）" />
    <el-table-column prop="phone2" label="联系人手机" />
    <el-table-column prop="email" label="电子信箱" />
    <el-table-column fixed="right" label="操作" width="185" >
      <template #default="scope">
        <el-button  type="primary"   @click="handleEdit(scope.row)">编辑</el-button>
        <el-popconfirm title="确认删除吗？" @confirm="handleDelete(scope.row.id)">
          <template #reference>
            <el-button type="danger" >删除</el-button>
          </template>
        </el-popconfirm>
      </template>
    </el-table-column>
  </el-table>
  </div>

<!-- 增加信息弹窗 -->
<el-dialog v-model="dialogVisible" title="提示" width="30%">
      <!-- 定义form对象 -->
     
      <el-form  :model="form2" label-width="120px">
        <el-form-item label="联系人姓名">
          <el-input v-model="form2.name2" style="width:80%" />
        </el-form-item>
        <el-form-item label="联系人电话(固话)">
          <el-input v-model="form2.phone1" style="width:80%" />
        </el-form-item>
        <el-form-item label="联系人手机">
          <el-input v-model="form2.phone2" style="width:80%" />
        </el-form-item>
        <el-form-item label="电子信箱">
          <el-input v-model="form2.email" style="width:80%" />
        </el-form-item>
        
      </el-form>
      
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="save">
            确定
          </el-button>
        </span>
      </template>
  </el-dialog>
 </div>

</div>
  
   
</template>
    
  
<style>
  #bg{
  background: url("../imge/shopbg.jpg") no-repeat;
  background-position: center;
  height: 100%;
  width: 100%;
  background-size: cover;

}
</style>
    <script>
    import  request  from "@/utils/request";
    export default {
    name:"",
    components:{
    },
    data(){
        return{
          form1:{},
          username:this.$store.getters.getUser.name,
          tableData:[],
          form2:{},
          dialogVisible:false,
        }
    
    },
    created(){
      this.loadenter();
      // setTimeOut(this.load(),1000)
      this.load();
      // this.init();
  },
    methods:{
    // async init(){
    //     await this.loadenter();
    //     this.load()
    // },
    search(){
          this.load();
    },
   submit(){
        
          if(this.form1.name==null||this.form1.nickName==null||
          this.form1.person==null||this.form1.account==null||
          this.form1.address==null||this.form1.post==null||this.form1.bank==null)
          {
          this.$message({
          type:"error",
          message:"请完善信息"
          })
          console.log("err")
          return
        }
          if(this.form1.id){//更新
            request.put("/enterprise",this.form1).then(res =>{
            console.log(res);
            this.dialogVisible=false;
            if(res.code==='0')
            {
              this.$message({
              type:"success",
              message:"更新成功"
            })
            }else{
              this.$message({
              type:'error',
              message:res.msg
            })
            }
            
            this.loadenter()
          })
            
          }else{//新增
            request.post("/enterprise",this.form1).then(res =>{
            console.log(res);
            this.dialogVisible=false;
            if(res.code==='0')
            {
              this.$message({
              type:"success",
              message:"新增成功"
            })
            }else{
              this.$message({
              type:'error',
              message:res.msg
            })
            }
            // this.load();
          })
            
          }
     
      },
    clear(){
      this.form1={}
      },
    load(){
      request.get("/contact",{
        params:{  
          search:this.form1.name  
      }
       
      }).then(res=>{
        console.log(res)
        if(res.data.records.length!=0)
        {
          this.tableData=res.data.records
        }else{
              // this.$message({
              // type:"error",
              // message:"未查询到信息"
              // })
              // console.log("err")
        }
       
      })
    },
    loadenter(){
      request.get("/enterprise",{
        params:{    
          search:this.username
      }
       
      }).then(res=>{
        console.log(res)   
        if(res.data.records.length!=0)
        {
          this.form1=res.data.records[0]
        }else{
              this.$message({
              type:"error",
              message:"请完善信息"
              })
              console.log("err")
        }
      })
    },
    save(){
      this.form2.name1=this.form1.name;
      if(this.form2.name1==null)
      {
        this.$message({
       type:"error",
       message:"请先完善公司"
      })
      return 
      }
      if(this.form2.name2==''||this.form2.phone2==''||this.form2.email==null){
      this.$message({
       type:"error",
       message:"请完善信息"
      })
      return
    }
      if(this.form2.id){//更新
        request.put("/contact",this.form2).then(res =>{
        console.log(res);
        this.dialogVisible=false;
        if(res.code==='0')
        {
          this.$message({
          type:"success",
          message:"更新成功"
        })
        }else{
          this.$message({
          type:'error',
          message:res.msg
        })
        }
        
        this.load();
      })
        
      }else{//新增
        request.post("/contact",this.form2).then(res =>{
        console.log(res);
        this.dialogVisible=false;
        if(res.code==='0')
        {
          this.$message({
          type:"success",
          message:"新增成功"
        })
        }else{
          this.$message({
          type:'error',
          message:res.msg
        })
        }
        this.load();
      })
        
      }
     
    },
    add(){
      this.dialogVisible=true;
      // 清空表单域
      this.form2={};
    },
    handleEdit(row){
      this.form2=JSON.parse(JSON.stringify(row));
      this.dialogVisible=true;

    },
    // 改变当前每页页数触发
    // handleSizeChange(pageSize){
    //   this.pageSize=pageSize;
    //   this.load();
    //   },
    // // 改变当前页码触发
    // handleCurrentChange(pageNum){
    //   this.currentPage=pageNum
    //   this.load();
    // },
    handleDelete(id){
      request.delete("/contact/"+id).then(res=>{
        if(res.code==='0')
        {
          console.log(res)
          this.$message({
          type:"success",
          message:"删除成功"
        })
        }else{
          this.$message({
          type:"error",
          message:res.msg
        })
        }
        this.load();
      })
    }
  }

    }
    </script>
<style>

.el-table, .el-table__expanded-cell {
   background-color: transparent;
}
.el-table tr {
   background-color: transparent !important;
}
.el-table th {
   background-color: transparent !important;
}

.el-table tbody tr td {
   background-color: transparent !important
}
 .el-table--enable-row-transition .el-table__body td, .el-table .cell{
   background-color: transparent;
}


#bg1{
 background: url("../imge/bg07.jpg") no-repeat;
 background-position: center;
 height: 100%;
 width: 100%;
 background-size: cover;

}
</style>   