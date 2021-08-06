<template lang="html">
  <div class="logout-panel">
    <h4>Are you shure you want to log out</h4>
    <button @click="logOut()">logout</button>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import { Prop } from 'vue-property-decorator'
import * as restService from '@/services/RestService'
import { AuthService } from '@/services/AuthService'

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

.logout-panel {
  margin-top: 30vh;
}

</style>
