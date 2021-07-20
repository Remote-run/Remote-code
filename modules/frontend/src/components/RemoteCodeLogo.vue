<template lang="html">
  <div class="remote-code-logo"
       :style="{gridRowGap: fontSize * 0.03 + 'em', gridColumnGap: fontSize * 0.02 + 'em', fontSize: fontSize +'em'}">

    <h1 id="top">Remote</h1>
    <h1 id="bottom">Code</h1>
    <div id="spacer" :style="{height: spikeHeight }"/>
    <div id="longspike" :style="{width: spikeWidth}"/>
    <div id="shortspike" :style="{width: spikeWidth}"/>

  </div>
  <slot/>
</template>

<script lang="ts">
import { Vue } from 'vue-class-component'
import { Prop } from 'vue-property-decorator'

export default class RemoteCodeLogo extends Vue {
  @Prop({ default: 10 }) fontSize!: number;
  @Prop({ default: '10px' }) spikeHeight!: string;
  @Prop({ default: '0.1em' }) spikeWidth!: string;
}
</script>

<style scoped lang="scss">

@use "src/assets/main";

.remote-code-logo {
  display: grid;
  width: fit-content;

  #longspike {
    background-color: main.$blue2;
    //width: 20px;
    //height: 30px;
    grid-column: 3;
    grid-row: 1/3;
  }

  #shortspike {
    background-color: main.$blue2;
    //width: 20px;
    //height: 30px;
    grid-column: 2;
    grid-row: 1/4;
  }

  #spacer {
    grid-column: 1;
    grid-row: 1/2;
  }

  h1 {
    color: #707070;
    font-family: 'Roboto', sans-serif;
    margin: 0;
    font-size: inherit;
    text-align: right;

    line-height: 0.75;
    //height: 30px;

    //margin-bottom: auto;

    #top {
      grid-row: 2;
    }

    #bottom {

      grid-row: 3;
    }
  }

}

</style>
