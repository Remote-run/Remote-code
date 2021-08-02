<template lang="html">
  <div class="new-project-dialouge"
       :style="{gridRowGap: fontSize * 0.03 + 'em', gridColumnGap: fontSize * 0.02 + 'em', fontSize: fontSize +'em'}">

    <div id="longspike" :style="{width: spikeWidth }"/>
    <div id="shortspike" :style="{width: spikeWidth}"/>
    <!--    <div class="padder" :style="{gridRow: 1/2}"/>-->

    <div id="dialouge">
      <h3 class="label" :style="{gridArea: '1/1/2/2'}">Template name:</h3>
      <h3 class="value" :style="{gridArea: '1/2/2/3'}">{{ templateName }}</h3>

      <h3 class="label" :style="{gridArea: '2/1/3/2'}">Project access tag:</h3>
      <h3 class="value" :style="{gridArea: '2/2/3/3'}">{{ containerAccesKey }}</h3>

      <button v-if="newProject !== undefined" @click="goToProject()">go to project</button>
    </div>
  </div>
  <!--  <div class="padder" :style="{gridRow: 3/4}"/>-->
</template>

<script lang="ts">
import { Vue } from 'vue-class-component'
import { Prop } from 'vue-property-decorator'
import { Template } from '@/models/Template'
import { Project } from '@/models/Project'
import { ContainerStatus } from '@/models/ContainerStatus'
import * as restService from '@/services/RestService'

export default class newProjectDialoug extends Vue {
  @Prop({ default: '10px' }) spikeHeight!: string;
  @Prop({ default: '0.1em' }) spikeWidth!: string;

  @Prop({ default: '0.1em' }) dialougLeftPad!: string;

  newProject!: Project;

  templateName = ' ';
  containerAccesKey = ' ';

  mounted () {
    const templateId = this.$route.params.id as string

    restService.initializeTemplateToProject(templateId).then(value => {
      console.log(value)
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
    grid-column: 3/4;
    display: grid;
    flex-direction: column;

    .label {
      text-align: end;
    }

    .value {
      margin-left: 1em;
      text-align: start;
    }
  }

}

</style>
