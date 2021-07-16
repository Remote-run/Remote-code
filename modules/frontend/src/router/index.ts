import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Home from '../views/Home.vue'
import testNav1 from '@/views/testNav1.vue'
import testNav2 from '@/views/testNav2.vue'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/test1',
    name: 'test1',
    component: testNav1
  },
  {
    path: '/test2',
    name: 'test2',
    component: testNav2
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
