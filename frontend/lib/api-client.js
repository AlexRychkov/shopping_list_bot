import axios from 'axios';

export const INVALID_TOKEN = 'INVALID_TOKEN'

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
    console.log(error)
    if (error.response && error.response.data) {
      if (error.response.status === 403) return
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
}
