<template lang="html">
  <div class="login-form"
       :style="{gridRowGap: gap, gridColumnGap: gap}">

    <div id="longspike" :style="{width: spikeWidth }"/>
    <div id="shortspike" :style="{width: spikeWidth}"/>
    <div class="input-box" :style="{gridRow: '1/2', gridColumn: '3/4'}">
      <label class="input-label">Username</label>
      <input type="text">
    </div>
    <div class="input-box" :style="{gridRow: '2/3', gridColumn: '3/4'}">
      <label class="input-label">Password</label>
      <input type="password">
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
import * as restService from '@/services/RestService'
import { AuthService } from '@/services/AuthService'

export default class LoginDialog extends Vue {
  @Prop({ default: '10px' }) spikeHeight!: string;
  @Prop({ default: '0.1em' }) spikeWidth!: string;
  @Prop({ default: '10px' }) gap!: string;

  private login (username: string, password: string) {
    restService.login(username, password).then(response => {
      if (response.status === 200) {
        const token = response.headers.Authorization
        console.log(token)
        AuthService.logIn(token)
        // this.$router.push(this.navOnComplete)
      } else {
        this.onFailedLogin()
      }
    }
    ).catch(reason => this.onLoginError(reason))
  }

  private onLoginError (reason: string) {
    console.log(reason)
  }

  private onFailedLogin () {
    console.log('loginerror')
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

}

.login-form {
  display: grid;
  width: fit-content;
  grid-template-rows: auto auto auto 1fr;

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
