<template>
  <h1> Warehouse Management System</h1>
  <div class="table" > 
    <div class="item">
      <h3> Items</h3>
      <h3> Stock </h3>
      <h3> Quantity </h3>
    </div>
    <div v-for="(item, pos) in items" :key="item.name" class = "item">
      <div>{{item.name}}</div>
      <div> {{ item.stock }} </div>
      <div> 
        <button v-on:click="buy(pos, 1)"> + </button>
        <b>{{ item.quantity }} </b>
        <button v-on:click="buy(pos, 0)"> - </button>
      </div>
    </div>
    <button class="buy" v-on:click="confirm()"> 
    Buy Items
    </button>
  </div>
  <h2>Files</h2>
  <div class="table"> 
    <div class="file">
      <label> Upload Products </label>
      <input type="file" @change="handleFileUpload($event)"/>
      <button v-on:click="submitFile(true)">Submit</button>
    </div>
    <br>
    <div class="file">
      <label> Upload Inventory </label>
      <input type="file" @change="handleFileUpload($event)"/>
      <button v-on:click="submitFile(false)">Submit</button>
    </div>
  </div>
  <div v-if="status">{{warning}}</div>
</template>

<script>

export default {
  name: 'App',

  data() {
    return {
      items: [],
      file: '',
      warning: '',
      status: false,
    }
  },
  mounted() {
    this.getList();

  },
  methods: {
    getList() {
      var inner = this;
      this.axios.get("http://localhost:8080/warehouse/list").then((response) => {
        console.log(response.data);
        response.data.forEach(x => {
          inner.items.push({name: x.name, stock: x.stock, quantity: 0});
        });
        
        
      })
    },
    buy(position, direction){
      let it = this.items[position];
      if(direction > 0){
        if(it.stock > it.quantity){
          it.quantity += 1;
        }  
      }else{
        if(it.quantity > 0){
          it.quantity -= 1;
        }
      }
    },
    confirm(){
      let context = this;
      let bought = [];
      let indexes = [];
      let i = 0;
      for(let it of this.items){
        if(it.quantity > 0){
          bought.push(it);
          indexes.push(i);
        }
        i += 1;
      }
      this.axios.post('http://localhost:8080/warehouse/sell', 
        {data: JSON.stringify(bought)}, {headers: {'Content-Type': 'multipart/form-data'}})
      .then(function (response) {
        let j = 0;
        response.data.forEach(x => {
          context.items[indexes[j]]["stock"] = x.stock;
          context.items[indexes[j]]["quantity"] = 0;
          j++;
        });
      })
      .catch(function (error) {
        console.log(error);
      });
    },
        handleFileUpload(event){
        this.file = event.target.files[0];
        event.target.reset();
      },
      
      submitFile(items){
        let formData = new FormData();
        let context = this;
        formData.append('file', this.file);
        let url = "";
        if(items){
          url = "http://localhost:8080/warehouse/products";
        }else{
          url = "http://localhost:8080/warehouse/inventory";
        }
        this.axios.post( url,
          formData,
          {headers: {'Content-Type': 'multipart/form-data'}}
        ).then(function(){
          context.warningChange("Uploaded Successfully", context);         
        })
        .catch(function(){
          context.warningChange("Failed to upload", context);
        });
      },
      warningChange(text, context){
        context.warning = text;
        context.status = true;
        context.file = '';
        
        setTimeout(() => context.status = false, 2000);

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
button, input[type="submit"], input[type="reset"] {
  background: none;
  color: inherit;
  border: none;
  padding: 0;
  font: inherit;
  cursor: pointer;
  outline: inherit;
}

.table{
  display: grid;
  border: 1px solid black;
  max-width: fit-content;
  margin: auto;
}
.item{
  display: flex;
  padding-bottom: 10px;
}
.item div, .item h3{
  text-align: center;
  min-width: 20vw;
}
.item button{
  border: 1px solid black;
  padding: 2px;
  min-width: 2vw;
  font-weight: bold;
}
.item b{
  display: inline-block;
  min-width: 4vw;
}

.buy{
  border: 1px solid black;
  border-bottom: none;
  font-weight: bold; 
  font-size: 3vw;
  margin: auto;
  display: block;
  padding:  10px;
}

.file{
  display: flex;
  flex-direction: column;
}
.file:first-child{
  border-bottom: 1px solid black;
  margin-bottom: 3vh;
}
.file button{
  border-top: 1px solid black;
  margin-top: 1vh;
}
</style>
