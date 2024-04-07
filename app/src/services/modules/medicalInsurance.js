import { axios } from "../axios";

function insuranceListView(searchObj) {
  return axios.get("/api/medical-insurance/list-view", {
    params: {
      insuranceCode: searchObj.insuranceCode,
      name: searchObj.name,
      city: searchObj.city,
      registrationPlace: searchObj.registrationPlace,
      isValid: searchObj.isValid,
      fiveYearsContinuous: searchObj.fiveYearsContinuous,
    },
  });
}

export { insuranceListView };
