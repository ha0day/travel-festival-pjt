<script setup>
import { ref, onMounted } from "vue";
import api from "axios";
// import { RouterLink, RouterView } from "vue-router";
import router from "../router";

const boards = ref([]);
//http://localhost:9999/vue/board/?pgno=1&word=&spp=4&key=
const boardlist = async () => {
  await api
    .get(`http://localhost:9999/vue/board/`, {
      params: {
        pgno: "1",
        key: "",
        word: "",
        spp: 5,
      },
    })
    .then(({ data }) => {
      boards.value = data.articles;
      console.log("1. boards data >> ", boards.value);
      //console.log("----------------------------->", response);
    })
    .catch((e) => {
      console.log("1. boards catch >> ", e);
    });
};
const showBoard = (no) => {
  router.push(`/boarddetail/${no}`);
};
onMounted(() => {
  boardlist();
});
</script>

<template>
  <h3>Example table</h3>
  <p>And don't forget about tables in these posts:</p>
  <table class="table">
    <col width="20%" />
    <col width="20%" />
    <col width="auto" />
    <thead>
      <tr>
        <th>번호</th>
        <th>작성자</th>
        <th>제목</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="(board, index) in boards" :key="board.articleNo">
        <td>{{ index + 1 }}</td>
        <td>{{ board.userName }}</td>
        <td @click="showBoard(board.articleNo)">{{ board.subject }}</td>
      </tr>
    </tbody>
  </table>
</template>

<style scoped></style>
