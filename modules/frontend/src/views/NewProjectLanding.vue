<template lang="html">
  <div class="new-proj-land">
    <div class="proj-daloug">

      <new-project-dialoug/>
    </div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'

import NewProjectDialoug from '@/components/NewProjectDialoug.vue'

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

.new-proj-land {
  display: grid;
  grid-template-rows: 1fr auto 1fr;

  .proj-daloug {
    grid-row: 2/3;
  }
}
</style>
