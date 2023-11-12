<script setup>
import { ref, onMounted } from "vue";
import api from "axios";
import { useRoute, useRouter } from "vue-router";
const router = useRouter(); // 네비게이션
const route = useRoute(); // 경로 매칭
const articleNo = ref(route.params.no);

const board = ref({});
//http://localhost:9999/vue/board/?pgno=1&word=&spp=4&key=
const boarddetail = async () => {
  await api
    .get(`http://localhost:9999/vue/board/${articleNo.value}`)
    .then(({ data }) => {
      board.value = data;
      console.log("2. board data detail >> ", board.value);
    })
    .catch((e) => {
      console.log("2. board catch >> ", e);
    });
};
onMounted(() => {
  boarddetail();
});
</script>

<template>
  <div class="row g-5">
    <div class="col-md-8">
      <h3 class="pb-4 mb-4 fst-italic border-bottom">Gallery</h3>
      <div class="row g-5">
        <div class="col-md-12">
          <div class="card shadow-sm">
            <img
              src="https://mdbootstrap.com/img/Others/documentation/1.webp"
              class="img-fluid mx-auto d-block"
              alt="Responsive image"
            />
            <div class="card-body">
              <p class="card-text">
                {{ board.articleNo }}
              </p>
              <p class="card-text">
                [{{ board.userId }} ] {{ board.userName }}님
              </p>
              <p class="card-text">
                {{ board.subject }}
              </p>
              <p class="card-text">
                {{ board.content }}
              </p>
              <p class="card-text">
                {{ board.registerTime }}
              </p>
              <div class="d-flex justify-content-between align-items-center">
                <small class="text-muted">{{ board.hit }}조회수</small>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
