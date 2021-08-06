<template lang="html">
  <div class="new-template">
    <NewAdminUserComponent/>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import NewProjectDialoug from '@/components/NewProjectDialoug.vue'
import RemoteCodeLogo from '@/components/RemoteCodeLogo.vue'
import SideNavigator from '@/components/SideNavigator.vue'
import Login from '@/views/Login.vue'
import CurrentProjects from '@/views/CurrentProjects.vue'
import EditTemplateForm from '@/components/EditTemplateForm.vue'
import TemplateCard from '@/components/TemplateCard.vue'
import ProjectCard from '@/components/ProjectCard.vue'
import NewAdminUserComponent from '@/components/NewAdminUserComponent.vue'

@Options({

  components: {
    NewAdminUserComponent,

    EditTemplateForm

  }
})
export default class NewAdminUser extends Vue {
  // mounted () {
  //   this.selectedIndex = this.findSelectedNavLocation()
  // }
}
</script>

<style scoped lang="scss">

@use "src/assets/main";

.new-template {
  display: flex;

  align-items: center;
  justify-content: center;

}
</style>
