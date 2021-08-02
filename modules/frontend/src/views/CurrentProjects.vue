<template lang="html">
  <div class="current-user-projects-page">

    <h2>Current user owned projects:</h2>
    <hr class="subhead-underline">
    <div v-for="(value, index)  in userProjects">
      <ProjectCard :card-project="value" :proj-index="index"/>
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
import { ProvideReactive } from 'vue-property-decorator'

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

  padding-top: 20vh;

  h2 {
    text-align: start;
    margin-bottom: 0;
  }

}
</style>
