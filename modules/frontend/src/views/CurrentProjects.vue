<template lang="html">
  <div class="current-user-projects-page">
    <h2>Current user owned projects:</h2>
    <hr class="subhead-underline">
    <div v-for="proj in userProjects">
      <ProjectCard :card-project="proj"/>
      <hr class="card-divider">
    </div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import { Project } from '@/models/Project'

import * as restService from '@/services/RestService'
import { AuthService } from '@/services/AuthService'
import router from '@/router'
import { Template } from '@/models/Template'
import { ContainerStatus } from '@/models/ContainerStatus'
import ProjectCard from '@/components/ProjectCard.vue'

@Options({
  components: {
    ProjectCard
  }
})
export default class CurrentProjects extends Vue {
  userProjects: Project[] = [];

  mounted () {
    this.fetchUserProjects()
  }

  fetchUserProjects () {
    // const testTemplate = new Template(11, 'some test name', 'http.abc', 'https://remote-code.woldseth.xyz')
    // const testProject = new Project(11, testTemplate, ContainerStatus.RUNNING, '11', 'testkode', 'https://remote-code.woldseth.xyz')
    // this.userProjects = [testProject, testProject, testProject]
    restService.getCurrentProjects().then(value => {
      const status = value.status
      if (status === 200) {
        this.userProjects = value.data
      } else if (status === 401) {
        AuthService.logOut()
        // router.push('/')
      }
    }).catch(reason => {
      console.log(reason)
    }
    )
  }
}
</script>

<style scoped lang="scss">

@use "src/assets/main";

.current-user-projects-page {
  h2 {
    text-align: start;
    margin-bottom: 0;
  }

}
</style>
