<template lang="html">
  <div class="remote-code-logo"
       :style="{gridRowGap: gap, gridColumnGap: gap, fontSize: fontSize}">

    <h1 id="top">Remote</h1>
    <h1 id="bottom">Code</h1>
    <div id="spacer" :style="{height: spikeHeight }"/>
    <div id="longspike" :style="{width: spikeWidth}"/>
    <div id="shortspike" :style="{width: spikeWidth}"/>

  </div>
</template>

<script lang="ts">
import { Vue } from 'vue-class-component'
import { Prop } from 'vue-property-decorator'

export default class RemoteCodeLogo extends Vue {
  @Prop({ default: '5rem' }) fontSize!: string;
  @Prop({ default: '10px' }) spikeHeight!: string;
  @Prop({ default: '0.1em' }) spikeWidth!: string;
  @Prop({ default: '10px' }) gap!: string;
}
</script>

<style scoped lang="scss">

@use "src/assets/main";

.remote-code-logo {
  display: grid;
  width: fit-content;
  grid-template-rows: auto auto auto 1fr;

  #longspike {
    background-color: main.$blue2;
    grid-column: 3;
    grid-row: 1/3;
  }

  #shortspike {
    background-color: main.$blue2;
    grid-column: 2;
    grid-row: 1/4;
  }

  #spacer {
    grid-column: 1;
    grid-row: 1/2;
  }

  h1 {
    line-height: 0.75;

    #top {
      grid-row: 2;
    }

    #bottom {

      grid-row: 3;
    }
  }

}

</style>
