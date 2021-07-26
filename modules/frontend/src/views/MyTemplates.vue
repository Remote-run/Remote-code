<template lang="html">
  <div>

    <h2>Current user owned templates:</h2>
    <hr class="subhead-underline">
    <div v-for="template in userTemplates">

      <TemplateCard :card-template="template"/>
      <hr class="card-divider">
    </div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import { Project } from '@/models/Project'
import * as restService from '@/services/RestService'
import { AuthService } from '@/services/AuthService'
import { Template } from '@/models/Template'
import ProjectCard from '@/components/ProjectCard.vue'
import TemplateCard from '@/components/TemplateCard.vue'

@Options({
  components: {
    TemplateCard
  }
})
export default class MyTemplates extends Vue {
  userTemplates: Template[] = [];

  mounted () {
    this.fetchUserTemplate()
  }

  fetchUserTemplate () {
    restService.getCurrentTemplates().then(value => {
      console.log(value)
      const status = value.status
      if (status === 200) {
        this.userTemplates = value.data
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
</style>
