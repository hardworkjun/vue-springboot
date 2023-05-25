import { createStore } from 'vuex'
import Home from'../views/Home.vue'



const routers =[
  {
  path:'/',
  name:'Home',
  component:Home
  },
  
]

export default createStore({
  state: {
    user:{
          name:'junajun',
          id:'',
          role:'1'
      
        }
  },
  getters: {
       getUser(state){
            return state.user
       }
      
  },
  mutations: {
    updateUser(state,user){
          state.user=user
        }
  },
  actions: {
    asyncUpdateUser(context,user){
          context.commit('updateUser',user)
        }
  },
  modules: {
  }
})
