import axios from 'axios';
import config from '../config';

export default class AppService {
  constructor(){
    this._baseUrl = `${config.backend.baseUrl}/app`;
  }

  getSettings = async () => {
    let resp = await axios.get(`${this._baseUrl}/settings`);
    return resp.data;
  }

  mergeSettings = async (settings) => {
    let resp = await axios.post(`${this._baseUrl}/settings`, settings);
    return resp.data;
  }

  loadSettings = async (settings) => {
    if(!settings){
      settings = await this.getSettings();
    }
    //update neo4j connection
    let host = settings.graph.host || window.location.hostname;
    let port = settings.graph.port;
    config.neovis.server_url = `bolt://${host}:${port}`;
  }
}