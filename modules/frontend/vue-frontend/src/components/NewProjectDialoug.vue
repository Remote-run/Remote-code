<template lang="html">
  <div class="new-project-dialouge" :style="{gridRowGap: gap, gridColumnGap: gap }">

    <div id="longspike" :style="{width: spikeWidth }"/>
    <div id="shortspike" :style="{width: spikeWidth}"/>
    <div class="padder" :style="{gridRow: '1/2', height: spikeHeight}"/>
    <div id="padder-hoisontal" :style="{gridColumn: '3/4', width: dialogLeftPad}"></div>
    <div id="dialouge">
      <h3 class="label" :style="{gridArea: '1/1/2/2'}">Template name:</h3>
      <h3 class="value" :style="{gridArea: '1/2/2/3'}">{{ templateName }}</h3>

      <h3 class="label" :style="{gridArea: '2/1/3/2'}">Project access tag:</h3>
      <h3 class="value" :style="{gridArea: '2/2/3/3'}">{{ containerAccesKey }}</h3>

      <button v-if="canGoToProj" @click="goToProject()">go to project</button>
      <h4 v-else id="waring-text">The project is building <br> and will be ready soon</h4>
    </div>
    <div class="padder" :style="{gridRow: '3/4'}"/>
  </div>
</template>

<script lang="ts">
import { Vue } from 'vue-class-component'
import { Prop } from 'vue-property-decorator'
import { Project } from '@/models/Project'
import { ContainerStatus } from '@/models/ContainerStatus'
import * as restService from '@/services/RestService'

export default class newProjectDialoug extends Vue {
  @Prop({ default: '10px' }) spikeHeight!: string;
  @Prop({ default: '0.1em' }) spikeWidth!: string;
  @Prop({ default: '0.1em' }) gap!: string;
  @Prop({ default: '0.1em' }) dialogLeftPad!: string;
  newProject!: Project;

  canGoToProj = false;

  templateName = ' ';
  containerAccesKey = ' ';

  mounted () {
    this.pollForOkProj()
  }

  async pollForOkProj () {
    let willRefresh = false
    if (this.newProject === undefined) {
      willRefresh = true
    } else if (this.newProject.containerStatus !== ContainerStatus.RUNNING) {
      willRefresh = true
    } else {
      // The container is running and we dont have to refresh anymore
      this.canGoToProj = true
      willRefresh = false
    }

    if (willRefresh) {
      this.refreshProject()
      setTimeout(this.pollForOkProj, 1000)
    }
  }

  private refreshProject () {
    console.log('other')
    const templateId = this.$route.params.id as string
    restService.initializeTemplateToProject(templateId).then(value => {
      if (value.status === 200) {
        this.newProject = value.data

        this.templateName = this.newProject.containerTemplate.templateName
        this.containerAccesKey = this.newProject.accessesKey
      } else {

      }
    })
  }

  private goToProject () {
    console.log(this.newProject)
    const projectUrl = 'http://' + this.newProject.containerName + '.' + process.env.VUE_APP_SERVER_URL

    console.log(projectUrl)

    window.location.replace(projectUrl)
  }
}
</script>

<style scoped lang="scss">

@use "src/assets/main";

.padder {
  height: 20px;
  grid-column: 3/4;
}

#waring-text {
  font-size: 1.5rem;
  margin: 0.5rem;
  color: main.$blue3;
}

.new-project-dialouge {
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

  //#dialouge  {
  //}

  #dialouge {

    font-size: 50px;
    grid-row: 2/3;
    grid-column: 4/5;
    display: grid;
    flex-direction: column;

    .label {
      margin: 0.5rem;
      font-weight: bolder;
      text-align: start;
    }

    .value {
      margin: 0.5rem;
      font-weight: bolder;
      margin-left: 1em;
      text-align: start;
    }

    button {
      margin-top: 1rem;
    }
  }

}

</style>
