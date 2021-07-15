<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <h2>this language is a {{ test }}</h2>

  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import { Inject } from 'vue-property-decorator'

@Options({
  props: {
    msg: String
  }
})
export default class HelloWorld extends Vue {
  msg!: string;
  @Inject('abc') test!: string;
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
h3 {
  margin: 40px 0 0;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}

a {
  color: #42b983;
}
</style>
