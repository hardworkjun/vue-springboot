<template> 

<div style="padding-left:30px;padding-top:30px;font-weight: 600;height: 100vh;" id="bg">
  <!-- 功能区域 -->
  <div style="margin:10px 0;">
    <el-button type="primary" @click="add">新增</el-button>
    <el-button type="success">导入</el-button>
    <el-button type="success" >导出</el-button>
  </div>
  <!-- 搜索区域 -->
  <div style="margin:10px 0;">
    <el-input v-model="search" placeholder="请输入用户名" style="width:20%" clearable="true" />
    <el-button type="primary" style="margin-left:5px" @click="load">查询</el-button>
  </div>
 <!-- 表格 -->
  <el-table :data="tableData" border style="width: 100%">
    <el-table-column prop="id" label="ID"  sortable/>
    <el-table-column prop="username" label="用户名"  />
    <!-- 后台数据库是下划线，前台自动变成驼峰命名法 -->
    <el-table-column prop="password" label="密码"  />
    <el-table-column prop="nickName" label="公司名称" />
    <el-table-column  label="角色" >
      <template #default="scope">
       <span v-if="scope.row.role==='1'">管理员</span>
       <span v-if="scope.row.role==='2'">客户</span>
       <span v-if="scope.row.role==='3'">客服</span>
      </template>
    </el-table-column>
    <el-table-column fixed="right" label="操作" >
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
  <!-- 分页 -->
  <div>
   
    <el-pagination
      :currentPage="currentPage"
      :page-size="pageSize"
      :page-sizes="[5, 10, 20]"
      :small="small"
      :disabled="disabled"
      :background="background"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
    <!-- 增加信息弹窗 -->
    <el-dialog v-model="dialogVisible" title="提示" width="30%">
      <!-- 定义form对象 -->
      <!-- {{}} -->
      <el-form  :model="form" label-width="120px">
        <el-form-item label="用户名">
          <el-input v-model="form.username" style="width:80%" />
        </el-form-item>
        <el-form-item label="用户密码">
          <el-input v-model="form.password" style="width:80%" />
        </el-form-item>
        <el-form-item label="公司名称">
          <el-input v-model="form.nickName" style="width:80%" />
        </el-form-item>
        
        <el-form-item label="用户类型">
          <el-radio-group v-model="form.role">
          <el-radio v-model="form.role" label="1">管理员</el-radio>
          <el-radio v-model="form.role" label="2">客户</el-radio>
          <el-radio v-model="form.role" label="3">客服</el-radio>
          </el-radio-group>
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

<script>
// @ is an alias to /src
import  request  from "@/utils/request";

export default {
  name: 'Home',
  components:{

  },
  data(){
    return {
      form:{},
      dialogVisible:false,
      search:'',
      currentPage:1,
      pageSize:10,
      total:10,
     
      tableData :[],
          
    }
  },
  created(){
      this.load();
  },
  methods:{
    load(){
      
      request.get("/user",{
        params:{
          pageNum:this.currentPage,
          pageSize:this.pageSize,
          search:this.search
          
      }
       
      }).then(res=>{
        // console.log(res)
        this.tableData=res.data.records,
        this.total=res.data.total
      })
    },
    save(){
      if(this.form.username==''||this.form.password==''||this.form.nickName==null){
      this.$message({
       type:"error",
       message:"请完善用户信息"
      })
      return
    }
      if(this.form.id){//更新
        request.put("/user",this.form).then(res =>{
        // console.log(res);
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
        request.post("/user",this.form).then(res =>{
        // console.log(res);
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
      this.form={};
    },
    handleEdit(row){
      this.form=JSON.parse(JSON.stringify(row));
      this.dialogVisible=true;

    },
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
    handleDelete(id){
      request.delete("/user/"+id).then(res=>{
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