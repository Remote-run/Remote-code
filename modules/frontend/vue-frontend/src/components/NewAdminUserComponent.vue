<template lang="html">
  <div class="new-admin-user-form">

    <div class="input-box">
      <label class="input-label">Username</label>
      <input type="text" v-model="username">
    </div>
    <div class="input-box">
      <label class="input-label">Password</label>
      <input type="password" v-model="password">
    </div>

    <div class="btn-box">
      <button @click="submitTemplate()">New admin user</button>
    </div>
  </div>
</template>

<script lang="ts">
import { Vue } from 'vue-class-component'
import { Prop } from 'vue-property-decorator'
import { Template } from '@/models/Template'
import * as restService from '@/services/RestService'
import { APP_ROUTES } from '@/router/Routes'

export default class NewAdminUserComponent extends Vue {
  username = '';
  password = '';

  createUser () {
    restService.createAdminUser(this.username, this.password).then(response => {
      if (response.status === 200) {
        window.alert('user created sucessfully')
        this.username = ''
        this.password = ''
      } else {
        window.alert('error creating user')
      }
    })
  }
}
</script>

<style scoped lang="scss">

@use "src/assets/main";

$formWidth: 20vw;

.new-admin-user-form {

  font-family: 'Roboto', sans-serif;

  .btn-box {
    padding-top: 2rem;
    font-family: inherit;
    font-weight: normal;
    display: flex;
    flex-shrink: 1;
    //line-height: 0.90;
    flex-direction: row;
  }

  .input-box {
    padding-top: 1rem;
    padding-bottom: 1rem;
    font-family: inherit;
    font-weight: normal;
    display: flex;
    flex-shrink: 1;
    //line-height: 0.90;
    flex-direction: column;

    input {
      width: $formWidth;
    }

  }
}

</style>
