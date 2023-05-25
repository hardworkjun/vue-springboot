<template>
<div id="bg" style=" height:100vh">
    <!-- 企业信息 -->
  <div  style="top: 140px;width: 90% ;padding-top: 60px;padding-left: 30px;font-weight: 600;" >
    <div style="margin-bottom:20px">
        <span style="position: relative;top: -40px; font-weight: 600;font-size: large;">企业信息</span>
        <el-input v-model="search" placeholder="请输入用户昵称" clearable="true" style="width:30%"/>
        <el-button type="primary" style="margin-left:5px" @click="loadenter">查询</el-button>
        <el-button type="primary" @click="add" style="">新增</el-button>
    </div>
    
    <el-table :data="tableData" border >
    <el-table-column prop="id" label="ID" style="width:8%" width="55"  sortable/>
    <el-table-column prop="username" label="绑定用户昵称" width="105"  />
    <!-- 后台数据库是下划线，前台自动变成驼峰命名法 -->
    <el-table-column prop="name" label="企业名称"  />
    <el-table-column prop="nickName" label="客户简称" />
    <el-table-column prop="person" label="企业法人" />
    <el-table-column prop="address" label="企业地址" />
    <el-table-column prop="post" label="邮政编码" />
    <el-table-column prop="bank" label="开户银行" />
    <el-table-column prop="account" label="账号" />
    <el-table-column fixed="right" label="操作" width="165" >
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
  
  <!-- 企业联系人信息 -->
  <div  style="top: 140px;width: 70%;margin-top: 60px;margin-left: 30px;margin-bottom: 30px;">
    <div style="margin-bottom:20px">
        <span style="position: relative;top: -40px; font-weight: 600;font-size: large;">企业联系人信息</span>
        <el-input v-model="search1" placeholder="请输入公司名称" clearable="true" style="width:30%"/>
        <el-button type="primary" style="margin-left:5px" @click="load">查询</el-button>
        <el-button type="primary" @click="add1" style="">新增</el-button>
    </div>
    
    <el-table :data="tableData1" border  style="width: 100%">
    <!-- <el-table-column prop="id" label="ID"  sortable/> -->
    <!-- 后台数据库是下划线，前台自动变成驼峰命名法 -->
    <el-table-column prop="name1" label="公司名称"  />
    <el-table-column prop="name2" label="联系人姓名"  />
    <el-table-column prop="phone1" label="联系人电话（座机）" />
    <el-table-column prop="phone2" label="联系人手机" />
    <el-table-column prop="email" label="电子信箱" />
    <el-table-column fixed="right" label="操作" width="165" >
      <template #default="scope">
        <el-button  type="primary"   @click="handleEdit1(scope.row)">编辑</el-button>
        <el-popconfirm title="确认删除吗？" @confirm="handleDelete1(scope.row.id)">
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
     
    <el-form  :model="form" label-width="120px">
        <el-form-item label="绑定用户昵称">
          <el-input v-model="form.username" style="width:80%" />
        </el-form-item>
        <el-form-item label="企业名称">
          <el-input v-model="form.name" style="width:80%" />
        </el-form-item>
        <el-form-item label="客户简称">
          <el-input v-model="form.nickName" style="width:80%" />
        </el-form-item>
        <el-form-item label="企业法人">
          <el-input v-model="form.person" style="width:80%" />
        </el-form-item>
        <el-form-item label="企业地址">
          <el-input v-model="form.address" style="width:80%" />
        </el-form-item>
        <el-form-item label="邮政编码">
          <el-input v-model="form.post" style="width:80%" />
        </el-form-item>
        <el-form-item label="开户银行">
          <el-input v-model="form.bank" style="width:80%" />
        </el-form-item>
        <el-form-item label="账号">
          <el-input v-model="form.account" style="width:80%" />
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
  <!-- 增加信息弹窗 -->
<el-dialog v-model="dialogVisible1" title="提示" width="30%">
      <!-- 定义form对象 -->
     
      <el-form  :model="form1" label-width="120px">
        <el-form-item label="公司名称">
          <el-input v-model="form1.name1" style="width:80%" />
        </el-form-item>
        <el-form-item label="联系人姓名">
          <el-input v-model="form1.name2" style="width:80%" />
        </el-form-item>
        <el-form-item label="联系人电话(固话)">
          <el-input v-model="form1.phone1" style="width:80%" />
        </el-form-item>
        <el-form-item label="联系人手机">
          <el-input v-model="form1.phone2" style="width:80%" />
        </el-form-item>
        <el-form-item label="电子信箱">
          <el-input v-model="form1.email" style="width:80%" />
        </el-form-item>
        
      </el-form>
      
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible1 = false">取消</el-button>
          <el-button type="primary" @click="save1">
            确定
          </el-button>
        </span>
      </template>
  </el-dialog>
</div>

</template>
    
  
    
<script>
import  request  from "@/utils/request";
export default {
 name:"",
components:{
    
    },
data(){
        return{
          form:{},
          form1:{},
          username:this.$store.getters.getUser.name,
          tableData:[],
          tableData1:[],
          dialogVisible:false,
          dialogVisible1:false,
          search:'',
          search1:'',
          currentPage:1,
          pageSize:10,
        }
    
    },
    created(){
      this.loadenter();

      this.load();

  },
    methods:{
     // 改变当前每页页数触发
    handleSizeChange(pageSize){
      this.pageSize=pageSize;
      this.load();
      },
    // 改变当前页码触发
    handleCurrentChange(pageNum){
      this.currentPage=pageNum
      this.load();
    },
    load(){
      request.get("/contacts",{
        params:{  
        pageNum:this.currentPage,
          pageSize:this.pageSize,
          search:this.search1
          
      }
       
      }).then(res=>{
        console.log(res)
        
          this.tableData1=res.data.records
      })
    },
    loadenter(){
      request.get("/enterprises",{
        params:{    
          pageNum:this.currentPage,
          pageSize:this.pageSize,
          search:this.search
          
      }
       
      }).then(res=>{
        console.log(res)   
       
          this.tableData=res.data.records
       
      })
    },
    save(){
      if(this.form.username==null||this.form.person==null||this.form.address==null||this.form.post==null||this.form.bank==null||this.form.account==null)
      {
        this.$message({
       type:"error",
       message:"请先完善信息"
      })
      return 
      }
      if(this.form.name==''||this.form.nickName==''){
      this.$message({
       type:"error",
       message:"请完善信息"
      })
      return
    }
      if(this.form.id){//更新
        request.put("/enterprises",this.form).then(res =>{
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
        
        this.loadenter();
      })
        
      }else{//新增
        request.post("/enterprises",this.form).then(res =>{
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
        this.loadenter();
      })
        
      }
     
    },
    save1(){
      if(this.form1.name1==null||this.form1.name2==null||this.form1.phone1==null||this.form1.phone2==null||this.form1.email==null)
      {
        this.$message({
       type:"error",
       message:"请先完善信息"
      })
      return 
      }
      if(this.form1.id){//更新
        request.put("/contacts",this.form1).then(res =>{
        console.log(res);
        this.dialogVisible1=false;
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
        request.post("/contacts",this.form1).then(res =>{
        console.log(res);
        this.dialogVisible1=false;
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
      this.form={};
    },
    add1(){
      this.dialogVisible1=true;
      // 清空表单域
      this.form1={};
    },
    handleEdit(row){
      this.form=JSON.parse(JSON.stringify(row));
      this.dialogVisible=true;

    },
    handleDelete(id){
      request.delete("/enterprises/"+id).then(res=>{
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
    },
    handleEdit1(row){
      this.form=JSON.parse(JSON.stringify(row));
      this.dialogVisible1=true;

    },
    handleDelete1(id){
      request.delete("/contacts/"+id).then(res=>{
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


#bg{
  background: url("../imge/shopbg.jpg") no-repeat;
  background-position: center;
  height: 100%;
  width: 100%;
  background-size: cover;

}
</style>   