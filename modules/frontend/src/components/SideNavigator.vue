<template lang="html">
  <div v-if="aNotGreatSolution()" class="side-bar-nav"
       :style="{gridRowGap: menuitemPadding, gridColumnGap: gap, fontSize: fontSize}">

    <div id="longspike" :style="getLongspikeStyle()"/>
    <div id="shortspike" :style="getShortSpikeStyle()"/>

    <div id="nav-text" v-for="(value, index) in getNavItems()" :key="value.title" :style="getNavEntryStyle(index)">
      <router-link class="not-here-link" v-if="index !== findSelectedNavLocation()" :to="value.path">{{
          value.title
        }}
      </router-link>
      <h2 v-if="index === findSelectedNavLocation()" :style="{fontSize: fontSize}">{{ value.title }}</h2>
    </div>
  </div>
</template>

<script lang="ts">
import { Vue } from 'vue-class-component'
import { Prop } from 'vue-property-decorator'
import { APP_ROUTES } from '@/router/Routes'
import { AuthService } from '@/services/AuthService'

export default class SideNavigator extends Vue {
  @Prop({ default: '2em' }) fontSize!: string;
  @Prop({ default: '10px' }) spikeHeight!: string;
  @Prop({ default: '0.1em' }) spikeWidth!: string;
  @Prop({ default: '10px' }) gap!: string;
  @Prop({ default: '10px' }) menuitemPadding!: string;

  aNotGreatSolution (): boolean {
    console.log(this.$route)
    return this.$route.name !== 'New project'
  }

  getNavEntryStyle (index: number): any {
    return { fontSize: this.fontSize, gridRow: (index + 1 + 1) + '/' + (index + 1 + 2) }
  }

  getBottpmPadderStyle (): any {
    return { height: this.spikeHeight, gridRow: (this.getNavItems().length - 1) + '/' + this.getNavItems().length }
  }

  getLongspikeStyle (): any {
    return { width: this.spikeWidth, gridRow: '1/' + (this.getNavItems().length + 3) }
  }

  getShortSpikeStyle (): any {
    return { width: this.spikeWidth, gridRow: '3/' + (this.getNavItems().length + 1) }
  }

  selectedIndex = -1;

  mounted () {
    this.selectedIndex = this.findSelectedNavLocation()
  }

  userNavItems = [
    { title: 'User info', path: APP_ROUTES.USER },
    { title: 'Current projects', path: APP_ROUTES.CURENT_PROJECTS },
    { title: 'Logg out', path: APP_ROUTES.LOGG_OUT }
  ]

  adminNavItems = [
    { title: 'User info', path: APP_ROUTES.USER },
    { title: 'Current projects', path: APP_ROUTES.CURENT_PROJECTS },
    { title: 'Current templates', path: APP_ROUTES.MY_TEMPLATES },
    { title: 'New template', path: APP_ROUTES.NEW_TEMPLATE },
    { title: 'Logg out', path: APP_ROUTES.LOGG_OUT }
  ]

  getNavItems (): { title: string, path: string }[] {
    if (AuthService.isAdmin()) {
      return this.adminNavItems
    } else {
      return this.userNavItems
    }
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
    // console.log(currentPathIndex)
    return currentPathIndex
  }
}
</script>

<style scoped lang="scss">

@use "src/assets/main";

.padder {
  height: 20px;
  grid-column: 1/2;
}

.side-bar-nav {
  display: grid;
  width: fit-content;

  #longspike {
    background-color: main.$blue2;
    grid-column: 2/3;
  }

  #shortspike {
    background-color: main.$blue2;
    grid-column: 1/2;
  }

  #nav-text {
    font-size: inherit;
    margin: 0;
    text-align: start;
    grid-column: 3/4;

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
      text-decoration: none;
      color: main.$blue2;

    }
  }

}

</style>
