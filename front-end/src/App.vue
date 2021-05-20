<template>
  <div id="app">
    <panel></panel>
  </div>
</template>

<script>
import panel from './components/search-panel.vue';

export default {
  name: 'App',
  components: {
    panel
  }
}
</script>

<style>
html {
  height: 100%;
}
body {
  background: url('./assets/background.png') no-repeat;
  background-size: cover;
}
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
}
</style>
