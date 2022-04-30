<template>
  
  <h1> HELOO </h1>
</template>

<script>

export default {
  name: 'App',
  mounted() {
    console.log("mounted");
    this.getList();
  },
  methods: {
    getList() {
      this.axios.get("http://localhost:8080/warehouse/list").then((response) => {
        console.log(response.data);
      })
    }
  }

}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
