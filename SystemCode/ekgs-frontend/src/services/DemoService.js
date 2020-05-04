import axios from 'axios';
import config from '../config';

export default class DemoService {
  constructor(){
    this._baseUrl = `${config.backend.baseUrl}/demo`;
  }

  getCurrentLoad = async (relation) => {
    let resp = await axios.get(`${this._baseUrl}/currentload/${relation}`);
    return resp.data;
  }

  simulateLoad = async (req) => {
    let resp = await axios.post(`${this._baseUrl}/distload`, req);
    return resp.data;
  }

  getResourceLoad = async (relation) => {
    let resp = await axios.get(`${this._baseUrl}/resourceload/${relation}`);
    return resp.data;
  }

  postResourceLoad = async (records) => {
    let resp = await axios.post(`${this._baseUrl}/resourceload`, records);
    return resp.data;
  }
}