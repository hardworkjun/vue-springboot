<template>
    
    <div style="padding-left:30px;padding-top:30px;" id="bg">
      <div style="height: 85vh;overflow: scroll;">
        <div style="width:100%">
           <el-input v-model="search" placeholder="请输入商品信息" style="width:20% ;margin-left: 50px;" clearable="true" />
           <el-button type="primary" style="margin-left:5px" @click="load">查询</el-button>
           <el-button type="success" style="margin-left:5px" @click="add" v-if="role!='2'">增加</el-button>
           <el-button type="success" style="margin-left:5px" @click="Delete" v-if="role!='2'">删除</el-button>
        </div>
       
      <div v-for="item in itemList" :key="item.id" style="width: 30%;height: 650px; float: left;margin: 20px;">
        <div style="width: 350px;box-shadow:5px 5px 10px 5px black;" @click="this.form.name=item.name;this.form.price=item.price">
            <el-image style="width: 350px;height: 450px;" :src="item.image"  />
            <div style="margin-bottom:  10px;position: relative;left: 2%;top:3px;font-family:STXingkai;font-size: larger;">{{item.name}}</div>
            <div style="margin: 15px;position: relative;font-family:STKaiti;font-size: larger;">{{item.features}}</div>
            <div style="margin:  10px 0px 5px 5px;position: relative;left: 1%;font-family:STKaiti;font-size: larger;font-weight: 600;">{{item.price}}￥
                <img src="../imge/购物车空.png"  style="width: 30px; height: 30px;float: right;position: relative;top: -5px; right: 20px;" @click="buy">
            </div>
        </div>    
      </div>
      </div>
    </div>
  <!-- 加入购物车弹窗 -->
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
          <el-button type="primary" @click="buy2">
            确定
          </el-button>
        </span>
      </template>
  </el-dialog>

   <!-- 新增弹窗 -->
   <el-dialog v-model="dialogVisible1" title="新增商品" width="30%">
      <!-- 定义form对象 -->
      <el-form  :model="formnew" label-width="120px">
        <el-form-item label="产品名称">
          <el-input v-model="formnew.name" style="width:80%" />
        </el-form-item>
        <el-form-item label="产品价格">
          <el-input v-model="formnew.price" style="width:80%" />
        </el-form-item>
        <el-form-item label="产品说明">
          <el-input v-model="formnew.features" style="width:80%" />
        </el-form-item>
        <el-form-item label="产品图片链接">
          <el-input v-model="formnew.image" style="width:80%" />
        </el-form-item>
      </el-form>
      
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible1 = false">取消</el-button>
          <el-button type="primary" @click="add2">
            确定
          </el-button>
        </span>
      </template>
  </el-dialog>
    <!-- 删除弹窗 -->
   <el-dialog v-model="dialogVisible2" title="删除产品" width="30%">
      <!-- 定义form对象 -->
      <el-form  :model="formde" label-width="120px">
        <el-form-item label="产品ID">
          <el-input v-model="formde.id" style="width:80%" />
        </el-form-item>
      </el-form>
      
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible2 = false">取消</el-button>
          <el-button type="primary" @click="Delete2">
            确定
          </el-button>
        </span>
      </template>
  </el-dialog>
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
 name:"shop",
components:{
    },
    data(){
    return {
    role:this.$store.getters.getUser.role,
    rules:{
        count: [
            {
            required: true,
            message: '请选择一个数量',
            trigger: 'change',
            }]
        },
     dialogVisible:false,
     dialogVisible1:false,
     dialogVisible2:false,
     form:{},
     formnew:{},
     formde:{},
     itemList :[],
     count:'',
     options: Array.from({ length: 10000 }).map((_, idx) => ({
                value: `${idx + 1}`,
                label: `${idx + 1}`,
                })),
    search:'',
    }
  },
  created(){
      this.load();
  },
  methods: {
    load(){   
      request.get("/products",{
        params:{
            pageNum:1,
            pageSize:100,
            search:this.search
      }
       
      }).then(res=>{
        console.log(res)
        this.itemList=res.data.records
      })
    },
    buy(){
       this.dialogVisible = true
       
    },
    buy2(){
      if(this.form.name==null||this.form.count==null)
      {
        this.$message({
          type:"error",
          message:"请完善信息"
          })
          console.log("err")
          return
      }
      this.form.nickName=this.$store.getters.getUser.name;
      this.form.buy="0";
      console.log(this.form.nickName)
      request.post("/shop",this.form).then(res =>{
        console.log(res);
        this.dialogVisible=false;
     
        if(res.code==='0')
        {
          this.$message({
          type:"success",
          message:"已加入购物车"
        })
        this.form={}
        }else{
          this.$message({
          type:'error',
          message:res.msg
        })
        }
      })

    },
    add(){
      this.dialogVisible1 = true
     
    },
    add2(){
      if(this.formnew.name==null||this.formnew.price==null||this.formnew.features==null||this.formnew.image==null)
      {
        this.$message({
          type:"error",
          message:"请完善信息"
          })
          
          return
      }
      request.post("/products",this.formnew).then(res =>{
        // console.log(res);
        this.dialogVisible1=false;
        if(res.code==='0')
        {
          this.$message({
          type:"success",
          message:"新增成功"
        })
        this.formnew={}
        }else{
          this.$message({
          type:'error',
          message:res.msg
        })
        }
        this.load();
      })

    },
    Delete(){
          this.dialogVisible2=true
    },
    Delete2(id){
      request.delete("/products/"+this.formde.id).then(res=>{
        this.dialogVisible2=false;
        if(res.code==='0')
        {
          console.log(res)
          this.$message({
          type:"success",
          message:"删除成功"
        })
        this.formde={}
        }else{
          this.$message({
          type:"error",
          message:res.msg
        })
        }
        this.load();
      })
    }


  },
}
</script>
<style scoped>
</style>
    