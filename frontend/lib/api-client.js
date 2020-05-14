import axios from 'axios';

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

  async login(token) {
    return this
      .handleRequest({
        method: 'get',
        url: `/api/v1/auth/${token}`,
      })
      .then(res => res.data)
      .catch(this.handleError);
  }
}
