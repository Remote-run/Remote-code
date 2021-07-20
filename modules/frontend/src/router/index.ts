import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import CurrentProjects from '@/views/CurrentProjects.vue'
import { APP_ROUTES } from '@/router/Routes'
import NewProjectLanding from '@/views/NewProjectLanding.vue'
import MyTemplates from '@/views/MyTemplates.vue'
import NewTemplate from '@/views/NewTemplate.vue'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/*',
    redirect: APP_ROUTES.CURENT_PROJECTS
  },
  {
    path: APP_ROUTES.CURENT_PROJECTS,
    name: 'Current projects',
    component: CurrentProjects
  },
  {
    path: APP_ROUTES.MY_TEMPLATES,
    name: 'My templates',
    component: MyTemplates
  },
  {
    path: APP_ROUTES.NEW_TEMPLATE,
    name: 'New template',
    component: NewTemplate
  },
  {
    path: APP_ROUTES.NEW_PROJECT,
    name: 'New project',
    component: NewProjectLanding
  },
  {
    path: APP_ROUTES.LOGG_OUT,
    name: 'Logg out',
    component: NewProjectLanding
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
