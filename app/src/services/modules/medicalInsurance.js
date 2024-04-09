import { axios } from "../axios";

function insuranceListView(searchObj, page) {
  return axios.get("/api/medical-insurance/list-view", {
    params: {
      insuranceCode: searchObj.insuranceCode,
      name: searchObj.name,
      city: searchObj.city,
      registrationPlace: searchObj.registrationPlace,
      isValid: searchObj.isValid,
      earningOption: searchObj.earningOption,
      fiveYearsContinuous: searchObj.fiveYearsContinuous,
      page: page,
    },
  });
}

function getSalaryAPI() {
  return axios.get("/api/medical-insurance/get-salary");
}

function updateSalaryAPI(amount) {
  return axios.get("/api/medical-insurance/get-salary", {
    params: {
      amount: amount,
    },
  });
}

export { insuranceListView, getSalaryAPI, updateSalaryAPI };
