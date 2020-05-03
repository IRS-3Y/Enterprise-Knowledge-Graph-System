import axios from 'axios';
import config from '../config';

export default class DemoService {
  constructor(){
    this._baseUrl = `${config.backend.baseUrl}/demo`;
  }

  simulateLoad = async (req) => {
    let resp = await axios.post(`${this._baseUrl}/distload`, req);
    return resp.data;
  }
}