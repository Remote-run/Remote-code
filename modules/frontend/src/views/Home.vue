<template>
  <div class="home">
    <!--    <img alt="Vue logo" src="../assets/logo.png">-->
    <RemoteCodeLogo font-size="5" spike-height="200px"></RemoteCodeLogo>
    <!--    <HelloWorld msg="Welcome to Your Vue.js + TypeScript App"/>-->
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import HelloWorld from '@/components/HelloWorld.vue'
import { Provide } from 'vue-property-decorator'
import RemoteCodeLogo from '@/components/RemoteCodeLogo.vue' // @ is an alias to /src

@Options({
  components: {
    RemoteCodeLogo,
    HelloWorld
  }
})
export default class Home extends Vue {
  @Provide('abc') lalalala = 'shitty language'
}

</script>
