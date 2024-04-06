import { axios } from "../axios";

// function miniSearchBooks(searchContent) {
//   return axios.get("/products/mini-search/", {
//     params: {
//       title: searchContent,
//     },
//   });
// }

function insuranceListView(searchObj) {
  return axios.get("/medical-insurance/list-view", {
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
