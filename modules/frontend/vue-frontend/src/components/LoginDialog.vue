<template lang="html">
  <div class="login-form"
       :style="{gridRowGap: gap, gridColumnGap: gap}">

    <div id="longspike" :style="{width: spikeWidth }"/>
    <div id="shortspike" :style="{width: spikeWidth}"/>
    <div class="input-box" :style="{gridRow: '1/2', gridColumn: '3/4'}">
      <label class="input-label">Username</label>
      <input type="text" v-model="username">
    </div>
    <div class="input-box" :style="{gridRow: '2/3', gridColumn: '3/4'}">
      <label class="input-label">Password</label>
      <input type="password" v-model="password">
    </div>
    <div class="btn-box" :style="{gridRow: '3/4', gridColumn: '3/4'}">
      <button @click="login()">login</button>
      <button @click="newUser()">create user</button>
    </div>
    <div class="padder" :style="{gridRow: '4/5', gridColumn: '3/4'}"/>
  </div>
</template>

<script lang="ts">

import { Vue } from 'vue-class-component'
import { Inject, InjectReactive, Prop, Provide } from 'vue-property-decorator'
import * as restService from '@/services/RestService'
import { AuthService } from '@/services/AuthService'

export default class LoginDialog extends Vue {
  @Prop({ default: '10px' }) spikeHeight!: string;
  @Prop({ default: '0.1em' }) spikeWidth!: string;
  @Prop({ default: '10px' }) gap!: string;

  username = '';
  password = '';

  private login () {
    restService.login(this.username, this.password).then(response => {
      if (response.status === 200) {
        const token = response.headers.authorization
        AuthService.logIn(token)
        window.location.reload()// yup this is bad
      } else {
        this.onFailedLogin()
      }
    }
    ).catch(reason => this.onLoginError(reason))
  }

  private newUser () {
    restService.createUser(this.username, this.password).then(response => {
      if (response.status === 200) {
        const token = response.headers.authorization
        AuthService.logIn(token)
        window.location.reload()// yup this is bad
      } else {
        window.alert('Error creating user, username may be in use')
      }
    })
  }

  private onLoginError (reason: string) {
    window.alert('Error logging in')
    console.log(reason)
  }

  private onFailedLogin () {
    window.alert('failed login')
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
  width: 20vw;
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
    justify-content: space-between;
    padding-top: 1rem;

    button {
      //padding-right: 0.3rem;
      //padding-left: 0.3rem;
    }
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
