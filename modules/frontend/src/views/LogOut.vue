<template lang="html">
  <div id="logout-panel">
    <h4>Are you shure you want to log out</h4>
    <button @click="logOut()">logout</button>
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

  components: {}
})
export default class LogOut extends Vue {
  logOut (): void {
    AuthService.logOut()
    window.location.reload()// yup this is bad
  }
}
</script>

<style scoped lang="scss">

@use "src/assets/main";

#login-page {
  margin-right: 4%;
  margin-left: 4%;
  width: 92vw;
  height: 100vh;
  display: flex;
  flex-direction: row;
  //flex-wrap: nowrap;
  justify-content: space-between;
}

.login-dialog {

  padding-top: 20%;
}

.login-logo {

}
</style>
