<template lang="html">
  <div class="login-form"
       :style="{gridRowGap: fontSize * 0.03 + 'em', gridColumnGap: fontSize * 0.02 + 'em', fontSize: fontSize +'em'}">

    <div id="longspike" :style="{width: spikeWidth }"/>
    <div id="shortspike" :style="{width: spikeWidth}"/>
    <div class="input-box" :style="{gridRow: '1/2', gridColumn: '3/4'}">
      <h2 class="input-label">Username</h2>
      <input type="text" id="username">
    </div>
    <div class="input-box" :style="{gridRow: '2/3', gridColumn: '3/4'}">
      <h2 class="input-label">Password</h2>
      <input type="password" id="passwd">
    </div>
    <div class="btn-box" :style="{gridRow: '3/4', gridColumn: '3/4'}">
      <button>login</button>
      <button>create user</button>
    </div>
    <div class="padder" :style="{gridRow: '4/5', gridColumn: '3/4'}"/>
  </div>
</template>

<script lang="ts">
import { Vue } from 'vue-class-component'
import { Prop } from 'vue-property-decorator'

export default class LoginDialog extends Vue {
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

.input-label {
  padding: 0;
  margin: 0;
  font-size: 50px;
  text-align: left;
}

.login-form {
  display: grid;
  width: fit-content;
  font-family: 'Roboto', sans-serif;

  #longspike {
    background-color: main.$blue2;
    grid-column: 2/3;
    grid-row: 1/5;
  }

  #shortspike {
    background-color: main.$blue2;
    grid-column: 1/2;
    grid-row: 2/5;
  }

  .btn-box {
    font-family: inherit;
    font-weight: normal;
    display: flex;
    flex-shrink: 1;
    //line-height: 0.90;
    flex-direction: row;
  }

  .input-box {
    font-family: inherit;
    font-weight: normal;
    display: flex;
    flex-shrink: 1;
    //line-height: 0.90;
    flex-direction: column;
  }
}

</style>
