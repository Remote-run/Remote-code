<template>
  <div v-if="isLoggedIn()">
    aaa
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

@Options({

  components: {
    Login,
    CurrentProjects,
    NewProjectDialoug,
    EditTemplateForm,
    TemplateCard,
    ProjectCard
  }
})
export default class App extends Vue {
  testTemplate = new Template(11, 'some test name', 'http.abc', 'https://remote-code.woldseth.xyz')
  testProject = new Project(11, this.testTemplate, ContainerStatus.RUNNING, '11', 'testkode', 'https://remote-code.woldseth.xyz')

  isLoggedIn (): boolean {
    return AuthService.isLoggedIn()
  }
}

</script>

<style lang="scss">

@use "src/assets/main";

#bipbop {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
}

#app {

  font-family: 'Roboto', Avenir, Helvetica, Arial, sans-serif;
  color: main.$gray1;

  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  //color: #2c3e50;

  h1 {

  }

  h2 {
    text-align: start;
    margin-bottom: 0;
    font-weight: bolder;

  }

  h3 {
  }

  label {

  }

  hr {
    display: block;
    height: 2px;
    //width: 28em; // tex 28chars
    border: 0;
    padding: 0;
  }

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
