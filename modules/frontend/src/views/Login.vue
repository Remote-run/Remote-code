<template lang="html">
  <div id="login-page">
    <login-dialog spike-height="8vh" spike-width="0.6rem" gap="2rem" class="login-dialog"/>
    <RemoteCodeLogo font-size="6rem" spike-height="8vh" spike-width="0.6rem" gap="2rem" class="login-logo"/>
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
