<script setup>
import { RouterLink } from "vue-router";
</script>

<template>
  <nav class="navbar navbar-expand-lg navbar-light">
    <div class="container-fluid">
      <a class="navbar-brand" href="/"> EnjoyTrips </a>
      <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <a class="navbar-brand" href="#"> EnjoyTrips </a>
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse justify-content-end" id="navbarSupportedContent">
        <ul class="navbar-nav m-1">
          <li class="nav-item">
            <router-link to="/" class="nav-link active" aria-current="page"
              >지역별 여행지</router-link
            >
          </li>
          <li class="nav-item">
            <a href="/planlist" class="nav-link active" aria-current="page">나의여행계획</a>
          </li>
          <li class="nav-item">
            <a href="/" class="nav-link active" aria-current="page">핫플자랑하기</a>
          </li>
          <li class="nav-item">
            <a href="/" class="btn btn-sm btn-outline-secondary">여행정보공유</a>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<style scoped></style>
