<template lang="html">
  <div id="login-page">
    <RemoteCodeLogo/>
    <login-dialog/>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import { Prop } from 'vue-property-decorator'
import * as restService from '@/services/RestService'
import { AuthService } from '@/services/AuthService'
import CurrentProjects from '@/views/CurrentProjects.vue'
import NewProjectDialoug from '@/components/NewProjectDialoug.vue'
import EditTemplateForm from '@/components/EditTemplateForm.vue'
import TemplateCard from '@/components/TemplateCard.vue'
import ProjectCard from '@/components/ProjectCard.vue'
import RemoteCodeLogo from '@/components/RemoteCodeLogo.vue'
import LoginDialog from '@/components/LoginDialog.vue'

@Options({

  components: {
    LoginDialog,
    RemoteCodeLogo
  }
})
export default class Login extends Vue {
}
</script>

<style scoped lang="scss">

@use "src/assets/main";
</style>
