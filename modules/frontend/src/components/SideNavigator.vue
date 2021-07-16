<template lang="html">
  <div class="side-bar-nav"
       :style="{gridRowGap: fontSize * 0.03 + 'em', gridColumnGap: fontSize * 0.02 + 'em', fontSize: fontSize +'em'}">
    <!--    <div class="side-bar-nav" :style="{gridRowGap: fontSize * 0.03 + 'em', gridColumnGap: fontSize * 0.02 + 'em', fontSize: fontSize +'em'}">-->

    <!--    <div id="longspike" :style="{width: spikeWidth, gridRowEnd: navOptions.length -1  }"/>-->
    <!--    <div id="shortspike" :style="{width: spikeWidth, gridRowEnd: navOptions.length -2 }"/>-->

    <div id="longspike" :style="{width: spikeWidth }"/>
    <div id="shortspike" :style="{width: spikeWidth}"/>
    <div class="padder" :style="{gridRow: 1/2}"/>

    <div id="nav-flex">
      <div id="nav-text" v-for="(value, index) in getNavItems()" :key="value.title">
        <router-link class="not-here-link" v-if="index !== selectedIndex" :to="value.path">{{
            value.title
          }}
        </router-link>
        <h2 v-if="index === selectedIndex">{{ value.title }}</h2>
      </div>
    </div>
    <div class="padder" :style="{gridRow: 3/4}"/>
  </div>
</template>

<script lang="ts">
import { Vue } from 'vue-class-component'
import { Prop } from 'vue-property-decorator'

export default class SideNavigator extends Vue {
  @Prop({ default: 10 }) fontSize!: number;
  @Prop({ default: '10px' }) spikeHeight!: string;
  @Prop({ default: '0.1em' }) spikeWidth!: string;

  selectedIndex = -1;
  isAdmin = true;

  mounted () {
    this.selectedIndex = this.findSelectedNavLocation()
  }

  userNavItems = [
    { title: 'test1', path: '/test1' },
    { title: 'test2', path: '/test2' }
  ]

  adminNavItems = [
    { title: 'test1', path: '/test1' },
    { title: 'test2', path: '/test2' }
  ]

  getNavItems (): { title: string, path: string }[] {
    return this.userNavItems
  }

  findSelectedNavLocation (): number {
    let currentPathIndex = -1
    const currentPath = this.$route.path
    const navList = this.getNavItems()
    navList.forEach((value, index) => {
      if (value.path === currentPath) {
        currentPathIndex = index
      }
    })
    return currentPathIndex
  }
}
</script>

<style scoped lang="scss">

@use "src/assets/main";

.padder {
  height: 20px;
  grid-column: 3/4;
}

.side-bar-nav {
  display: grid;
  width: fit-content;

  #longspike {
    background-color: main.$blue2;
    grid-column: 2/3;
    grid-row: 1/4;
  }

  #shortspike {
    background-color: main.$blue2;
    grid-column: 1/2;
    grid-row: 2/3;
  }

  #nav-text {
    font-size: 100px;
    margin: 0;

    .not-here-link {
      font-size: inherit;

      //font-family: 'Roboto', sans-serif;
      margin: inherit;
      text-decoration: none;
      color: main.$gray1;

    }

    h2 {
      font-weight: normal;
      font-size: inherit;
      margin: inherit;
      text-decoration: underline;
      color: main.$blue2;

    }
  }

  #nav-flex {
    grid-row: 2/3;
    grid-column: 3/4;
    display: flex;
    flex-direction: column;
  }

}

</style>
