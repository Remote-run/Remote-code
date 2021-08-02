<template lang="html">
  <div class="project-card">
    <h3>
      {{ cardProject.containerTemplate.templateName }}
    </h3>
    <h3>
      Access key: {{ cardProject.accessesKey }}
    </h3>
    <h2>
      Status: <span :style="statusStyle">{{ cardProject.containerStatus.toString() }}</span>
    </h2>
    <div v-if="isDeleted">
      <button @click="goToProject()">Go to project</button>
      <button @click="deleteContainer()">Delete</button>
    </div>
  </div>
</template>

<script lang="ts">
import { Vue } from 'vue-class-component'
import { Project } from '@/models/Project'
import { InjectReactive, Prop } from 'vue-property-decorator'
import { ContainerStatus } from '@/models/ContainerStatus'

import * as restService from '@/services/RestService'

export default class ProjectCard extends Vue {
  @Prop() cardProject!: Project;
  @Prop() projIndex!: number;

  @InjectReactive('user-proj') userProjects: Project[] = [];

  mounted () {
    this.updateStatusStyle()
  }

  private isDeleted (): boolean {
    return this.cardProject.containerStatus !== ContainerStatus.DELETED
  }

  private goToProject () {
    console.log(this.cardProject)
    const projectUrl = 'http://' + this.cardProject.containerName + '.' + process.env.VUE_APP_SERVER_URL

    console.log(projectUrl)

    window.location.replace(projectUrl)
  }

  private stopContainer () {
    console.log('WIP')
  }

  private deleteContainer () {
    restService.deleteProject(this.cardProject.containerName).then(value => {
      if (value.status === 200) {
        this.cardProject.containerStatus = ContainerStatus.DELETED

        // TODO: fix
        window.location.reload()
      }
    })
  }

  private statusStyle = {
    color: 'black'
  }

  private updateStatusStyle () {
    if (this.cardProject !== undefined) {
      switch (this.cardProject.containerStatus) {
        case ContainerStatus.RUNNING:
          this.statusStyle.color = 'green'
          break
        case ContainerStatus.IDLE:
          this.statusStyle.color = 'yellow'
          break
        case ContainerStatus.OFF:
          this.statusStyle.color = 'pink'
          break
        case ContainerStatus.DELETED:
          this.statusStyle.color = 'black'
          break
        case ContainerStatus.REQUESTED:
          // this should not be possible
          break
      }
    }
    console.log(this.statusStyle)
  }
}
</script>

<style scoped lang="scss">

@use "src/assets/main";

.project-card {
  display: grid;

  grid-row-gap: 0.5rem;

  background: main.$card-bg;

  border-radius: main.$border-radius;
  border-color: main.$card-border-color;
  border-style: solid;
  border-width: 1px;

  box-shadow: main.$appBoxShadow;

  padding: 1rem;

  h2 {
    grid-column: 2/3;
    grid-row: 1/3;
  }

  h3 {
    margin: 0;
  }
}
</style>
