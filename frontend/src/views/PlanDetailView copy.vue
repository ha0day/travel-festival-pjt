<script setup>
import { useRoute } from "vue-router";
import { ref, onMounted, computed } from "vue";
import { useRouter } from "vue-router";
import api from "axios";
import PlanTimeLine from "../components/board/PlanTimeLine.vue";
import VKakaoMap from "@/components/common/VKakaoMap.vue";
import { searchStore } from "@/stores/planListStore";
import { userStore } from "@/stores/userStore";

const sstore = searchStore();
const ustore = userStore();
const router = useRouter();
const route = useRoute();

const planId = ref(route.params.id);
const plan = ref({});
const shared = ref("");
const attractions = ref([]);
const planNameEdit = ref("");
const planDetailEdit = ref(""); // 변경된 데이터
const startDateEdit = ref(""); // 변경된 데이터
const endDateEdit = ref(""); // 변경된 데이터

const isMyPlan = sstore.isMy;

const deletePlace = (index) => {
  console.log("삭제 전: ", addedPlaces.value);

  plan.value.attrInfo.splice(index, 1);
  addedPlaces.value.splice(index, 1);

  console.log("삭제 후: ", addedPlaces.value);
};

const addPlace = (place) => {
  plan.value.attrInfo.push(place);
  searchResult.value = [];
  console.log(plan.value.attrInfo);

  //지도에 전달할 배열에 장소 추가
  addedPlaces.value.push(place);
};

const markPlaceOnMap = (place) => {
  markPlace.value = place;
};

const hasSearchResult = computed(() => {
  return searchResult.value.length > 0 ? true : false;
});

const searchAttraction = async () => {
  await api
    .post(`${import.meta.env.VITE_VUE_API_URL}/attraction/search`, searchWord.value, {
      headers: { "Content-Type": "application/text" },
    })
    .then(({ data }) => {
      searchResult.value = data;
      console.log(searchResult.value);
    })
    .catch((e) => {
      console.log(e);
    });
};

// 수정
const isEdit = ref(false);
const editPlan = () => {
  if (!isEdit.value) {
    // 수정
    isEdit.value = true;
    planNameEdit.value = plan.value.planName;
    planDetailEdit.value = plan.value.planDetail;
    startDateEdit.value = plan.value.startDate;
    endDateEdit.value = plan.value.endDate;
  } else {
    // 완료
    isEdit.value = false;
    editDetail();
  }
};

const editDetail = async () => {
  await api
    .put(`${import.meta.env.VITE_VUE_API_URL}/plan`, {
      planId: planId.value,
      planName: planNameEdit.value,
      startDate: startDateEdit.value,
      endDate: endDateEdit.value,
      planDetail: planDetailEdit.value,
    })
    .then(() => {
      getDetail();
    })
    .catch((e) => {
      console.log(e);
    });
};

const getDetail = async () => {
  await api
    .get(`${import.meta.env.VITE_VUE_API_URL}/plan/${planId.value}`)
    .then(({ data }) => {
      plan.value = data;
      attractions.value = plan.value.attrInfoList;
      shared.value = plan.value.shared;
    })
    .catch((e) => {
      console.log(e);
    });
};

const deletePlan = async () => {
  await api
    .delete(`${import.meta.env.VITE_VUE_API_URL}/plan/${planId.value}`)
    .then(() => {
      router.push({ path: "/planlist", params: { planId: planId } });
    })
    .catch((e) => {
      console.log(e);
    });
};

const shareMyPlan = async () => {
  await api
    .put(`${import.meta.env.VITE_VUE_API_URL}/plan/${planId.value}`, {
      planId: planId.value,
    })
    .then(({ data }) => {
      shared.value = data;
      console.log("shared 값 ");
      console.log(shared.value);
    })
    .catch((e) => {
      console.log(e);
    });
};

getDetail();
</script>
<template>
  <div class="row g-5">
    <div class="col-md-12">
      <h3 class="pb-4 mb-4 fst-italic border-bottom">내 마음대로 여행코스!!!</h3>
      <div class="row g-5">
        <div class="col-md-12">
          <div class="card shadow-sm">
            <div class="card-body">
              <div v-if="isMyPlan">
                <button class="w-btn w-btn-blue" type="button" @click="shareMyPlan()">
                  <div v-if="shared == 0">공유하기</div>
                  <div v-if="shared == 1">공유 취소하기</div>
                </button>
              </div>

              <div class="row g-5">
                <div class="col-md-4">
                  <img
                    :src="plan.img"
                    class="mt-3 img-fluid mx-auto d-block"
                    alt="Responsive image"
                  />
                </div>
                <div class="col-md-8">
                  <h2 v-if="!isEdit" class="card-title mx-auto d-block mt-1 mb-2">
                    {{ plan.planName }}
                  </h2>
                  <input
                    v-if="isEdit"
                    type="text"
                    class="form-control"
                    id="planName"
                    :placeholder="planNameEdit"
                    v-model="planNameEdit"
                  />

                  <h6 class="card-subtitle mx-auto d-block mb-3">[{{ plan.userId }}]님</h6>
                  <figcaption v-if="!isEdit" class="blockquote-footer mt-3">
                    시작일: {{ plan.startDate }}
                  </figcaption>
                  <div>
                    <input
                      v-if="isEdit"
                      type="text"
                      class="form-control"
                      id="startDate"
                      :placeholder="startDateEdit"
                      v-model="startDateEdit"
                    />
                  </div>
                  <figcaption v-if="!isEdit" class="blockquote-footer">
                    마지막일: {{ plan.endDate }}
                  </figcaption>
                  <input
                    v-else
                    type="text"
                    class="form-control"
                    id="endDate"
                    :placeholder="endDateEdit"
                    v-model="endDateEdit"
                  />
                  <h4 class="box-title mt-5">[ 세부 내용 ]</h4>
                  <p v-if="!isEdit" class="card-text mb-5">
                    {{ plan.planDetail }}
                  </p>
                  <textarea
                    v-if="isEdit"
                    class="form-control mb-5"
                    id="planDetail"
                    rows="3"
                    :placeholder="planDetailEdit"
                    v-model="planDetailEdit"
                  ></textarea>

                  <h4>[ 태그 ]</h4>
                  <div
                    class="mb-4 row"
                    style="float: left; justify-content: space-between; display: flex"
                    v-for="(tag, index) in plan.tagList"
                    :key="index"
                  >
                    <div class="col-md-12">
                      <button type="button" class="btn btn-primary rounded-pill m-1">
                        # {{ tag.tagName }}
                      </button>
                    </div>
                  </div>
                </div>
              </div>

              <div v-if="!isEdit" class="row g-3">
                <div class="col-md-8">
                  <h5>지도</h5>
                  <VKakaoMap :attractions="attractions" />
                </div>

                <div class="col-md-4">
                  <h5>타임라인</h5>
                  <plan-time-line :attractions="attractions" />
                </div>
              </div>

              <div v-if="isEdit" class="row g-3">
                <div class="col-md-3">
                  <!--  여행지 검색 -->
                  <h5>여행지 검색</h5>
                  <!-- class="m-2 mb-3 row justify-content-center" -->
                  <div class="input-group justify-content-center">
                    <input
                      type="text"
                      class="form-control"
                      placeholder="장소를 검색하세요."
                      aria-label="장소를 검색하세요."
                      aria-describedby="button-addon2"
                      v-model="searchWord"
                    />
                    <button
                      class="btn btn-outline-secondary"
                      type="button"
                      id="button-addon2"
                      @click="searchAttraction()"
                    >
                      검색
                    </button>
                  </div>

                  <div class="justify-content-center">
                    <div
                      id="searchResult"
                      v-show="hasSearchResult"
                      class="overflow-y-scroll h-100 bg-body-tertiary p-2 rounded-2"
                      style="max-height: 800px"
                    >
                      <ul class="list-group" v-for="(place, index) in searchResult" :key="index">
                        <a
                          @click="markPlaceOnMap(place)"
                          class="list-group-item list-group-item-action"
                          aria-current="true"
                        >
                          <div class="row g-2">
                            <!-- <div class="col-md-8"> -->
                            <div>
                              <div class="time-title">{{ place.title }}</div>
                              <div>{{ place.addr1 }}</div>
                            </div>
                            <!-- </div> -->
                            <!-- <div class="col-md-4 align-items-center"> -->
                            <div @click="addPlace(place)" aria-current="true">
                              <div class="align-middle blue">여행계획에 추가</div>
                            </div>
                            <!-- </div> -->
                          </div>
                        </a>
                      </ul>
                    </div>
                  </div>
                </div>
                <div class="col-md-6">
                  <h5>지도</h5>
                  <VKakaoMapAdd
                    :markPlace="markPlace"
                    :addedPlaces="addedPlaces"
                    :reload="reload"
                    style="width: 100%"
                  />
                </div>

                <div class="col-md-3">
                  <h5>타임라인</h5>
                  <!-- 타임라인 -->
                  <div
                    id="timescroll"
                    class="overflow-y-scroll h-100 rounded-2 timeline"
                    style="max-height: 800px"
                  >
                    <div class="timeline-row" v-for="(attr, index) in plan.attrInfo" :key="index">
                      <!-- <div class="timeline-time">7:45PM<small>Dec 21</small></div> -->
                      <div class="timeline-content">
                        <!-- <i class="icon-attachment"></i> -->
                        <div class="time-title">{{ attr.title }}</div>
                        <!-- 사진 -->
                        <div class="thumbs">
                          <img
                            class="img-fluid rounded"
                            :src="attr.firstImage"
                            alt="Maxwell Admin"
                          />
                        </div>
                        <div @click="deletePlace(index)">삭제</div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <div class="m-2 p-1 row justify-content-end">
                <div v-if="isMyPlan">
                  <a
                    href="#"
                    v-if="!isEdit"
                    @click="editPlan()"
                    type="button"
                    class="btn btn-primary float-right m-2 col-1"
                  >
                    수정
                  </a>
                  <button
                    v-else
                    type="button"
                    class="btn btn-primary float-right m-2 col-1"
                    data-bs-toggle="modal"
                    data-bs-target="#editModal"
                  >
                    완료
                  </button>

                  <button
                    type="button"
                    class="btn btn-danger float-right m-2 col-1"
                    data-bs-toggle="modal"
                    data-bs-target="#deleteModal"
                  >
                    삭제
                  </button>
                </div>
              </div>

              <!-- Edit Modal -->
              <div
                class="modal fade"
                id="editModal"
                tabindex="-1"
                aria-labelledby="exampleModalLabel"
                aria-hidden="true"
              >
                <div class="modal-dialog">
                  <div class="modal-content">
                    <div class="modal-header">
                      <h1 class="modal-title fs-5" id="exampleModalLabel">수정하기</h1>
                      <button
                        type="button"
                        class="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"
                      ></button>
                    </div>
                    <div class="modal-body">정말 수정하시겠습니까?</div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                        아니요
                      </button>
                      <a
                        href="#"
                        type="button"
                        class="btn btn-primary"
                        @click="editPlan()"
                        data-bs-dismiss="modal"
                      >
                        네
                      </a>
                    </div>
                  </div>
                </div>
              </div>

              <!-- Delete Modal -->
              <div
                class="modal fade"
                id="deleteModal"
                tabindex="-1"
                aria-labelledby="exampleModalLabel"
                aria-hidden="true"
              >
                <div class="modal-dialog">
                  <div class="modal-content">
                    <div class="modal-header">
                      <h1 class="modal-title fs-5" id="exampleModalLabel">삭제하기</h1>
                      <button
                        type="button"
                        class="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"
                      ></button>
                    </div>
                    <div class="modal-body">정말 삭제하시겠습니까?</div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                        아니요
                      </button>
                      <button
                        type="button"
                        class="btn btn-primary"
                        @click="deletePlan()"
                        data-bs-dismiss="modal"
                      >
                        네
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
body {
  margin-top: 20px;
  color: #bcd0f7;
  background: #1a233a;
}

.w-btn {
  position: relative;
  border: none;
  display: inline-block;
  padding: 15px 30px;
  border-radius: 15px;
  font-family: "paybooc-Light", sans-serif;
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
  text-decoration: none;
  font-weight: 600;
  transition: 0.25s;
}
.w-btn-blue {
  background-color: #6aafe6;
  color: #d4dfe6;
}

.timeline {
  position: relative;
  /* background: #272e48; */
  /* -webkit-border-radius: 4px; */
  /* -moz-border-radius: 4px; */
  border-radius: 4px;
  /* padding: 1rem; */
  /* margin: 0 auto 1rem auto; */
  overflow: hidden;
}

.timeline-row:after {
  content: "";
  position: absolute;
  top: 0;
  right: 90%;
  margin-right: -2px;
  border-left: 2px dashed #4b546f;
  height: 100%;
  display: block;
}

.timeline-row {
  /* padding-left: 50%; */
  position: relative;
  /* margin-bottom: 30px; */
}
</style>
