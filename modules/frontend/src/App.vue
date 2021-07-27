<template>
  <div id="app-display" v-if="isLoggedIn()">
    <SideNavigator font-size="2rem" spike-height="2vh" spike-width="0.5rem" gap="1rem" id="side-nav"
                   menuitem-padding="1.6rem"/>
    <router-view id="router-v"/>
    <RemoteCodeLogo font-size="4rem" spike-height="2rem" spike-width="0.6rem" gap="1rem" id="rc-logo"/>
  </div>
  <div v-if="!isLoggedIn()">
    <Login/>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import { Template } from '@/models/Template'
import { Project } from '@/models/Project'
import { ContainerStatus } from '@/models/ContainerStatus'
import ProjectCard from '@/components/ProjectCard.vue'
import TemplateCard from '@/components/TemplateCard.vue'
import EditTemplateForm from '@/components/EditTemplateForm.vue'
import NewProjectDialoug from '@/components/NewProjectDialoug.vue'
import { AuthService } from '@/services/AuthService'
import CurrentProjects from '@/views/CurrentProjects.vue'
import Login from '@/views/Login.vue'
import SideNavigator from '@/components/SideNavigator.vue'
import RemoteCodeLogo from '@/components/RemoteCodeLogo.vue'
import { Provide } from 'vue-property-decorator'

@Options({
  components: {
    RemoteCodeLogo,
    SideNavigator,
    Login,
    CurrentProjects,
    NewProjectDialoug,
    EditTemplateForm,
    TemplateCard,
    ProjectCard
  }
})
export default class App extends Vue {
  isLoggedIn (): boolean {
    return AuthService.isLoggedIn()
  }
}

</script>

<style lang="scss">

@use "src/assets/main";

body {
  //background: #fcfcfc;
  margin: 0;
}

#app-display {
  display: grid;
  grid-template-columns: 0.5fr 1fr 0.5fr;
  grid-template-rows: 1fr auto 1fr;
  margin-right: 4%;
  margin-left: 4%;
  width: 92vw;
  height: 100vh;

}

#router-v {
  grid-row: 1/4;
  grid-column: 2/3;
}

#rc-logo {
  grid-column: 3/4;
  grid-row: 1/2;
  margin-left: auto;
}

#side-nav {
  grid-column: 1/2;
  grid-row: 2/3;
}

#app {
  height: 100vh;
  font-family: 'Roboto', Avenir, Helvetica, Arial, sans-serif;
  color: main.$gray1;

  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  //color: #2c3e50;

  .card-divider {
    margin-left: 20%;
    margin-right: 20%;
    background: main.$gray1;
  }

  .subhead-underline {
    background: main.$blue2;
    margin-right: 30%;
  }
}

h1 {
  margin: 0;
  font-size: inherit;
  text-align: right;
}

h2 {
  color: #747474;
  font-size: 2rem;

  text-align: start;
  margin-bottom: 0;
  font-weight: normal;
  //font-weight: bolder;

}

h3 {
  font-weight: normal;
  font-family: inherit;
  text-align: start;
  font-size: 1.7rem;
}

button {
  font-family: inherit;
  background-color: main.$blue2;
  border-radius: main.$border-radius;
  font-size: 1.5rem;
  color: white;
  padding: 0.3em 1.8em 0.3em 1.8em;
}

label {
  padding: 0;
  margin: 0;
  font-size: 2rem;
  text-align: left;
}

input {
  border-radius: 5px;
  height: 1.3rem;
  border: none;
  box-shadow: main.$appBoxShadow;
  padding: 0.3em 0.8em 0.3em 0.8em;
  outline: none;
}

textarea {
  border-radius: 5px;
  border: #747474;
  padding: 1em;
  box-shadow: main.$appBoxShadow;
  white-space: nowrap
}

hr {
  display: block;
  height: 2px;
  //width: 28em; // tex 28chars
  border: 0;
  padding: 0;
}

#nav {
  padding: 30px;

  a {
    font-weight: bold;
    color: #2c3e50;

    &.router-link-exact-active {
      color: #42b983;
    }
  }
}
</style>
