<script setup>
import { ref, onMounted, watch } from "vue";
import BoardCard from "./BoardCard.vue";
import api from "axios";
import { searchStore } from "@/stores/planListStore";
import { userStore } from "@/stores/userStore";
const sstore = searchStore();
const ustore = userStore();
const planList = ref([]);
const onlyFavoriteList = ref([]);
const onlySharedList = ref([]);
const onlyFavoriteToggle = ref("");
const onlySharedToggle = ref("");

const boardlist = async () => {
  await api
    .post(`${import.meta.env.VITE_VUE_API_URL}/plan`, {
      key: sstore.key,
      word: sstore.word,
      userId: ustore.userInfo.userId,
    })
    .then(({ data }) => {
      console.log(data);
      if (ustore.isLogin && sstore.isMy) {
        // 내여행계획
        planList.value = data.filter((d) => d.userId === ustore.userInfo.userId);
      } else {
        planList.value = data.filter((d) => d.shared === 1);
      }

      onlyFavoriteList.value = planList.value.filter((plan) => {
        return plan.isFavorite === true;
      });

      onlySharedList.value = planList.value.filter((plan) => {
        return plan.shared === 1;
      });
      console.log("onlySharedList", onlySharedList.value);
    })
    .catch((e) => {
      console.log(e);
    });
};

watch(
  () => sstore.onlyFavorite,
  () => {
    if (sstore.onlyFavorite) {
      onlyFavoriteToggle.value = true;
      onlySharedToggle.value = false;
    } else onlyFavoriteToggle.value = false;
  }
);

watch(
  () => sstore.onlyShared,
  () => {
    if (sstore.onlyShared) {
      onlySharedToggle.value = true;
      onlyFavoriteToggle.value = false;
    } else onlySharedToggle.value = false;
  }
);

onMounted(() => {
  boardlist();
});
</script>

<template>
  <div class="cards-1 section-gray">
    <div class="container">
      <div class="form-check form-check-inline m-3">
        <input
          class="form-check-input"
          type="checkbox"
          id="favoriteCheckBox"
          v-model="sstore.onlyFavorite"
          @change="check($event)"
        />
        <label class="form-check-label ms-3" for="inlineCheckbox1">좋아요한 여행계획만</label>
      </div>
      <div
        v-if="!onlyFavoriteToggle && !onlySharedToggle"
        class="row row-cols-1 row-cols-sm-2 row-cols-md-4 g-3"
      >
        <div v-for="(plan, index) in planList" :key="index">
          <board-card :plan="plan"></board-card>
        </div>
      </div>

      <div class="col-md-12">
        <div v-if="onlyFavoriteToggle" class="row row-cols-sm-2 row-cols-md-3 g-3">
          <div v-for="(plan, index) in onlyFavoriteList" :key="index">
            <board-card :plan="plan"></board-card>
          </div>
        </div>
      </div>
    </div>

    <div class="col-md-12">
      <div v-if="onlySharedToggle" class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
        <div v-for="(plan, index) in onlySharedList" :key="index">
          <board-card :plan="plan"></board-card>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
