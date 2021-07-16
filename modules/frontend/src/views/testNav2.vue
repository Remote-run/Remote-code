<template>
  <div class="home">
    <h1>test nav 2</h1>
    <side-navigator/>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import HelloWorld from '@/components/HelloWorld.vue'
import RemoteCodeLogo from '@/components/RemoteCodeLogo.vue'
import SideNavigator from '@/components/SideNavigator.vue' // @ is an alias to /src

@Options({
  components: {
    SideNavigator
  }
})
export default class testNav2 extends Vue {
}

</script>
