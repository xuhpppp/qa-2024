import Axios from "axios";

const instance = Axios.create({
  baseURL: "http://localhost:8080",
  responseType: "json",
  timeout: 20000,
});

const axios = instance;

export { axios };
