import axios from 'axios';

export const INVALID_TOKEN = 'INVALID_TOKEN'

export const PeriodEnum = Object.freeze({
  "DAY": 0,
  "WEEK": 1,
  "MONTH": 2,
  "YEAR": 3,
  "ALL_TIME": 4
})

export const MarkEnum = Object.freeze({
  "WAIT": 0,
  "ABSENT": 1,
  "BOUGHT": 2,
  "CANCELED": 3
})

export default class ApiClient {
  constructor() {
    this.defaultOptions = {
      baseURL: "http://localhost:8080",//config.api.baseURL,
      method: 'get',
      headers: {
        'Content-Type': 'application/json',
      },
    };
  }

  handleRequest(options) {
    return axios.request(Object.assign(this.defaultOptions, options));
  }

  handleError(error) {
    if (error.response && error.response.data) {
      throw new Error(error.response.data.message);
    }
    throw new Error('Unknown Error');
  }

  async exchToken(token) {
    return this
      .handleRequest({
        method: 'get',
        url: `/api/v1/auth/exch`,
        headers: {
          'Content-Type': 'application/json',
          'Authorization': token
        },
      })
      .then(res => res.data)
      .catch(error => {
        if (error.response && error.response.status === 403) return INVALID_TOKEN
        else this.handleError(error)
      })
  }

  async createdStatistic(token, period) {
    return this
      .handleRequest({
        method: 'get',
        url: `/api/v1/statistic/created/${period}`,
        headers: {
          'Content-Type': 'application/json',
          'Authorization': token
        },
      })
  }

  async markStatistic(token, period, mark) {
    return this
      .handleRequest({
        method: 'get',
        url: `/api/v1/statistic/total/${mark}/${period}`,
        headers: {
          'Content-Type': 'application/json',
          'Authorization': token
        },
      })
  }
}
