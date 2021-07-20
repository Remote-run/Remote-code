import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import CurrentProjects from '@/views/CurrentProjects.vue'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/projects',
    name: 'Current projects',
    component: CurrentProjects
  }

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
