<script setup>
import { ref, onMounted } from "vue";
import BoardCard from "./BoardCard.vue";
import api from "axios";

const planList = ref([]);
const boardlist = async () => {
  await api
    .post(`http://localhost:8090/trip/plan`, {
      key: "",
      word: ""
    })
    .then(({ data }) => {
      planList.value = data;
    })
    .catch((e) => {
      console.log(e);
    });
};

onMounted(() => {
  boardlist();
});

</script>

<template>
  <div class="row g-5">
    <div class="col-md-12">
      <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
        <div v-for="(plan, index) in planList" :key="index">
          <board-card :plan="plan"></board-card>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
