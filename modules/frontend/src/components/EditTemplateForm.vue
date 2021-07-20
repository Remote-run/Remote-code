<template lang="html">
  <div class="edit-template-form">

    <div class="input-box">
      <h2 class="input-label">Template name</h2>
      <input type="text" id="username" v-model="templateName">
    </div>
    <div class="input-box">
      <h2 class="input-label">Git hub HTTP clone link</h2>
      <input type="text" id="passwd" v-model="templateHttpCloneLink">
    </div>
    <div class="input-box">
      <h2 class="input-label">Docker build steps</h2>
      <textarea v-model="templateDockerBuildSteps"></textarea>
    </div>
    <div class="btn-box">
      <button>Save</button>
    </div>
  </div>
</template>

<script lang="ts">
import { Vue } from 'vue-class-component'
import { Prop } from 'vue-property-decorator'
import { Template } from '@/models/Template'

export default class EditTemplateForm extends Vue {
  templateName = '';
  templateHttpCloneLink = '';
  templateDockerBuildSteps = '';

  // mounted () {
  //   if (this.activeTemplate !== undefined){
  //     this.templateName = this.activeTemplate.templateName;
  //     this.templateHttpCloneLink = thisthis.activeTemplate.templateName;.
  //   }
  // }
}
</script>

<style scoped lang="scss">

@use "src/assets/main";

.input-label {
  padding: 0;
  margin: 0;
  font-size: 50px;
  text-align: left;
}

.edit-template-form {
  display: flex;
  flex-direction: column;
  align-items: start;
  font-family: 'Roboto', sans-serif;

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
