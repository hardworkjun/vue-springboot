<template>
  <div  style="padding-top:50px;height: 100vh;font-weight: 600;" id="bg">
  <div style="width: 90%;margin-bottom: 50px;" >
      <el-input v-model="search2" placeholder="请输入订单号" style="width:20% ;margin-left: 50px;margin-bottom: 20px;" clearable="true" />
      <el-button type="primary" style="margin-left:5px;margin-bottom: 20px;" @click="search1">查询</el-button>
    <div  style="top: 140px;width: 90%">
      <!-- <el-input v-model="search" placeholder="请输入商品名称" style="width:20% ;margin-left: 50px; margin-bottom: 20px;" clearable="true" />
      <el-button type="primary" style="margin-left:5px;margin-bottom: 20px;" @click="load">查询</el-button> -->
   
      <el-table :data="tableData" border  style="width: 100%;margin-left: 30px;"> 
          <el-table-column prop="id" label="ID" width="55"  sortable/>
          <!-- 后台数据库是下划线，前台自动变成驼峰命名法 -->
          <el-table-column prop="order1" label="订单号" sortable />
          <el-table-column prop="nickName" label="绑定账号昵称"  />
          <el-table-column prop="name" label="联系人名称" width="75"  />
          <el-table-column prop="date1" label="到货日期" />
          <el-table-column prop="date2" label="下单时间"  width="105"/>
          <el-table-column prop="address" label="到货地址" />
          <el-table-column prop="say" label="特殊说明" />
          <el-table-column  label="状态" sortable>
          <template #default="scope">
          <span v-if="scope.row.geter==='0'">未收货</span>
          <span v-if="scope.row.geter==='1'">已收货</span>
          </template>
        </el-table-column>
          <el-table-column fixed="right" label="操作" width="165" >
              <template #default="scope">
                <el-button  type="primary"   @click="handleEdit(scope.row)">详情</el-button>
                  <el-popconfirm title="已经发货了吗？" @confirm="handleDelete(scope.row)">
                  <!-- <template #reference>
                      <el-button type="success" >发货</el-button>
                  </template> -->
                  </el-popconfirm>
              </template>
          </el-table-column>
     </el-table>
     <el-pagination style="margin-top:30px;margin-left:20px"
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
   </div>
   <!-- 查看详情弹窗 -->
   <el-dialog v-model="dialogVisible" title="查看订单详情" width="40%">
    <el-table :data="tableData1" border   style="width: 90%;margin-left: 10px;"> 
          <!-- 后台数据库是下划线，前台自动变成驼峰命名法 -->
          <el-table-column prop="name" label="产品名称" width="105" />
          <el-table-column prop="count" label="购买数量" />
          <el-table-column prop="price" label="单价" />
          <el-table-column prop="num" label="总价" />
     </el-table>
        
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="close">取消</el-button>
            <el-button @click="open">查询</el-button>
          </span>
        </template>
    </el-dialog>
   </div> 
  </div>  
</template>
      
    
      
<script>
      import  request  from "@/utils/request";
      export default {
      name:"",
      components:{
      },
      watch:{
        dialogVisible:{
        // 此处监听dialogVisible变量，当期有变化时执行
        handler(item1,item2){
          // item1为新值，item2为旧值
          if(item1==false)
          {
            this.arr2=[]
            this.arr=[]
          }
        }
        },
      },
      data(){
          return{
            form:{},
            form1:{},
          //   username:this.$store.getters.getUser.name,
            tableData:[],
            tableData1:[],
            arr:[],
            arr2:[],
            search2:'',
            currentPage:1,
            pageSize:10,
            total:10,
            dialogVisible:false,
            count:0
          }
      
      },
      created(){

        this.load();
    },
      methods:{

      search(){
            this.load();
      },
      load(){
        this.count=0
        request.get("/shopping",{
          params:{  
            pageNum:this.currentPage,
            pageSize:this.pageSize,
            search1:this.$store.getters.getUser.name,
            choice: "4"
        }
         
        }).then(res=>{
          console.log(res)
            this.tableData=res.data.records
            this.total=res.data.total
        })
      },
      handleDelete(row){//发货函数 
          this.form=JSON.parse(JSON.stringify(row));
          console.log(this.form)
          request.put("/shopping",this.form,{
            params:{
                    choice:"1"
          }
     }).then(res =>{
              console.log(res);
              if(res.code==='0')
              {
              this.$message({
              type:"success",
              message:"发货成功"
              })
              }else{
              this.$message({
              type:'error',
              message:res.msg
              })
          } })
          this.load()
       },
      search1(){
          request.get("/shopping",{
          params:{  
            pageNum:this.currentPage,
            pageSize:this.pageSize,
            search1:this.$store.getters.getUser.name,
            choice: "5",
            search: this.search2
        }
         
        }).then(res=>{
          console.log(res)
            this.tableData=res.data.records
            this.total=res.data.total
        })
      },
      handleSizeChange(pageSize){
          this.pageSize=pageSize;
          this.load();
      },
      // 改变当前页码触发
      handleCurrentChange(pageNum){
          this.currentPage=pageNum
          this.load();
      },
     
      open(){//渲染订单详情数据
        request.get("/shopping",{
          params:{  
            pageNum:this.currentPage,
            pageSize:this.pageSize,
            search1:this.$store.getters.getUser.name,
            choice: "1",
            order1:this.form1.order1
        }
         
        }).then(res=>{
          console.log(res)
           this.arr=res.data.records
        })
        for(var i=0;i<this.arr.length;i++)
        {
          request.get("/shop",{
              params:{  
                search:this.$store.getters.getUser.name,
                choice:"1",
                pid:this.arr[i].pid
            }
            
            }).then(res=>{
              console.log(res)      
                this.arr2.push(res.data.records[0])
            })
        }
          this.tableData1=this.arr2
          this.arr2=[]
      },
      handleEdit(row){//详情弹窗展开
        this.dialogVisible=true
        this.form1=JSON.parse(JSON.stringify(row));
        this.open()
      },
      close(){//详情弹窗关闭
        this.tableData1=[]
        this.dialogVisible=false
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