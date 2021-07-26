<template lang="html">
  <div class="template-card">
    <div class="template-card-inner">

      <h3>
        {{ cardTemplate.templateName }}
      </h3>
      <div>
        <!--        <button @click="editTemplate()">Edit</button>-->
        <button @click="deleteTemplate()">Delete</button>
      </div>

    </div>
    <h2>
      SHARE LINK:
    </h2>
    <h3>
      {{ getShareLink() }}
    </h3>

  </div>
</template>

<script lang="ts">
import { Vue } from 'vue-class-component'
import { Prop } from 'vue-property-decorator'

import * as restService from '@/services/RestService'
import { Template } from '@/models/Template'

export default class TemplateCard extends Vue {
  @Prop() cardTemplate!: Template;

  intitTemplateLinkPrefix = process.env.VUE_APP_SERVER_URL + '/projects/new'

  // mounted () {
  // }

  getShareLink () {
    return this.intitTemplateLinkPrefix + '/' + this.cardTemplate.templateKey
  }

  editTemplate () {
    window.location.replace('/')
  }

  private deleteTemplate () {
    console.log('WIP')
  }
}
</script>

<style scoped lang="scss">

@use "src/assets/main";

.template-card {
  display: grid;

  border-radius: main.$border-radius;
  border-color: main.$card-border-color;
  color: main.$card-bg;
  background: main.$blue1;

  .template-card-inner {
    color: main.$gray1;
    background: main.$card-bg;
    grid-row: 1/2;
    grid-column: 1/3;
  }

  h2 {
    color: inherit;
    grid-row: 2/3;
    grid-column: 1/2;
  }

  h3 {
    color: inherit;
    grid-row: 2/3;
    grid-column: 2/3;
  }
}
</style>
