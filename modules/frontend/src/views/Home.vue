<template>
  <div class="home">
    <!--    <img alt="Vue logo" src="../assets/logo.png">-->
    <!--    <SideNavigator font-size="5" spike-height="200px"/>-->
    <LoginDialog/>
    <!--    <HelloWorld msg="Welcome to Your Vue.js + TypeScript App"/>-->
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import HelloWorld from '@/components/HelloWorld.vue'
import RemoteCodeLogo from '@/components/RemoteCodeLogo.vue'
import SideNavigator from '@/components/SideNavigator.vue'
import LoginDialog from '@/components/LoginDialog.vue' // @ is an alias to /src

@Options({
  components: {
    LoginDialog,
    SideNavigator,
    RemoteCodeLogo,
    HelloWorld
  }
})
export default class Home extends Vue {
}

</script>
