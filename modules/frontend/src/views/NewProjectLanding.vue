<template lang="html">
  <div>
    <new-project-dialoug/>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import RemoteCodeLogo from '@/components/RemoteCodeLogo.vue'
import SideNavigator from '@/components/SideNavigator.vue'
import Login from '@/views/Login.vue'
import CurrentProjects from '@/views/CurrentProjects.vue'
import NewProjectDialoug from '@/components/NewProjectDialoug.vue'
import EditTemplateForm from '@/components/EditTemplateForm.vue'
import TemplateCard from '@/components/TemplateCard.vue'
import ProjectCard from '@/components/ProjectCard.vue'

@Options({

  components: {

    NewProjectDialoug

  }
})
export default class NewProjectLanding extends Vue {
  // mounted () {
  //   this.selectedIndex = this.findSelectedNavLocation()
  // }
}
</script>

<style scoped lang="scss">

@use "src/assets/main";
</style>
