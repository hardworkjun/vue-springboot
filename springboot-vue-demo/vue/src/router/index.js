import { createRouter, createWebHistory } from 'vue-router'
import Layout from '../layout/Layout.vue'

const routes = [
  {
    path: '/',
    name: 'Layout ',
    component: Layout ,
 
    children:[
      {
        path:'/home',
        name:'Layout',
        component:()=>import("@/views/Home")
      },
      {
        path:'/shop',
        name:'shop',
        component: Layout ,
        component:()=>import("@/views/Shop")
       
      },
      {
        path:'/contact',
        name:'contact',
        component: Layout ,
        component:()=>import("@/views/Contact")
       
      },
      {
        path:'/contacts',
        name:'contacts',
        component: Layout ,
        component:()=>import("@/views/Contacts")
       
      },
      {
        path:'/shopping',
        name:'shopping',
        component: Layout ,
        component:()=>import("@/views/Shopping")
       
      },
      {
        path:'/orders',
        name:'orders',
        component: Layout ,
        component:()=>import("@/views/Orders")
       
      },
      {
        path:'/getorder',
        name:'getorder',
        component: Layout ,
        component:()=>import("@/views/Getorder")
       
      },
      {
        path:'/get',
        name:'get',
        component: Layout ,
        component:()=>import("@/views/Get")
       
      },
      {
        path:'/notget',
        name:'notget',
        component: Layout ,
        component:()=>import("@/views/NotGet")
       
      }
     
    ]
  },
  {
    path:'/',
    name:'login',
    redirect:'/login',
    children:[
      {
        path:'login',
        component:()=>import("@/views/Login")
      }
     
    ]
  },
  {
    path:'/register',
    name:'register',
    component:()=>import("@/views/Register")
  },
  
 
  
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
