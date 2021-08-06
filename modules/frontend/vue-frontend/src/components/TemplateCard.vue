<template lang="html">
  <div class="template-card">
    <div class="blue-card-bg"></div>
    <div class="template-card-inner">

      <h3>
        {{ cardTemplate.templateName }}
      </h3>
      <div class="template-btn-box">
        <button @click="editTemplate()">Edit</button>
        <button @click="deleteTemplate()">Delete</button>
      </div>

    </div>
    <h3 class="share-link-t">
      SHARE LINK:
    </h3>
    <p class="share-link">
      {{ getShareLink() }}
    </p>

  </div>
</template>

<script lang="ts">
import { Vue } from 'vue-class-component'
import { Emit, Prop } from 'vue-property-decorator'

import * as restService from '@/services/RestService'
import { Template } from '@/models/Template'
import { ContainerStatus } from '@/models/ContainerStatus'

export default class TemplateCard extends Vue {
  @Prop() cardTemplate!: Template;

  intitTemplateLinkPrefix = 'https://' + process.env.VUE_APP_SERVER_URL + '/projects/new'

  getShareLink () {
    return this.intitTemplateLinkPrefix + '/' + this.cardTemplate.templateKey
  }

  editTemplate () {
    window.alert('Not implemented yet')
  }

  private deleteTemplate () {
    restService.deleteTemplate(this.cardTemplate.templateKey).then(value => {
      if (value.status === 200) {
        this.$emit('template-changed')
      } else {
        window.alert('error')
      }
    })
  }
}
</script>

<style scoped lang="scss">

@use "src/assets/main";

.template-btn-box {
  display: flex;
  align-content: start;

  button {
    margin: 0.3rem 2rem 1.2rem 1rem;
  }
}

.template-card {
  display: grid;

  .blue-card-bg {
    border-radius: main.$border-radius;
    border-color: main.$card-border-color;
    border-style: solid;
    border-width: 1px;

    box-shadow: main.$appBoxShadow;

    background: main.$blue1;
    z-index: -1;

    grid-row: 1/3;
    grid-column: 1/3;
  }

  .template-card-inner {
    border-radius: main.$border-radius;
    border-color: main.$card-border-color;
    border-style: solid;
    border-width: 1px;

    box-shadow: main.$appBoxShadow;

    background: main.$card-bg;
    grid-row: 1/2;
    grid-column: 1/3;

    h3 {
      //font-weight: bolder;

      margin: 1rem;
    }
  }

  .share-link-t {
    color: white;
    margin-left: 1rem;
    margin-top: 0.7rem;
    margin-bottom: 0.7rem;
    grid-row: 2/3;
    grid-column: 1/2;
  }

  .share-link {
    color: white;
    grid-row: 2/3;
    grid-column: 2/3;
    margin: auto;
  }
}
</style>
