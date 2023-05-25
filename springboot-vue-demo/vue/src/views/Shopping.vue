<template>
    <div  style="padding-top:50px;height: 100vh;" id="bg">
    <div style="width: 90%;" >
     
      <div  style="top: 140px;width: 90%">
        <!-- <el-input v-model="search" placeholder="请输入商品名称" style="width:20% ;margin-left: 50px; margin-bottom: 20px;" clearable="true" />
        <el-button type="primary" style="margin-left:5px;margin-bottom: 20px;" @click="load">查询</el-button> -->
        
        <el-table :data="tableData" border ref="multipleTable" @selection-change="handleSelectionChange"   style="width: 100%;margin-left: 30px;"> 
            <el-table-column type="selection" width="55" />
            <el-table-column prop="id" label="ID" width="55"  sortable/>
            <!-- 后台数据库是下划线，前台自动变成驼峰命名法 -->
            <el-table-column prop="nickName" label="绑定账号昵称"  />
            <el-table-column prop="name" label="产品名称" width="105" />
            <el-table-column prop="count" label="购买数量" />
            <el-table-column prop="price" label="单价" />
            <el-table-column prop="num" label="总价" />
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
     <el-button  style="margin-top: 20px;margin-left: 45%;" type="primary" @click="buy" >购买</el-button>
    <!-- 修改信息弹窗 -->
  <el-dialog v-model="dialogVisible" title="加入购物车" width="30%">
          <!-- 定义form对象 -->
          <div><span style="font-family:STXingkai;font-size:20px;font-weight: 600;">{{form.name}}</span>  </div>
      
          <el-form  :model="form" label-width="120px">
            <el-form-item label="数量" prop="count">
                <el-select-v2
                    v-model="form.count"
                    placeholder="选择您购买商品的数量"
                    :options="options"
                />
            </el-form-item>
          
          </el-form>
          
          <template #footer>
            <span class="dialog-footer">
              <el-button @click="dialogVisible = false">取消</el-button>
              <el-button type="primary" @click="changeshop">
                确定
              </el-button>
            </span>
          </template>
      </el-dialog>
     <!-- 购买弹窗 -->
      <el-dialog v-model="dialogVisible1" title="修改商品信息" width="30%" >
      <!-- 定义form对象 -->
      <!-- <div><span style="font-family:STXingkai;font-size:20px;font-weight: 600;">{{form.pname}}</span>  </div> -->
   
      <el-form  :model="form" label-width="120px">
        <el-form-item label="下单人">
          <el-input v-model="form.name" style="width:80%" />
        </el-form-item>
        <el-form-item label="到货日期">
            <el-col :span="11">
                <el-date-picker
                v-model="form.date1"
                type="date"
                placeholder="日期"
                style="width: 100%"
                />
            </el-col>
        </el-form-item>
        <el-form-item label="送货地址">
          <el-input v-model="form.address" style="width:80%" />
        </el-form-item>
        <el-form-item label="特殊说明">
          <el-input v-model="form.say" style="width:80%" />
        </el-form-item>
       
      </el-form>
      
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible1 = false">取消</el-button>
          <el-button type="primary" @click="buying">
            确定
          </el-button>
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
        data(){
            return{
              form:{},
            //   username:this.$store.getters.getUser.name,
              tableData:[],
              dialogVisible:false,
              dialogVisible1:false,
              search:'',
              time:'',
              arr:[],
              options: Array.from({ length: 10000 }).map((_, idx) => ({
                value: `${idx + 1}`,
                label: `${idx + 1}`,
                })),

            }
        
        },
        created(){

          this.load();
      },
        methods:{
          buy(){
            this.dialogVisible1=true;
        
          },
          buying(){// 购买，生成订单
            console.log(this.arr)
            for( var i=0;i<this.arr.length;i++)
            {
                request.put("/shop",this.form,{
                params:{
                        id:this.arr[i],
                        choice:"1"
              }
              }).then(res =>{
                  console.log(res);
                   })
            }
            var a = new Date().getTime(); //获取到当前时间戳
	          var b = new Date(a); //创建一个指定的日期对象

           function nowDate(now) {
              var year = now.getFullYear(); //年份
              var month = now.getMonth() + 1; //月份（0-11）
              var date = now.getDate(); //天数（1到31）
              var hour = now.getHours(); //小时数（0到23）
              var minute = now.getMinutes(); //分钟数（0到59）
              var second = now.getSeconds(); //秒数（0到59） 
              return (
                year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second
              )
            };
            this.form.date2=nowDate(b).toString()
            this.form.order1=new Date().getTime().toString()+""

            this.form.pusher="0"
            this.form.geter="0"
            this.form.nickName=this.$store.getters.getUser.name
            for(var i=0;i<this.arr.length;i++)
            {
              this.form.pid=this.arr[i];
              request.post("/shopping",this.form).then(res =>{
              console.log(res);
              // if(res.code==='0')
              // {
             
              // }else{
              //   this.$message({
              //   type:'error',
              //   message:res.msg
              // })
              // }
              })
            }
            this.$message({
                type:"success",
                message:"购买成功"
              })
            this.dialogVisible1=false;
            this.load();
          },
          handleSelectionChange(val) {//将多选id传给arr【】
            // console.log(val.id)
            // this.arr.push(val.id)
            // console.log(this.arr)
            val.forEach(item => {
            // 判断数组中是否包含这个 id 
            if (this.arr.indexOf(item.id) == -1) {
              this.arr.push(item.id)
            }
          })
        },
        changeshop(){//更新
          this.form.buy="0";
          request.put("/shop",this.form).then(res =>{
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
        },

        search(){
              this.load();
        },
      
        load(){
          request.get("/shop",{
            params:{  
              search:this.$store.getters.getUser.name,
          }
           
          }).then(res=>{
            console.log(res)
              this.tableData=res.data.records
          })
        },
        handleEdit(row){
          this.form=JSON.parse(JSON.stringify(row));
          this.dialogVisible=true;
        },
        handleDelete(id){
          request.delete("/shop/"+id).then(res=>{
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