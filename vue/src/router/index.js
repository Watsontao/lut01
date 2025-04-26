import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

// 解决导航栏或者底部导航tabBar中的vue-router在3.0版本以上频繁点击菜单报错的问题。
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push (location) {
  return originalPush.call(this, location).catch(err => err)
}

const routes = [
  {
    path: '/',
    name: 'Manager',
    component: () => import('../views/Manager.vue'),
    redirect: '/home',  // 重定向到主页
    children: [
      { path: '403', name: 'NoAuth', meta: { name: '无权限' }, component: () => import('../views/manager/403') },
      { path: 'home', name: 'Home', meta: { name: '系统首页' }, component: () => import('../views/manager/Home') },
      { path: 'admin', name: 'Admin', meta: { name: '管理员信息' }, component: () => import('../views/manager/Admin') },
      { path: 'adminPerson', name: 'AdminPerson', meta: { name: '个人信息' }, component: () => import('../views/manager/AdminPerson') },
      { path: 'password', name: 'Password', meta: { name: '修改密码' }, component: () => import('../views/manager/Password') },
      { path: 'notice', name: 'Notice', meta: { name: '公告信息' }, component: () => import('../views/manager/Notice') },
      { path: 'farmer', name: 'Farmer', meta: { name: '农工管理' }, component: () => import('../views/manager/Farmer.vue') },
      { path: 'task', name: 'Task', meta: { name: '任务管理' }, component: () => import('../views/manager/TaskList.vue') },
      { path: 'greenhouse', name: 'Greenhouse', meta: { name: '大棚管理' }, component: () => import('../views/manager/GreenhouseList.vue') },
      { path: 'greenhouseForm', name: 'GreenhouseForm', meta: { name: '新增大棚' }, component: () => import('../views/manager/GreenhouseForm.vue') },
      { path: 'greenhouseDetail', name: 'GreenhouseDetail', meta: { name: '大棚详情' }, component: () => import('../views/manager/GreenhouseDetail.vue') },

    ]
  },
  { path: '/login', name: 'Login', meta: { name: '登录' }, component: () => import('../views/Login.vue') },
  { path: '/register', name: 'Register', meta: { name: '注册' }, component: () => import('../views/Register.vue') },
  { path: '*', name: 'NotFound', meta: { name: '无法访问' }, component: () => import('../views/404.vue') },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem("token")

  // 仅登录页是白名单
  const whiteList = ['/login']

  if (whiteList.includes(to.path)) {
    return next()
  }

  // 没有 token，跳回登录页
  if (!token) {
    return next('/login')
  }

  // 如果访问根路径 /，跳转到后台首页
  if (to.path === '/') {
    return next('/home')
  }

  // 放行其他已登录页面
  next()
})



export default router
