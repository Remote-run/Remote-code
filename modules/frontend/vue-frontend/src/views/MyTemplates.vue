<template lang="html">
  <div class="my-templates">

    <h2>Current user owned templates:</h2>
    <hr class="subhead-underline">
    <div v-for="template in userTemplates" :key="template.templateKey">

      <TemplateCard @template-changed="fetchUserTemplate()" :card-template="template"/>
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
import { ProvideReactive } from 'vue-property-decorator'

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

.my-templates {

  padding-top: 20vh;

}
</style>
